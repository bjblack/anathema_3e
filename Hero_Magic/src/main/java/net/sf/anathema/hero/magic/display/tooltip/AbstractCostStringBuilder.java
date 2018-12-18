package net.sf.anathema.hero.magic.display.tooltip;

import net.sf.anathema.magic.data.cost.Cost;
import net.sf.anathema.library.resources.Resources;
import net.sf.anathema.library.tooltip.ConfigurableTooltip;

import com.google.common.base.Strings;

import static net.sf.anathema.library.lang.StringUtilities.EMPTY_STRING;

public abstract class AbstractCostStringBuilder<T extends Cost> implements ICostStringBuilder<T>
{
	private final Resources resources;
	private final String singularKey;
	private final String pluralKey;
	
	public AbstractCostStringBuilder (Resources resources, String singularKey, String pluralKey)
	{
		this.resources = resources;
		this.singularKey = singularKey;
		this.pluralKey = pluralKey;
	}
	
	@Override
	public String getCostString (T cost)
	{
		String value = cost.getCost ();
		String text = cost.getText ();
		String costString = EMPTY_STRING;
		try
		{
			int intValue = Integer.parseInt (value);
			if (intValue != 0)
			{
				costString = getQualifiedValueString (cost);
			}
		}
		catch (NumberFormatException e)
		{
			if (!Strings.isNullOrEmpty (value))
			{
				costString = value;
			}
		}
		if (!Strings.isNullOrEmpty (text))
		{
			costString = costString.concat (ConfigurableTooltip.Space + text);
		}
		return costString;
	}
	
	protected final Resources getResources ()
	{
		return resources;
	}
	
	protected String getSingularKey ()
	{
		return singularKey;
	}
	
	protected String getPluralKey ()
	{
		return pluralKey;
	}
	
	protected abstract String getQualifiedValueString (T cost);
}
