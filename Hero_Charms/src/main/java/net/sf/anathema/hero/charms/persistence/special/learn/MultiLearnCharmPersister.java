package net.sf.anathema.hero.charms.persistence.special.learn;

import net.sf.anathema.hero.charms.model.special.CharmSpecialLearningModel;
import net.sf.anathema.hero.charms.model.special.learning.multilearn.MultiLearnCharmSpecials;
import net.sf.anathema.hero.charms.persistence.special.CharmSpecialsPto;
import net.sf.anathema.hero.charms.persistence.special.SpecialCharmPersister;
import net.sf.anathema.hero.traits.model.Trait;

public class MultiLearnCharmPersister implements SpecialCharmPersister
{
	@Override
	public void saveCharmSpecials (CharmSpecialLearningModel charmSpecials, CharmSpecialsPto specialsPto)
	{
		MultiLearnCharmSpecials multiLearn = (MultiLearnCharmSpecials) charmSpecials;
		specialsPto.multiLearn = createMultiLearnPto (multiLearn);
	}
	
	private MultiLearnPto createMultiLearnPto (MultiLearnCharmSpecials multiLearn)
	{
		MultiLearnPto multiLearnPto = new MultiLearnPto ();
		multiLearnPto.learnCount.creationValue = multiLearn.getCategory ().getCreationValue ();
		if (multiLearn.getCategory ().getExperiencedValue () > 0)
		{
			multiLearnPto.learnCount.experienceValue = multiLearn.getCategory ().getExperiencedValue ();
		}
		return multiLearnPto;
	}
	
	@Override
	public void loadCharmSpecials (CharmSpecialLearningModel charmSpecials, CharmSpecialsPto specialsPto)
	{
		MultiLearnCharmSpecials configuration = (MultiLearnCharmSpecials) charmSpecials;
		if (specialsPto.multiLearn == null)
		{
			return;
		}
		restoreLearnCount (specialsPto, configuration);
	}
	
	private void restoreLearnCount (CharmSpecialsPto specialsPto, MultiLearnCharmSpecials configuration)
	{
		Trait category = configuration.getCategory ();
		LearnCountPto learnCount = specialsPto.multiLearn.learnCount;
		category.setUncheckedCreationValue (learnCount.creationValue);
		if (learnCount.experienceValue != null)
		{
			category.setUncheckedExperiencedValue (learnCount.experienceValue);
		}
	}
}
