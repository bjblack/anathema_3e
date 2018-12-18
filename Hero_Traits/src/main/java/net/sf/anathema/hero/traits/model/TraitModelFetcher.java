package net.sf.anathema.hero.traits.model;

import net.sf.anathema.hero.individual.model.Hero;

public class TraitModelFetcher
{
	public static TraitModel fetch (Hero hero)
	{
		return (TraitModel) hero.getModel (TraitModel.ID);
	}
}
