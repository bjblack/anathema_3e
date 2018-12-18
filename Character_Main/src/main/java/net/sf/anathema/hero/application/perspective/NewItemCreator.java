package net.sf.anathema.hero.application.perspective;

import net.sf.anathema.hero.application.IItemCreator;
import net.sf.anathema.hero.application.item.Item;
import net.sf.anathema.hero.application.persistence.RepositoryItemPersister;
import net.sf.anathema.hero.individual.splat.HeroSplat;
import net.sf.anathema.library.exception.PersistenceException;

public class NewItemCreator implements IItemCreator
{
	private RepositoryItemPersister persister;
	
	public NewItemCreator (RepositoryItemPersister persister)
	{
		this.persister = persister;
	}
	
	@Override
	public Item createItem (HeroSplat template) throws PersistenceException
	{
		Item item = persister.createNew (template);
		item.getItemData ().getChangeManagement ().setClean ();
		return item;
	}
}
