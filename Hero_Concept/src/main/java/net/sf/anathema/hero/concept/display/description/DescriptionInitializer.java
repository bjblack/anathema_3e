package net.sf.anathema.hero.concept.display.description;

import net.sf.anathema.hero.concept.model.concept.HeroConcept;
import net.sf.anathema.hero.concept.model.concept.HeroConceptFetcher;
import net.sf.anathema.hero.concept.model.description.HeroDescription;
import net.sf.anathema.hero.concept.model.description.HeroDescriptionFetcher;
import net.sf.anathema.hero.environment.HeroEnvironment;
import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.individual.model.HeroModelInitializer;
import net.sf.anathema.hero.individual.model.RegisteredInitializer;
import net.sf.anathema.hero.individual.view.SectionView;
import net.sf.anathema.library.initialization.Weight;
import net.sf.anathema.library.view.ConfigurableCharacterView;

import static net.sf.anathema.hero.individual.overview.HeroModelGroup.Background;

@RegisteredInitializer (Background)
@Weight (weight = 0)
public class DescriptionInitializer implements HeroModelInitializer
{
	private HeroEnvironment environment;
	
	@SuppressWarnings ("UnusedParameters")
	public DescriptionInitializer (HeroEnvironment environment)
	{
		this.environment = environment;
	}
	
	@Override
	public void initialize (SectionView sectionView, Hero hero)
	{
		String header = environment.getResources ().getString ("CardView.CharacterDescription.Title");
		ConfigurableCharacterView descriptionView = sectionView.addView (header, ConfigurableCharacterView.class);
		DescriptionDetails descriptionDetails = createDescriptionDetails (hero);
		new DescriptionPresenter (descriptionDetails, environment, descriptionView).initPresentation ();
	}
	
	@Override
	public boolean canWorkForHero (Hero hero)
	{
		return HeroDescriptionFetcher.fetch (hero) != null && HeroConceptFetcher.fetch (hero) != null;
	}
	
	private DescriptionDetails createDescriptionDetails (Hero hero)
	{
		HeroDescription characterDescription = HeroDescriptionFetcher.fetch (hero);
		HeroConcept heroConcept = HeroConceptFetcher.fetch (hero);
		boolean isExalt = hero.getSplat ().getTemplateType ().getHeroType ().isExaltType ();
		return new DescriptionDetails (characterDescription, heroConcept, isExalt);
	}
}
