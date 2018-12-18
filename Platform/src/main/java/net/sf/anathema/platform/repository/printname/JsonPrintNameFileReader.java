package net.sf.anathema.platform.repository.printname;

import net.sf.anathema.library.io.InputOutput;
import net.sf.anathema.platform.item.IItemType;
import net.sf.anathema.platform.repository.PrintNameFile;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class JsonPrintNameFileReader implements PrintNameFileReader
{
	private final Gson gson = new Gson ();
	
	@Override
	public PrintNameFile readPrintName (File file, IItemType itemType) throws IOException
	{
		try (InputStream stream = new FileInputStream (file))
		{
			String content = InputOutput.toString (stream);
			ItemReference itemReference = gson.fromJson (content, ItemReference.class);
			return new PrintNameFile (file, itemReference.printName, itemReference.repositoryId, itemType);
		}
	}
}
