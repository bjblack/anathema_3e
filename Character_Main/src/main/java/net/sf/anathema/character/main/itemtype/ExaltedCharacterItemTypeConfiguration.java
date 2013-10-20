package net.sf.anathema.character.main.itemtype;

import net.sf.anathema.framework.item.IItemType;
import net.sf.anathema.framework.module.ItemTypeConfiguration;
import net.sf.anathema.framework.repository.ItemType;
import net.sf.anathema.framework.repository.RepositoryConfiguration;
import net.sf.anathema.initialization.RegisteredItemTypeConfiguration;

@RegisteredItemTypeConfiguration
public class ExaltedCharacterItemTypeConfiguration implements ItemTypeConfiguration {

  public static final String CHARACTER_ITEM_TYPE_ID = "ExaltedCharacter";
  private static final IItemType ITEM_TYPE = new ItemType(CHARACTER_ITEM_TYPE_ID, new RepositoryConfiguration(".ecg", "ExaltedCharacter/", "main"));

  @Override
  public final IItemType getItemType() {
    return ITEM_TYPE;
  }
}