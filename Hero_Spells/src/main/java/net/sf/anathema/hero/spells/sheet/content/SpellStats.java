package net.sf.anathema.hero.spells.sheet.content;

import com.google.common.collect.Lists;
import net.sf.anathema.hero.magic.display.tooltip.IMagicSourceStringBuilder;
import net.sf.anathema.hero.magic.display.tooltip.source.MagicSourceContributor;
import net.sf.anathema.hero.magic.sheet.content.stats.AbstractMagicStats;
import net.sf.anathema.hero.spells.data.Spell;
import net.sf.anathema.library.resources.Resources;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SpellStats extends AbstractMagicStats<Spell>
{
	public SpellStats (Spell spell)
	{
		super (spell);
	}
	
	@Override
	public String getGroupName (Resources resources)
	{
		return resources.getString ("Sheet.Magic.Group.Sorcery");
	}
	
	@Override
	public String getType (Resources resources)
	{
		return resources.getString (getMagic ().getCircleType ().getId ());
	}
	
	@Override
	public String getDurationString (Resources resources)
	{
		return "-";
	}
	
	@Override
	public String getSourceString (Resources resources)
	{
		IMagicSourceStringBuilder<Spell> stringBuilder = new MagicSourceContributor<> (resources);
		return stringBuilder.createShortSourceString (getMagic ());
	}
	
	protected Collection<String> getDetailKeys ()
	{
		String duration = getMagic ().getDuration ();
		if (duration != null)
		{
			return Lists.newArrayList ("Spells.Duration." + duration);
		}
		return new ArrayList<> ();
	}
	
	@Override
	public Collection<String> getDetailStrings (final Resources resources)
	{
		Stream<String> keys = getDetailKeys ().stream ();
		return keys.map (resources::getString).collect (Collectors.toList ());
	}
	
	@Override
	public String getNameString (Resources resources)
	{
		return resources.getString (getMagic ().getName ().text);
	}
	
	@SuppressWarnings ("SimplifiableIfStatement")
	@Override
	public boolean equals (Object obj)
	{
		if (! (obj instanceof SpellStats))
		{
			return false;
		}
		SpellStats other = (SpellStats) obj;
		return other.getMagic ().getCircleType ().equals (getMagic ().getCircleType ()) && other.getMagic ().getName ().equals (
		getMagic ().getName ());
	}
	
	@Override
	public int hashCode ()
	{
		return new HashCodeBuilder ().append (getMagic ().getCircleType ()).append (getMagic ().getName ().text).build ();
	}
}
