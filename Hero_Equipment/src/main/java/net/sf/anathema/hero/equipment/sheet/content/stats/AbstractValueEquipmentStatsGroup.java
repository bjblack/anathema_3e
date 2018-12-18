package net.sf.anathema.hero.equipment.sheet.content.stats;

import net.sf.anathema.equipment.stats.IEquipmentStats;
import net.sf.anathema.hero.traits.sheet.content.AbstractValueStatsGroup;
import net.sf.anathema.library.resources.Resources;

public abstract class AbstractValueEquipmentStatsGroup<T extends IEquipmentStats> extends AbstractValueStatsGroup<T> implements
IEquipmentStatsGroup<T>
{
	public AbstractValueEquipmentStatsGroup (Resources resources, String resourceKey)
	{
		super (resources, resourceKey);
	}
	
	@Override
	protected String getHeaderResourceBase ()
	{
		return "Sheet.Equipment.Header.";
	}
}
