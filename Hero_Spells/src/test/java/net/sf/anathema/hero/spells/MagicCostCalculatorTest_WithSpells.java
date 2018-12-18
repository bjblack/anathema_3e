package net.sf.anathema.hero.spells;

import net.sf.anathema.hero.dummy.DummyHero;
import net.sf.anathema.hero.dummy.magic.DummySpell;
import net.sf.anathema.hero.magic.advance.costs.CostAnalyzer;
import net.sf.anathema.hero.magic.advance.creation.MagicCreationCostCalculator;
import net.sf.anathema.hero.magic.advance.creation.MagicCreationCostEvaluator;
import net.sf.anathema.hero.magic.advance.creation.MagicCreationData;
import net.sf.anathema.hero.magic.template.advance.MagicPointsTemplate;
import net.sf.anathema.hero.spells.data.Spells;
import net.sf.anathema.hero.spells.model.SpellsLearner;
import net.sf.anathema.magic.data.Magic;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class MagicCostCalculatorTest_WithSpells
{
	private MagicCreationCostCalculator calculator;
	private DummySpellsModel spells = new DummySpellsModel ();
	private boolean favorSpells = false;
	
	@Before
	public void setUp () throws Exception
	{
		DummyHero hero = new DummyHero ();
		hero.addModel (spells);
		MagicPointsTemplate template = new MagicPointsTemplate ();
		template.generalCreationPoints.freePicks = 3;
		template.generalCreationPoints.costs = 5;
		template.favoredCreationPoints.freePicks = 2;
		template.favoredCreationPoints.costs = 4;
		MagicCreationCostEvaluator magicCostEvaluator = new MagicCreationCostEvaluator ();
		magicCostEvaluator.registerMagicLearner (new SpellsLearner (spells));
		MagicCreationData creationData = new MagicCreationData (template);
		calculator = new MagicCreationCostCalculator (magicCostEvaluator, creationData, new ConfigurableFavoredChecker ());
	}
	
	@Test
	public void testNoSpellsLearned ()
	{
		assertEquals (0, calculator.getGeneralMagicPicksSpent ());
		assertEquals (0, calculator.getFavoredMagicPicksSpent ());
		assertEquals (0, calculator.getBonusPointCost ());
	}
	
	@Test
	public void calculatesCategoriesForUnfavoredSpell ()
	{
		spells.addSpells (Spells.singleSpell (new DummySpell ()));
		calculator.calculateMagicCosts ();
		assertEquals (1, calculator.getGeneralMagicPicksSpent ());
		assertEquals (0, calculator.getFavoredMagicPicksSpent ());
		assertEquals (0, calculator.getBonusPointCost ());
	}
	
	@Test
	public void calculatesCategoriesForFavoredSpell ()
	{
		setSpellsFavored ();
		spells.addSpells (Spells.from (new DummySpell ()));
		calculator.calculateMagicCosts ();
		assertEquals (0, calculator.getGeneralMagicPicksSpent ());
		assertEquals (1, calculator.getFavoredMagicPicksSpent ());
		assertEquals (0, calculator.getBonusPointCost ());
	}
	
	@Test
	public void testUnfavoredSpellsOverflowToBonus ()
	{
		DummySpell dummySpell = new DummySpell ();
		spells.addSpells (Spells.from (dummySpell, dummySpell, dummySpell, dummySpell));
		calculator.calculateMagicCosts ();
		assertEquals (3, calculator.getGeneralMagicPicksSpent ());
		assertEquals (0, calculator.getFavoredMagicPicksSpent ());
		assertEquals (5, calculator.getBonusPointCost ());
	}
	
	@Test
	public void testUnfavoredSpellsOverflowToBonusAndAreReset ()
	{
		DummySpell dummySpell = new DummySpell ();
		DummySpell dummySpellToRemove = new DummySpell ();
		spells.addSpells (Spells.from (dummySpell, dummySpell, dummySpell, dummySpellToRemove));
		calculator.calculateMagicCosts ();
		assertEquals (3, calculator.getGeneralMagicPicksSpent ());
		assertEquals (0, calculator.getFavoredMagicPicksSpent ());
		assertEquals (5, calculator.getBonusPointCost ());
		spells.removeSpells (Spells.from (dummySpellToRemove), false);
		calculator.calculateMagicCosts ();
		assertEquals (3, calculator.getGeneralMagicPicksSpent ());
	}
	
	@Test
	public void removalRemovesBonusPointCost ()
	{
		DummySpell dummySpell = new DummySpell ();
		DummySpell dummySpellToRemove = new DummySpell ();
		spells.addSpells (Spells.from (dummySpell, dummySpell, dummySpell, dummySpellToRemove), false);
		spells.removeSpells (Spells.from (dummySpellToRemove), false);
		calculator.calculateMagicCosts ();
		assertThat (calculator.getBonusPointCost (), is (0));
	}
	
	@Test
	public void testFavoredSpellsOverflowToGeneralAndBonus ()
	{
		setSpellsFavored ();
		spells.addSpells (Spells.from (new DummySpell (), new DummySpell (), new DummySpell (), new DummySpell (),
		new DummySpell (), new DummySpell ()));
		calculator.calculateMagicCosts ();
		assertEquals (3, calculator.getGeneralMagicPicksSpent ());
		assertEquals (2, calculator.getFavoredMagicPicksSpent ());
		assertEquals (4, calculator.getBonusPointCost ());
	}
	
	private void setSpellsFavored ()
	{
		this.favorSpells = true;
	}
	
	private class ConfigurableFavoredChecker implements CostAnalyzer
	{
		@Override
		public boolean isMagicFavored (Magic magic)
		{
			return favorSpells;
		}
	}
}
