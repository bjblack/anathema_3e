package net.sf.anathema.library.identifier;

public class IdentifiedInteger extends SimpleIdentifier
{
	private int value;
	
	public IdentifiedInteger (String id, int value)
	{
		super (id);
		this.value = value;
	}
	
	public int getValue ()
	{
		return value;
	}
	
	public void setValue (int value)
	{
		this.value = value;
	}
}
