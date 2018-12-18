package net.sf.anathema.hero.equipment.model;

import net.sf.anathema.equipment.character.IEquipmentItem;

public class EquipmentPersonalizationModel implements net.sf.anathema.hero.equipment.display.presenter.EquipmentPersonalizationModel
{
	private final IEquipmentItem item;
	private String title;
	private String description;
	
	public EquipmentPersonalizationModel (IEquipmentItem item)
	{
		this.item = item;
		setTitle (item.getTitle ());
		setDescription (item.getDescription ());
	}
	
	@Override
	public void setTitle (String text)
	{
		this.title = text;
		item.setPersonalization (getTitle (), getDescription ());
	}
	
	@Override
	public void setDescription (String text)
	{
		this.description = text;
		item.setPersonalization (getTitle (), getDescription ());
	}
	
	@Override
	public String getTitle ()
	{
		return title;
	}
	
	@Override
	public String getDescription ()
	{
		return description;
	}
	
	public void apply ()
	{
		item.setPersonalization (getTitle (), getDescription ());
	}
}
