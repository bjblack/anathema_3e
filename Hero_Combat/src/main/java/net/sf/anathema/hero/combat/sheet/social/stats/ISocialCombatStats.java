package net.sf.anathema.hero.combat.sheet.social.stats;

import net.sf.anathema.hero.sheet.pdf.content.stats.IStats;

public interface ISocialCombatStats extends IStats
{
	int getDeceptionAttackValue ();
	
	int getHonestyAttackValue ();
	
	int getRate ();
	
	int getSpeed ();
}
