package net.sf.anathema.hero.charms.model;

import com.google.common.base.Functions;
import net.sf.anathema.charm.data.reference.CategoryReference;
import net.sf.anathema.charm.data.reference.CharmName;
import net.sf.anathema.charm.data.reference.TreeReference;
import net.sf.anathema.charm.old.CharmAttributeList;
import net.sf.anathema.magic.attribute.MagicAttribute;
import net.sf.anathema.hero.charms.advance.creation.MagicCreationCostEvaluator;
import net.sf.anathema.hero.charms.compiler.CharmCache;
import net.sf.anathema.hero.charms.compiler.CharmProvider;
import net.sf.anathema.hero.charms.display.special.CharmSpecialistImpl;
import net.sf.anathema.hero.charms.model.context.CreationCharmLearnStrategy;
import net.sf.anathema.hero.charms.model.context.ExperiencedCharmLearnStrategy;
import net.sf.anathema.hero.charms.model.context.ProxyCharmLearnStrategy;
import net.sf.anathema.hero.charms.model.favored.FavoredChecker;
import net.sf.anathema.hero.charms.model.favored.IsFavoredCharm;
import net.sf.anathema.hero.charms.model.favored.IsFavoredMagic;
import net.sf.anathema.hero.charms.model.learn.CharmLearnAdapter;
import net.sf.anathema.hero.charms.model.learn.CharmLearner;
import net.sf.anathema.hero.charms.model.learn.ICharmLearnListener;
import net.sf.anathema.hero.charms.model.learn.ILearningCharmGroupContainer;
import net.sf.anathema.hero.charms.model.learn.LearningCharmTree;
import net.sf.anathema.hero.charms.model.learn.LearningCharmTreeImpl;
import net.sf.anathema.hero.charms.model.learn.MagicLearner;
import net.sf.anathema.hero.charms.model.learn.MartialArtsLearnModel;
import net.sf.anathema.hero.charms.model.learn.MartialArtsLearnModelImpl;
import net.sf.anathema.hero.charms.model.options.CharmOptions;
import net.sf.anathema.hero.charms.model.options.CharmOptionsImpl;
import net.sf.anathema.hero.charms.model.options.CharmTreeCategory;
import net.sf.anathema.hero.charms.model.rules.CharmsRules;
import net.sf.anathema.hero.charms.model.rules.CharmsRulesImpl;
import net.sf.anathema.hero.charms.model.special.CharmSpecialsModel;
import net.sf.anathema.hero.charms.model.special.ISpecialCharm;
import net.sf.anathema.hero.charms.model.special.ISpecialCharmManager;
import net.sf.anathema.hero.charms.model.special.SpecialCharmManager;
import net.sf.anathema.hero.charms.model.special.prerequisite.PrerequisiteModifyingCharms;
import net.sf.anathema.hero.charms.sheet.content.IMagicStats;
import net.sf.anathema.hero.charms.sheet.content.PrintCharmsProvider;
import net.sf.anathema.hero.charms.template.model.CharmsTemplate;
import net.sf.anathema.hero.concept.CasteType;
import net.sf.anathema.hero.concept.HeroConceptFetcher;
import net.sf.anathema.hero.experience.ExperienceModel;
import net.sf.anathema.hero.experience.ExperienceModelFetcher;
import net.sf.anathema.hero.framework.HeroEnvironment;
import net.sf.anathema.magic.Magic;
import net.sf.anathema.hero.magic.charm.Charm;
import net.sf.anathema.charm.data.martial.MartialArtsLevel;
import net.sf.anathema.hero.magic.charm.prerequisite.CharmPrerequisite;
import net.sf.anathema.hero.model.Hero;
import net.sf.anathema.hero.model.change.ChangeAnnouncer;
import net.sf.anathema.hero.spiritual.model.pool.EssencePoolModel;
import net.sf.anathema.hero.spiritual.model.pool.EssencePoolModelFetcher;
import net.sf.anathema.hero.traits.model.TraitModel;
import net.sf.anathema.hero.traits.model.TraitModelFetcher;
import net.sf.anathema.lib.control.ChangeListener;
import net.sf.anathema.lib.util.Identifier;
import org.jmock.example.announcer.Announcer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.text.MessageFormat.format;
import static net.sf.anathema.hero.charms.model.learn.prerequisites.IsAutoSatisfiable.isAutoSatisfiable;
import static net.sf.anathema.hero.charms.model.learn.prerequisites.IsSatisfied.isSatisfied;
import static net.sf.anathema.charm.data.martial.MartialArtsLevel.Sidereal;
import static net.sf.anathema.hero.magic.charm.martial.MartialArtsUtilities.hasLevel;
import static net.sf.anathema.hero.magic.charm.martial.MartialArtsUtilities.isFormMagic;
import static net.sf.anathema.hero.magic.charm.martial.MartialArtsUtilities.isMartialArts;

