package net.sf.anathema.hero.concept.dummy;

import net.sf.anathema.hero.concept.model.concept.CasteCollection;
import net.sf.anathema.hero.concept.model.concept.CasteType;
import net.sf.anathema.hero.individual.splat.SplatType;

import java.util.Collection;
import java.util.Collections;

public class NullCasteCollection implements CasteCollection
{
	@Override
	public Collection<CasteType> getAllCasteTypes (SplatType template)
	{
		return Collections.emptyList ();
	}
	
	@Override
	public CasteType getById (String casteTypeId)
	{
		return CasteType.NULL_CASTE_TYPE;
	}
	
	@Override
	public boolean isEmpty ()
	{
		return true;
	}
}
