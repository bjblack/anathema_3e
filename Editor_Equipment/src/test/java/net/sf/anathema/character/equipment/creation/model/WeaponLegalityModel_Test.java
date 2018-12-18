package net.sf.anathema.character.equipment.creation.model;

import net.sf.anathema.equipment.editor.stats.model.impl.WeaponLegalityModel;
import net.sf.anathema.equipment.editor.stats.model.impl.WeaponTagsModel;
import net.sf.anathema.equipment.stats.WeaponTag;

import org.junit.Before;
import org.junit.Test;

import static net.sf.anathema.equipment.stats.WeaponTag.Archery;
import static net.sf.anathema.equipment.stats.WeaponTag.MediumRange;
import static net.sf.anathema.equipment.stats.WeaponTag.getDamageTags;
import static net.sf.anathema.equipment.stats.WeaponTag.getSizeTags;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class WeaponLegalityModel_Test
{
	WeaponTagsModel tagsModel = new WeaponTagsModel ();
	WeaponLegalityModel legalityModel = new WeaponLegalityModel (tagsModel);
	
	@Before
	public void makeValid () throws Exception
	{
		legalityModel.getName ().setText ("Testname");
		tagsModel.makeValid ();
	}
	
	@Test
	public void isValidInitially () throws Exception
	{
		assertValid ();
	}
	
	@Test
	public void isInvalidWithoutWeaponSize () throws Exception
	{
		deselectAll (getSizeTags ());
		assertInvalid ();
	}
	
	@Test
	public void isInvalidWithoutDamageType () throws Exception
	{
		deselectAll (getDamageTags ());
		assertInvalid ();
	}
	
	@Test
	public void rangedWeaponIsInvalidWithoutRange () throws Exception
	{
		select (Archery);
		assertInvalid ();
	}
	
	@Test
	public void rangedWeaponIsValidWithRange () throws Exception
	{
		select (Archery);
		select (MediumRange);
		assertValid ();
	}
	
	private void deselectAll (WeaponTag[] sizeTags)
	{
		for (WeaponTag tag : sizeTags)
		{
			deselect (tag);
		}
	}
	
	private void select (WeaponTag tag)
	{
		tagsModel.getSelectedModel (tag).setValue (true);
	}
	
	private void deselect (WeaponTag tag)
	{
		tagsModel.getSelectedModel (tag).setValue (false);
	}
	
	private void assertValid ()
	{
		assertThat (legalityModel.isValid (), is (true));
	}
	
	private void assertInvalid ()
	{
		assertThat (legalityModel.isValid (), is (false));
	}
}
