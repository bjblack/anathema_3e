package net.sf.anathema;

import com.google.inject.Inject;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.java.guice.ScenarioScoped;
import net.sf.anathema.hero.concept.model.concept.CasteCollection;
import net.sf.anathema.hero.concept.model.concept.CasteType;
import net.sf.anathema.hero.concept.model.concept.HeroConceptFetcher;
import net.sf.anathema.hero.experience.model.ExperienceModelFetcher;
import net.sf.anathema.hero.intimacies.model.IntimaciesModel;
import net.sf.anathema.hero.intimacies.model.IntimaciesModelFetcher;
import net.sf.anathema.hero.traits.model.Trait;
import net.sf.anathema.hero.traits.model.TraitType;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ScenarioScoped
public class CharacterChangeSteps
{
	public static final int MANY = 20;
	private final CharacterHolder character;
	
	@Inject
	public CharacterChangeSteps (CharacterHolder character)
	{
		this.character = character;
	}
	
	@Given ("^her current Essence is (\\d+)$")
	public void herCurrentEssenceIs (int value) throws Throwable
	{
		I_set_her_trait_to ("Essence", value);
	}
	
	@When ("^she goes experienced")
	public void whenSheGoesExperienced ()
	{
		setToExperienced ();
	}
	
	@Given ("^she is experienced")
	public void setToExperienced ()
	{
		ExperienceModelFetcher.fetch (character.getHero ()).setExperienced (true);
	}
	
	@When ("^I set her Caste to (.*)$")
	public void I_set_her_Caste (String casteName) throws Throwable
	{
		CasteCollection casteCollection = HeroConceptFetcher.fetch (character.getHero ()).getCasteCollection ();
		CasteType caste = casteCollection.getById (casteName);
		character.getCharacterConcept ().getCaste ().setType (caste);
	}
	
	@When ("^I set her (.*) to (\\d+)$")
	public void I_set_her_trait_to (String traitId, int value) throws Throwable
	{
		TraitType type = new TraitType (traitId);
		Trait trait = character.getTraitConfiguration ().getTrait (type);
		trait.setCurrentValue (value);
	}
	
	@When ("^I add a fresh Intimacy$")
	public void I_add_a_fresh_intimacy () throws Throwable
	{
		addIntimacy ("New Intimacy");
	}
	
	private void addIntimacy (String name)
	{
		IntimaciesModel model = IntimaciesModelFetcher.fetch (character.getHero ());
		model.setCurrentName (name);
		model.commitSelection ();
	}
	
	@When ("^I add many Intimacies$")
	public void I_add_intimacies () throws Throwable
	{
		for (int i = 1; i <= MANY; i++)
		{
			addIntimacy ("Intimacy " + i);
		}
	}
	
	@Then ("^the character has as many Intimacies$")
	public void the_character_as_intimacies () throws Throwable
	{
		IntimaciesModel model = IntimaciesModelFetcher.fetch (character.getHero ());
		assertThat (model.getEntries ().size (), is (MANY));
	}
}
