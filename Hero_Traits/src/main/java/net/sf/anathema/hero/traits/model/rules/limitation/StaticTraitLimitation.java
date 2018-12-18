package net.sf.anathema.hero.traits.model.rules.limitation;

import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.traits.model.TraitLimitation;

public class StaticTraitLimitation implements TraitLimitation
{
	private final int staticLimit;
	
	public StaticTraitLimitation (int staticLimit)
	{
		this.staticLimit = staticLimit;
	}
	
	@Override
	public int getAbsoluteLimit (Hero hero)
	{
		return staticLimit;
	}
	
	@Override
	public int getCurrentMaximum (Hero hero, boolean modified)
	{
		return staticLimit;
	}
}
