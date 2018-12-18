package net.sf.anathema;

import cucumber.api.java.en.Then;
import cucumber.runtime.java.guice.ScenarioScoped;

import com.google.inject.Inject;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ScenarioScoped
public class CharacterFrameworkSteps
{
	private final CharacterHolder character;
	
	@Inject
	public CharacterFrameworkSteps (CharacterHolder character)
	{
		this.character = character;
	}
	
	@Then ("^the character needs to be saved$")
	public void the_character_needs_to_be_saved () throws Throwable
	{
		assertThat (character.getHero ().getChangeManagement ().isDirty (), is (true));
	}
}
