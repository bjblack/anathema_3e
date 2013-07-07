package net.sf.anathema.character.equipment.character;

import net.sf.anathema.character.equipment.character.model.IEquipmentItem;
import net.sf.anathema.character.main.equipment.weapon.IEquipmentStats;

public interface IEquipmentStringBuilder {

  String createString(IEquipmentItem item, IEquipmentStats equipment);
}