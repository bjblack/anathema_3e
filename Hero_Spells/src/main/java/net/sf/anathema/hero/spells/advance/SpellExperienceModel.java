package net.sf.anathema.hero.spells.advance;

import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.magic.advance.experience.MagicExperienceCostCalculator;
import net.sf.anathema.hero.spells.data.Spell;
import net.sf.anathema.hero.spells.model.SpellsModel;
import net.sf.anathema.hero.spells.model.SpellsModelFetcher;
import net.sf.anathema.points.display.overview.model.AbstractIntegerValueModel;

public class SpellExperienceModel extends AbstractIntegerValueModel
{
	private final Hero hero;
	private final MagicExperienceCostCalculator calculator;
	
	public SpellExperienceModel (Hero hero, MagicExperienceCostCalculator calculator)
	{
		super ("Experience", "Spells");
		this.hero = hero;
		this.calculator = calculator;
	}
	
	@Override
	public Integer getValue ()
	{
		return getSpellCosts ();
	}
	
	private int getSpellCosts ()
	{
		int experienceCosts = 0;
		SpellsModel spellsModel = SpellsModelFetcher.fetch (hero);
		for (Spell spell : spellsModel.getLearnedSpells (true))
		{
			if (!spellsModel.isLearnedOnCreation (spell))
			{
				experienceCosts += calculator.getMagicCosts (hero, spell);
			}
		}
		return experienceCosts;
	}
}
