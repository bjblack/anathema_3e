package net.sf.anathema.magic.data.attribute;

import net.sf.anathema.library.identifier.SimpleIdentifier;

public class MagicAttributeImpl extends SimpleIdentifier implements MagicAttribute
{
	private final boolean visualized;
	private final String value;
	
	public MagicAttributeImpl (String id, boolean visualized)
	{
		this (id, visualized, null);
	}
	
	public MagicAttributeImpl (String id, boolean visualized, String value)
	{
		super (id);
		this.visualized = visualized;
		this.value = value;
	}
	
	@Override
	public boolean isVisualized ()
	{
		return visualized;
	}
	
	@Override
	public String getValue ()
	{
		return value;
	}
}
