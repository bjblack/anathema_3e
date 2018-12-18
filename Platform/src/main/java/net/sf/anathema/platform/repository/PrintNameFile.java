package net.sf.anathema.platform.repository;

import net.sf.anathema.library.io.InputOutput;
import net.sf.anathema.platform.item.IItemType;

import java.io.File;
import java.io.IOException;

public class PrintNameFile
{
	private final File file;
	private final String printName;
	private final String repositoryId;
	private final IItemType itemType;
	
	public PrintNameFile (File file, String printName, String repositoryId, IItemType itemType)
	{
		this.file = file;
		this.printName = printName;
		this.repositoryId = repositoryId;
		this.itemType = itemType;
	}
	
	public File getFile ()
	{
		return file;
	}
	
	public String getPrintName ()
	{
		return printName;
	}
	
	public String getRepositoryId ()
	{
		return repositoryId;
	}
	
	public IItemType getItemType ()
	{
		return itemType;
	}
	
	@Override
	public boolean equals (Object obj)
	{
		if (! (obj instanceof PrintNameFile))
		{
			return false;
		}
		PrintNameFile objFile = (PrintNameFile) obj;
		return this.repositoryId.equals (objFile.getRepositoryId ()) && this.itemType.equals (objFile.getItemType ());
	}
	
	@Override
	public int hashCode ()
	{
		return repositoryId.length () * 23 + printName.length () * 19;
	}
	
	
	public void delete () throws IOException
	{
		if (file.exists ())
		{
			InputOutput.forceDelete (file);
		}
	}
}
