package net.sf.anathema.hero.charms.model.special.learning.multilearn;

import net.sf.anathema.magic.data.Charm;
import net.sf.anathema.magic.data.prerequisite.TraitPrerequisite;
import net.sf.anathema.hero.traits.model.Trait;
import net.sf.anathema.hero.traits.model.TraitMap;
import net.sf.anathema.hero.traits.model.TraitType;

public class LearnRangeContext
{
	private final TraitMap traitCollection;
	private final TraitRequirementChecker requirementChecker;
	private final Charm charm;
	
	public LearnRangeContext (TraitMap traitCollection, TraitRequirementChecker requirementChecker, Charm charm)
	{
		this.traitCollection = traitCollection;
		this.requirementChecker = requirementChecker;
		this.charm = charm;
	}
	
	public Trait getTrait (TraitType type)
	{
		return traitCollection.getTrait (type);
	}
	
	public boolean isMinimumSatisfied (TraitPrerequisite requirement)
	{
		return requirementChecker.isMinimumSatisfied (charm, requirement);
	}
}
