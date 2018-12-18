package net.sf.anathema.hero.equipment.sheet.rendering.panoply;

import net.sf.anathema.equipment.stats.IArmourStats;
import net.sf.anathema.hero.equipment.sheet.content.ArmourContent;
import net.sf.anathema.hero.equipment.sheet.content.stats.armour.IArmourStatsGroup;
import net.sf.anathema.hero.equipment.sheet.rendering.EquipmentTableEncoder;
import net.sf.anathema.hero.sheet.pdf.content.stats.IStatsGroup;
import net.sf.anathema.hero.sheet.pdf.encoder.general.Bounds;
import net.sf.anathema.hero.sheet.pdf.encoder.graphics.SheetGraphics;
import net.sf.anathema.hero.sheet.pdf.session.ReportSession;

import com.itextpdf.text.pdf.PdfPTable;

public class ArmourTableEncoder extends EquipmentTableEncoder<IArmourStats, ArmourContent>
{
	public ArmourTableEncoder (Class<? extends ArmourContent> contentClass)
	{
		super (contentClass);
	}
	
	@Override
	protected PdfPTable createTable (SheetGraphics graphics, ReportSession reportSession, Bounds bounds)
	{
		PdfPTable armourTable = super.createTable (graphics, reportSession, bounds);
		ArmourContent content = createContent (reportSession);
		IArmourStats totalArmour = content.getTotalArmour ();
		IStatsGroup<IArmourStats>[] groups = content.createStatsGroups ();
		for (int index = 0; index < groups.length; index++)
		{
			if (index != 0)
			{
				armourTable.addCell (createSpaceCell (graphics));
			}
			IStatsGroup<IArmourStats> group = groups[index];
			if (group instanceof IArmourStatsGroup)
			{
				 ( (IArmourStatsGroup) group).addTotal (armourTable, createFont (graphics), totalArmour);
			}
			else
			{
				group.addContent (armourTable, createFont (graphics), totalArmour);
			}
		}
		return armourTable;
	}
}
