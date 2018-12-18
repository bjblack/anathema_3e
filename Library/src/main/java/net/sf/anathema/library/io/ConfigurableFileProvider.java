package net.sf.anathema.library.io;

import java.io.File;

public class ConfigurableFileProvider implements IFileProvider
{
	private File file;
	
	public void setFile (File file)
	{
		this.file = file;
	}
	
	@Override
	public File getFile ()
	{
		return file;
	}
}
