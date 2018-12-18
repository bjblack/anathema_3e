package net.sf.anathema.hero.equipment.sheet.content;

import net.sf.anathema.equipment.stats.IArmourStats;
import net.sf.anathema.hero.equipment.sheet.content.stats.EquipmentNameStatsGroup;
import net.sf.anathema.hero.equipment.sheet.content.stats.IEquipmentStatsGroup;
import net.sf.anathema.hero.equipment.sheet.content.stats.armour.HardnessStatsGroup;
import net.sf.anathema.hero.equipment.sheet.content.stats.armour.MobilityPenaltyStatsGroup;
import net.sf.anathema.hero.equipment.sheet.content.stats.armour.SoakArmourStatsGroup;
import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.sheet.pdf.content.stats.IStatsGroup;
import net.sf.anathema.library.resources.Resources;

public class ArmourContent extends AbstractEquipmentContent<IArmourStats>
{
	public ArmourContent (Hero hero, Resources resources)
	{
		super (hero, resources);
	}
	
	@Override
	public int getLineCount ()
	{
		return 3;
	}
	
	@Override
	public IArmourStats[] getPrintStats ()
	{
		return getEquipmentModel ().getPrintArmours ();
	}
	
	@SuppressWarnings ("unchecked")
	@Override
	public IStatsGroup<IArmourStats>[] createStatsGroups ()
	{
		return new IEquipmentStatsGroup[]
		{
			new EquipmentNameStatsGroup<IArmourStats> (getResources ()), new SoakArmourStatsGroup (getResources ()),
			new HardnessStatsGroup (getResources ()), new MobilityPenaltyStatsGroup (getResources ())
		}
		;
	}
	
	public IArmourStats getTotalArmour ()
	{
		int lineCount = getLineCount ();
		return getEquipmentModel ().getEffectivePrintArmour (getResources (), lineCount);
	}
}
