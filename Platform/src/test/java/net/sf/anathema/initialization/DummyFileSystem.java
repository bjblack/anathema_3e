package net.sf.anathema.initialization;

import net.sf.anathema.platform.repository.FileSystem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DummyFileSystem implements FileSystem
{
	private final List<File> existingFiles = new ArrayList<> ();
	private final List<File> createdFolders = new ArrayList<> ();
	private final List<File> writeProtectedFiles = new ArrayList<> ();
	private final List<File> readProtectedFiles = new ArrayList<> ();
	
	public boolean wasCreated (File file)
	{
		return createdFolders.contains (file);
	}
	
	@Override
	public void createFolder (File folder)
	{
		createdFolders.add (folder);
	}
	
	@Override
	public boolean canWrite (File file)
	{
		return !writeProtectedFiles.contains (file);
	}
	
	@Override
	public boolean canRead (File file)
	{
		return !readProtectedFiles.contains (file);
	}
	
	public void addWriteProtectedFile (File file)
	{
		writeProtectedFiles.add (file);
	}
	
	public void addReadProtectedFile (File file)
	{
		readProtectedFiles.add (file);
	}
	
	@Override
	public boolean exists (File file)
	{
		return existingFiles.contains (file) || createdFolders.contains (file);
	}
	
	public void addExistingFile (File file)
	{
		existingFiles.add (file);
	}
}
