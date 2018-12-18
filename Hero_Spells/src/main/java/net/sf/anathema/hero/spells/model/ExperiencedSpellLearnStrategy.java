package net.sf.anathema.hero.spells.model;

import net.sf.anathema.hero.spells.data.Spell;
import net.sf.anathema.hero.spells.data.Spells;

public class ExperiencedSpellLearnStrategy implements ISpellLearnStrategy
{
	@Override
	public void addSpells (SpellsModel configuration, Spells addedSpells)
	{
		configuration.addSpells (addedSpells, true);
	}
	
	@Override
	public void removeSpells (SpellsModel configuration, Spells removedSpells)
	{
		configuration.removeSpells (removedSpells, true);
	}
	
	@Override
	public boolean isSpellAllowed (SpellsModel configuration, Spell spell)
	{
		return configuration.isSpellAllowed (spell, true);
	}
	
	@Override
	public Spells getLearnedSpells (SpellsModel configuration)
	{
		return configuration.getLearnedSpells (true);
	}
	
	@Override
	public boolean isLearned (SpellsModel configuration, Spell spell)
	{
		return configuration.isLearnedOnCreationOrExperience (spell);
	}
}
