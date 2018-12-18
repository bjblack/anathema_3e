package net.sf.anathema.framework.preferences.persistence;

import de.idos.updates.configuration.ConfigurationFailedException;
import de.idos.updates.configuration.PropertiesLoader;

import net.sf.anathema.framework.preferences.perspective.PreferencesPersister;
import net.sf.anathema.library.logging.Logger;
import net.sf.anathema.platform.preferences.PreferenceKey;
import net.sf.anathema.platform.preferences.PreferencePto;
import net.sf.anathema.platform.preferences.PreferenceValue;
import net.sf.anathema.platform.updatecheck.PropertiesSaver;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class PropertiesPreferencesPersister implements PreferencesPersister
{
	public static final Logger LOGGER = Logger.getLogger (PropertiesPreferencesPersister.class);
	private final String filename;
	
	public PropertiesPreferencesPersister (String filename)
	{
		this.filename = filename;
	}
	
	@Override
	public void save (PreferencePto pto)
	{
		PropertiesSaver saver = new PropertiesSaver (filename);
		Properties properties = createProperties (pto);
		try
		{
			saver.save (properties);
		}
		catch (IOException e)
		{
			LOGGER.warn ("Could not save preferences.", e);
		}
	}
	
	@Override
	public PreferencePto load ()
	{
		PropertiesLoader loader = new PropertiesLoader (filename);
		try
		{
			Properties properties = loader.load ();
			return createPto (properties);
		}
		catch (ConfigurationFailedException e)
		{
			LOGGER.warn ("Could not restore preferences: " + e.getMessage ());
			return new PreferencePto ();
		}
	}
	
	private PreferencePto createPto (Properties properties)
	{
		PreferencePto pto = new PreferencePto ();
		for (Object key : properties.keySet ())
		{
			String proertyKey = (String) key;
			String propertyValue = properties.getProperty (proertyKey);
			pto.map.put (new PreferenceKey (proertyKey), new PreferenceValue (propertyValue));
		}
		return pto;
	}
	
	private Properties createProperties (PreferencePto pto)
	{
		Properties properties = new Properties ();
		for (Map.Entry<PreferenceKey, PreferenceValue> entry : pto.map.entrySet ())
		{
			properties.setProperty (entry.getKey ().key, entry.getValue ().value);
		}
		return properties;
	}
}