public class CharmsModelImpl implements CharmsModel {

  private final ProxyCharmLearnStrategy charmLearnStrategy = new ProxyCharmLearnStrategy(
          new CreationCharmLearnStrategy());
  private final CharmsRules charmsRules;
  private ISpecialCharmManager manager;
  private ILearningCharmGroupContainer learningCharmGroupContainer = this::getGroup;
  private final Map<CategoryReference, LearningCharmTree[]> learnTreesByCategory = new HashMap<>();
  private final Announcer<ChangeListener> control = Announcer.to(ChangeListener.class);
  private ExperienceModel experience;
  private TraitModel traits;
  private PrerequisiteModifyingCharms prerequisiteModifyingCharms;
  private Hero hero;
  private CharmOptionsImpl options;
  private final List<PrintMagicProvider> printMagicProviders = new ArrayList<>();
  private final List<MagicLearner> magicLearners = new ArrayList<>();
  private final IsFavoredMagic isFavoredMagic = new IsFavoredMagic();

  public CharmsModelImpl(CharmsTemplate template) {
    this.charmsRules = new CharmsRulesImpl(template);
  }

  @Override
  public Identifier getId() {
    return CharmsModel.ID;
  }

  @Override
  public void initialize(HeroEnvironment environment, Hero hero) {
    isFavoredMagic.add(new IsFavoredCharm(hero));
    CharmSpecialistImpl specialist = new CharmSpecialistImpl(hero);
    this.experience = ExperienceModelFetcher.fetch(hero);
    this.traits = TraitModelFetcher.fetch(hero);
    this.hero = hero;
    CharmProvider provider = environment.getDataSet(CharmCache.class);
    this.options = new CharmOptionsImpl(provider, charmsRules, hero);
    this.manager = new SpecialCharmManager(specialist, hero, this);
    initializeCharmTrees();
    initSpecialCharmConfigurations();
    learnCompulsiveCharms();
    addOverdrivePools(hero);
    addPrintProvider(new PrintCharmsProvider(hero));
    addLearnProvider(new CharmLearner(this));
  }

  private void initializeCharmTrees() {
    for (CharmTreeCategory category : options) {
      LearningCharmTree[] learningCharmTrees = createTrees(category.getAllCharmTrees());
      learnTreesByCategory.put(category.getReference(), learningCharmTrees);
    }
  }

  private void addOverdrivePools(Hero hero) {
    EssencePoolModel poolModel = EssencePoolModelFetcher.fetch(hero);
    if (poolModel == null) {
      return;
    }
  }

  @Override
  public void initializeListening(ChangeAnnouncer announcer) {
    for (LearningCharmTree group : getAllGroups()) {
      group.addCharmLearnListener(new CharmLearnAdapter() {
        @Override
        public void charmForgotten(Charm charm) {
          control.announce().changeOccurred();
        }

        @Override
        public void charmLearned(Charm charm) {
          control.announce().changeOccurred();
        }
      });
    }
    this.experience.addStateChangeListener(() -> {
      if (experience.isExperienced()) {
        charmLearnStrategy.setStrategy(new ExperiencedCharmLearnStrategy());
      } else {
        charmLearnStrategy.setStrategy(new CreationCharmLearnStrategy());
      }
    });
    announcer.addListener(flavor -> {
      verifyCharms();
      control.announce().changeOccurred();
    });
    addCharmLearnListener(new CharacterChangeCharmListener(announcer));
  }

  private void learnCompulsiveCharms() {
    charmsRules.forAllCompulsiveCharms(charmName -> {
      Charm charm = getCharmById(charmName);
      getGroup(charm).learnCharm(charm, false);
    });
  }

  @Override
  public void addCharmLearnListener(ICharmLearnListener listener) {
    for (LearningCharmTree group : getAllGroups()) {
      group.addCharmLearnListener(listener);
    }
  }

