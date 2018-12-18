package net.sf.anathema.hero.application.perspective;

import net.sf.anathema.hero.application.item.Item;
import net.sf.anathema.hero.application.perspective.model.HeroIdentifier;
import net.sf.anathema.hero.concept.model.concept.HeroConceptFetcher;
import net.sf.anathema.hero.concept.model.description.HeroNameFetcher;
import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.individual.splat.SplatType;
import net.sf.anathema.library.identifier.Identifier;

public class LoadedDescriptiveFeatures implements DescriptiveFeatures
{
	private HeroIdentifier identifier;
	private Item characterItem;
	
	public LoadedDescriptiveFeatures (HeroIdentifier identifier, Item characterItem)
	{
		this.identifier = identifier;
		this.characterItem = characterItem;
	}
	
	@Override
	public String getPrintName ()
	{
		return new HeroNameFetcher ().getName (getHero ());
	}
	
	@Override
	public HeroIdentifier getIdentifier ()
	{
		return identifier;
	}
	
	@Override
	public SplatType getTemplateType ()
	{
		Hero hero = getHero ();
		return hero.getSplat ().getTemplateType ();
	}
	
	@Override
	public Identifier getCasteType ()
	{
		Hero hero = getHero ();
		return HeroConceptFetcher.fetch (hero).getCaste ().getType ();
	}
	
	@Override
	public boolean isDirty ()
	{
		return characterItem.getItemData ().getChangeManagement ().isDirty ();
	}
	
	private Hero getHero ()
	{
		return (Hero) characterItem.getItemData ();
	}
}
