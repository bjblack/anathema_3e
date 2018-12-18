package net.sf.anathema.hero.application;

import net.sf.anathema.ProxySplashscreen;
import net.sf.anathema.hero.application.environment.InjectingObjectFactory;
import net.sf.anathema.hero.environment.initialization.ExtensibleDataSetCompiler;
import net.sf.anathema.hero.environment.initialization.ExtensibleDataSetProvider;
import net.sf.anathema.library.initialization.InitializationException;
import net.sf.anathema.library.initialization.ObjectFactory;
import net.sf.anathema.library.logging.Logger;
import net.sf.anathema.library.resources.ResourceFile;
import net.sf.anathema.library.resources.ResourceFileLoader;
import net.sf.anathema.platform.dependencies.InterfaceFinder;

import java.util.Collection;
import java.util.Set;

public class DataSetInitializer
{
	private static final Logger logger = Logger.getLogger (DataSetInitializer.class);
	private final ResourceFileLoader resourceLoader;
	private final ObjectFactory objectFactory;
	private InterfaceFinder interfaceFinder;
	
	public DataSetInitializer (ResourceFileLoader resourceFileLoader, ObjectFactory objectFactory, InterfaceFinder interfaceFinder)
	{
		this.resourceLoader = resourceFileLoader;
		this.objectFactory = objectFactory;
		this.interfaceFinder = interfaceFinder;
	}
	
	public ExtensibleDataSetProvider initializeExtensibleResources () throws InitializationException
	{
		ExtensibleDataManager manager = new ExtensibleDataManager ();
		ObjectFactory injectingFactory = new InjectingObjectFactory (objectFactory, objectFactory, interfaceFinder, manager);
		Collection<ExtensibleDataSetCompiler> compilers = injectingFactory.instantiateOrdered (net.sf.anathema.platform.initialization.ExtensibleDataSetCompiler.class);
		for (ExtensibleDataSetCompiler compiler : compilers)
		{
			try
			{
				ProxySplashscreen.getInstance ().displayStatusMessage ("Compiling " + compiler.getName () + "...");
				getDataFilesFromReflection (compiler);
				manager.addDataSet (compiler.build ());
			}
			catch (Exception e)
			{
				String message = handleCompilationException (compiler, e);
				throw new InitializationException (message, e);
			}
		}
		return manager;
	}
	
	private void getDataFilesFromReflection (ExtensibleDataSetCompiler compiler) throws Exception
	{
		Set<ResourceFile> files = resourceLoader.getResourcesMatching (compiler.getRecognitionPattern ());
		logger.info (compiler.getName () + ": Found " + files.size () + " data files.");
		for (ResourceFile file : files)
		{
			compiler.registerFile (file);
		}
	}
	
	private String handleCompilationException (ExtensibleDataSetCompiler compiler, Exception e)
	{
		StringBuilder message = new StringBuilder ("Could not compile ");
		message.append (compiler.getName ());
		Throwable cause = e.getCause ();
		while (cause != null)
		{
			message.append (":");
			message.append ("\n");
			String messagePart = cause.getMessage ();
			if (messagePart.contains ("Nested"))
			{
				int nested = messagePart.indexOf ("Nested");
				messagePart = messagePart.substring (0, nested);
			}
			message.append (messagePart);
			cause = cause.getCause ();
		}
		return message.toString ();
	}
}
