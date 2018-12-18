package net.sf.anathema.equipment.database.gson;

import net.sf.anathema.library.identifier.Identifier;
import net.sf.anathema.library.identifier.SimpleIdentifier;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class IdentificateAdapter extends TypeAdapter<Identifier>
{
	@Override
	public void write (JsonWriter out, Identifier value) throws IOException
	{
		out.value (value.getId ());
	}
	
	@Override
	public Identifier read (JsonReader in) throws IOException
	{
		String id = in.nextString ();
		return new SimpleIdentifier (id);
	}
}
