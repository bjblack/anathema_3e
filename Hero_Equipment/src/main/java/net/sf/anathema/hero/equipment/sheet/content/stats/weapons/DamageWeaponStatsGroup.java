package net.sf.anathema.hero.equipment.sheet.content.stats.weapons;

import net.sf.anathema.equipment.stats.IWeaponStats;
import net.sf.anathema.hero.equipment.sheet.content.stats.AbstractValueEquipmentStatsGroup;
import net.sf.anathema.hero.health.model.HealthType;
import net.sf.anathema.hero.sheet.pdf.encoder.table.TableColumns;
import net.sf.anathema.hero.traits.model.TraitMap;
import net.sf.anathema.hero.traits.model.TraitType;
import net.sf.anathema.library.resources.Resources;

import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPTable;

public class DamageWeaponStatsGroup extends AbstractValueEquipmentStatsGroup<IWeaponStats>
{
	private final TraitMap traitMap;
	
	public DamageWeaponStatsGroup (Resources resources, TraitMap traitMap)
	{
		super (resources, "Damage");
		this.traitMap = traitMap;
	}
	
	@Override
	public TableColumns getColumnWeights ()
	{
		TableColumns columns = super.getColumnWeights ();
		columns.changeLastTo (0.7f);
		return columns;
	}
	
	@Override
	public int getColumnCount ()
	{
		return 3;
	}
	
	@Override
	public void addContent (PdfPTable table, Font font, IWeaponStats weapon)
	{
		if (weapon == null)
		{
			table.addCell (createEmptyValueCell (font));
			table.addCell (createFinalValueCell (font));
			table.addCell (createFinalValueCell (font));
		}
		else if (weapon.inflictsNoDamage ())
		{
			table.addCell (createEquipmentValueCell (font, null));
			table.addCell (createFinalValueCell (font, (Integer) null));
			table.addCell (createFinalValueCell (font, (Integer) null));
		}
		else
		{
			int weaponValue = weapon.getDamage ();
			int finalValue = weaponValue;
			TraitType damageTraitType = weapon.getDamageTraitType ();
			if (damageTraitType != null)
			{
				finalValue = calculateFinalValue (weaponValue, traitMap.getTrait (damageTraitType));
				table.addCell (createEquipmentValueCell (font, weaponValue));
			}
			else
			{
				table.addCell (createEquipmentValueCell (font, null));
			}
			table.addCell (createFinalValueCell (font, finalValue));
			table.addCell (createFinalValueCell (font, getDamageTypeLabel (weapon.getDamageType ()), Element.ALIGN_CENTER));
		}
	}
	
	private String getDamageTypeLabel (HealthType damageType)
	{
		return getResources ().getString ("HealthType." + damageType.getId () + ".Short");
	}
}
