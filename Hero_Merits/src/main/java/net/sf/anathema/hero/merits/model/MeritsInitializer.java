package net.sf.anathema.hero.merits.model;

import net.sf.anathema.hero.environment.HeroEnvironment;
import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.individual.model.HeroModelInitializer;
import net.sf.anathema.hero.individual.model.RegisteredInitializer;
import net.sf.anathema.hero.individual.view.SectionView;
import net.sf.anathema.library.initialization.Weight;
import net.sf.anathema.library.presenter.CategorizedOptionalTraitPresenter;
import net.sf.anathema.library.view.trait.OptionalTraitsView;

import static net.sf.anathema.hero.individual.overview.HeroModelGroup.Perks;

@RegisteredInitializer (Perks)
@Weight (weight = 300)
public class MeritsInitializer implements HeroModelInitializer
{
	private HeroEnvironment environment;
	
	@SuppressWarnings ("UnusedParameters")
	public MeritsInitializer (HeroEnvironment environment)
	{
		this.environment = environment;
	}
	
	@Override
	public void initialize (SectionView sectionView, Hero hero)
	{
		String viewName = environment.getResources ().getString ("AdditionalTemplateView.TabName.Merits");
		OptionalTraitsView view = sectionView.addView (viewName, OptionalTraitsView.class);
		MeritsModel meritsModel = MeritsModelFetcher.fetch (hero);
		new CategorizedOptionalTraitPresenter (hero, meritsModel, view, environment.getResources (), MeritOption.MAX_MERIT_RATING).initPresentation ();
	}
	
	@Override
	public boolean canWorkForHero (Hero hero)
	{
		return MeritsModelFetcher.fetch (hero) != null;
	}
}
