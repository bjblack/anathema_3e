package net.sf.anathema.hero.charms.sheet.content.stats;

import net.sf.anathema.hero.charms.display.tooltip.CharmTypeContributor;
import net.sf.anathema.hero.magic.display.tooltip.IMagicSourceStringBuilder;
import net.sf.anathema.hero.magic.display.tooltip.source.MagicSourceContributor;
import net.sf.anathema.hero.magic.sheet.content.stats.AbstractMagicStats;
import net.sf.anathema.library.resources.Resources;
import net.sf.anathema.magic.data.Charm;
import net.sf.anathema.magic.data.CharmType;
import net.sf.anathema.magic.data.attribute.MagicAttribute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractCharmStats extends AbstractMagicStats<Charm>
{
	public AbstractCharmStats (Charm magic)
	{
		super (magic);
	}
	
	@Override
	public String getGroupName (Resources resources)
	{
		return resources.getString (getMagic ().getTreeReference ().name.text);
	}
	
	@Override
	public String getType (Resources resources)
	{
		CharmType charmType = getMagic ().getCharmType ();
		return new CharmTypeContributor (resources).createTypeString (charmType);
	}
	
	@Override
	public String getDurationString (Resources resources)
	{
		return getMagic ().getDuration ().getText ();
	}
	
	@Override
	public String getSourceString (Resources resources)
	{
		IMagicSourceStringBuilder<Charm> stringBuilder = new MagicSourceContributor<> (resources);
		return stringBuilder.createShortSourceString (getMagic ());
	}
	
	protected Collection<String> getDetailKeys ()
	{
		List<String> details = new ArrayList<> ();
		for (MagicAttribute attribute : getMagic ().getAttributes ())
		{
			String attributeId = attribute.getId ();
			if (attribute.isVisualized ())
			{
				details.add ("Keyword." + attributeId);
			}
		}
		return details;
	}
	
	@Override
	public Collection<String> getDetailStrings (final Resources resources)
	{
		Stream<String> keys = getDetailKeys ().stream ();
		return keys.map (resources::getString).collect (Collectors.toList ());
	}
}
