package net.sf.anathema.hero.charms.display.tree;

import net.sf.anathema.character.main.magic.description.MagicDescriptionProvider;
import net.sf.anathema.hero.charms.display.coloring.CharacterColoringStrategy;
import net.sf.anathema.hero.charms.display.coloring.ConfigurableCharmDye;
import net.sf.anathema.hero.charms.display.model.CharacterCharmTypes;
import net.sf.anathema.hero.charms.display.model.CharacterGroupCollection;
import net.sf.anathema.hero.charms.display.model.CharmDisplayModel;
import net.sf.anathema.hero.charms.display.presenter.CharmDisplayPropertiesMap;
import net.sf.anathema.hero.charms.display.special.CharacterSpecialCharmPresenter;
import net.sf.anathema.hero.charms.display.special.CommonSpecialCharmList;
import net.sf.anathema.hero.charms.display.special.SpecialCharmViewBuilder;
import net.sf.anathema.hero.charms.display.special.SwingSpecialCharmViewBuilder;
import net.sf.anathema.hero.charms.display.view.CharmView;
import net.sf.anathema.hero.charms.display.view.DefaultFunctionalNodeProperties;
import net.sf.anathema.hero.charms.model.CharmIdMap;
import net.sf.anathema.hero.charms.model.CharmsModel;
import net.sf.anathema.hero.charms.model.special.SpecialCharmList;
import net.sf.anathema.lib.resources.Resources;
import net.sf.anathema.magic.description.display.ShowMagicDetailListener;
import net.sf.anathema.platform.tree.document.visualizer.TreePresentationProperties;

public class CharacterCharmTreePresenter {

  private final CharmView view;
  private final CascadePresenter cascadePresenter;

  public CharacterCharmTreePresenter(Resources resources, CharmView view, CharmDisplayModel model,
                                     TreePresentationProperties presentationProperties,
                                     CharmDisplayPropertiesMap displayPropertiesMap, CharmIdMap charmIdMap) {
    this.cascadePresenter = new CascadePresenter(resources, new CharacterCharmTreeMap(model));
    this.view = view;
    CharmsModel charmConfiguration = model.getCharmModel();
    CharacterCharmGroupChangeListener charmGroupChangeListener = new CharacterCharmGroupChangeListener(
            charmConfiguration, view.getCharmTreeRenderer(), displayPropertiesMap);
    ConfigurableCharmDye colorist = new ConfigurableCharmDye(charmGroupChangeListener,
            new CharacterColoringStrategy(presentationProperties.getColor(), view, model));
    cascadePresenter.setCharmTypes(new CharacterCharmTypes(model));
    cascadePresenter.setChangeListener(charmGroupChangeListener);
    cascadePresenter.setView(view);
    SpecialCharmViewBuilder specialViewBuilder = new SwingSpecialCharmViewBuilder(resources, charmConfiguration);
    SpecialCharmList specialCharmList = new CommonSpecialCharmList(view, specialViewBuilder);
    cascadePresenter.setSpecialPresenter(new CharacterSpecialCharmPresenter(charmGroupChangeListener, model, specialCharmList));
    cascadePresenter.setCharmDye(colorist);
    cascadePresenter.setAlienCharmPresenter(new CharacterAlienCharmPresenter(model));
    cascadePresenter.setInteractionPresenter(
            new LearnInteractionPresenter(model, view, new DefaultFunctionalNodeProperties(), colorist));
    cascadePresenter.setCharmGroups(new CharacterGroupCollection(model));
    CharacterSpecialCharmSet specialCharmSet = new CharacterSpecialCharmSet(model);
    MagicDescriptionProvider magicDescriptionProvider = model.getMagicDescriptionProvider();
    cascadePresenter.addTreeView(specialCharmSet, magicDescriptionProvider, charmIdMap);
  }

  public void initPresentation() {
    cascadePresenter.initPresentation();
  }

  @SuppressWarnings("UnusedDeclaration")
  public void addShowDetailListener(ShowMagicDetailListener listener) {
    view.addCharmInteractionListener(new ShowDetailInteractionListener(listener));
  }
}
