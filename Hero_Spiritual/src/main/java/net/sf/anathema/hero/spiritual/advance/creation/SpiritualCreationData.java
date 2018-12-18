package net.sf.anathema.hero.spiritual.advance.creation;

import net.sf.anathema.hero.traits.advance.TraitListCreationData;
import net.sf.anathema.hero.traits.model.TraitType;

public interface SpiritualCreationData extends TraitListCreationData
{
	int getCalculationBase (TraitType type);
	
	int getWillpowerCost ();
}
