package net.sf.anathema.hero.spiritual.display;

import net.sf.anathema.library.resources.Resources;

public class DefaultSpiritualTraitsViewProperties implements SpiritualTraitsViewProperties
{
	private Resources resources;
	
	public DefaultSpiritualTraitsViewProperties (Resources resources)
	{
		this.resources = resources;
	}
	
	@Override
	public String getWillpowerTitle ()
	{
		return resources.getString ("AdvantagesView.Willpower.Title");
	}
	
	@Override
	public String getEssenceTitle ()
	{
		return resources.getString ("AdvantagesView.Essence.Title");
	}
	
	public String getOverallHeader ()
	{
		return getWillpowerTitle () + " & " + getEssenceTitle ();
	}
}
