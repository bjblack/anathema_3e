package net.sf.anathema.library.io;

import net.sf.anathema.library.logging.Logger;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PathUtils
{
	private static final Logger logger = Logger.getLogger (PathUtils.class);
	
	public static String readFileToString (Path path) throws IOException
	{
		return new String (Files.readAllBytes (path));
	}
	
	public static void writeStringToFile (Path file, String string) throws IOException
	{
		Files.write (file, string.getBytes ());
	}
	
	public static Collection<Path> listAll (Path directory, String glob)
	{
		try (DirectoryStream<Path> stream = Files.newDirectoryStream (directory, glob))
		{
			List<Path> items = new ArrayList<> ();
			for (Path path : stream)
			{
				items.add (path);
			}
			return items;
		}
		catch (IOException | DirectoryIteratorException x)
		{
			logger.error ("Could not read files.", x);
			return Collections.emptyList ();
		}
	}
}
