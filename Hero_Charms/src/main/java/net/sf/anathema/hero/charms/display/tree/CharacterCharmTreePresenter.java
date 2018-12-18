package net.sf.anathema.hero.charms.display.tree;

import net.sf.anathema.hero.charms.display.coloring.CharacterColoringStrategy;
import net.sf.anathema.hero.charms.display.coloring.ConfigurableCharmDye;
import net.sf.anathema.hero.charms.display.model.CharacterCategoryCollection;
import net.sf.anathema.hero.charms.display.model.CharacterGroupCollection;
import net.sf.anathema.hero.charms.display.model.CharmDisplayModel;
import net.sf.anathema.hero.charms.display.special.AgnosticSpecialCharmViewBuilder;
import net.sf.anathema.hero.charms.display.special.CharacterSpecialCharmPresenter;
import net.sf.anathema.hero.charms.display.special.CommonSpecialCharmList;
import net.sf.anathema.hero.charms.display.special.SpecialCharmViewBuilder;
import net.sf.anathema.hero.charms.display.view.CharmView;
import net.sf.anathema.hero.charms.display.view.DefaultFunctionalNodeProperties;
import net.sf.anathema.hero.charms.model.CharmMap;
import net.sf.anathema.hero.charms.model.CharmsModel;
import net.sf.anathema.hero.charms.model.special.SpecialCharmList;
import net.sf.anathema.library.resources.Resources;
import net.sf.anathema.magic.description.model.MagicDescriptionProvider;
import net.sf.anathema.platform.tree.document.visualizer.TreePresentationProperties;

public class CharacterCharmTreePresenter
{
	private final CharmView view;
	private final CascadePresenter cascadePresenter;
	private final Resources resources;
	private CharmDisplayModel model;
	private TreePresentationProperties presentationProperties;
	
	public CharacterCharmTreePresenter (Resources resources, CharmView view, CharmDisplayModel model,
	TreePresentationProperties presentationProperties,
	CharmMap charmMap,
	MagicDescriptionProvider magicDescriptionProvider)
	{
		this.resources = resources;
		this.model = model;
		this.presentationProperties = presentationProperties;
		this.cascadePresenter = new CascadePresenter (resources, charmMap, magicDescriptionProvider);
		this.view = view;
	}
	
	public void initPresentation ()
	{
		CharmsModel charmsModel = model.getCharmModel ();
		CharacterCharmGroupChangeListener charmGroupChangeListener = new CharacterCharmGroupChangeListener (
		charmsModel.getOptions ());
		ConfigurableCharmDye colorist = new ConfigurableCharmDye (charmGroupChangeListener,
		new CharacterColoringStrategy (presentationProperties.getColor (), model));
		cascadePresenter.setCharmTreeCollectionMap (new CharacterCharmTreeMap (model));
		cascadePresenter.setCategoryCollection (new CharacterCategoryCollection (model));
		cascadePresenter.setChangeListener (charmGroupChangeListener);
		cascadePresenter.setView (view);
		SpecialCharmViewBuilder specialViewBuilder = new AgnosticSpecialCharmViewBuilder (resources, charmsModel, view);
		SpecialCharmList specialCharmList = new CommonSpecialCharmList (specialViewBuilder);
		cascadePresenter.setSpecialPresenter (
		new CharacterSpecialCharmPresenter (charmGroupChangeListener, model, specialCharmList));
		cascadePresenter.setCharmDye (colorist);
		cascadePresenter.setAlienCharmPresenter (new CharacterAlienCharmPresenter (model));
		cascadePresenter.setInteractionPresenter (
		new LearnInteractionPresenter (model, new DefaultFunctionalNodeProperties (), colorist));
		cascadePresenter.setCharmTrees (new CharacterGroupCollection (model));
		cascadePresenter.setSpecialCharmSet (new CharacterSpecialCharmSet (model));
		cascadePresenter.initPresentation ();
	}
}
