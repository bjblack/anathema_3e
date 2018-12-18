package net.sf.anathema.hero.specialties.advance;

import net.sf.anathema.hero.environment.HeroEnvironment;
import net.sf.anathema.hero.individual.change.ChangeAnnouncer;
import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.individual.model.HeroModel;
import net.sf.anathema.hero.specialties.advance.creation.SpecialtiesBonusPointCalculator;
import net.sf.anathema.hero.specialties.advance.creation.SpecialtyBonusModel;
import net.sf.anathema.hero.specialties.advance.creation.SpecialtyCreationData;
import net.sf.anathema.hero.specialties.advance.experience.SpecialtyExperienceData;
import net.sf.anathema.hero.specialties.advance.experience.SpecialtyExperienceModel;
import net.sf.anathema.hero.specialties.template.SpecialtyPointsTemplate;
import net.sf.anathema.library.identifier.Identifier;
import net.sf.anathema.library.identifier.SimpleIdentifier;
import net.sf.anathema.points.model.PointModelFetcher;
import net.sf.anathema.points.model.PointsModel;

public class SpecialtiesPointsModel implements HeroModel
{
	public static final Identifier ID = new SimpleIdentifier ("SpecialtiesPoints");
	private SpecialtyPointsTemplate template;
	
	public SpecialtiesPointsModel (SpecialtyPointsTemplate template)
	{
		this.template = template;
	}
	
	@Override
	public Identifier getId ()
	{
		return ID;
	}
	
	@Override
	public void initialize (HeroEnvironment environment, Hero hero)
	{
		PointsModel pointsModel = PointModelFetcher.fetch (hero);
		SpecialtyCreationData creationData = new SpecialtyCreationData (template);
		SpecialtiesBonusPointCalculator specialtiesBonusPointCalculator = new SpecialtiesBonusPointCalculator (hero, creationData);
		pointsModel.addBonusPointCalculator (specialtiesBonusPointCalculator);
		if (creationData.getFreebiePoints () > 0)
		{
			pointsModel.addToBonusOverview (new SpecialtyBonusModel (specialtiesBonusPointCalculator, template));
		}
		pointsModel.addToExperienceOverview (new SpecialtyExperienceModel (hero, new SpecialtyExperienceData (template)));
	}
	
	@Override
	public void initializeListening (ChangeAnnouncer announcer)
	{
		// nothing to do
	}
}
