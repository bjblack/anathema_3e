package net.sf.anathema.hero.charms.display.model;

import net.sf.anathema.magic.data.reference.CategoryReference;

import java.util.List;

public class CharacterCategoryCollection implements CategoryCollection
{
	private CharmDisplayModel model;
	
	public CharacterCategoryCollection (CharmDisplayModel model)
	{
		this.model = model;
	}
	
	@Override
	public List<CategoryReference> getCurrentCategories ()
	{
		return model.getValidCategoriesForHero ();
	}
}
