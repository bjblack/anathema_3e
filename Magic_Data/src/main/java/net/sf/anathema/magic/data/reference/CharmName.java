package net.sf.anathema.magic.data.reference;

public class CharmName extends MagicName implements Comparable<CharmName>
{
	public CharmName (String charmName)
	{
		super (charmName);
	}
	
	@Override
	public int compareTo (CharmName o)
	{
		return text.compareTo (o.text);
	}
}
