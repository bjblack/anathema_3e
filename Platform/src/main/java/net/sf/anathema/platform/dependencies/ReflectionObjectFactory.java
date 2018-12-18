package net.sf.anathema.platform.dependencies;

import net.sf.anathema.library.initialization.DoNotInstantiateAutomatically;
import net.sf.anathema.library.initialization.InitializationException;
import net.sf.anathema.library.initialization.ObjectFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.google.common.collect.Collections2.filter;
import static java.text.MessageFormat.format;
import static org.apache.commons.lang3.reflect.ConstructorUtils.invokeConstructor;

public class ReflectionObjectFactory implements ObjectFactory
{
	private AnnotationFinder finder;
	private InterfaceFinder interfaceFinder;
	
	public ReflectionObjectFactory (AnnotationFinder finder, InterfaceFinder interfaceFinder)
	{
		this.finder = finder;
		this.interfaceFinder = interfaceFinder;
	}
	
	@Override
	public <T> Collection<T> instantiateOrdered (Class<? extends Annotation> annotation,
	Object... parameter) throws InitializationException
	{
		Set<Class<?>> pluginClasses = finder.getTypesAnnotatedWith (annotation);
		List<Class<?>> sortedClasses = sort (pluginClasses);
		return sortedClasses.stream ().map (new Instantiate<T> (parameter)).collect (Collectors.toList ());
	}
	
	@Override
	public <T> Collection<T> instantiateAll (Class<? extends Annotation> annotation,
	Object... parameter) throws InitializationException
	{
		Set<Class<?>> pluginClasses = finder.getTypesAnnotatedWith (annotation);
		return pluginClasses.stream ().map (new Instantiate<T> (parameter)).collect (Collectors.toList ());
	}
	
	@Override
	public <T> Collection<T> instantiateAllImplementers (Class<T> interfaceClass, Object... parameter)
	{
		Collection<Class<? extends T>> filteredClasses = findLegalImplementers (interfaceClass);
		return filteredClasses.stream ().map (new Instantiate<T> (parameter)).collect (Collectors.toList ());
	}
	
	@Override
	public <T> Collection<T> instantiateAllImplementersOrdered (Class<T> interfaceClass, Object... parameter)
	{
		Collection<Class<? extends T>> filteredClasses = findLegalImplementers (interfaceClass);
		List<Class<?>> sortedClasses = sort (filteredClasses);
		return sortedClasses.stream ().map (new Instantiate<T> (parameter)).collect (Collectors.toList ());
	}
	
	private <T> Collection<Class<? extends T>> findLegalImplementers (Class<T> interfaceClass)
	{
		Set<Class<? extends T>> classes = interfaceFinder.findAll (interfaceClass);
		Collection<Class<? extends T>> filteredClasses = filterBlackListedClasses (classes);
		filteredClasses = filterAbstractClasses (filteredClasses);
		return filteredClasses;
	}
	
	private <T> Collection<Class<? extends T>> filterBlackListedClasses (Set<Class<? extends T>> classes)
	{
		return filter (classes, input -> !input.isAnnotationPresent (DoNotInstantiateAutomatically.class));
	}
	
	private <T> Collection<Class<? extends T>> filterAbstractClasses (Collection<Class<? extends T>> classes)
	{
		return filter (classes, input -> !Modifier.isAbstract (input.getModifiers ()));
	}
	
	private List<Class<?>> sort (Set<Class<?>> pluginClasses)
	{
		List<Class<?>> list = new ArrayList<> (pluginClasses);
		Collections.sort (list, new ByWeightAnnotation ());
		return list;
	}
	
	private <T> List<Class<?>> sort (Collection<Class<? extends T>> filteredClasses)
	{
		List<Class<?>> list = new ArrayList<> (filteredClasses);
		Collections.sort (list, new ByWeightAnnotation ());
		return list;
	}
	
	private class Instantiate<T> implements Function<Class<?>, T>
	{
		private final Object[] parameters;
		
		public Instantiate (Object[] parameters)
		{
			this.parameters = parameters;
		}
		
		@Override
		public T apply (Class<?> input)
		{
			return instantiate (input);
		}
		
		@SuppressWarnings ("unchecked")
		private T instantiate (Class<?> input)
		{
			try
			{
				return (T) invokeConstructor (input, parameters);
			}
			catch (InstantiationException e)
			{
				throw new RuntimeException (format ("Class {0} is abstract, but should not be.", input.getName ()), e);
			}
			catch (IllegalAccessException e)
			{
				String parameterTypes[] = new String[parameters.length];
				for (int i = 0; i < parameters.length; i++)
				{
					parameterTypes[i] = parameters[i].getClass ().getName ();
				}
				String message = format ("Class {0} declares a private constructor for {1}, but should be public.",
				input.getName (), Arrays.deepToString (parameterTypes));
				throw new RuntimeException (message, e);
			}
			catch (InvocationTargetException e)
			{
				throw new RuntimeException (format ("Constructor of {0} threw an exception.", input.getName ()), e);
			}
			catch (NoSuchMethodException e)
			{
				throw new RuntimeException ("Could not find a matching constructor.", e);
			}
		}
	}
}
