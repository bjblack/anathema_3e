package net.sf.anathema.points.display.overview.presenter;

import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.individual.overview.OverviewCategory;
import net.sf.anathema.library.message.MessageToken;
import net.sf.anathema.library.resources.Resources;
import net.sf.anathema.points.display.overview.view.CategorizedOverview;
import net.sf.anathema.points.model.BonusPointManagement;
import net.sf.anathema.points.model.PointModelFetcher;
import net.sf.anathema.points.model.overview.IOverviewModel;
import net.sf.anathema.points.model.overview.WeightedCategory;

public class CreationOverviewPresenter
{
	private final Resources resources;
	private final CategorizedOverview view;
	private final Hero hero;
	private MessageToken token;
	private final BonusPointManagement management;
	private final OverviewUpdater updater = new OverviewUpdater ();
	private final MappedOverviewCategories categoriesById = new MappedOverviewCategories ();
	
	public CreationOverviewPresenter (Resources resources, Hero hero, CategorizedOverview overviewView, BonusPointManagement management, MessageToken token)
	{
		this.management = management;
		this.resources = resources;
		this.hero = hero;
		this.token = token;
		hero.getChangeAnnouncer ().addListener (flavor -> updateOverview ());
		this.view = overviewView;
	}
	
	public void initPresentation ()
	{
		initCategories ();
		initModels ();
		updateOverview ();
	}
	
	private void initCategories ()
	{
		initCategories (PointModelFetcher.fetch (hero).getBonusCategories ());
	}
	
	private void initCategories (Iterable<WeightedCategory> bonusCategories)
	{
		for (WeightedCategory category : bonusCategories)
		{
			initCategory (category.getId ());
		}
	}
	
	private void initCategory (String categoryId)
	{
		OverviewCategory category = categoriesById.get (categoryId);
		if (category == null)
		{
			category = view.addOverviewCategory (getString ("Overview.Creation.Category." + categoryId));
			categoriesById.put (categoryId, category);
		}
	}
	
	private void initModels ()
	{
		for (IOverviewModel model : PointModelFetcher.fetch (hero).getBonusOverviewModels ())
		{
			model.accept (new InitOverviewPresentationVisitor (resources, updater, categoriesById));
		}
		updater.add (new OverviewBonusPointsPresenter (resources,management.getTotalModel (), token, hero));
	}
	
	private void updateOverview ()
	{
		this.management.recalculate ();
		this.updater.update ();
	}
	
	private String getString (String string)
	{
		return resources.getString (string);
	}
	
	public void refresh ()
	{
		updateOverview ();
	}
}
