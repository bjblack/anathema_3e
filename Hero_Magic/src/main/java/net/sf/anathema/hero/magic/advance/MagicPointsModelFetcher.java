package net.sf.anathema.hero.magic.advance;

import net.sf.anathema.hero.individual.model.Hero;

public class MagicPointsModelFetcher
{
	public static MagicPointsModel fetch (Hero hero)
	{
		return hero.getModel (MagicPointsModel.ID);
	}
}
