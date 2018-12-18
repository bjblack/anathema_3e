package net.sf.anathema.hero.application.perspective.model;

import net.sf.anathema.hero.application.item.Item;

import java.util.Collection;

public interface ItemSystemModel extends ItemSelectionModel
{
	Collection<CharacterItemModel> getAllKnownHeroes ();
	
	Collection<CharacterItemModel> getMostRecentHeroes ();
	
	Item loadItem (HeroIdentifier identifier);
	
	void setCurrentCharacter (HeroIdentifier identifier);
	
	HeroIdentifier getSelection ();
}
