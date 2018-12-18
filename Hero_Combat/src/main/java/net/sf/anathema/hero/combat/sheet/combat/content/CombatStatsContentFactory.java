package net.sf.anathema.hero.combat.sheet.combat.content;

import net.sf.anathema.hero.sheet.pdf.content.ReportContentFactory;
import net.sf.anathema.hero.sheet.pdf.session.ReportSession;
import net.sf.anathema.library.dependencies.Produces;
import net.sf.anathema.library.resources.Resources;

@Produces (CombatStatsContent.class)
public class CombatStatsContentFactory implements ReportContentFactory<CombatStatsContent>
{
	private Resources resources;
	
	public CombatStatsContentFactory (Resources resources)
	{
		this.resources = resources;
	}
	
	@Override
	public CombatStatsContent create (ReportSession session)
	{
		return new CombatStatsContent (session.getHero (), resources);
	}
}
