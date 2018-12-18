package net.sf.anathema.platform.repository;

import net.sf.anathema.library.event.ChangeListener;
import net.sf.anathema.library.interaction.model.Command;
import net.sf.anathema.platform.preferences.PreferenceKey;
import net.sf.anathema.platform.preferences.PreferenceModel;
import net.sf.anathema.platform.preferences.PreferencePto;
import net.sf.anathema.platform.preferences.PreferenceValue;
import net.sf.anathema.platform.preferences.RegisteredPreferenceModel;

import org.jmock.example.announcer.Announcer;

import java.nio.file.Path;
import java.nio.file.Paths;

@RegisteredPreferenceModel
public class RepositoryPreferenceModel implements PreferenceModel
{
	public static final PreferenceKey key = new PreferenceKey ("framework.repository.location");
	public static final String DEFAULT_REPOSITORY_LOCATION = "./repository/";
	private final Path defaultPath = Paths.get (DEFAULT_REPOSITORY_LOCATION);
	private final Announcer<ChangeListener> announcer = Announcer.to (ChangeListener.class);
	private Path repositoryPath;
	
	@Override
	public void serializeTo (PreferencePto pto)
	{
		PreferenceValue value = new PreferenceValue (repositoryPath.toString ());
		pto.map.put (key, value);
	}
	
	@Override
	public void initializeFrom (PreferencePto pto)
	{
		PreferenceValue preferenceValue = pto.map.get (key);
		Path newPath;
		if (preferenceValue == null)
		{
			newPath = defaultPath;
		}
		else
		{
			newPath = Paths.get (preferenceValue.value);
		}
		requestChangeOfRepositoryPath (newPath);
	}
	
	public void whenLocationChanges (ChangeListener changeListener)
	{
		announcer.addListener (changeListener);
	}
	
	public Path getRepositoryPath ()
	{
		return repositoryPath;
	}
	
	public void requestChangeOfRepositoryPath (Path path)
	{
		this.repositoryPath = path;
		announcer.announce ().changeOccurred ();
	}
	
	public void resetToDefault ()
	{
		requestChangeOfRepositoryPath (defaultPath);
	}
	
	@Override
	public void whenDirtied (Command command)
	{
		whenLocationChanges (command::execute);
	}
}
