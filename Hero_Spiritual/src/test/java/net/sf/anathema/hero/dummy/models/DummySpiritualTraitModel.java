package net.sf.anathema.hero.dummy.models;

import net.sf.anathema.hero.environment.HeroEnvironment;
import net.sf.anathema.hero.individual.change.ChangeAnnouncer;
import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.spiritual.model.traits.SpiritualTraitModel;
import net.sf.anathema.hero.traits.dummy.DummyTrait;
import net.sf.anathema.hero.traits.model.DefaultTraitMap;
import net.sf.anathema.hero.traits.model.TraitLimitation;
import net.sf.anathema.hero.traits.model.rules.limitation.StaticTraitLimitation;
import net.sf.anathema.library.identifier.Identifier;

import static net.sf.anathema.hero.traits.model.types.CommonTraitTypes.Essence;
import static net.sf.anathema.hero.traits.model.types.CommonTraitTypes.Willpower;

public class DummySpiritualTraitModel extends DefaultTraitMap implements SpiritualTraitModel
{
	public DummySpiritualTraitModel ()
	{
		addTraits (new DummyTrait (Essence, 2));
		addTraits (new DummyTrait (Willpower, 5));
	}
	
	@Override
	public int getEssenceCap (boolean modified)
	{
		return 10;
	}
	
	@Override
	public TraitLimitation getEssenceLimitation ()
	{
		return new StaticTraitLimitation (10);
	}
	
	@Override
	public Identifier getId ()
	{
		return ID;
	}
	
	@Override
	public void initialize (HeroEnvironment environment, Hero hero)
	{
		// nothing to do
	}
	
	@Override
	public void initializeListening (ChangeAnnouncer announcer)
	{
		// nothing to do
	}
}
