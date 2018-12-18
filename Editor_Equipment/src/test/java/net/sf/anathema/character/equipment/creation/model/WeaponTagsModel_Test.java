package net.sf.anathema.character.equipment.creation.model;

import net.sf.anathema.equipment.editor.stats.model.impl.WeaponTagsModel;
import net.sf.anathema.equipment.stats.WeaponTag;

import org.junit.Test;

import static net.sf.anathema.equipment.stats.WeaponTag.Archery;
import static net.sf.anathema.equipment.stats.WeaponTag.Bashing;
import static net.sf.anathema.equipment.stats.WeaponTag.CloseRange;
import static net.sf.anathema.equipment.stats.WeaponTag.Flame;
import static net.sf.anathema.equipment.stats.WeaponTag.Heavy;
import static net.sf.anathema.equipment.stats.WeaponTag.Lethal;
import static net.sf.anathema.equipment.stats.WeaponTag.LongRange;
import static net.sf.anathema.equipment.stats.WeaponTag.Medium;
import static net.sf.anathema.equipment.stats.WeaponTag.MediumRange;
import static net.sf.anathema.equipment.stats.WeaponTag.ShortRange;
import static net.sf.anathema.equipment.stats.WeaponTag.Thrown;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class WeaponTagsModel_Test
{
	WeaponTagsModel model = new WeaponTagsModel ();
	
	@Test
	public void aWeaponCanOnlyHaveOneSize () throws Exception
	{
		select (Medium);
		assertTagIsForbidden (Heavy);
	}
	
	@Test
	public void allSizesAreAvailableWhenOneBecomesDeselected () throws Exception
	{
		select (Medium);
		deselect (Medium);
		assertTagIsAllowed (Heavy);
	}
	
	@Test
	public void aWeaponCanOnlyHaveOneDamageType () throws Exception
	{
		select (Lethal);
		assertTagIsForbidden (Bashing);
	}
	
	@Test
	public void aWeaponCanOnlyHaveOneRangedType () throws Exception
	{
		select (Archery);
		assertTagIsForbidden (Thrown);
	}
	
	@Test
	public void aWeaponCanOnlyHaveOneRange () throws Exception
	{
		select (MediumRange);
		assertTagIsForbidden (ShortRange);
		assertTagIsForbidden (CloseRange);
		assertTagIsForbidden (LongRange);
	}
	
	@Test
	public void closeCombatWeaponsCantUseFlame () throws Exception
	{
		assertTagIsForbidden (Flame);
	}
	
	@Test
	public void rangedWeaponsCanUseFlame () throws Exception
	{
		select (Archery);
		assertTagIsAllowed (Flame);
	}
	
	private void select (WeaponTag tag)
	{
		model.getSelectedModel (tag).setValue (true);
	}
	
	private void deselect (WeaponTag tag)
	{
		model.getSelectedModel (tag).setValue (false);
	}
	
	private void assertTagIsAllowed (WeaponTag tag)
	{
		assertThat (model.getEnabledModel (tag).getValue (), is (true));
	}
	
	private void assertTagIsForbidden (WeaponTag tag)
	{
		assertThat (model.getEnabledModel (tag).getValue (), is (false));
	}
}
