package net.sf.anathema.equipment.editor.presenter;

import net.sf.anathema.equipment.editor.model.IEquipmentDatabaseManagement;
import net.sf.anathema.library.model.Condition;

public class DirtyEquipmentCondition implements Condition
{
	private IEquipmentDatabaseManagement model;
	
	public DirtyEquipmentCondition (IEquipmentDatabaseManagement model)
	{
		this.model = model;
	}
	
	@Override
	public boolean isFulfilled ()
	{
		return model.getTemplateEditModel ().isDirty ();
	}
}
