package net.sf.anathema.hero.charms.model.special.learning.multilearn;

import net.sf.anathema.magic.data.reference.CharmName;

public class StaticMultiLearnableCharm extends AbstractMultiLearnableCharm
{
	private final int learnCount;
	
	public StaticMultiLearnableCharm (CharmName charmId, int learnCount)
	{
		super (charmId);
		this.learnCount = learnCount;
	}
	
	@Override
	public int getAbsoluteLearnLimit ()
	{
		return learnCount;
	}
	
	@Override
	public int getMaximumLearnCount (LearnRangeContext context)
	{
		return learnCount;
	}
	
	public String toString ()
	{
		return "[" + getCharmName () + ";" + learnCount + "]";
	}
}
