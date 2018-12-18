package net.sf.anathema.hero.application.item;

public class HeroItem implements Item
{
	private final ItemData itemData;
	private final ItemRepositoryLocation repositoryLocation = new SimpleRepositoryLocation ();
	
	public HeroItem (HeroItemData heroItemData)
	{
		this.itemData = heroItemData;
	}
	
	@Override
	public ItemData getItemData ()
	{
		return itemData;
	}
	
	@Override
	public ItemRepositoryLocation getRepositoryLocation ()
	{
		return repositoryLocation;
	}
}
