package net.sf.anathema.character.equipment.view;

import net.sf.anathema.character.equipment.dummy.DemoMeleeWeapon;
import net.sf.anathema.equipment.presentation.EquipmentStringBuilder;
import net.sf.anathema.equipment.presentation.IEquipmentStringBuilder;
import net.sf.anathema.equipment.stats.ArmourTag;
import net.sf.anathema.equipment.stats.impl.ArmourStats;
import net.sf.anathema.hero.health.model.HealthType;
import net.sf.anathema.lib.dummy.DummyResources;
import net.sf.anathema.library.identifier.SimpleIdentifier;

import org.junit.Before;
import org.junit.Test;

import static net.sf.anathema.equipment.stats.WeaponTag.Artifact;
import static net.sf.anathema.equipment.stats.WeaponTag.Lethal;
import static net.sf.anathema.equipment.stats.WeaponTag.Light;
import static org.junit.Assert.assertEquals;

public class EquipmentStringBuilderTest
{
	private IEquipmentStringBuilder equipmentStringBuilder;
	
	@Before
	public void setUp () throws Exception
	{
		DummyResources resources = new DummyResources ();
		resources.putString ("Melee", "Melee");
		equipmentStringBuilder = new EquipmentStringBuilder (resources);
	}
	
	@Test
	public void testMeleeWeapon ()
	{
		DemoMeleeWeapon weapon = new DemoMeleeWeapon (new SimpleIdentifier ("Sword"), 0, 0, HealthType.Lethal, 0, 0, Light, Lethal, Artifact);
		assertEquals ("Sword (Melee): Light, Lethal, Artifact", equipmentStringBuilder.createString (null, weapon));
	}
	
	@Test
	public void testNaturalArmour () throws Exception
	{
		ArmourStats stats = new ArmourStats ();
		stats.setName (new SimpleIdentifier ("Bracers"));
		stats.addTag (ArmourTag.Heavy);
		String result = equipmentStringBuilder.createString (null, stats);
		assertEquals ("Bracers: Heavy", result);
	}
}
