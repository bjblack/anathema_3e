package net.sf.anathema.hero.charms.display.tooltip;

import net.sf.anathema.hero.magic.display.tooltip.MagicTooltipContributor;
import net.sf.anathema.magic.data.Charm;
import net.sf.anathema.library.resources.Resources;
import net.sf.anathema.library.tooltip.ConfigurableTooltip;
import net.sf.anathema.magic.data.Magic;

public class CharmDurationContributor implements MagicTooltipContributor
{
	private final Resources resources;
	
	public CharmDurationContributor (Resources resources)
	{
		this.resources = resources;
	}
	
	@Override
	public void buildStringForMagic (ConfigurableTooltip tooltip, Magic magic, Object details)
	{
		if (magic instanceof Charm)
		{
			String durationLabel = resources.getString ("CharmTreeView.ToolTip.Duration");
			String durationText = ( (Charm) magic).getDuration ().getText ();
			tooltip.appendLine (durationLabel, durationText);
		}
	}
}
