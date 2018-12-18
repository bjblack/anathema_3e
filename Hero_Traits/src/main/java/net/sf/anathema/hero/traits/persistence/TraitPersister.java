package net.sf.anathema.hero.traits.persistence;

import net.sf.anathema.hero.traits.model.Trait;
import net.sf.anathema.hero.traits.model.state.TraitStateMap;
import net.sf.anathema.hero.traits.model.state.TraitStateType;
import net.sf.anathema.hero.traits.model.state.TraitStateTypes;

public class TraitPersister
{
	private TraitStateMap stateMap;
	
	public TraitPersister (TraitStateMap stateMap)
	{
		this.stateMap = stateMap;
	}
	
	public void save (Trait trait, TraitPto pto)
	{
		saveTraitName (trait, pto);
		saveCreationValue (trait, pto);
		saveExperiencedValue (trait, pto);
		saveFavoredValue (trait, pto);
	}
	
	private void saveFavoredValue (Trait trait, TraitPto pto)
	{
		pto.state = stateMap.getState (trait).getType ().getId ();
	}
	
	private void saveTraitName (Trait trait, TraitPto pto)
	{
		pto.name = trait.getType ().getId ();
	}
	
	private void saveCreationValue (Trait trait, TraitPto pto)
	{
		pto.creationValue = trait.getCreationValue ();
	}
	
	private void saveExperiencedValue (Trait trait, TraitPto pto)
	{
		int experienceValue = trait.getExperiencedValue ();
		if (experienceValue >= 0)
		{
			pto.experienceValue = experienceValue;
		}
	}
	
	public void load (Trait trait, TraitPto pto)
	{
		loadCreationValue (trait, pto);
		loadExperiencedValue (trait, pto);
		loadFavoredValue (trait, pto);
	}
	
	private void loadCreationValue (Trait trait, TraitPto pto)
	{
		trait.setUncheckedCreationValue (pto.creationValue);
	}
	
	private void loadExperiencedValue (Trait trait, TraitPto pto)
	{
		if (pto.experienceValue != null)
		{
			trait.setUncheckedExperiencedValue (pto.experienceValue);
		}
	}
	
	private void loadFavoredValue (Trait trait, TraitPto pto)
	{
		TraitStateType stateType = TraitStateTypes.withAllKnown ().lookUp (pto.state);
		stateMap.getState (trait).restore (stateType);
	}
}
