package net.sf.anathema.hero.charms.display.tree;

import net.sf.anathema.magic.data.Charm;
import net.sf.anathema.hero.charms.display.coloring.CharmDye;
import net.sf.anathema.hero.charms.model.learn.CharmLearnAdapter;

import java.awt.Toolkit;

public final class CharmLearnVisualizer extends CharmLearnAdapter
{
	private final CharmDye dye;
	
	public CharmLearnVisualizer (CharmDye dye)
	{
		this.dye = dye;
	}
	
	@Override
	public void charmLearned (Charm charm)
	{
		dye.colorCharm (charm);
	}
	
	@Override
	public void charmForgotten (Charm charm)
	{
		dye.colorCharm (charm);
	}
	
	@Override
	public void charmNotLearnable (Charm charm)
	{
		Toolkit.getDefaultToolkit ().beep ();
	}
	
	@Override
	public void charmNotForgettable (Charm charm)
	{
		Toolkit.getDefaultToolkit ().beep ();
	}
}
