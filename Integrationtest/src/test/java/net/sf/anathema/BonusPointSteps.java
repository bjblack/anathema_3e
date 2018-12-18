package net.sf.anathema;

import cucumber.api.java.en.Then;
import cucumber.runtime.java.guice.ScenarioScoped;

import net.sf.anathema.points.model.BonusPointManagement;
import net.sf.anathema.points.model.PointModelFetcher;
import net.sf.anathema.points.model.overview.SpendingModel;

import com.google.inject.Inject;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ScenarioScoped
public class BonusPointSteps
{
	private final CharacterHolder character;
	private final BonusModelFetcher bonusModel;
	
	@Inject
	public BonusPointSteps (CharacterHolder character)
	{
		this.character = character;
		this.bonusModel = new BonusModelFetcher (character);
	}
	
	@Then ("^she has spent (\\d+) bonus points$")
	public void she_has_spent_bonus_points (int amount) throws Throwable
	{
		BonusPointManagement bonusPointManagement = calculateBonusPoints ();
		int spentBonusPoints = bonusPointManagement.getTotalModel ().getValue ();
		assertThat (spentBonusPoints, is (amount));
	}
	
	@Then ("^she has (\\d+) favored dots spent$")
	public void she_has_favored_dots_spent (int amount) throws Throwable
	{
		calculateBonusPoints ();
		Integer dotsSpent = findBonusModel ("Abilities", "FavoredDot").getValue ();
		assertThat (dotsSpent, is (amount));
	}
	
	@Then ("^she has (\\d+) ability dots spent$")
	public void she_has_ability_dots_spent (int amount) throws Throwable
	{
		calculateBonusPoints ();
		Integer dotsSpent = findBonusModel ("Abilities", "General").getValue ();
		assertThat (dotsSpent, is (amount));
	}
	
	@Then ("^she has (\\d+) favored picks spent$")
	public void she_has_favored_picks_spent (int amount) throws Throwable
	{
		calculateBonusPoints ();
		Integer dotsSpent = findBonusModel ("Abilities", "FavoredPick").getValue ();
		assertThat (dotsSpent, is (amount));
	}
	
	@Then ("^she has spent (\\d+) Charm pick$")
	public void she_has_spent_Charm_pick (int picks) throws Throwable
	{
		calculateBonusPoints ();
		Integer dotsSpent = findBonusModel ("Magic", "General").getValue ();
		assertThat (dotsSpent, is (picks));
	}
	
	@Then ("^she has all her ability dots spent$")
	public void she_has_all_her_ability_dots_spent () throws Throwable
	{
		SpendingModel bonusModel = findBonusModel ("Abilities", "General");
		she_has_ability_dots_spent (bonusModel.getAllotment ());
	}
	
	@Then ("^she has spent (\\d+) points on Primary Attributes$")
	public void she_has_spent_points_on_Primary_Attributes (int dots) throws Throwable
	{
		calculateBonusPoints ();
		Integer dotsSpent = findBonusModel ("Attributes", "Primary").getValue ();
		assertThat (dotsSpent, is (dots));
	}
	
	@Then ("^she has spent (\\d+) points on Secondary Attributes$")
	public void she_has_spent_points_on_Secondary_Attributes (int dots) throws Throwable
	{
		calculateBonusPoints ();
		Integer dotsSpent = findBonusModel ("Attributes", "Secondary").getValue ();
		assertThat (dotsSpent, is (dots));
	}
	
	@Then ("^she has spent (\\d+) points on Tertiary Attributes$")
	public void she_has_spent_points_on_Tertiary_Attributes (int dots) throws Throwable
	{
		calculateBonusPoints ();
		Integer dotsSpent = findBonusModel ("Attributes", "Tertiary").getValue ();
		assertThat (dotsSpent, is (dots));
	}
	
	private SpendingModel findBonusModel (String category, String id)
	{
		return (SpendingModel) bonusModel.findBonusModel (category, id);
	}
	
	private BonusPointManagement calculateBonusPoints ()
	{
		BonusPointManagement bonusPointManagement = PointModelFetcher.fetch (character.getHero ()).getBonusPointManagement ();
		bonusPointManagement.recalculate ();
		return bonusPointManagement;
	}
}
