package net.sf.anathema.hero.charms.display.tooltip;

import net.sf.anathema.hero.magic.display.tooltip.MagicTooltipContributor;
import net.sf.anathema.magic.data.Charm;
import net.sf.anathema.magic.data.CharmType;
import net.sf.anathema.library.resources.Resources;
import net.sf.anathema.library.tooltip.ConfigurableTooltip;
import net.sf.anathema.magic.data.Magic;

public class CharmTypeContributor implements ICharmTypeStringBuilder, MagicTooltipContributor
{
	private final Resources resources;
	
	public CharmTypeContributor (Resources resources)
	{
		this.resources = resources;
	}
	
	@Override
	public void buildStringForMagic (ConfigurableTooltip tooltip, Magic magic, Object details)
	{
		if (magic instanceof Charm)
		{
			String label = resources.getString ("CharmTreeView.ToolTip.Type");
			String text = createTypeString ( ( (Charm) magic).getCharmType ());
			tooltip.appendLine (label, text);
		}
	}
	
	@Override
	public String createTypeString (CharmType charmType)
	{
		return getResources ().getString (charmType.getId ());
	}
	
	protected Resources getResources ()
	{
		return resources;
	}
}
