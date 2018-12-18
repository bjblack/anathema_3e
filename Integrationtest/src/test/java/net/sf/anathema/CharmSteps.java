package net.sf.anathema;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.java.guice.ScenarioScoped;

import net.sf.anathema.magic.data.Charm;
import net.sf.anathema.magic.data.reference.CharmName;
import net.sf.anathema.hero.charms.model.CharmsModel;
import net.sf.anathema.hero.charms.model.special.subeffects.MultipleEffectCharmSpecials;

import com.google.inject.Inject;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ScenarioScoped
public class CharmSteps
{
	private final CharacterHolder character;
	
	@Inject
	public CharmSteps (CharacterHolder character)
	{
		this.character = character;
	}
	
	@When ("^she learns the Charm (.*)$")
	public void she_learns_the_Charm (String id) throws Throwable
	{
		toggleLearned (id);
	}
	
	@When ("^she forgets the Charm (.*)$")
	public void she_forgets_the_Charm (String id) throws Throwable
	{
		toggleLearned (id);
	}
	
	@Then ("^she can learn the Charm (.*)$")
	public void she_can_learn_the_Charm (String id) throws Throwable
	{
		Charm charm = character.getCharms ().getCharmById (new CharmName (id));
		boolean learnable = character.getCharms ().isLearnable (charm);
		assertThat (learnable, is (true));
	}
	
	@Then ("^she can not learn the Charm (.*)$")
	public void she_can_not_learn_the_Charm (String id) throws Throwable
	{
		Charm charm = character.getCharms ().getCharmById (new CharmName (id));
		boolean learnable = character.getCharms ().isLearnable (charm);
		assertThat (learnable, is (false));
	}
	
	@Then ("^she knows the Charm (.*)$")
	public void she_knows_the_Charm (String id) throws Throwable
	{
		boolean learned = character.getCharms ().isLearned (new CharmName (id));
		assertThat (learned, is (true));
	}
	
	@Then ("^she does not know the Charm (.*)$")
	public void she_does_not_know_the_Charm (String id) throws Throwable
	{
		boolean learned = character.getCharms ().isLearned (new CharmName (id));
		assertThat (learned, is (false));
	}
	
	@Then ("^she has chosen the effect (.*) for the Charm (.*)$")
	public void she_has_chosen_the_effect_for_the_Charm (String effect, String charmId) throws Throwable
	{
		CharmsModel charms = character.getCharms ();
		assertThat (charms.isLearned (new CharmName (charmId)), is (true));
		Charm charm = character.getCharms ().getCharmById (new CharmName (charmId));
		MultipleEffectCharmSpecials configuration = (MultipleEffectCharmSpecials) charms.getCharmSpecialLearningModel (charm);
		boolean effectLearned = configuration.getEffectById (effect).isLearned ();
		assertThat (effectLearned, is (true));
	}
	
	private void toggleLearned (String id)
	{
		Charm charm = character.getCharms ().getCharmById (new CharmName (id));
		character.getCharms ().getLearningModel ().toggleLearned (charm);
	}
}
