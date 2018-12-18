package net.sf.anathema.herotype.mortal;

import net.sf.anathema.hero.charms.display.presenter.AbstractCharmPresentationProperties;
import net.sf.anathema.hero.environment.herotype.ForCharacterType;
import net.sf.anathema.hero.individual.splat.HeroType;
import net.sf.anathema.library.presenter.RGBColor;

@ForCharacterType ("Mortal")
public class MortalCharmPresentationProperties extends AbstractCharmPresentationProperties
{
	public static final String ID = "Mortal";
	
	public MortalCharmPresentationProperties ()
	{
		super (new RGBColor (177, 177, 253));
	}
	
	@Override
	public boolean supportsCharacterType (HeroType type)
	{
		return type.getId ().equals (ID);
	}
}
