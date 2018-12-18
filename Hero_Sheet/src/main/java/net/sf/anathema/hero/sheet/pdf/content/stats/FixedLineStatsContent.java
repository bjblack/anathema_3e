package net.sf.anathema.hero.sheet.pdf.content.stats;

import net.sf.anathema.hero.sheet.pdf.content.SubContent;

public interface FixedLineStatsContent<STATS extends IStats> extends SubContent
{
	int getLineCount ();
	
	STATS[] getPrintStats ();
	
	IStatsGroup<STATS>[] createStatsGroups ();
}
