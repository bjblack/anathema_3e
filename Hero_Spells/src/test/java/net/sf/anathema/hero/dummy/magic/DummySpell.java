package net.sf.anathema.hero.dummy.magic;

import net.sf.anathema.magic.data.cost.CostList;
import net.sf.anathema.magic.data.reference.SpellName;
import net.sf.anathema.hero.spells.data.CircleType;
import net.sf.anathema.hero.spells.data.Spell;
import net.sf.anathema.library.exception.NotYetImplementedException;
import net.sf.anathema.library.identifier.Identifier;
import net.sf.anathema.magic.data.attribute.MagicAttribute;
import net.sf.anathema.magic.data.source.SourceBook;

import java.util.Collections;
import java.util.List;

public class DummySpell implements Spell
{
	public DummySpell ()
	{
		// nothing to do
	}
	@Override
	public CircleType getCircleType ()
	{
		return null;
	}
	
	@Override
	public SpellName getName ()
	{
		return null;
	}
	
	@Override
	public Iterable<MagicAttribute> getAttributes ()
	{
		return Collections.emptyList ();
	}
	
	@Override
	public List<SourceBook> getSources ()
	{
		throw new NotYetImplementedException ();
	}
	
	@Override
	public CostList getTemporaryCost ()
	{
		throw new NotYetImplementedException ();
	}
	
	@Override
	public boolean hasAttribute (Identifier attribute)
	{
		return false;
	}
	
	@Override
	public String getDuration ()
	{
		throw new NotYetImplementedException ();
	}
	
	@Override
	public List<String> getKeywords ()
	{
		throw new NotYetImplementedException ();
	}
}
