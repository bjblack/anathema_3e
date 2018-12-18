package net.sf.anathema.equipment.editor.stats.presenter;

import net.sf.anathema.equipment.editor.stats.model.IIntValueModel;
import net.sf.anathema.library.fx.configurableview.IIntegerSpinner;

public class IntValuePresentation
{
	public void initPresentation (IIntegerSpinner integerSpinner, IIntValueModel intValueModel)
	{
		integerSpinner.setValue (intValueModel.getValue ());
		integerSpinner.setMinimum (intValueModel.getMinimum ());
		integerSpinner.setMaximum (intValueModel.getMaximum ());
		intValueModel.addIntValueChangeListener (integerSpinner::setValue);
		integerSpinner.addChangeListener (intValueModel::setValue);
	}
}
