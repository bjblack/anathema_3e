package net.sf.anathema.hero.spiritual.display;

import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.spiritual.model.pool.EssencePoolModelFetcher;
import net.sf.anathema.hero.traits.model.TraitMap;
import net.sf.anathema.hero.traits.model.TraitModelFetcher;
import net.sf.anathema.library.presenter.Presenter;
import net.sf.anathema.library.resources.Resources;

import java.util.ArrayList;
import java.util.List;

import static net.sf.anathema.hero.traits.model.types.CommonTraitTypes.Essence;
import static net.sf.anathema.hero.traits.model.types.CommonTraitTypes.Willpower;

public class BasicSpiritualTraitsPresenter
{
	private final List<Presenter> subPresenters = new ArrayList<> ();
	private final SpiritualTraitsView view;
	private final Resources resources;
	
	public BasicSpiritualTraitsPresenter (Resources resources, Hero hero, SpiritualTraitsView view)
	{
		this.resources = resources;
		this.view = view;
		TraitMap traitMap = TraitModelFetcher.fetch (hero);
		subPresenters.add (new WillpowerPresenter (resources, traitMap.getTrait (Willpower), view));
		subPresenters.add (new EssencePresenter (resources, EssencePoolModelFetcher.fetch (hero), traitMap.getTrait (Essence), view));
	}
	
	public void initPresentation ()
	{
		for (Presenter presenter : subPresenters)
		{
			presenter.initPresentation ();
		}
		view.initGui (new DefaultSpiritualTraitsViewProperties (resources));
	}
}
