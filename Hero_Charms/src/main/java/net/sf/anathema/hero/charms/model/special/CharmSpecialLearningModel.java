package net.sf.anathema.hero.charms.model.special;

import net.sf.anathema.magic.data.Charm;

public interface CharmSpecialLearningModel
{
	int getCreationLearnCount ();
	
	void addSpecialCharmLearnListener (ISpecialCharmLearnListener listener);
	
	Charm getCharm ();
	
	int getCurrentLearnCount ();
	
	void forget ();
	
	void learn (boolean experienced);
}
