package net.sf.anathema.hero.application.type;

import com.google.common.base.Functions;
import net.sf.anathema.hero.environment.herotype.ForCharacterType;
import net.sf.anathema.hero.individual.splat.CharacterType;
import net.sf.anathema.library.initialization.ObjectFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CharacterSpecificsMap<TYPE> {
  private final Map<String, TYPE> objectsByCharacterType = new HashMap<>();
  private final TYPE defaultValue;

  public CharacterSpecificsMap(ObjectFactory objectFactory, Class<TYPE> typeClass, TYPE defaultValue) {
    this.defaultValue = defaultValue;
    Collection<TYPE> allObjects = objectFactory.instantiateAllImplementers(typeClass);
    for (TYPE object : allObjects) {
      String applicableType = object.getClass().getAnnotation(ForCharacterType.class).value();
      objectsByCharacterType.put(applicableType, object);
    }
  }

  public TYPE getForCharacterType(CharacterType type) {
    return Functions.forMap(objectsByCharacterType, defaultValue).apply(type.getId());
  }
}