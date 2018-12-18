package net.sf.anathema.hero.charms.model.special.subeffects;

import net.sf.anathema.magic.data.Charm;
import net.sf.anathema.hero.charms.model.learn.CharmLearnableArbitrator;
import net.sf.anathema.library.model.Condition;

public class ArbitratorLearnCondition implements Condition
{
	private final CharmLearnableArbitrator arbitrator;
	private final Charm charm;
	
	public ArbitratorLearnCondition (CharmLearnableArbitrator arbitrator, Charm charm)
	{
		this.arbitrator = arbitrator;
		this.charm = charm;
	}
	
	@Override
	public boolean isFulfilled ()
	{
		return arbitrator.isLearnable (charm);
	}
}
