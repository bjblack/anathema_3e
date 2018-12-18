package net.sf.anathema.equipment.initialization;

import net.sf.anathema.equipment.database.gson.EquipmentGson;
import net.sf.anathema.equipment.database.gson.GsonEquipmentDatabase;
import net.sf.anathema.equipment.template.IEquipmentTemplate;
import net.sf.anathema.platform.frame.ApplicationModel;
import net.sf.anathema.platform.item.RepositoryItemInitializationStrategy;

public class EquipmentInitializationStrategy implements RepositoryItemInitializationStrategy
{
	private final static String EQUIPMENT_REGEX = "^.*\\.item$";
	
	private final EquipmentGson gson = new EquipmentGson ();
	private final GsonEquipmentDatabase database;
	
	public EquipmentInitializationStrategy (ApplicationModel anathemaModel)
	{
		database = GsonEquipmentDatabase.CreateFrom (anathemaModel);
	}
	
	public boolean shouldInitializeData ()
	{
		return database.isEmpty ();
	}
	
	public void storeInRepository (String itemJSON)
	{
		IEquipmentTemplate iEquipmentTemplate = gson.fromJson (itemJSON);
		database.saveTemplateNoOverwrite (iEquipmentTemplate);
	}
	
	public String getMessageKey ()
	{
		return "Equipment.Bootjob.DefaultDatabaseSplashmessage";
	}
	
	public String getItemPattern ()
	{
		return EQUIPMENT_REGEX;
	}
}
