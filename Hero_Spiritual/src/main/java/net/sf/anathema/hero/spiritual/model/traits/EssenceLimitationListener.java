package net.sf.anathema.hero.spiritual.model.traits;

import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.traits.model.Trait;
import net.sf.anathema.hero.traits.model.TraitIterable;
import net.sf.anathema.library.event.IntegerChangedListener;

public class EssenceLimitationListener implements IntegerChangedListener
{
	private final TraitIterable traitIterable;
	private Hero hero;
	
	public EssenceLimitationListener (TraitIterable traitIterable, Hero hero)
	{
		this.traitIterable = traitIterable;
		this.hero = hero;
	}
	
	@Override
	public void valueChanged (int newValue)
	{
		if (!hero.isFullyLoaded ())
		{
			return;
		}
		for (Trait trait : traitIterable)
		{
			trait.resetCurrentValue ();
		}
	}
}
