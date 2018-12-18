package net.sf.anathema.hero.thaumaturgy.model;

import net.sf.anathema.hero.environment.HeroEnvironment;
import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.individual.model.HeroModelInitializer;
import net.sf.anathema.hero.individual.model.RegisteredInitializer;
import net.sf.anathema.hero.individual.overview.HeroModelGroup;
import net.sf.anathema.hero.individual.view.SectionView;
import net.sf.anathema.library.initialization.Weight;
import net.sf.anathema.library.presenter.CategorizedOptionalTraitPresenter;
import net.sf.anathema.library.view.trait.OptionalTraitsView;

@RegisteredInitializer (HeroModelGroup.Sorcery)
@Weight (weight = 300)
public class RitualsInitializer implements HeroModelInitializer
{
	private HeroEnvironment environment;
	
	@SuppressWarnings ("UnusedParameters")
	public RitualsInitializer (HeroEnvironment environment)
	{
		this.environment = environment;
	}
	
	@Override
	public void initialize (SectionView sectionView, Hero hero)
	{
		String viewName = environment.getResources ().getString ("AdditionalTemplateView.TabName.Thaumaturgy");
		OptionalTraitsView view = sectionView.addView (viewName, OptionalTraitsView.class);
		ThaumaturgyModel thaumaturgyModel = ThaumaturgyModelFetcher.fetch (hero);
		new CategorizedOptionalTraitPresenter (hero, thaumaturgyModel, view, environment.getResources (), ThaumaturgyRitual.RITUAL_MAX_LEVEL).initPresentation ();
	}
	
	@Override
	public boolean canWorkForHero (Hero hero)
	{
		return ThaumaturgyModelFetcher.fetch (hero) != null;
	}
}
