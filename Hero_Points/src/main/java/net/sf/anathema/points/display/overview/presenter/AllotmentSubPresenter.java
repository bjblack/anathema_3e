package net.sf.anathema.points.display.overview.presenter;

import net.sf.anathema.library.view.LabelledAllotmentView;
import net.sf.anathema.points.model.overview.SpendingModel;

public class AllotmentSubPresenter implements IOverviewSubPresenter
{
	private final LabelledAllotmentView view;
	private final SpendingModel model;
	private final FontParameterSetter setter;
	
	public AllotmentSubPresenter (SpendingModel model, LabelledAllotmentView view)
	{
		this.model = model;
		this.view = view;
		this.setter = new FontParameterSetter (model, view);
	}
	
	@Override
	public void update ()
	{
		view.setValue (model.getValue ());
		view.setAllotment (model.getAllotment ());
		setter.setFontParameters ();
	}
}
