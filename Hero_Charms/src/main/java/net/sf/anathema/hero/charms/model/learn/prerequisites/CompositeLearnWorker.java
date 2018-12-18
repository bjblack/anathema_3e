package net.sf.anathema.hero.charms.model.learn.prerequisites;

import net.sf.anathema.magic.data.Charm;
import net.sf.anathema.magic.data.reference.CategoryReference;
import net.sf.anathema.magic.data.reference.TreeReference;
import net.sf.anathema.hero.charms.model.learn.CharmLearnArbitrator;
import net.sf.anathema.hero.charms.model.learn.CharmLearnWorker;
import net.sf.anathema.hero.traits.model.TraitType;
import net.sf.anathema.magic.data.attribute.MagicAttribute;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CompositeLearnWorker implements CharmLearnWorker
{
	private final CharmLearnArbitrator learnArbitrator;
	private final Set<Charm> forgottenCharm = new HashSet<> ();
	
	public CompositeLearnWorker (CharmLearnArbitrator learnArbitrator)
	{
		this.learnArbitrator = learnArbitrator;
	}
	
	@Override
	public boolean isLearned (Charm charm)
	{
		return learnArbitrator.isLearned (charm) && !forgottenCharm.contains (charm);
	}
	
	@Override
	public boolean hasLearnedThresholdCharmsWithKeyword (MagicAttribute attribute,
	int threshold)
	{
		return learnArbitrator.hasLearnedThresholdCharmsWithKeyword (attribute, threshold);
	}
	
	@Override
	public boolean hasLearnedThresholdCharmsWithKeywordFromTree (
	TreeReference tree, MagicAttribute attribute, int threshold)
	{
		return learnArbitrator.hasLearnedThresholdCharmsWithKeywordFromTree (tree,  attribute, threshold);
	}
	
	@Override
	public boolean hasLearnedThresholdCharmsOfTrait (List<TraitType> traits, CategoryReference category,
	int threshold, int minimumEssence)
	{
		return learnArbitrator.hasLearnedThresholdCharmsOfTrait (traits, category, threshold, minimumEssence);
	}
	
	@Override
	public boolean hasLearnedThresholdCharmsOfAnyOneTrait (int threshold)
	{
		return learnArbitrator.hasLearnedThresholdCharmsOfAnyOneTrait (threshold);
	}
	
	@Override
	public void forget (Charm charm)
	{
		forgottenCharm.add (charm);
	}
	
	public Set<Charm> getForgottenCharms ()
	{
		return new HashSet<> (forgottenCharm);
	}
}