  private void initSpecialCharmConfigurations() {
    CharmMap charmMap = options.getCharmIdMap();
    ISpecialCharm[] specialCharms = options.getSpecialCharms();
    for (ISpecialCharm specialCharm : specialCharms) {
      Charm charm = charmMap.getCharmById(specialCharm.getCharmName());
      if (charm == null) {
        continue;
      }
      LearningCharmTree group = getLearningTree(charm.getTreeReference());
      manager.registerSpecialCharmConfiguration(specialCharm, charm, group);
    }
  }

  private LearningCharmTree[] createTrees(CharmTree[] charmGroups) {
    List<LearningCharmTree> newGroups = new ArrayList<>();
    for (CharmTree charmGroup : charmGroups) {
      LearningCharmTree group = new LearningCharmTreeImpl(charmLearnStrategy, charmGroup, this,
              learningCharmGroupContainer);
      newGroups.add(group);
    }
    return newGroups.toArray(new LearningCharmTree[newGroups.size()]);
  }

  @Override
  public LearningCharmTree[] getAllGroups() {
    List<LearningCharmTree> allGroups = new ArrayList<>();
    for (LearningCharmTree[] groups : learnTreesByCategory.values()) {
      allGroups.addAll(Arrays.asList(groups));
    }
    return allGroups.toArray(new LearningCharmTree[allGroups.size()]);
  }

  @Override
  public Charm getCharmById(CharmName charmId) {
    Charm charm = options.getCharmIdMap().getCharmById(charmId);
    if (charm != null) {
      return charm;
    }
    throw new IllegalArgumentException("No charm found for id \"" + charmId.text + "\"");
  }

  @Override
  public LearningCharmTree[] getCharmGroups(CategoryReference category) {
    return getLearningCharmTrees(category);
  }

  private LearningCharmTree[] getLearningCharmTrees(CategoryReference category) {
    return Functions.forMap(learnTreesByCategory, new LearningCharmTree[0]).apply(category);
  }

  @Override
  public Charm[] getLearnedCharms(boolean experienced) {
    List<Charm> allLearnedCharms = new ArrayList<>();
    for (LearningCharmTree group : getAllGroups()) {
      Collections.addAll(allLearnedCharms, group.getCreationLearnedCharms());
      if (experienced) {
        Collections.addAll(allLearnedCharms, group.getExperienceLearnedCharms());
      }
    }
    return allLearnedCharms.toArray(new Charm[allLearnedCharms.size()]);
  }

  @Override
  public CharmSpecialsModel getCharmSpecialsModel(Charm charm) {
    return manager.getSpecialCharmConfiguration(charm);
  }

  @Override
  public void forgetAllAlienCharms() {
    for (LearningCharmTree[] allLearnTrees : learnTreesByCategory.values()) {
      for (LearningCharmTree learnTree : allLearnTrees) {
        if (charmsRules.isAlienCategory(learnTree.getReference().category)) {
          learnTree.forgetAll();
        } else {
          learnTree.forgetExclusives();
        }
      }
    }
  }

  private void verifyCharms() {
    if (!hero.isFullyLoaded()) {
      return;
    }
    List<Charm> charmsToUnlearn = new ArrayList<>();
    for (Charm charm : this.getLearnedCharms(true)) {
      boolean prerequisitesForCharmAreNoLongerMet = !isLearnable(charm);
      boolean charmCanBeUnlearned = isUnlearnable(charm);
      if (prerequisitesForCharmAreNoLongerMet && charmCanBeUnlearned) {
        charmsToUnlearn.add(charm);
      }
    }
    for (Charm charm : charmsToUnlearn) {
      LearningCharmTree group = learningCharmGroupContainer.getLearningCharmGroup(charm);
      boolean learnedAtCreation = group.isLearned(charm, false);
      boolean learnedWithExperience = !learnedAtCreation;
      group.forgetCharm(charm, learnedWithExperience);
    }
  }

  @Override
  public void addLearnableListener(ChangeListener listener) {
    control.addListener(listener);
  }

