package net.sf.anathema.platform.initialization;

import net.sf.anathema.library.dependencies.Produces;
import net.sf.anathema.library.initialization.ObjectFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ReflectionFactoryMap<FACTORYTYPE>
{
	private final Map<Class, FACTORYTYPE> factories = new HashMap<> ();
	
	public ReflectionFactoryMap (ObjectFactory objectFactory, Class<FACTORYTYPE> factoryType, Object... parameters)
	{
		Collection<FACTORYTYPE> discoveredFactories = objectFactory.instantiateAllImplementers (factoryType, parameters);
		for (FACTORYTYPE factory : discoveredFactories)
		{
			Class producedClass = factory.getClass ().getAnnotation (Produces.class).value ();
			factories.put (producedClass, factory);
		}
	}
	
	public FACTORYTYPE get (Class producedClass)
	{
		return factories.get (producedClass);
	}
}
