package net.sf.anathema.hero.charms.model.special.learning.multilearn;

import net.sf.anathema.magic.data.Charm;
import net.sf.anathema.hero.charms.display.special.CharmSpecialistImpl;
import net.sf.anathema.hero.charms.model.CharmTraitRequirementCalculator;
import net.sf.anathema.hero.charms.model.CharmTraitRequirementChecker;
import net.sf.anathema.hero.charms.model.TraitStateFetcher;
import net.sf.anathema.hero.charms.model.learn.CharmLearnableArbitrator;
import net.sf.anathema.hero.charms.model.special.CharmSpecialist;
import net.sf.anathema.hero.charms.model.special.ISpecialCharmLearnListener;
import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.traits.model.TraitType;
import net.sf.anathema.hero.traits.model.Trait;
import net.sf.anathema.hero.traits.model.TraitModelFetcher;
import net.sf.anathema.hero.traits.model.event.TraitChangeFlavor;
import net.sf.anathema.hero.traits.model.rules.LimitedTraitImpl;
import net.sf.anathema.hero.traits.model.state.IncrementChecker;
import net.sf.anathema.hero.traits.template.TraitTemplate;
import net.sf.anathema.hero.traits.template.TraitTemplateFactory;
import net.sf.anathema.library.number.Range;

import org.jmock.example.announcer.Announcer;

public class MultiLearnableCharmSpecialsImpl implements MultiLearnCharmSpecials
{
	private final Announcer<ISpecialCharmLearnListener> control = Announcer.to (ISpecialCharmLearnListener.class);
	private final Trait trait;
	private Charm charm;
	private IMultiLearnableCharm specialCharm;
	private CharmSpecialist specialist;
	private CharmLearnableArbitrator arbitrator;
	private Hero hero;
	
	public MultiLearnableCharmSpecialsImpl (Hero hero, Charm charm, IMultiLearnableCharm specialCharm,
	CharmLearnableArbitrator arbitrator)
	{
		this.hero = hero;
		this.specialist = new CharmSpecialistImpl (hero);
		this.charm = charm;
		this.specialCharm = specialCharm;
		this.arbitrator = arbitrator;
		TraitTemplate template = TraitTemplateFactory.createStaticLimitedTemplate (0, specialCharm.getAbsoluteLearnLimit ());
		this.trait = new LimitedTraitImpl (hero, new TraitType (charm.getName ().text), template, new MultiLearnableIncrementChecker ());
		this.trait.addCurrentValueListener (this::fireLearnCountChanged);
		hero.getChangeAnnouncer ().addListener (flavor ->
		{
			if (flavor instanceof TraitChangeFlavor)
			{
				validateLearnCount ();
			}
		}
		);
	}
	
	@Override
	public void forget ()
	{
		trait.setCurrentValue (0);
	}
	
	@Override
	public void learn (boolean experienced)
	{
		int minimumLearnCount = specialCharm.getMinimumLearnCount (createLearnRangeContext ());
		if (experienced)
		{
			if (getCurrentLearnCount () == 0)
			{
				trait.setExperiencedValue (minimumLearnCount);
			}
		}
		else if (getCreationLearnCount () == 0)
		{
			trait.setCreationValue (minimumLearnCount);
		}
	}
	
	@Override
	public int getCreationLearnCount ()
	{
		return trait.getCreationValue ();
	}
	
	@Override
	public void addSpecialCharmLearnListener (ISpecialCharmLearnListener listener)
	{
		control.addListener (listener);
	}
	
	private void fireLearnCountChanged (int learnCount)
	{
		control.announce ().learnCountChanged (learnCount);
	}
	
	@Override
	public Charm getCharm ()
	{
		return charm;
	}
	
	@Override
	public Trait getCategory ()
	{
		return trait;
	}
	
	@Override
	public int getCurrentLearnCount ()
	{
		return trait.getCurrentValue ();
	}
	
	@Override
	public void setCurrentLearnCount (int newValue)
	{
		trait.setCurrentValue (newValue);
	}
	
	private void validateLearnCount ()
	{
		if (trait.getCurrentValue () == 0)
		{
			return;
		}
		Range range = getRange ();
		if (trait.getCurrentValue () < range.getLowerBound ())
		{
			setCurrentLearnCount (range.getLowerBound ());
		}
		if (trait.getCurrentValue () > range.getUpperBound ())
		{
			setCurrentLearnCount (range.getUpperBound ());
		}
	}
	
	private Range getRange ()
	{
		int minValue = specialCharm.getMinimumLearnCount (createLearnRangeContext ());
		int maxValue = specialCharm.getMaximumLearnCount (createLearnRangeContext ());
		return Range.bounded (minValue, maxValue);
	}
	
	private LearnRangeContext createLearnRangeContext ()
	{
		CharmTraitRequirementChecker requirementChecker = new CharmTraitRequirementChecker (
		new CharmTraitRequirementCalculator (new TraitStateFetcher (hero)), specialist.getTraits ());
		return new LearnRangeContext (TraitModelFetcher.fetch (hero), requirementChecker, charm);
	}
	
	private class MultiLearnableIncrementChecker implements IncrementChecker
	{
		@Override
		public boolean isValidIncrement (int increment)
		{
			int incrementedValue = MultiLearnableCharmSpecialsImpl.this.trait.getCurrentValue () + increment;
			if (incrementedValue == 0)
			{
				return true;
			}
			boolean learnable = arbitrator.isLearnable (charm);
			return learnable && getRange ().contains (incrementedValue);
		}
	}
}