  @Override
  public final boolean isLearnable(Charm charm) {
    if (isAlienCharm(charm)) {
      CasteType casteType = HeroConceptFetcher.fetch(hero).getCaste().getType();
      if (!(charmsRules.isAllowedAlienCharms(casteType))) {
        return false;
      }
      if (charm.hasAttribute(CharmAttributeList.NATIVE)) {
        return false;
      }
    }
    if (isMartialArts(charm)) {
      boolean isSiderealFormCharm = isFormMagic(charm) && hasLevel(Sidereal, charm);
      MartialArtsLearnModel martialArtsConfiguration = new MartialArtsLearnModelImpl(this, experience);
      if (isSiderealFormCharm && !martialArtsConfiguration.isAnyCelestialStyleCompleted()) {
        return false;
      }
      if (!charmsRules.getMartialArtsRules().isCharmAllowed(charm, isExperienced())) {
        return false;
      }
    }
    for (CharmPrerequisite prerequisite : charm.getPrerequisites().getCharmPrerequisites()) {
      if (!isSatisfied(prerequisite, this) && !isAutoSatisfiable(prerequisite, this)) {
        return false;
      }
    }
    CharmTraitRequirementChecker traitRequirementChecker = new CharmTraitRequirementChecker(
            getPrerequisiteModifyingCharms(), traits, this);
    return traitRequirementChecker.areTraitMinimumsSatisfied(charm);
  }

  @Override
  public boolean hasLearnedThresholdCharmsWithKeyword(MagicAttribute attribute, int threshold) {
    Charm[] learnedCharms = getLearnedCharms(true);
    int count = 0;
    for (Charm charm : learnedCharms) {
      if (charm.hasAttribute(attribute)) {
        count++;
      }
      if (count >= threshold) {
        return true;
      }
    }
    return false;
  }

  private boolean isExperienced() {
    return ExperienceModelFetcher.fetch(hero).isExperienced();
  }

  private PrerequisiteModifyingCharms getPrerequisiteModifyingCharms() {
    if (prerequisiteModifyingCharms == null) {
      this.prerequisiteModifyingCharms = new PrerequisiteModifyingCharms(options.getSpecialCharms());
    }
    return prerequisiteModifyingCharms;
  }

  @Override
  public boolean isLearned(CharmName charmId) {
    Charm charm = getCharmById(charmId);
    return charm != null && isLearned(charm);
  }

  public final boolean isUnlearnable(Charm charm) {
    LearningCharmTree group = getGroup(charm);
    return group.isForgettable(charm);
  }

  @Override
  public final boolean isLearned(Charm charm) {
    LearningCharmTree group = getGroup(charm);
    return group != null && group.isLearned(charm);
  }

  private LearningCharmTree getLearningTree(TreeReference reference) {
    LearningCharmTree[] charmTrees = getLearningCharmTrees(reference.category);
    for (LearningCharmTree tree : charmTrees) {
      if (tree.getReference().name.equals(reference.name)) {
        return tree;
      }
    }
    String pattern = "No charm tree defined for id: {0} in {1}.";
    throw new IllegalArgumentException(format(pattern, reference.name.text, reference.category.text));
  }

  @Override
  public final LearningCharmTree getGroup(Charm charm) {
    return getLearningTree(charm.getTreeReference());
  }


  @Override
  public void addFavoredChecker(FavoredChecker favoredChecker) {
    isFavoredMagic.add(favoredChecker);
  }

  @Override
  public void addPrintProvider(PrintMagicProvider provider) {
    printMagicProviders.add(provider);
  }

  @Override
  public void addPrintMagic(List<IMagicStats> printMagic) {
    for (PrintMagicProvider provider : printMagicProviders) {
      provider.addPrintMagic(printMagic);
    }
  }

  @Override
  public void addLearnProvider(MagicLearner provider) {
    magicLearners.add(provider);
  }

  @Override
  public MagicCreationCostEvaluator getMagicCostEvaluator() {
    return new MagicCreationCostEvaluator(magicLearners);
  }

  @Override
  public boolean isAlienCharm(Charm charm) {
    return charmsRules.isAlienCharm(charm);
  }

  @Override
  public boolean isFavoredMagic(Magic magic) {
    return isFavoredMagic.isFavored(magic);
  }

  @Override
  public final boolean isCompulsiveCharm(Charm charm) {
    return charmsRules.isCompulsiveCharm(charm);
  }

  public MartialArtsLevel getStandardMartialArtsLevel() {
    return charmsRules.getMartialArtsRules().getStandardLevel();
  }

  @Override
  public CharmOptions getOptions() {
    return options;
  }
}