package net.sf.anathema.hero.charms.model.learn.prerequisites;

import net.sf.anathema.magic.data.Charm;
import net.sf.anathema.magic.data.prerequisite.CharmPrerequisite;
import net.sf.anathema.magic.data.prerequisite.PrerequisiteProcessor;
import net.sf.anathema.magic.data.prerequisite.RequiredTraitType;
import net.sf.anathema.magic.data.reference.CategoryReference;
import net.sf.anathema.magic.data.reference.TreeReference;
import net.sf.anathema.hero.charms.model.learn.CharmLearnableArbitrator;
import net.sf.anathema.magic.data.attribute.MagicAttribute;

import java.util.List;

public class IsAutoSatisfiable implements PrerequisiteProcessor
{
	public static boolean isAutoSatisfiable (CharmPrerequisite prerequisite, CharmLearnableArbitrator arbitrator)
	{
		IsAutoSatisfiable satisfied = new IsAutoSatisfiable (arbitrator);
		prerequisite.process (satisfied);
		return satisfied.satisfiable;
	}
	
	private final CharmLearnableArbitrator arbitrator;
	public boolean satisfiable = true;
	
	public IsAutoSatisfiable (CharmLearnableArbitrator learnArbitrator)
	{
		this.arbitrator = learnArbitrator;
	}
	
	@Override
	public void requiresMagicAttributes (MagicAttribute attribute, int count)
	{
		this.satisfiable = false;
	}
	
	@Override
	public void requiresMagicAttributesFromTree (TreeReference tree, MagicAttribute attribute, int count)
	{
		this.satisfiable = false;
	}
	
	@Override
	public void requiresCharm (Charm prerequisite)
	{
		this.satisfiable = arbitrator.isLearnable (prerequisite);
	}
	
	@Override
	public void requiresCharmFromSelection (Charm[] prerequisites, int count)
	{
		int knowable = 0;
		for (Charm charm : prerequisites)
		{
			if (arbitrator.isLearnable (charm))
			{
				knowable++;
			}
			if (knowable >= count)
			{
				this.satisfiable = true;
			}
		}
		this.satisfiable = false;
	}
	
	@Override
	public void requiresCharmsOfTraits (List<RequiredTraitType> traits, CategoryReference category, int count, int minimumEssence)
	{
		this.satisfiable = false;
	}
	
	@Override
	public void requiresCharmsOfAnyOneTrait (int count)
	{
		this.satisfiable = false;
	}
}
