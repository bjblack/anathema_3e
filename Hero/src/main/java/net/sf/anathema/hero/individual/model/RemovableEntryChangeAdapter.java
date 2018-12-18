package net.sf.anathema.hero.individual.model;

import net.sf.anathema.hero.individual.change.ChangeAnnouncer;
import net.sf.anathema.hero.individual.change.ChangeFlavor;
import net.sf.anathema.library.model.RemovableEntryListener;

public class RemovableEntryChangeAdapter<E> implements RemovableEntryListener<E>
{
	private final ChangeAnnouncer announcer;
	
	public RemovableEntryChangeAdapter (ChangeAnnouncer announcer)
	{
		this.announcer = announcer;
	}
	
	@Override
	public void entryAdded (E entry)
	{
		announcer.announceChangeFlavor (ChangeFlavor.UNSPECIFIED);
	}
	
	@Override
	public void entryRemoved (E entry)
	{
		announcer.announceChangeFlavor (ChangeFlavor.UNSPECIFIED);
	}
	
	@Override
	public void entryAllowed (boolean complete)
	{
		// nothing to do
	}
}
