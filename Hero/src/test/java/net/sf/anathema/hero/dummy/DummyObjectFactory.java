package net.sf.anathema.hero.dummy;

import net.sf.anathema.library.initialization.InitializationException;
import net.sf.anathema.library.initialization.ObjectFactory;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;

public class DummyObjectFactory implements ObjectFactory
{
	@Override
	public <T> Collection<T> instantiateOrdered (Class<? extends Annotation> annotation, Object... parameter) throws InitializationException
	{
		return new ArrayList<> ();
	}
	
	@Override
	public <T> Collection<T> instantiateAll (Class<? extends Annotation> annotation, Object... parameter) throws InitializationException
	{
		return new ArrayList<> ();
	}
	
	@Override
	public <T> Collection<T> instantiateAllImplementers (Class<T> interfaceClass, Object... parameter)
	{
		return new ArrayList<> ();
	}
	
	@Override
	public <T> Collection<T> instantiateAllImplementersOrdered (Class<T> interfaceClass, Object... parameter)
	{
		return new ArrayList<> ();
	}
}
