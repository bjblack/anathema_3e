package net.sf.anathema.scribe.scroll.persistence;

import net.sf.anathema.platform.item.IItemType;
import net.sf.anathema.platform.repository.RepositoryIdData;
import net.sf.anathema.scribe.scroll.ScrollItemType;

public class TimedRepositoryData implements RepositoryIdData
{
	private final Clock clock;
	
	public TimedRepositoryData (Clock clock)
	{
		this.clock = clock;
	}
	
	@Override
	public String getIdProposal ()
	{
		return String.valueOf (clock.getCurrentTimeInMillis ());
	}
	
	@Override
	public IItemType getItemType ()
	{
		return ScrollItemType.ITEM_TYPE;
	}
}
