package net.sf.anathema.hero.magic.display.tooltip;

import net.sf.anathema.library.resources.Resources;
import net.sf.anathema.library.tooltip.ConfigurableTooltip;
import net.sf.anathema.magic.data.Magic;

public class ScreenDisplayInfoContributor extends MagicInfoStringBuilder implements MagicTooltipContributor
{
	private Resources resources;
	
	public ScreenDisplayInfoContributor (Resources resources)
	{
		super (resources, new CostStringBuilder (resources, "CharmTreeView.ToolTip.Mote", "CharmTreeView.ToolTip.Motes"),
		new CostStringBuilder (resources, "CharmTreeView.ToolTip.SorcerousMote", "CharmTreeView.ToolTip.SorcerousMotes"),
		new CostStringBuilder (resources, "WillpowerType.Name"),
		new HealthCostStringBuilder (resources, "CharmTreeView.ToolTip.HealthLevel", "CharmTreeView.ToolTip.HealthLevels"),
		new CostStringBuilder (resources, "CharmTreeView.ToolTip.ExperiencePoint", "CharmTreeView.ToolTip.ExperiencePoints"));
		this.resources = resources;
	}
	
	@Override
	public void buildStringForMagic (ConfigurableTooltip tooltip, Magic magic, Object details)
	{
		String label = resources.getString ("CharmTreeView.ToolTip.Cost");
		String text = createCostString (magic);
		tooltip.appendLine (label, text);
	}
}
