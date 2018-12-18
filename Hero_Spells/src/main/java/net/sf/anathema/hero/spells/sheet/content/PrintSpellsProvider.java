package net.sf.anathema.hero.spells.sheet.content;

import net.sf.anathema.hero.magic.model.PrintMagicProvider;
import net.sf.anathema.hero.magic.sheet.content.IMagicStats;
import net.sf.anathema.hero.experience.model.ExperienceModelFetcher;
import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.spells.data.Spell;
import net.sf.anathema.hero.spells.data.Spells;
import net.sf.anathema.hero.spells.model.SpellsModelFetcher;

import java.util.List;

public class PrintSpellsProvider implements PrintMagicProvider
{
	private Hero hero;
	
	public PrintSpellsProvider (Hero hero)
	{
		this.hero = hero;
	}
	
	@Override
	public void addPrintMagic (List<IMagicStats> printMagic)
	{
		for (Spell spell : getAllLearnedSpells ())
		{
			printMagic.add (new SpellStats (spell));
		}
	}
	
	private Spells getAllLearnedSpells ()
	{
		boolean experienced = ExperienceModelFetcher.fetch (hero).isExperienced ();
		return SpellsModelFetcher.fetch (hero).getLearnedSpells (experienced);
	}
}
