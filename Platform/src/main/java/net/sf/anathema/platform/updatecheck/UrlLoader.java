package net.sf.anathema.platform.updatecheck;

import net.sf.anathema.library.io.InputOutput;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class UrlLoader
{
	private final String urlString;
	
	public UrlLoader (String url)
	{
		this.urlString = url;
	}
	
	public String readAll () throws IOException
	{
		String response;
		try (InputStream input = new URL (urlString).openStream ())
		{
			response = InputOutput.toString (input);
		}
		return response;
	}
}
