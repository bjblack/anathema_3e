package net.sf.anathema.platform.repository;

import net.sf.anathema.library.io.PathUtils;
import net.sf.anathema.platform.item.IItemType;
import net.sf.anathema.platform.item.RepositoryConfiguration;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;

public class RepositoryFileResolver implements IRepositoryFileResolver
{
	private final File repositoryFile;
	
	public RepositoryFileResolver (File repositoryFile)
	{
		this.repositoryFile = repositoryFile;
	}
	
	@Override
	public File getMainFile (RepositoryConfiguration configuration, String id)
	{
		if (configuration.isItemSavedToSingleFile ())
		{
			return getItemFile (configuration, id);
		}
		return getMainFile (getItemFolder (configuration, id), configuration);
	}
	
	@Override
	public File getMainFile (File folder, RepositoryConfiguration configuration)
	{
		return new File (folder, configuration.getMainFileName () + configuration.getFileExtension ());
	}
	
	@Override
	public Collection<Path> listAllFiles (RepositoryConfiguration configuration)
	{
		File folder = getExistingItemTypeFolder (configuration);
		String fileExtension = getExtension (configuration);
		return PathUtils.listAll (folder.toPath (), "*." + fileExtension);
	}
	
	@Override
	public File getFolder (RepositoryConfiguration configuration)
	{
		return new File (repositoryFile, configuration.getFolderName ());
	}
	
	public File getExistingItemTypeFolder (RepositoryConfiguration configuration)
	{
		File typeFolder = getFolder (configuration);
		createNonExistentFolder (typeFolder);
		return typeFolder;
	}
	
	public File getExistingItemFolder (IItemType type, String id)
	{
		File itemFolder = getItemFolder (type.getRepositoryConfiguration (), id);
		createNonExistentFolder (itemFolder);
		return itemFolder;
	}
	
	public File getExistingDataFolder (String folderName)
	{
		File folder = new File (repositoryFile, folderName);
		createNonExistentFolder (folder);
		return folder;
	}
	
	@SuppressWarnings ("ResultOfMethodCallIgnored")
	private void createNonExistentFolder (File typeFolder)
	{
		if (!typeFolder.exists ())
		{
			typeFolder.mkdir ();
		}
	}
	
	private File getItemFile (RepositoryConfiguration configuration, String id)
	{
		String extension = configuration.getFileExtension ();
		return new File (getExistingItemTypeFolder (configuration), id + extension);
	}
	
	private String getExtension (RepositoryConfiguration configuration)
	{
		String fileExtension = configuration.getFileExtension ();
		if (fileExtension.startsWith ("."))
		{
			return fileExtension.substring (1);
		}
		return fileExtension;
	}
	
	private File getItemFolder (RepositoryConfiguration configuration, String id)
	{
		File typeFolder = getExistingItemTypeFolder (configuration);
		return new File (typeFolder, id);
	}
}
