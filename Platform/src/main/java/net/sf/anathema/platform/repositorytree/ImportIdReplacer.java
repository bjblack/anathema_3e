package net.sf.anathema.platform.repositorytree;

import net.sf.anathema.library.io.InputOutput;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImportIdReplacer
{
	private final String oldId;
	private final String newId;
	
	public ImportIdReplacer (String oldId, String newId)
	{
		this.oldId = oldId;
		this.newId = newId;
	}
	
	public InputStream createStreamWithLegalId (InputStream inputStream) throws IOException
	{
		String string = InputOutput.toString (inputStream);
		string = string.replaceFirst ("\"repositoryId\": \"" + oldId + "\"",
		"\"repositoryId\": \"" + newId + "\"");
		return new ByteArrayInputStream (string.getBytes ());
	}
}
