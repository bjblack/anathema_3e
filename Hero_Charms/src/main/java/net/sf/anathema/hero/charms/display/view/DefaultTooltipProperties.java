package net.sf.anathema.hero.charms.display.view;

import net.sf.anathema.magic.data.Charm;
import net.sf.anathema.magic.data.reference.CharmName;
import net.sf.anathema.hero.charms.display.tooltip.CharmTooltipBuilderImpl;
import net.sf.anathema.hero.charms.model.CharmMap;
import net.sf.anathema.hero.charms.model.special.CharmSpecialLearning;
import net.sf.anathema.hero.charms.model.special.NullSpecialCharm;
import net.sf.anathema.library.resources.Resources;
import net.sf.anathema.library.tooltip.ConfigurableTooltip;
import net.sf.anathema.magic.description.model.MagicDescriptionProvider;
import net.sf.anathema.platform.tree.display.ToolTipProperties;

import com.google.common.base.Preconditions;

import static net.sf.anathema.hero.charms.display.view.NodeIds.toCharmName;

public class DefaultTooltipProperties implements ToolTipProperties
{
	private final FunctionalNodeProperties treeProperties;
	private final CharmMap map;
	private final CharmTooltipBuilderImpl tooltipTextProvider;
	private SpecialCharmSet specialCharmSet;
	
	public DefaultTooltipProperties (FunctionalNodeProperties treeProperties, CharmMap map, Resources resources,
	MagicDescriptionProvider magicDescriptionProvider, SpecialCharmSet specialCharmSet)
	{
		this.treeProperties = treeProperties;
		this.map = map;
		this.specialCharmSet = specialCharmSet;
		this.tooltipTextProvider = new CharmTooltipBuilderImpl (resources, magicDescriptionProvider);
	}
	
	@Override
	public void configureTooltip (String nodeId, ConfigurableTooltip tooltip)
	{
		if (treeProperties.isRequirementNode (nodeId))
		{
			tooltip.showNoTooltip ();
			return;
		}
		Charm charm = findNonNullCharm (toCharmName (nodeId));
		CharmSpecialLearning specialCharm = getSpecialCharm (toCharmName (nodeId));
		tooltipTextProvider.configureTooltipWithSpecials (charm, specialCharm, tooltip);
	}
	
	private Charm findNonNullCharm (CharmName charmId)
	{
		Charm charm = map.getCharmById (charmId);
		Preconditions.checkNotNull (charm, "Charm with id '" + charmId + "' not found.");
		return charm;
	}
	
	private CharmSpecialLearning getSpecialCharm (CharmName charmName)
	{
		for (CharmSpecialLearning special : specialCharmSet)
		{
			if (special.getCharmName ().equals (charmName))
			{
				return special;
			}
		}
		return new NullSpecialCharm ();
	}
}
