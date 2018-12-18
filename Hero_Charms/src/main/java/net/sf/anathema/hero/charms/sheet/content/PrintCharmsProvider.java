package net.sf.anathema.hero.charms.sheet.content;

import net.sf.anathema.hero.magic.sheet.content.IMagicStats;
import net.sf.anathema.magic.data.Charm;
import net.sf.anathema.hero.magic.model.PrintMagicProvider;
import net.sf.anathema.hero.charms.sheet.content.stats.CharmStats;
import net.sf.anathema.hero.charms.sheet.content.stats.MultipleEffectCharmStats;
import net.sf.anathema.hero.individual.model.Hero;

import java.util.List;

public class PrintCharmsProvider implements PrintMagicProvider
{
	private Hero hero;
	
	public PrintCharmsProvider (Hero hero)
	{
		this.hero = hero;
	}
	
	@Override
	public void addPrintMagic (List<IMagicStats> printMagic)
	{
		addCharms (printMagic);
	}
	
	private void addCharms (List<IMagicStats> printMagic)
	{
		for (Charm charm: createContentHelper ().getLearnedCharms ())
		{
			addStatsForCharm (printMagic, charm);
		}
	}
	
	private void addStatsForCharm (List<IMagicStats> printMagic, Charm charm)
	{
		if (createContentHelper ().isMultipleEffectCharm (charm))
		{
			for (String effect : createContentHelper ().getLearnedEffects (charm))
			{
				printMagic.add (new MultipleEffectCharmStats (charm, effect));
			}
		}
		else
		{
			printMagic.add (new CharmStats (charm, createContentHelper ()));
		}
	}
	
	private CharmContentHelper createContentHelper ()
	{
		return new CharmContentHelper (hero);
	}
}
