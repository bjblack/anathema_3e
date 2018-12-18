package net.sf.anathema.hero.charms.model.special.subeffects;

import net.sf.anathema.magic.data.Charm;
import net.sf.anathema.hero.charms.model.learn.IExtendedCharmLearnableArbitrator;
import net.sf.anathema.hero.charms.model.special.CharmSpecialist;

public class SubEffectCharmSpecialsImpl extends MultipleEffectCharmSpecialsImpl implements SubEffectCharmSpecials
{
	private final double pointCost;
	
	public SubEffectCharmSpecialsImpl (CharmSpecialist specialist, Charm charm, ISubEffectCharm visited, IExtendedCharmLearnableArbitrator arbitrator)
	{
		super (specialist, charm, visited, arbitrator);
		this.pointCost = visited.getPointCost ();
	}
	
	@Override
	public int getCreationLearnCount ()
	{
		return super.getCreationLearnCount () > 0 ? 1 : 0;
	}
	
	@Override
	public int getCurrentLearnCount ()
	{
		return super.getCurrentLearnCount () > 0 ? 1 : 0;
	}
	
	@Override
	public int getCreationLearnedSubEffectCount ()
	{
		int count = 0;
		for (SubEffect subeffect : getEffects ())
		{
			if (subeffect.isCreationLearned ())
			{
				count++;
			}
		}
		return count;
	}
	
	@Override
	public int getExperienceLearnedSubEffectCount ()
	{
		int count = 0;
		for (SubEffect subeffect : getEffects ())
		{
			if (subeffect.isLearned () && !subeffect.isCreationLearned ())
			{
				count++;
			}
		}
		return count;
	}
	
	@Override
	public double getPointCostPerEffect ()
	{
		return pointCost;
	}
}
