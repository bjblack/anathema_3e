package net.sf.anathema.equipment.editor.stats.presenter;

import net.sf.anathema.equipment.editor.stats.model.EquipmentStatisticsType;
import net.sf.anathema.library.resources.RelativePath;

public class NewStatsConfiguration
{
	private final String tooltipKey;
	private final RelativePath iconPath;
	private final String nameKey;
	private final EquipmentStatisticsType type;
	
	public NewStatsConfiguration (String tooltipKey, RelativePath iconPath, String nameKey, EquipmentStatisticsType type)
	{
		this.tooltipKey = tooltipKey;
		this.iconPath = iconPath;
		this.nameKey = nameKey;
		this.type = type;
	}
	
	public String getTooltipKey ()
	{
		return tooltipKey;
	}
	
	public RelativePath getIconPath ()
	{
		return iconPath;
	}
	
	public String getNameKey ()
	{
		return nameKey;
	}
	
	public EquipmentStatisticsType getType ()
	{
		return type;
	}
}
