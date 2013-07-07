package net.sf.anathema.cascades.presenter;

import net.sf.anathema.cascades.module.ICascadeViewFactory;
import net.sf.anathema.cascades.presenter.view.ICascadeView;
import net.sf.anathema.character.main.framework.HeroEnvironment;
import net.sf.anathema.character.main.magic.parser.charms.ICharmCache;
import net.sf.anathema.character.main.magic.model.charmtree.GroupCharmTree;
import net.sf.anathema.character.main.magic.description.MagicDescriptionProvider;
import net.sf.anathema.character.main.template.ITemplateRegistry;
import net.sf.anathema.character.main.type.CharacterTypes;
import net.sf.anathema.hero.magic.display.tree.AbstractCascadePresenter;
import net.sf.anathema.hero.magic.display.coloring.CharmDye;
import net.sf.anathema.hero.magic.display.coloring.ConfigurableCharmDye;
import net.sf.anathema.character.main.magic.display.view.charmtree.CharmDisplayPropertiesMap;
import net.sf.anathema.character.main.magic.display.view.charmtree.DefaultNodeProperties;
import net.sf.anathema.lib.resources.Resources;
import net.sf.anathema.lib.util.Identifier;

public class CascadePresenter extends AbstractCascadePresenter implements ICascadePresenter {

  private final CharmTreeIdentificateMap treeIdentificateMap = new CharmTreeIdentificateMap();

  public CascadePresenter(Resources resources, HeroEnvironment generics, ICascadeViewFactory factory,
                          MagicDescriptionProvider magicDescriptionProvider) {
    super(resources);
    ICharmCache cache = generics.getDataSet(ICharmCache.class);
    CascadeCharmTreeViewProperties viewProperties =
            new CascadeCharmTreeViewProperties(resources, magicDescriptionProvider, generics, cache, treeIdentificateMap);
    DefaultNodeProperties nodeProperties = new DefaultNodeProperties(resources, viewProperties, viewProperties);
    ICascadeView view = factory.createCascadeView(viewProperties, nodeProperties);
    view.initGui(viewProperties, nodeProperties);
    ITemplateRegistry templateRegistry = generics.getTemplateRegistry();
    CascadeCharmGroupChangeListener selectionListener =
            new CascadeCharmGroupChangeListener(view, viewProperties, new CharmDisplayPropertiesMap(templateRegistry));
    CharacterTypes characterTypes = generics.getCharacterTypes();
    setCharmTypes(new CascadeCharmTypes(characterTypes, templateRegistry));
    setChangeListener(selectionListener);
    setView(view);
    CharmDye dye = new ConfigurableCharmDye(selectionListener, new CascadeColoringStrategy(view));
    setCharmDye(dye);
    setCharmGroups(new CascadeGroupCollection(characterTypes, templateRegistry, treeIdentificateMap));
  }

  @Override
  protected GroupCharmTree getCharmTree(Identifier type) {
    return treeIdentificateMap.get(type);
  }
}