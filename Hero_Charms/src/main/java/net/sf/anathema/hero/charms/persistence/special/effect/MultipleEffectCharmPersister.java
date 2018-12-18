package net.sf.anathema.hero.charms.persistence.special.effect;

import net.sf.anathema.hero.charms.model.special.CharmSpecialLearningModel;
import net.sf.anathema.hero.charms.model.special.subeffects.MultipleEffectCharmSpecials;
import net.sf.anathema.hero.charms.model.special.subeffects.SubEffect;
import net.sf.anathema.hero.charms.persistence.special.CharmSpecialsPto;
import net.sf.anathema.hero.charms.persistence.special.SpecialCharmPersister;

public class MultipleEffectCharmPersister implements SpecialCharmPersister
{
	@Override
	public void saveCharmSpecials (CharmSpecialLearningModel charmSpecials, CharmSpecialsPto specialsPto)
	{
		MultipleEffectCharmSpecials multipleEffects = (MultipleEffectCharmSpecials) charmSpecials;
		SubEffectListPto subEffectsList = createPto (multipleEffects);
		specialsPto.subEffects = subEffectsList;
	}
	
	private SubEffectListPto createPto (MultipleEffectCharmSpecials multipleEffects)
	{
		SubEffectListPto subEffectsList = new SubEffectListPto ();
		for (SubEffect effect : multipleEffects.getEffects ())
		{
			SubEffectPto pto = createSubEffectPto (effect);
			subEffectsList.subEffects.add (pto);
		}
		return subEffectsList;
	}
	
	private SubEffectPto createSubEffectPto (SubEffect effect)
	{
		SubEffectPto pto = new SubEffectPto ();
		pto.id = effect.getId ();
		pto.creationLearned = effect.isCreationLearned ();
		pto.experienceLearned = effect.isCreationLearned () || effect.isLearned ();
		return pto;
	}
	
	@Override
	public void loadCharmSpecials (CharmSpecialLearningModel charmSpecials, CharmSpecialsPto specialsPto)
	{
		if (specialsPto.subEffects == null)
		{
			return;
		}
		MultipleEffectCharmSpecials configuration = (MultipleEffectCharmSpecials) charmSpecials;
		for (SubEffectPto effectPto : specialsPto.subEffects.subEffects)
		{
			SubEffect effect = configuration.getEffectById (effectPto.id);
			effect.setCreationLearned (effectPto.creationLearned);
			effect.setExperienceLearned (effectPto.experienceLearned);
		}
	}
}
