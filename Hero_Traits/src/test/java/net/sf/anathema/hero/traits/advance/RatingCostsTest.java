package net.sf.anathema.hero.traits.advance;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RatingCostsTest
{
	@Test (expected = UnsupportedOperationException.class)
	public void testMulitplyRatingCostsWithoutInitalValue () throws Exception
	{
		final MultiplyRatingCost costs = new MultiplyRatingCost (4);
		costs.getRatingCosts (0);
	}
	
	@Test
	public void testMulitplyRatingCostsWithInitalValue () throws Exception
	{
		MultiplyRatingCost costs = new MultiplyRatingCost (4, 3);
		assertEquals (3, costs.getRatingCosts (0));
		assertEquals (4, costs.getRatingCosts (1));
		assertEquals (8, costs.getRatingCosts (2));
		assertEquals (12, costs.getRatingCosts (3));
		assertEquals (16, costs.getRatingCosts (4));
	}
}
