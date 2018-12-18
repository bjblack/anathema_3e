package net.sf.anathema.hero.charms.display.coloring;

import net.sf.anathema.magic.data.Charm;

public class SimpleCharmBrush implements CharmBrush
{
	private final CharmColoring coloring;
	
	public SimpleCharmBrush (CharmColoring coloring)
	{
		this.coloring = coloring;
	}
	
	public void color (Charm charm)
	{
		coloring.colorCharm (charm);
	}
}
