package net.sf.anathema.character.main.abilities;

import net.sf.anathema.character.generic.traits.TraitType;
import net.sf.anathema.character.generic.traits.groups.IIdentifiedCasteTraitTypeGroup;
import net.sf.anathema.character.library.trait.Trait;
import net.sf.anathema.character.library.trait.TraitGroup;
import net.sf.anathema.character.library.trait.specialties.SpecialtiesConfiguration;

public interface AbilityModel {

  Trait[] getAllAbilities();

  TraitGroup[] getTraitGroups();

  Trait getTrait(TraitType type);

  IIdentifiedCasteTraitTypeGroup[] getAbilityTypeGroups();

  SpecialtiesConfiguration getSpecialtyConfiguration();
}
