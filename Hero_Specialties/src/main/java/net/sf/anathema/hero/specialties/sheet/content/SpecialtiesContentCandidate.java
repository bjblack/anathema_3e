package net.sf.anathema.hero.specialties.sheet.content;

import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.specialties.model.SpecialtiesModel;
import net.sf.anathema.hero.specialties.model.SpecialtiesModelFetcher;
import net.sf.anathema.hero.specialties.model.Specialty;
import net.sf.anathema.hero.traits.model.TraitType;

import java.util.Collection;

public class SpecialtiesContentCandidate
{
	private Hero hero;
	
	public SpecialtiesContentCandidate (Hero hero)
	{
		this.hero = hero;
	}
	
	public Collection<Specialty> getSpecialties (TraitType traitType)
	{
		SpecialtiesModel specialtyConfiguration = SpecialtiesModelFetcher.fetch (hero);
		return null; //specialtyConfiguration.getAllSpecialtiesOfType(traitType);
	}
}
