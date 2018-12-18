package net.sf.anathema.hero.magic.advance.creation;

import net.sf.anathema.hero.magic.model.learn.MagicLearner;
import net.sf.anathema.magic.data.Magic;

import java.util.ArrayList;
import java.util.List;

public class MagicCreationCostEvaluator
{
	private final List<MagicLearner> magicLearners = new ArrayList<> ();
	
	public void registerMagicLearner (MagicLearner learner)
	{
		magicLearners.add (learner);
	}
	
	public List<Magic> compileCompleteMagicList ()
	{
		List<Magic> completeList = new ArrayList<> ();
		for (MagicLearner learner : magicLearners)
		{
			completeList.addAll (learner.getLearnedMagic (false));
		}
		return completeList;
	}
	
	public int getLearnCount (Magic magic)
	{
		MagicLearner learner = getLearnerFor (magic);
		return learner.getCreationLearnCount (magic);
	}
	
	public int getAdditionalBonusPoints (Magic magic)
	{
		MagicLearner learner = getLearnerFor (magic);
		return learner.getAdditionalBonusPoints (magic);
	}
	
	private MagicLearner getLearnerFor (Magic magic)
	{
		for (MagicLearner learner : magicLearners)
		{
			if (learner.handlesMagic (magic))
			{
				return learner;
			}
		}
		throw new IllegalStateException ("Unknown magic " + magic);
	}
}
