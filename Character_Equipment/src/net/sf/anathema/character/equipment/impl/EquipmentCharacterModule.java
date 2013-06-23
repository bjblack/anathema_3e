package net.sf.anathema.character.equipment.impl;

import net.sf.anathema.character.equipment.IEquipmentAdditionalModelTemplate;
import net.sf.anathema.character.equipment.MaterialRules;
import net.sf.anathema.character.equipment.ReflectionMaterialRules;
import net.sf.anathema.character.equipment.impl.character.EquipmentAdditionalModelFactory;
import net.sf.anathema.character.equipment.impl.character.EquipmentAdditionalPersisterFactory;
import net.sf.anathema.character.equipment.impl.character.model.EquipmentAdditionalModelTemplate;
import net.sf.anathema.character.equipment.impl.item.model.gson.GsonEquipmentDatabase;
import net.sf.anathema.character.equipment.item.model.IEquipmentTemplateProvider;
import net.sf.anathema.character.generic.framework.ICharacterGenerics;
import net.sf.anathema.character.generic.framework.module.CharacterModule;
import net.sf.anathema.character.generic.framework.module.CharacterModuleAdapter;
import net.sf.anathema.initialization.InitializationException;
import net.sf.anathema.initialization.ObjectFactory;

import java.nio.file.Path;

import static net.sf.anathema.character.equipment.impl.item.model.gson.GsonEquipmentDatabase.DATABASE_FOLDER;

@CharacterModule
public class EquipmentCharacterModule extends CharacterModuleAdapter {

  @Override
  public void addAdditionalTemplateData(ICharacterGenerics characterGenerics) throws InitializationException {
    Path dataBaseDirectory = characterGenerics.getDataFileProvider().getDataBaseDirectory(DATABASE_FOLDER);
    EquipmentDirectAccess access = new EquipmentDirectAccess(dataBaseDirectory);
    ObjectFactory objectFactory = characterGenerics.getInstantiater();
    IEquipmentTemplateProvider equipmentDatabase = new GsonEquipmentDatabase(access);
    MaterialRules materialRules = new ReflectionMaterialRules(objectFactory);
    characterGenerics.getAdditionalModelFactoryRegistry()
                     .register(IEquipmentAdditionalModelTemplate.ID, new EquipmentAdditionalModelFactory(equipmentDatabase, materialRules));
    characterGenerics.getAdditonalPersisterFactoryRegistry()
                     .register(IEquipmentAdditionalModelTemplate.ID, new EquipmentAdditionalPersisterFactory());
    characterGenerics.getGlobalAdditionalTemplateRegistry()
                     .add(new EquipmentAdditionalModelTemplate(characterGenerics.getCharacterTypes(), objectFactory));
  }
}
