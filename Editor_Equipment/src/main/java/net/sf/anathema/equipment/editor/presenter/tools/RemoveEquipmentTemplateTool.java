package net.sf.anathema.equipment.editor.presenter.tools;

import net.sf.anathema.equipment.editor.model.IEquipmentDatabaseManagement;
import net.sf.anathema.equipment.editor.stats.model.StatsEditModel;
import net.sf.anathema.equipment.editor.view.EquipmentNavigation;
import net.sf.anathema.library.event.ObjectChangedListener;
import net.sf.anathema.library.interaction.model.Tool;
import net.sf.anathema.library.resources.RelativePath;
import net.sf.anathema.library.resources.Resources;

public class RemoveEquipmentTemplateTool
{
	private final IEquipmentDatabaseManagement model;
	private StatsEditModel editModel;
	private final Resources resources;
	
	public RemoveEquipmentTemplateTool (Resources resources, IEquipmentDatabaseManagement model, StatsEditModel editModel)
	{
		this.resources = resources;
		this.model = model;
		this.editModel = editModel;
	}
	
	public void addToolTo (EquipmentNavigation view)
	{
		final Tool removeTool = view.addEditTemplateTool ();
		removeTool.setIcon (new RelativePath ("icons/ButtonCross24.png"));
		removeTool.setTooltip (resources.getString ("Equipment.Creation.Item.RemoveActionTooltip"));
		view.getTemplateListView ().addObjectSelectionChangedListener (new EnableWhenItemSelected (removeTool));
		updateEnabled (removeTool, view.getTemplateListView ().getSelectedObject ());
		removeTool.setCommand (new RemoveEquipmentItem (view, model, resources,editModel));
	}
	
	private void updateEnabled (Tool removeTool, String selectedObject)
	{
		if (selectedObject != null)
		{
			removeTool.enable ();
		}
		else
		{
			removeTool.disable ();
		}
	}
	
	private class EnableWhenItemSelected implements ObjectChangedListener<String>
	{
		private final Tool removeTool;
		
		public EnableWhenItemSelected (Tool removeTool)
		{
			this.removeTool = removeTool;
		}
		
		@Override
		public void valueChanged (String newValue)
		{
			updateEnabled (removeTool, newValue);
		}
	}
}
