package net.sf.anathema.cascades.presenter;

import net.sf.anathema.magic.data.reference.CategoryReference;
import net.sf.anathema.hero.charms.compiler.CharmCache;
import net.sf.anathema.hero.charms.display.view.SpecialCharmSet;
import net.sf.anathema.hero.charms.model.special.CharmSpecialLearning;

import java.util.Iterator;

public class CascadeSpecialCharmSet implements SpecialCharmSet
{
	private CategoryReference category;
	private final CharmCache cache;
	
	public CascadeSpecialCharmSet (CharmCache cache)
	{
		this.cache = cache;
	}
	
	public void setCategory (CategoryReference type)
	{
		this.category = type;
	}
	
	@Override
	public Iterator<CharmSpecialLearning> iterator ()
	{
		return cache.getSpecialLearningCharms (category).iterator ();
	}
}
