package net.sf.anathema.graph.ordering;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class IntegerUtilitiesTest
{
	@Test
	public void testPermutateArray () throws Exception
	{
		Assert.assertTrue (Arrays.equals (new int[]
		{
			2, 1
		}
		, IntegerUtilities.permutate (new int[]
		{
			1, 2
		}
		)));
		Assert.assertTrue (Arrays.equals (new int[]
		{
			2, 4, 1, 3
		}
		, IntegerUtilities.permutate (new int[]
		{
			1, 2, 3, 4
		}
		)));
		Assert.assertTrue (Arrays.equals (new int[]
		{
			3, 6, 2, 5, 1, 4
		}
		, IntegerUtilities.permutate (new int[]
		{
			1,
			2,
			3,
			4,
			5,
			6
		}
		)));
	}
}
