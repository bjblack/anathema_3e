package net.sf.anathema.hero.concept.model.concept;

import net.sf.anathema.hero.individual.model.HeroModel;
import net.sf.anathema.library.identifier.Identifier;
import net.sf.anathema.library.identifier.SimpleIdentifier;

public interface HeroConcept extends HeroModel
{
	public static final Identifier ID = new SimpleIdentifier ("Concept");
	
	CasteSelection getCaste ();
	
	CasteCollection getCasteCollection ();
}
