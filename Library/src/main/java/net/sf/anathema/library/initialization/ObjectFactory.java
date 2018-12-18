package net.sf.anathema.library.initialization;

import java.lang.annotation.Annotation;
import java.util.Collection;

public interface ObjectFactory
{
	<T> Collection<T> instantiateOrdered (Class<? extends Annotation> annotation, Object... parameter) throws InitializationException;
	
	<T> Collection<T> instantiateAll (Class<? extends Annotation> annotation, Object... parameter) throws InitializationException;
	
	<T> Collection<T> instantiateAllImplementers (Class<T> interfaceClass, Object... parameter);
	
	<T> Collection<T> instantiateAllImplementersOrdered (Class<T> interfaceClass, Object... parameter);
}
