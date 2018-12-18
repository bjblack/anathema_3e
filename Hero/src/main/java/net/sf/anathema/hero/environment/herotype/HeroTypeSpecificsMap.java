package net.sf.anathema.hero.environment.herotype;

import net.sf.anathema.hero.individual.splat.HeroType;
import net.sf.anathema.library.initialization.ObjectFactory;

import com.google.common.base.Functions;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class HeroTypeSpecificsMap<TYPE>
{
	private final Map<String, TYPE> objectsByCharacterType = new HashMap<> ();
	private final TYPE defaultValue;
	
	public HeroTypeSpecificsMap (ObjectFactory objectFactory, Class<TYPE> typeClass, TYPE defaultValue)
	{
		this.defaultValue = defaultValue;
		Collection<TYPE> allObjects = objectFactory.instantiateAllImplementers (typeClass);
		for (TYPE object : allObjects)
		{
			String applicableType = object.getClass ().getAnnotation (ForCharacterType.class).value ();
			objectsByCharacterType.put (applicableType, object);
		}
	}
	
	public TYPE getForCharacterType (HeroType type)
	{
		return Functions.forMap (objectsByCharacterType, defaultValue).apply (type.getId ());
	}
}
