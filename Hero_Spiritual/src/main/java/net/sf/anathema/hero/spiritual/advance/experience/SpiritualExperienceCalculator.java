package net.sf.anathema.hero.spiritual.advance.experience;

import net.sf.anathema.hero.traits.advance.CurrentRatingCost;
import net.sf.anathema.hero.traits.advance.TraitRatingCostCalculator;
import net.sf.anathema.hero.traits.model.Trait;

public class SpiritualExperienceCalculator
{
	private SpiritualExperienceData experienceData;
	
	public SpiritualExperienceCalculator (SpiritualExperienceData experienceData)
	{
		this.experienceData = experienceData;
	}
	
	public int getWillpowerCosts (Trait willpower)
	{
		CurrentRatingCost cost = experienceData.getWillpowerCost ();
		return getTraitRatingCosts (willpower, cost);
	}
	
	private int getTraitRatingCosts (Trait trait, CurrentRatingCost ratingCost)
	{
		return TraitRatingCostCalculator.getTraitRatingCost (trait, ratingCost);
	}
}
