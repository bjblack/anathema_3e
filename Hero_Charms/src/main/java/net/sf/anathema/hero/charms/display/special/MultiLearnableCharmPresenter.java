package net.sf.anathema.hero.charms.display.special;

import net.sf.anathema.hero.charms.model.special.learning.multilearn.MultiLearnCharmSpecials;
import net.sf.anathema.hero.traits.display.TraitPresenter;
import net.sf.anathema.hero.traits.model.Trait;
import net.sf.anathema.library.resources.Resources;
import net.sf.anathema.library.view.IntValueView;
import net.sf.anathema.platform.tree.display.CategorizedSpecialNodeView;

public class MultiLearnableCharmPresenter
{
	private final CategorizedSpecialNodeView view;
	private final MultiLearnCharmSpecials model;
	private final Resources resources;
	
	public MultiLearnableCharmPresenter (Resources resources, CategorizedSpecialNodeView view, MultiLearnCharmSpecials model)
	{
		this.resources = resources;
		this.view = view;
		this.model = model;
	}
	
	public void initPresentation ()
	{
		String label = resources.getString ("MultiLearnableCharm.Label");
		Trait category = model.getCategory ();
		IntValueView display = view.addCategory (label, category.getMaximalValue (), category.getCurrentValue ());
		new TraitPresenter (category, display).initPresentation ();
	}
}
