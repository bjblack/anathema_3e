package net.sf.anathema.hero.equipment.sheet.content;

import net.sf.anathema.equipment.character.IEquipmentItem;
import net.sf.anathema.equipment.stats.IEquipmentStats;
import net.sf.anathema.equipment.stats.IWeaponStats;
import net.sf.anathema.library.identifier.Identifier;
import net.sf.anathema.library.resources.Resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class EquipmentPrintNameFactory
{
	private final Resources resources;
	
	public EquipmentPrintNameFactory (Resources resources)
	{
		this.resources = resources;
	}
	
	public String create (IEquipmentItem item, IWeaponStats stats)
	{
		StringBuilder builder = new StringBuilder (item.getTitle ());
		if (item.getStats ().hasOnlyOneStat ())
		{
			return builder.toString ();
		}
		if (!hasSingleOriginalStat (item))
		{
			builder.append (" - ");
			builder.append (stats.getName ());
		}
		if (Collections.frequency (getStatNames (item, new ArrayList<> ()), stats.getName ()) > 1)
		{
			builder.append (" (");
			builder.append (resources.getString (stats.getTraitType ().getId ()));
			builder.append (")");
		}
		return builder.toString ();
	}
	
	private boolean hasSingleOriginalStat (IEquipmentItem item)
	{
		return getStatNames (item, new HashSet<> ()).size () == 1;
	}
	
	private Collection<Identifier> getStatNames (IEquipmentItem item, Collection<Identifier> names)
	{
		for (IEquipmentStats stats : item.getStats ())
		{
			if (!stats.representsItemForUseInCombat ())
			{
				continue;
			}
			names.add (stats.getName ());
		}
		return names;
	}
}
