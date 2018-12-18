package net.sf.anathema.equipment.editor.stats.properties;

import net.sf.anathema.library.resources.Resources;

public class AbstractProperties
{
	private final Resources resources;
	
	public AbstractProperties (Resources resources)
	{
		this.resources = resources;
	}
	
	protected final String getString (String key)
	{
		return resources.getString (key);
	}
	
	protected final String getLabelString (String key)
	{
		return getString (key) + ":";
	}
	
	protected final Resources getResources ()
	{
		return resources;
	}
}
