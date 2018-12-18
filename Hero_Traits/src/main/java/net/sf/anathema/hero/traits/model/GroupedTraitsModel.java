package net.sf.anathema.hero.traits.model;

import net.sf.anathema.hero.individual.model.HeroModel;
import net.sf.anathema.hero.traits.model.lists.IdentifiedTraitTypeList;
import net.sf.anathema.hero.traits.model.state.TraitStateMap;

public interface GroupedTraitsModel extends TraitMap, TraitStateMap, HeroModel
{
	IdentifiedTraitTypeList[] getGroups ();
	
	int getTraitMaximum ();
}
