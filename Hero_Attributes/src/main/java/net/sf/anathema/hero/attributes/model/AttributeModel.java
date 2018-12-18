package net.sf.anathema.hero.attributes.model;

import net.sf.anathema.hero.traits.model.GroupedTraitType;
import net.sf.anathema.hero.traits.model.GroupedTraitsModel;
import net.sf.anathema.hero.traits.model.TraitGroup;
import net.sf.anathema.hero.traits.model.TraitType;
import net.sf.anathema.hero.traits.model.state.TraitStateMap;
import net.sf.anathema.library.identifier.Identifier;
import net.sf.anathema.library.identifier.SimpleIdentifier;

import java.util.Collection;
import java.util.List;

public interface AttributeModel extends GroupedTraitsModel
{
	Identifier ID = new SimpleIdentifier ("Attributes");
	
	Collection<TraitGroup> getTraitGroups ();
	
	GroupedTraitType[] getAttributeGroups ();
	
	TraitStateMap getStateMap ();
	
	List<TraitType> getAllAttributeTypes ();
}
