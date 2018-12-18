package net.sf.anathema.hero.magic.display.tooltip;

import net.sf.anathema.magic.data.cost.Cost;
import net.sf.anathema.library.lang.StringUtilities;
import net.sf.anathema.library.resources.Resources;
import net.sf.anathema.library.tooltip.ConfigurableTooltip;

public class CostStringBuilder extends AbstractCostStringBuilder<Cost>
{
	public CostStringBuilder (Resources resources, String key)
	{
		super (resources, key, key);
	}
	
	public CostStringBuilder (Resources resources, String singularKey, String pluralKey)
	{
		super (resources, singularKey, pluralKey);
	}
	
	@Override
	protected String getQualifiedValueString (Cost cost)
	{
		int intValue = Integer.parseInt (cost.getCost ());
		return intValue + ConfigurableTooltip.Space +
		 (cost.isPermanent () ? getResources ().getString ("Magic.Cost.Permanent") + ConfigurableTooltip.Space : StringUtilities.EMPTY_STRING) +
		getResources ().getString (intValue == 1 ? getSingularKey () : getPluralKey ());
	}
}
