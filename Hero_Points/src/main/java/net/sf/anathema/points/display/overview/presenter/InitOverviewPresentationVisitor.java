package net.sf.anathema.points.display.overview.presenter;

import net.sf.anathema.hero.individual.overview.OverviewCategory;
import net.sf.anathema.library.resources.Resources;
import net.sf.anathema.library.view.LabelledAllotmentView;
import net.sf.anathema.library.view.StyledValueView;
import net.sf.anathema.points.model.overview.IOverviewModel;
import net.sf.anathema.points.model.overview.IOverviewModelVisitor;
import net.sf.anathema.points.model.overview.IValueModel;
import net.sf.anathema.points.model.overview.SpendingModel;

public class InitOverviewPresentationVisitor implements IOverviewModelVisitor
{
	private Resources resources;
	private OverviewUpdater updater;
	private OverviewCategories categories;
	
	public InitOverviewPresentationVisitor (Resources resources, OverviewUpdater updater, OverviewCategories categories)
	{
		this.resources = resources;
		this.updater = updater;
		this.categories = categories;
	}
	
	@Override
	public void visitStringValueModel (IValueModel<String> visitedModel)
	{
		OverviewCategory overviewCategory = getCategory (visitedModel);
		StyledValueView<String> view = overviewCategory.addStringValueView (getString (visitedModel.getId ()));
		updater.add (new StringSubPresenter (visitedModel, view, resources));
	}
	
	@Override
	public void visitIntegerValueModel (IValueModel<Integer> visitedModel)
	{
		OverviewCategory overviewCategory = getCategory (visitedModel);
		StyledValueView<Integer> valueView = overviewCategory.addIntegerValueView (getLabelString (visitedModel), 2);
		updater.add (new ValueSubPresenter (visitedModel, valueView));
	}
	
	@Override
	public void visitAllotmentModel (SpendingModel visitedModel)
	{
		OverviewCategory overviewCategory = getCategory (visitedModel);
		LabelledAllotmentView valueView = overviewCategory.addAllotmentView (getLabelString (visitedModel), 2);
		updater.add (new AllotmentSubPresenter (visitedModel, valueView));
	}
	
	private OverviewCategory getCategory (IOverviewModel visitedModel)
	{
		return categories.get (visitedModel.getCategoryId ());
	}
	
	private String getLabelString (IOverviewModel visitedModel)
	{
		return getString ("Overview.Creation." + visitedModel.getCategoryId () + "." + visitedModel.getId ());
	}
	
	private String getString (String string)
	{
		return resources.getString (string);
	}
}
