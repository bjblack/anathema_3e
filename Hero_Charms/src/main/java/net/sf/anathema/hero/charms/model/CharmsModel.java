package net.sf.anathema.hero.charms.model;

import net.sf.anathema.charm.data.reference.CategoryReference;
import net.sf.anathema.hero.charms.advance.creation.MagicCreationCostEvaluator;
import net.sf.anathema.hero.charms.model.learn.IExtendedCharmLearnableArbitrator;
import net.sf.anathema.hero.charms.model.learn.LearningCharmTree;
import net.sf.anathema.hero.charms.model.learn.MagicLearner;
import net.sf.anathema.hero.charms.model.options.CharmOptions;
import net.sf.anathema.hero.charms.model.special.CharmSpecialsModel;
import net.sf.anathema.hero.charms.model.special.SpecialCharmLearnArbitrator;
import net.sf.anathema.hero.magic.charm.Charm;
import net.sf.anathema.hero.magic.charm.martial.MartialArtsLevel;
import net.sf.anathema.hero.model.HeroModel;
import net.sf.anathema.lib.control.ChangeListener;
import net.sf.anathema.lib.util.Identifier;
import net.sf.anathema.lib.util.SimpleIdentifier;

public interface CharmsModel extends HeroModel, IExtendedCharmLearnableArbitrator, CharmIdMap,
        SpecialCharmLearnArbitrator, PrintMagicProvider {

  Identifier ID = new SimpleIdentifier("Charms");

  void addPrintProvider(PrintMagicProvider provider);

  void addLearnableListener(ChangeListener listener);

  void addLearnProvider(MagicLearner provider);

  LearningCharmTree getGroup(Charm charm);

  LearningCharmTree[] getAllGroups();

  LearningCharmTree[] getCharmGroups(CategoryReference type);

  Charm[] getLearnedCharms(boolean experienced);

  void forgetAllAlienCharms();

  boolean isAlienCharm(Charm charm);

  CharmSpecialsModel getCharmSpecialsModel(Charm charm);

  MartialArtsLevel getStandardMartialArtsLevel();

  MagicCreationCostEvaluator getMagicCostEvaluator();

  CharmOptions getOptions();
}