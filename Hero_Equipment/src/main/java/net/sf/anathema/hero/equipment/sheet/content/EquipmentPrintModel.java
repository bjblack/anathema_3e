package net.sf.anathema.hero.equipment.sheet.content;

import net.sf.anathema.equipment.character.IEquipmentItem;
import net.sf.anathema.equipment.stats.IArmourStats;
import net.sf.anathema.equipment.stats.IEquipmentStats;
import net.sf.anathema.equipment.stats.IWeaponStats;
import net.sf.anathema.hero.equipment.IEquipmentPrintModel;
import net.sf.anathema.hero.equipment.model.IEquipmentItemCollection;
import net.sf.anathema.library.resources.Resources;

import java.util.ArrayList;
import java.util.List;

public class EquipmentPrintModel implements IEquipmentPrintModel
{
	private final IEquipmentItemCollection collection;
	private final IArmourStats naturalArmour;
	
	public EquipmentPrintModel (IEquipmentItemCollection collection, IArmourStats naturalArmour)
	{
		this.collection = collection;
		this.naturalArmour = naturalArmour;
	}
	
	@Override
	public IArmourStats[] getPrintArmours ()
	{
		List<IArmourStats> printStats = getNaturalArmourList ();
		printStats.addAll (getPrintEquipmentList (IArmourStats.class, new ArmourStatsDecorationFactory ()));
		return printStats.toArray (new IArmourStats[printStats.size ()]);
	}
	
	private List<IArmourStats> getNaturalArmourList ()
	{
		List<IArmourStats> printStats = new ArrayList<> ();
		printStats.add (naturalArmour);
		return printStats;
	}
	
	@Override
	public IWeaponStats[] getPrintWeapons (Resources resources)
	{
		List<IWeaponStats> printStats = new ArrayList<> ();
		printStats.addAll (getNaturalWeaponList ());
		printStats.addAll (getPrintEquipmentList (IWeaponStats.class, new WeaponStatsDecorationFactory (resources)));
		return printStats.toArray (new IWeaponStats[printStats.size ()]);
	}
	
	private List<IWeaponStats> getNaturalWeaponList ()
	{
		List<IWeaponStats> printStats = new ArrayList<> ();
		for (IEquipmentItem item : collection.getNaturalWeapons ())
		{
			printStats.addAll (getPrintedStats (item, IWeaponStats.class));
		}
		return printStats;
	}
	
	private <K extends IEquipmentStats> List<K> getPrintEquipmentList (Class<K> printedClass, IEquipmentStatsDecorationFactory<K> factory)
	{
		List<K> printStats = new ArrayList<> ();
		for (IEquipmentItem item : collection.getEquipmentItems ())
		{
			for (K stats : getPrintedStats (item, printedClass))
			{
				printStats.add (factory.createRenamedPrintDecoration (item, stats));
			}
		}
		return printStats;
	}
	
	@SuppressWarnings ("unchecked")
	private <K> List<K> getPrintedStats (IEquipmentItem item, Class<K> printedClass)
	{
		List<K> printedStats = new ArrayList<> ();
		for (IEquipmentStats stats : item.getStats ())
		{
			if (doPrint (item, stats, printedClass))
			{
				printedStats.add ( (K) stats);
			}
		}
		return printedStats;
	}
	
	private <K> boolean doPrint (IEquipmentItem item, IEquipmentStats stats, Class<K> printedClass)
	{
		return printedClass.isInstance (stats) && item.isPrintEnabled (stats);
	}
	
	@Override
	public final IArmourStats getEffectivePrintArmour (Resources resources, int lineCount)
	{
		EffectiveArmour armour = new EffectiveArmour ();
		IArmourStats[] printArmours = getPrintArmours ();
		for (int index = 0; index < Math.min (lineCount, printArmours.length); index++)
		{
			armour.addArmour (printArmours[index]);
		}
		IWeaponStats[] printWeapons = getPrintWeapons (resources);
		for (IWeaponStats stats : printWeapons)
		{
			armour.modifyMobilityPenalty (stats.getMobilityPenalty ());
		}
		return armour;
	}
}
