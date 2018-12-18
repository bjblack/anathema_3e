package net.sf.anathema.scribe.scroll.gson;

import net.sf.anathema.library.exception.PersistenceException;
import net.sf.anathema.library.io.InputOutput;
import net.sf.anathema.platform.repository.printname.RepositoryId;
import net.sf.anathema.platform.repository.printname.SimpleRepositoryId;
import net.sf.anathema.scribe.scroll.persistence.Scroll;
import net.sf.anathema.scribe.scroll.persistence.ScrollDto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ScrollGson
{
	private Gson gson;
	
	public ScrollGson ()
	{
		GsonBuilder gsonBuilder = new GsonBuilder ();
		gsonBuilder.setPrettyPrinting ();
		gson = gsonBuilder.create ();
	}
	
	public void save (Scroll scroll, OutputStream outputStream)
	{
		try
		{
			String json = toJson (scroll);
			outputStream.write (json.getBytes ());
		}
		catch (IOException e)
		{
			throw new PersistenceException (e);
		}
	}
	
	public String toJson (Scroll scroll)
	{
		ScrollRepositoryItem item = new ScrollRepositoryItem ();
		item.repositoryId = scroll.repositoryId.getStringRepresentation ();
		item.title = scroll.dto.title;
		item.wikiText = scroll.dto.wikiText;
		return gson.toJson (item);
	}
	
	public Scroll load (InputStream inputStream)
	{
		try
		{
			String json = InputOutput.toString (inputStream);
			return fromJson (json);
		}
		catch (IOException e)
		{
			throw  new PersistenceException (e);
		}
	}
	
	public Scroll fromJson (String json)
	{
		ScrollRepositoryItem item = gson.fromJson (json, ScrollRepositoryItem.class);
		ScrollDto dto = new ScrollDto (item.title, item.wikiText);
		RepositoryId repositoryId = new SimpleRepositoryId (item.repositoryId);
		return new Scroll (dto, repositoryId);
	}
}
