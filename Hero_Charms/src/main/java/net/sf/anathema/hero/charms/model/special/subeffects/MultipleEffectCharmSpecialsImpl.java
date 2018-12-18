package net.sf.anathema.hero.charms.model.special.subeffects;

import net.sf.anathema.magic.data.Charm;
import net.sf.anathema.hero.charms.model.learn.CharmLearnableArbitrator;
import net.sf.anathema.hero.charms.model.special.CharmSpecialist;
import net.sf.anathema.hero.charms.model.special.ISpecialCharmLearnListener;

import org.jmock.example.announcer.Announcer;

public class MultipleEffectCharmSpecialsImpl implements MultipleEffectCharmSpecials
{
	private final Charm charm;
	private final SubEffects subeffects;
	private final Announcer<ISpecialCharmLearnListener> control = Announcer.to (ISpecialCharmLearnListener.class);
	
	public MultipleEffectCharmSpecialsImpl (CharmSpecialist specialist, Charm charm, IMultipleEffectCharm visited, CharmLearnableArbitrator arbitrator)
	{
		this.charm = charm;
		this.subeffects = visited.buildSubEffects (specialist, arbitrator, charm);
		for (SubEffect subeffect : subeffects)
		{
			subeffect.addChangeListener (this::fireLearnCountChanged);
		}
	}
	
	@Override
	public void forget ()
	{
		for (SubEffect effect : subeffects)
		{
			effect.setLearned (false);
		}
	}
	
	@Override
	public void learn (boolean experienced)
	{
		SubEffect firstEffect = subeffects.getEffects ().get (0);
		if (experienced && getCurrentLearnCount () == 0)
		{
			firstEffect.setExperienceLearned (true);
		}
		else if (!experienced && getCreationLearnCount () == 0)
		{
			firstEffect.setCreationLearned (true);
		}
	}
	
	private void fireLearnCountChanged ()
	{
		control.announce ().learnCountChanged (getCurrentLearnCount ());
	}
	
	@Override
	public void addSpecialCharmLearnListener (ISpecialCharmLearnListener listener)
	{
		control.addListener (listener);
	}
	
	@Override
	public Charm getCharm ()
	{
		return charm;
	}
	
	@Override
	public Iterable<SubEffect> getEffects ()
	{
		return subeffects.getEffects ();
	}
	
	@Override
	public SubEffect getEffectById (final String id)
	{
		return subeffects.getById (id);
	}
	
	@Override
	public int getCreationLearnCount ()
	{
		int sum = 0;
		for (SubEffect subeffect : subeffects)
		{
			if (subeffect.isCreationLearned ())
			{
				sum++;
			}
		}
		return sum;
	}
	
	@Override
	public int getCurrentLearnCount ()
	{
		int sum = 0;
		for (SubEffect subeffect : subeffects)
		{
			if (subeffect.isLearned ())
			{
				sum++;
			}
		}
		return sum;
	}
	
	protected SubEffects getSubeffects ()
	{
		return subeffects;
	}
}
