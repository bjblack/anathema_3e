package net.sf.anathema.hero.charms.model.special.learning.multilearn;

import net.sf.anathema.magic.data.prerequisite.TraitPrerequisite;
import net.sf.anathema.hero.traits.model.TraitType;
import net.sf.anathema.hero.traits.model.TraitTypeUtils;

import com.google.common.collect.Lists;

import java.util.List;

public class TraitCharmTier implements CharmTier
{
	private final List<TraitPrerequisite> requirements = Lists.newArrayList ();
	
	public void addRequirement (TraitPrerequisite requirement)
	{
		requirements.add (requirement);
	}
	
	@SuppressWarnings ("RedundantIfStatement")
	@Override
	public boolean isLearnable (LearnRangeContext context)
	{
		for (TraitPrerequisite requirement : requirements)
		{
			if (!context.isMinimumSatisfied (requirement))
			{
				return false;
			}
		}
		return true;
	}
	
	@Override
	public int getRequirement (TraitType type)
	{
		for (TraitPrerequisite requirement : requirements)
		{
			if (type.equals (new TraitTypeUtils ().getTraitTypeFor (requirement)))
			{
				return requirement.minimalValue;
			}
		}
		return 0;
	}
}
