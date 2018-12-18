package net.sf.anathema.hero.equipment.sheet.content;

import net.sf.anathema.equipment.character.IEquipmentItem;
import net.sf.anathema.equipment.stats.IArmourStats;
import net.sf.anathema.equipment.stats.IArtifactStats;
import net.sf.anathema.equipment.stats.IEquipmentStats;
import net.sf.anathema.library.identifier.Identifier;
import net.sf.anathema.library.identifier.SimpleIdentifier;

import java.util.Collection;

public class ArmourStatsDecorationFactory implements IEquipmentStatsDecorationFactory<IArmourStats>
{
	private String createItemName (IEquipmentItem item, IEquipmentStats stats)
	{
		String itemName = item.getTitle ();
		if (getListedStats (item) > 1)
		{
			itemName += " - " + stats.getName ();
		}
		return itemName;
	}
	
	private int getListedStats (IEquipmentItem item)
	{
		int listedStats = 0;
		for (IEquipmentStats stats : item.getStats ())
		{
			if (stats instanceof IArtifactStats)
			{
				continue;
			}
			listedStats++;
		}
		return listedStats;
	}
	
	@Override
	public IArmourStats createRenamedPrintDecoration (IEquipmentItem item, final IArmourStats stats)
	{
		final String name = createItemName (item, stats);
		return new IArmourStats ()
		{
			@Override
			public Integer getHardness ()
			{
				return stats.getHardness ();
			}
			
			@Override
			public Integer getMobilityPenalty ()
			{
				return stats.getMobilityPenalty ();
			}
			
			@Override
			public Integer getSoak ()
			{
				return stats.getSoak ();
			}
			
			@Override
			public Collection<Identifier> getTags ()
			{
				return stats.getTags ();
			}
			
			@Override
			public Identifier getName ()
			{
				return new SimpleIdentifier (name);
			}
			
			@Override
			public String getId ()
			{
				return getName ().getId ();
			}
			
			@Override
			public boolean representsItemForUseInCombat ()
			{
				return stats.representsItemForUseInCombat ();
			}
		}
		;
	}
}
