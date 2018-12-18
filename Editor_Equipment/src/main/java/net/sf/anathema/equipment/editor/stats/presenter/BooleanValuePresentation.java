package net.sf.anathema.equipment.editor.stats.presenter;

import net.sf.anathema.library.interaction.model.ToggleTool;
import net.sf.anathema.library.model.BooleanValueModel;
import net.sf.anathema.library.view.BooleanView;

public class BooleanValuePresentation
{
	public void initPresentation (ToggleTool tool, BooleanValueModel model)
	{
		tool.setCommand ( () -> model.setValue (!model.getValue ()));
		model.addChangeListener (newValue -> BooleanValuePresentation.this.selectBasedOnModelState (newValue, tool));
		selectBasedOnModelState (model.getValue (), tool);
	}
	
	private void selectBasedOnModelState (boolean newValue, ToggleTool tool)
	{
		if (newValue)
		{
			tool.select ();
		}
		else
		{
			tool.deselect ();
		}
	}
	
	public void initPresentation (BooleanView view, BooleanValueModel model)
	{
		view.addChangeListener (model::setValue);
		model.addChangeListener (view::setSelected);
		view.setSelected (model.getValue ());
	}
}
