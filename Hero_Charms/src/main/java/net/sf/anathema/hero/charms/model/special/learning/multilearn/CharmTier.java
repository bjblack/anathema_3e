package net.sf.anathema.hero.charms.model.special.learning.multilearn;

import net.sf.anathema.hero.traits.model.TraitType;

public interface CharmTier
{
	boolean isLearnable (LearnRangeContext traitProvider);
	
	int getRequirement (TraitType type);
}
