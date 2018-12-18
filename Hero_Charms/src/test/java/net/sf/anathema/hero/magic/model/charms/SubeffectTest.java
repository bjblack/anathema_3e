package net.sf.anathema.hero.magic.model.charms;

import net.sf.anathema.hero.charms.model.special.subeffects.SubEffectImpl;
import net.sf.anathema.hero.dummy.DummyCondition;
import net.sf.anathema.hero.experience.model.ExperienceModel;

import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SubeffectTest
{
	@Test
	public void testIsCorrectlyLearned () throws Exception
	{
		ExperienceModel experience = mock (ExperienceModel.class);
		when (experience.isExperienced ()).thenReturn (false);
		DummyCondition condition = new DummyCondition ();
		SubEffectImpl subeffect = new SubEffectImpl ("Effective", experience, condition);
		subeffect.setLearned (true);
		Assert.assertFalse (subeffect.isLearned ());
		condition.setValue (true);
		subeffect.setLearned (true);
		Assert.assertTrue (subeffect.isLearned ());
		condition.setValue (false);
		subeffect.setLearned (false);
		Assert.assertFalse (subeffect.isLearned ());
	}
}
