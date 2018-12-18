package net.sf.anathema;

import net.sf.anathema.library.initialization.ObjectFactory;
import net.sf.anathema.library.resources.ResourceFile;
import net.sf.anathema.platform.dependencies.DefaultAnathemaReflections;
import net.sf.anathema.platform.dependencies.InterfaceFinder;
import net.sf.anathema.platform.dependencies.ReflectionObjectFactory;
import net.sf.anathema.platform.environment.Environment;
import net.sf.anathema.platform.exception.ConsoleExceptionHandler;

import java.util.Set;

public class DummyEnvironment implements Environment
{
	private final DefaultAnathemaReflections finder = new DefaultAnathemaReflections ();
	private final ObjectFactory factory = new ReflectionObjectFactory (finder, finder);
	
	@Override
	public void handle (Throwable exception)
	{
		new ConsoleExceptionHandler ().handle (exception);
	}
	
	@Override
	public void handle (Throwable exception, String message)
	{
		new ConsoleExceptionHandler ().handle (exception, message);
	}
	
	@Override
	public String getPreference (String key)
	{
		return null;
	}
	
	@Override
	public boolean supportsKey (String key)
	{
		return false;
	}
	
	@Override
	public String getString (String key, Object... arguments)
	{
		return key;
	}
	
	@Override
	public Set<ResourceFile> getResourcesMatching (String namePattern)
	{
		return finder.getResourcesMatching (namePattern);
	}
	
	@Override
	public ObjectFactory getObjectFactory ()
	{
		return factory;
	}
	
	@Override
	public InterfaceFinder getInterfaceFinder ()
	{
		return finder;
	}
}
