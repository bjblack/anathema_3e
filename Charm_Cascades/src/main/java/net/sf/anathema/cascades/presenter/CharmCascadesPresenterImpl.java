package net.sf.anathema.cascades.presenter;

import net.sf.anathema.hero.charms.compiler.CharmCache;
import net.sf.anathema.hero.charms.display.coloring.CharmBorderColorEvaluator;
import net.sf.anathema.hero.charms.display.coloring.ConfigurableCharmDye;
import net.sf.anathema.hero.charms.display.presenter.CharmDisplayPropertiesMap;
import net.sf.anathema.hero.charms.display.tree.CascadePresenter;
import net.sf.anathema.hero.charms.display.view.CharmView;
import net.sf.anathema.hero.environment.HeroEnvironment;
import net.sf.anathema.library.initialization.ObjectFactory;
import net.sf.anathema.magic.description.model.MagicDescriptionProvider;
import net.sf.anathema.platform.environment.Environment;

public class CharmCascadesPresenterImpl
{
	private final CascadePresenter cascadePresenter;
	private final CharmCache cache;
	private final ObjectFactory objectFactory;
	private final CharmView view;
	private final CharmTreeMap identifierMap;
	
	public CharmCascadesPresenterImpl (Environment environment, HeroEnvironment heroEnvironment, CharmView view,
	MagicDescriptionProvider magicDescriptionProvider,
	CharmTreeMap identifierMap)
	{
		this.view = view;
		this.identifierMap = identifierMap;
		this.cache = heroEnvironment.getDataSet (CharmCache.class);
		this.cascadePresenter = new CascadePresenter (environment, cache, magicDescriptionProvider);
		this.objectFactory = environment.getObjectFactory ();
	}
	
	public void initPresentation ()
	{
		CascadeSpecialCharmSet specialCharmSet = new CascadeSpecialCharmSet (cache);
		CharmDisplayPropertiesMap charmDisplayPropertiesMap = new CharmDisplayPropertiesMap (objectFactory);
		CascadeCharmGroupChangeListener selectionListener = new CascadeCharmGroupChangeListener (specialCharmSet, charmDisplayPropertiesMap);
		cascadePresenter.setCharmTreeCollectionMap (identifierMap);
		cascadePresenter.setCategoryCollection (new CascadeCategoryCollection (cache));
		cascadePresenter.setChangeListener (selectionListener);
		cascadePresenter.setView (view);
		cascadePresenter.setCharmDye (new ConfigurableCharmDye (selectionListener, new CascadeColoringStrategy (new CharmBorderColorEvaluator (objectFactory))));
		cascadePresenter.setCharmTrees (new CascadeGroupCollection (cache, identifierMap));
		cascadePresenter.setSpecialCharmSet (specialCharmSet);
		cascadePresenter.initPresentation ();
	}
}
