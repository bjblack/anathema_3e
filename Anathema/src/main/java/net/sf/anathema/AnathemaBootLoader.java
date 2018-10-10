package net.sf.anathema;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.Properties;

public class AnathemaBootLoader {

	public static void main (String[] arguments) throws Exception
	{
		
		ClassLoader loader = selectClassLoader ();
		Class<?> mainClass = loadMainClass (loader);
		Object instance = mainClass.newInstance ();
		
		Method method = mainClass.getMethod ("startApplication");
		// This is a method of the Anathema class, defined in the Platform_FX project.
		
		method.invoke (instance);
		
	}

	private static ClassLoader selectClassLoader () throws IOException
	{
		
		// Returns the system class loader if it will work; returns a custom loader if the system loader excepts out.
		if (isClasspathConfigured ()) {
			return useSystemClassLoader ();
		} else {
			return useCustomClassLoader ();
		}
		
	}

	private static ClassLoader useSystemClassLoader () throws IOException
	{
		
		return ClassLoader.getSystemClassLoader ();
		
	}

	private static ClassLoader useCustomClassLoader () throws MalformedURLException
	{
		
		Properties properties = new PropertiesLoader ("anathema.properties").load ();
		String libraryFolder = properties.getProperty ("library.folder");
		return new EasyLoader (Paths.get (libraryFolder));
		
	}

	private static boolean isClasspathConfigured ()
	{
		
		// Tests the system class loader. If loading class "net.sf.anathema.Anathema" throws the exception, the classpath is not configured.
		try {
			loadMainClass (ClassLoader.getSystemClassLoader ());
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
		
	}

	private static Class<?> loadMainClass (ClassLoader loader) throws ClassNotFoundException
	{
		
		return loader.loadClass ("net.sf.anathema.Anathema");
		
	}
}