package net.sf.anathema.magic.data.prerequisite;

import net.sf.anathema.magic.data.reference.CategoryReference;
import net.sf.anathema.library.lang.ReflectionEqualsObject;

import java.util.List;

public class TraitGroupCharmPrerequisite extends ReflectionEqualsObject implements CharmPrerequisite
{
	private final List<RequiredTraitType> traits;
	private final int threshold;
	private final int minimumEssence;
	private final CategoryReference category;
	
	public TraitGroupCharmPrerequisite (List<RequiredTraitType> traits, CategoryReference category, int count, int minimumEssence)
	{
		this.traits = traits;
		this.threshold = count;
		this.minimumEssence = minimumEssence;
		this.category = category;
	}
	
	@Override
	public void process (PrerequisiteProcessor processor)
	{
		processor.requiresCharmsOfTraits (traits, category, threshold, minimumEssence);
	}
	
	@Override
	public void accept (PrerequisiteVisitor visitor)
	{
		visitor.visit (this);
	}
	
	@Override
	public boolean isSpecific ()
	{
		return false;
	}
	
	public List<RequiredTraitType> getTraits ()
	{
		return traits;
	}
	
	public int getThreshold ()
	{
		return threshold;
	}
	
	public int getEssenceMinimum ()
	{
		return minimumEssence;
	}
	
	public CategoryReference getCategory ()
	{
		return category;
	}
}
