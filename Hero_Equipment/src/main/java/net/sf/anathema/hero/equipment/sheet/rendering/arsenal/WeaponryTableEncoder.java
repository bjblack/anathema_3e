package net.sf.anathema.hero.equipment.sheet.rendering.arsenal;

import net.sf.anathema.equipment.stats.IWeaponStats;
import net.sf.anathema.hero.equipment.sheet.rendering.EquipmentTableEncoder;
import net.sf.anathema.hero.sheet.pdf.content.stats.FixedLineStatsContent;

public class WeaponryTableEncoder<C extends FixedLineStatsContent<IWeaponStats>> extends EquipmentTableEncoder<IWeaponStats, C>
{
	public WeaponryTableEncoder (Class<C> contentClass)
	{
		super (contentClass);
	}
}
