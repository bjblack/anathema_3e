package net.sf.anathema.character.equipment.impl.character.model.stats;

import net.sf.anathema.character.equipment.impl.creation.model.WeaponTag;
import net.sf.anathema.character.equipment.item.model.ICollectionFactory;
import net.sf.anathema.character.generic.traits.types.AbilityType;

public class RangedWeaponStats extends AbstractWeaponStats {

  public RangedWeaponStats(ICollectionFactory collectionFactory) {
    super(collectionFactory);
  }

  @Override
  protected AbilityType getCombatTrait() {
    return hasTag(WeaponTag.Thrown) ? AbilityType.Thrown : AbilityType.Archery;
  }
}