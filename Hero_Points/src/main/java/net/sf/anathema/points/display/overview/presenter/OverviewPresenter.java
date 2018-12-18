package net.sf.anathema.points.display.overview.presenter;

import net.sf.anathema.hero.experience.model.ExperienceChange;
import net.sf.anathema.hero.experience.model.ExperienceModelFetcher;
import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.library.message.MessageToken;
import net.sf.anathema.library.message.Messaging;
import net.sf.anathema.library.resources.Resources;
import net.sf.anathema.points.display.overview.view.CategorizedOverview;
import net.sf.anathema.points.display.overview.view.OverviewContainer;
import net.sf.anathema.points.model.BonusPointManagement;
import net.sf.anathema.points.model.ExperiencePointManagement;

public class OverviewPresenter
{
	private Resources resources;
	private Hero hero;
	private OverviewContainer container;
	private BonusPointManagement bonusPoints;
	private ExperiencePointManagement experiencePoints;
	private Messaging messaging;
	
	public OverviewPresenter (Resources resources, Hero hero, OverviewContainer container, BonusPointManagement bonusPoints,
	ExperiencePointManagement experiencePoints, Messaging messaging)
	{
		this.resources = resources;
		this.hero = hero;
		this.container = container;
		this.bonusPoints = bonusPoints;
		this.experiencePoints = experiencePoints;
		this.messaging = messaging;
	}
	
	public void initPresentation ()
	{
		CategorizedOverview creationPointView = container.addCreationOverviewView ();
		MessageToken tokenForCreationAndExperience = messaging.obtainInitialToken ();
		CreationOverviewPresenter creationOverviewPresenter = new CreationOverviewPresenter (resources, hero, creationPointView, bonusPoints, tokenForCreationAndExperience);
		creationOverviewPresenter.initPresentation ();
		CategorizedOverview experiencePointView = container.addExperienceOverviewView ();
		ExperiencedOverviewPresenter experiencedOverviewPresenter = new ExperiencedOverviewPresenter (resources, hero, experiencePointView, experiencePoints, tokenForCreationAndExperience);
		experiencedOverviewPresenter.initPresentation ();
		boolean experienced = ExperienceModelFetcher.fetch (hero).isExperienced ();
		setOverviewView (experienced);
		if (experienced)
		{
			experiencedOverviewPresenter.refresh ();
		}
		else
		{
			creationOverviewPresenter.refresh ();
		}
		hero.getChangeAnnouncer ().addListener (flavor ->
		{
			if (flavor == ExperienceChange.FLAVOR_EXPERIENCE_STATE)
			{
				setOverviewView (ExperienceModelFetcher.fetch (hero).isExperienced ());
			}
		}
		);
	}
	
	private void setOverviewView (boolean experienced)
	{
		container.toggleOverviewView (experienced);
	}
}
