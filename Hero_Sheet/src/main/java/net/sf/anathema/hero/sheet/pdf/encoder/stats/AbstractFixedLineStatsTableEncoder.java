package net.sf.anathema.hero.sheet.pdf.encoder.stats;

import net.sf.anathema.hero.sheet.pdf.content.stats.IStats;
import net.sf.anathema.hero.sheet.pdf.content.stats.IStatsGroup;
import net.sf.anathema.hero.sheet.pdf.encoder.general.Bounds;
import net.sf.anathema.hero.sheet.pdf.encoder.graphics.SheetGraphics;
import net.sf.anathema.hero.sheet.pdf.session.ReportSession;

import com.itextpdf.text.pdf.PdfPTable;

public abstract class AbstractFixedLineStatsTableEncoder<T extends IStats> extends AbstractStatsTableEncoder<T, ReportSession>
{
	@Override
	protected void encodeContent (SheetGraphics graphics, PdfPTable table, ReportSession session, Bounds bounds)
	{
		IStatsGroup<T>[] groups = createStatsGroups (session);
		T[] printStats = getPrintStats (session);
		int line = 0;
		while (line < getLineCount (session))
		{
			T printStat = line < printStats.length ? printStats[line] : null;
			encodeContentLine (graphics, table, groups, printStat);
			line++;
		}
	}
	
	protected abstract int getLineCount (ReportSession session);
	
	protected abstract T[] getPrintStats (ReportSession session);
}
