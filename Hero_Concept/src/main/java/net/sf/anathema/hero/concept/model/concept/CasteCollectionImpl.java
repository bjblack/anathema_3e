package net.sf.anathema.hero.concept.model.concept;

import net.sf.anathema.hero.concept.template.caste.CasteListTemplate;
import net.sf.anathema.hero.individual.splat.SplatType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CasteCollectionImpl implements CasteCollection
{
	private final List<CasteType> allTypes = new ArrayList<> ();
	
	public CasteCollectionImpl (CasteListTemplate template)
	{
		for (String caste : template.castes)
		{
			allTypes.add (new CasteTypeImpl (caste));
		}
	}
	
	@Override
	public CasteType getById (String casteTypeId)
	{
		for (CasteType type : allTypes)
		{
			if (type.getId ().equals (casteTypeId))
			{
				return type;
			}
		}
		throw new IllegalArgumentException ("No caste with found for id " + casteTypeId);
	}
	
	@Override
	public boolean isEmpty ()
	{
		return allTypes.isEmpty ();
	}
	
	@Override
	public Collection<CasteType> getAllCasteTypes (SplatType template)
	{
		return Collections.unmodifiableCollection (allTypes);
	}
}
