package net.sf.anathema.hero.equipment.sheet.content.stats;

import net.sf.anathema.equipment.presentation.IEquipmentObjectPresenter;
import net.sf.anathema.equipment.stats.IEquipmentStats;
import net.sf.anathema.hero.sheet.pdf.content.stats.AbstractNameStatsGroup;
import net.sf.anathema.library.resources.Resources;

import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPTable;

public final class EquipmentNameStatsGroup<T extends IEquipmentStats> extends AbstractNameStatsGroup<T> implements IEquipmentStatsGroup<T>
{
	private final Resources resources;
	
	public EquipmentNameStatsGroup (Resources resources)
	{
		super (resources);
		this.resources = resources;
	}
	
	@Override
	protected String getHeaderResourceKey ()
	{
		return "Sheet.Equipment.Header.Name";
	}
	
	@Override
	public void addContent (PdfPTable table, Font font, T stats)
	{
		if (stats == null)
		{
			table.addCell (createTextCell (font, ""));
		}
		else
		{
			String name = stats.getName ().getId ();
			String resourceKey = getResourceBase () + name;
			if (resources.supportsKey (resourceKey))
			{
				name = resources.getString (resourceKey);
			}
			table.addCell (createTextCell (font, name));
		}
	}
	
	@Override
	protected String getResourceBase ()
	{
		return IEquipmentObjectPresenter.EQUIPMENT_NAME_PREFIX;
	}
}
