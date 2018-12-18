package net.sf.anathema.hero.charms.display.special;

import net.sf.anathema.hero.charms.model.special.subeffects.MultipleEffectCharmSpecials;
import net.sf.anathema.hero.charms.model.special.subeffects.SubEffect;
import net.sf.anathema.library.resources.Resources;
import net.sf.anathema.library.view.BooleanView;

public class MultipleEffectCharmPresenter
{
	private final Resources resources;
	private final ToggleButtonSpecialNodeView view;
	private final MultipleEffectCharmSpecials model;
	
	public MultipleEffectCharmPresenter (Resources resources, ToggleButtonSpecialNodeView subeffectView, MultipleEffectCharmSpecials model)
	{
		this.resources = resources;
		this.view = subeffectView;
		this.model = model;
	}
	
	public void initPresentation ()
	{
		for (SubEffect subeffect : model.getEffects ())
		{
			String key = model.getCharm ().getName ().text + ".Subeffects." + subeffect.getId ();
			String label = resources.getString (key);
			BooleanView display = view.addSubeffect (label);
			subeffect.addChangeListener ( () -> display.setSelected (subeffect.isLearned ()));
			display.addChangeListener (newValue ->
			{
				subeffect.setLearned (newValue);
				display.setSelected (subeffect.isLearned ());
			}
			);
			display.setSelected (subeffect.isLearned ());
		}
	}
}
