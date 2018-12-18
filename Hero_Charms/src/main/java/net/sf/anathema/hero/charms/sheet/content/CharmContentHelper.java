package net.sf.anathema.hero.charms.sheet.content;

import net.sf.anathema.hero.charms.model.CharmsModel;
import net.sf.anathema.hero.charms.model.CharmsModelFetcher;
import net.sf.anathema.hero.charms.model.learn.Charms;
import net.sf.anathema.hero.charms.model.special.CharmSpecialLearningModel;
import net.sf.anathema.hero.charms.model.special.subeffects.MultipleEffectCharmSpecials;
import net.sf.anathema.hero.charms.model.special.subeffects.SubEffect;
import net.sf.anathema.hero.charms.model.special.subeffects.SubEffectCharmSpecials;
import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.magic.data.Charm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CharmContentHelper
{
	private final Hero hero;
	
	public CharmContentHelper (Hero hero)
	{
		this.hero = hero;
	}
	
	public boolean isMultipleEffectCharm (Charm charm)
	{
		CharmSpecialLearningModel charmConfiguration = CharmsModelFetcher.fetch (hero).getCharmSpecialLearningModel (charm);
		return charmConfiguration instanceof MultipleEffectCharmSpecials && ! (charmConfiguration instanceof SubEffectCharmSpecials);
	}
	
	public Iterable<String> getLearnedEffects (Charm charm)
	{
		CharmSpecialLearningModel charmConfiguration = CharmsModelFetcher.fetch (hero).getCharmSpecialLearningModel (charm);
		if (! (charmConfiguration instanceof MultipleEffectCharmSpecials))
		{
			return Collections.emptyList ();
		}
		MultipleEffectCharmSpecials configuration = (MultipleEffectCharmSpecials) charmConfiguration;
		List<String> learnedEffectIds = new ArrayList<> ();
		for (SubEffect effect : configuration.getEffects ())
		{
			if (effect.isLearned ())
			{
				learnedEffectIds.add (effect.getId ());
			}
		}
		return learnedEffectIds;
	}
	
	public Charms getLearnedCharms ()
	{
		return CharmsModelFetcher.fetch (hero).getLearningModel ().getCurrentlyLearnedCharms ();
	}
	
	public boolean isSubEffectCharm (Charm charm)
	{
		CharmSpecialLearningModel charmConfiguration = CharmsModelFetcher.fetch (hero).getCharmSpecialLearningModel (charm);
		return charmConfiguration instanceof SubEffectCharmSpecials;
	}
	
	public int getLearnCount (Charm charm)
	{
		return getLearnCount (charm, CharmsModelFetcher.fetch (hero));
	}
	
	private int getLearnCount (Charm charm, CharmsModel model)
	{
		CharmSpecialLearningModel specialCharmConfiguration = model.getCharmSpecialLearningModel (charm);
		if (specialCharmConfiguration != null)
		{
			return specialCharmConfiguration.getCurrentLearnCount ();
		}
		return model.isLearned (charm) ? 1 : 0;
	}
}
