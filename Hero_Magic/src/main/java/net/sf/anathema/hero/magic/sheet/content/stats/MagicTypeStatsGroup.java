package net.sf.anathema.hero.magic.sheet.content.stats;

import net.sf.anathema.hero.magic.sheet.content.IMagicStats;
import net.sf.anathema.hero.sheet.pdf.content.stats.AbstractTextStatsGroup;
import net.sf.anathema.hero.sheet.pdf.encoder.table.TableColumns;
import net.sf.anathema.library.resources.Resources;

import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPTable;

public class MagicTypeStatsGroup extends AbstractTextStatsGroup<IMagicStats>
{
	private final Resources resources;
	
	public MagicTypeStatsGroup (Resources resources)
	{
		this.resources = resources;
	}
	
	@Override
	public void addContent (PdfPTable table, Font font, IMagicStats stats)
	{
		String text = stats == null ? null : stats.getType (resources);
		table.addCell (createTextCell (font, text));
	}
	
	@Override
	public TableColumns getColumnWeights ()
	{
		return TableColumns.singleColumn (2.5f);
	}
	
	@Override
	public String getTitle ()
	{
		return resources.getString ("Sheet.Magic.Type");
	}
}
