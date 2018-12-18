package net.sf.anathema.magic.description.model;

import java.util.List;

public class AggregatedMagicDescription implements MagicDescription
{
	private List<MagicDescription> descriptions;
	
	public AggregatedMagicDescription (List<MagicDescription> descriptions)
	{
		this.descriptions = descriptions;
	}
	
	@Override
	public boolean isEmpty ()
	{
		for (MagicDescription description : descriptions)
		{
			if (!description.isEmpty ())
			{
				return false;
			}
		}
		return true;
	}
	
	@Override
	public Paragraphs getParagraphs ()
	{
		for (MagicDescription description : descriptions)
		{
			if (!description.isEmpty ())
			{
				return description.getParagraphs ();
			}
		}
		return new Paragraphs ();
	}
}
