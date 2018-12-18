package net.sf.anathema.hero.combat.sheet.social.encoder;

import net.sf.anathema.hero.combat.model.social.InvestigationSocialAttack;
import net.sf.anathema.hero.combat.model.social.PerformanceSocialAttack;
import net.sf.anathema.hero.combat.model.social.PresenceSocialAttack;
import net.sf.anathema.hero.combat.sheet.social.stats.DeceptionStatsGroup;
import net.sf.anathema.hero.combat.sheet.social.stats.HonestyStatsGroup;
import net.sf.anathema.hero.combat.sheet.social.stats.ISocialCombatStats;
import net.sf.anathema.hero.combat.sheet.social.stats.SocialCombatNameStatsGroup;
import net.sf.anathema.hero.combat.sheet.social.stats.SocialRateStatsGroup;
import net.sf.anathema.hero.combat.sheet.social.stats.SocialSpeedStatsGroup;
import net.sf.anathema.hero.sheet.pdf.content.stats.IStatsGroup;
import net.sf.anathema.hero.sheet.pdf.encoder.stats.AbstractFixedLineStatsTableEncoder;
import net.sf.anathema.hero.sheet.pdf.session.ReportSession;
import net.sf.anathema.hero.traits.model.TraitMap;
import net.sf.anathema.hero.traits.model.TraitModelFetcher;
import net.sf.anathema.library.resources.Resources;

public class SocialCombatStatsTableEncoder extends AbstractFixedLineStatsTableEncoder<ISocialCombatStats>
{
	private final Resources resources;
	
	public SocialCombatStatsTableEncoder (Resources resources)
	{
		this.resources = resources;
	}
	
	@SuppressWarnings ("unchecked")
	@Override
	protected IStatsGroup<ISocialCombatStats>[] createStatsGroups (ReportSession session)
	{
		return new IStatsGroup[]
		{
			new SocialCombatNameStatsGroup (resources), new SocialSpeedStatsGroup (
			resources), new HonestyStatsGroup (resources), new DeceptionStatsGroup (resources), new SocialRateStatsGroup (
			resources)
		}
		;
	}
	
	@Override
	protected int getLineCount (ReportSession session)
	{
		return 3;
	}
	
	@Override
	protected ISocialCombatStats[] getPrintStats (ReportSession session)
	{
		TraitMap traitCollection = TraitModelFetcher.fetch (session.getHero ());
		return new ISocialCombatStats[]
		{
			new PresenceSocialAttack (traitCollection), new PerformanceSocialAttack (
			traitCollection), new InvestigationSocialAttack (traitCollection)
		}
		;
	}
}
