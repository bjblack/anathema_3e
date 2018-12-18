package net.sf.anathema.points.display.overview;

import net.sf.anathema.hero.environment.HeroEnvironment;
import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.individual.model.HeroModelInitializer;
import net.sf.anathema.hero.individual.model.RegisteredInitializer;
import net.sf.anathema.hero.individual.overview.HeroModelGroup;
import net.sf.anathema.hero.individual.view.SectionView;
import net.sf.anathema.library.message.Messaging;
import net.sf.anathema.points.display.overview.presenter.OverviewPresenter;
import net.sf.anathema.points.display.overview.view.OverviewContainer;
import net.sf.anathema.points.model.BonusPointManagement;
import net.sf.anathema.points.model.ExperiencePointManagement;
import net.sf.anathema.points.model.PointModelFetcher;

@RegisteredInitializer (HeroModelGroup.Miscellaneous)
public class OverviewInitializer implements HeroModelInitializer
{
	private HeroEnvironment environment;
	
	public OverviewInitializer (HeroEnvironment environment)
	{
		this.environment = environment;
	}
	
	@Override
	public void initialize (SectionView sectionView, Hero hero)
	{
		String header = "Overview";
		OverviewContainer container = sectionView.addView (header, OverviewContainer.class);
		initOverviewPresentation (hero, container);
	}
	
	@Override
	public boolean canWorkForHero (Hero hero)
	{
		return PointModelFetcher.fetch (hero) != null;
	}
	
	private void initOverviewPresentation (Hero hero, OverviewContainer container)
	{
		BonusPointManagement bonusPoints = PointModelFetcher.fetch (hero).getBonusPointManagement ();
		ExperiencePointManagement experiencePoints = PointModelFetcher.fetch (hero).getExperiencePointManagement ();
		Messaging messaging = environment.getMessaging ();
		OverviewPresenter presenter = new OverviewPresenter (environment.getResources (), hero, container, bonusPoints, experiencePoints, messaging);
		presenter.initPresentation ();
	}
}
