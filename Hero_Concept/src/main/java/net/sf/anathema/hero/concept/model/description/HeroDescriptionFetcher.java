package net.sf.anathema.hero.concept.model.description;

import net.sf.anathema.hero.individual.model.Hero;

public class HeroDescriptionFetcher
{
	public static final HeroDescription NOT_REGISTERED = null;
	
	public static HeroDescription fetch (Hero hero)
	{
		return hero.getModel (HeroDescription.ID);
	}
}
