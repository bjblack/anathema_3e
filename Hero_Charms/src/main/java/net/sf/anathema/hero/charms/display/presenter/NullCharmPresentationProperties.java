package net.sf.anathema.hero.charms.display.presenter;

import net.sf.anathema.hero.individual.splat.HeroType;
import net.sf.anathema.library.initialization.DoNotInstantiateAutomatically;

@DoNotInstantiateAutomatically
public class NullCharmPresentationProperties extends AbstractCharmPresentationProperties
{
	@Override
	public boolean supportsCharacterType (HeroType type)
	{
		return true;
	}
}
