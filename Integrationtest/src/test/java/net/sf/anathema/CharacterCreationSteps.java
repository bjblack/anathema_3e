package net.sf.anathema;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.java.guice.ScenarioScoped;

import net.sf.anathema.hero.application.item.HeroItemData;
import net.sf.anathema.integration.CharacterFactory;
import net.sf.anathema.integration.concept.ConceptModelUtilities;

import com.google.inject.Inject;

@ScenarioScoped
public class CharacterCreationSteps
{
	private final CharacterHolder holder;
	private CharacterFactory characterFactory;
	
	@Inject
	public CharacterCreationSteps (CharacterHolder holder)
	{
		this.holder = holder;
	}
	
	@Before
	public void startAnathema ()
	{
		this.characterFactory = new CharacterFactory ();
		this.characterFactory.startAnathema ();
	}
	
	@Given ("Anathema is running")
	public void Anathema_is_running ()
	{
		//for readable tests only
	}
	
	@Given ("^a new God-Blooded of any kind$")
	public void I_create_a_new_god_blooded () throws Throwable
	{
		I_create_a_new_character_with_subtype ("Lunar", "HalfCasteLunar");
	}
	
	@Given ("^a new (.*) using rules for (.*)$")
	public void I_create_a_new_character_with_subtype (String type, String subtype) throws Throwable
	{
		HeroItemData heroItemData = characterFactory.createCharacter (type, subtype);
		holder.setCharacter (heroItemData);
	}
	
	@Given ("^any Solar$")
	public void I_create_any_Solar () throws Throwable
	{
		HeroItemData heroItemData = characterFactory.createCharacter ("Solar", "RookieLawgiver");
		holder.setCharacter (heroItemData);
	}
	
	@Given ("^any Mortal$")
	public void I_create_any_Molar () throws Throwable
	{
		HeroItemData heroItemData = characterFactory.createCharacter ("Mortal", "EverydayHero");
		holder.setCharacter (heroItemData);
	}
	
	@Given ("^any Solar with Caste (.*)$")
	public void I_create_any_Solar_with_Caste (String caste) throws Throwable
	{
		I_create_any_Solar ();
		ConceptModelUtilities.setCaste (holder.getHero (), caste);
	}
	
	@Then ("^I can create a new (.*) using rules for (.*)$")
	public void I_can_create_a_new_character (String type, String subtype) throws Throwable
	{
		characterFactory.createCharacter (type, subtype);
	}
	
	@When ("^I save and reload the character$")
	public void I_save_and_reload_the_character () throws Throwable
	{
		HeroItemData reloadedHeroItemData = characterFactory.saveAndReload (holder.getHero ());
		holder.setCharacter (reloadedHeroItemData);
	}
	
	@Given ("^a new Character of any kind$")
	public void a_new_Character_of_any_kind () throws Throwable
	{
		I_create_a_new_character_with_subtype ("Solar", "RookieLawgiver");
	}
	
	@After
	public void deleteRepository () throws Throwable
	{
		this.characterFactory.tearDownRepository ();
	}
}
