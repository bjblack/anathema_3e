package net.sf.anathema.equipment.database.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class EmptyStringAdapter extends TypeAdapter<String>
{
	@Override
	public void write (JsonWriter out, String value) throws IOException
	{
		if (value == null)
		{
			out.value ("");
		}
		else
		{
			out.value (value);
		}
	}
	
	@Override
	public String read (JsonReader in) throws IOException
	{
		return in.nextString ();
	}
}
