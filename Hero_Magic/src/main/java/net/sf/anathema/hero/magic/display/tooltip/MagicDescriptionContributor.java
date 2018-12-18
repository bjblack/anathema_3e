package net.sf.anathema.hero.magic.display.tooltip;

import net.sf.anathema.library.tooltip.ConfigurableTooltip;
import net.sf.anathema.magic.data.Magic;
import net.sf.anathema.magic.description.model.MagicDescription;
import net.sf.anathema.magic.description.model.MagicDescriptionProvider;

public class MagicDescriptionContributor implements MagicTooltipContributor
{
	private MagicDescriptionProvider magicDescriptionProvider;
	
	public MagicDescriptionContributor (MagicDescriptionProvider magicDescriptionProvider)
	{
		this.magicDescriptionProvider = magicDescriptionProvider;
	}
	
	public void buildStringForMagicWithoutSpecials (ConfigurableTooltip tooltip, Magic magic)
	{
		this.buildStringForMagic (tooltip, magic, null);
	}
	
	@Override
	public void buildStringForMagic (ConfigurableTooltip tooltip, Magic magic, Object specialDetails)
	{
		MagicDescription charmDescription = magicDescriptionProvider.getCharmDescription (magic);
		if (charmDescription.isEmpty ())
		{
			return;
		}
		for (String paragraph : charmDescription.getParagraphs ())
		{
			tooltip.appendDescriptiveLine (paragraph);
		}
	}
}
