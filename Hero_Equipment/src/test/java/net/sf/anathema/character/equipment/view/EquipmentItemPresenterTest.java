package net.sf.anathema.character.equipment.view;

import net.sf.anathema.character.equipment.dummy.DemoMeleeWeapon;
import net.sf.anathema.character.equipment.dummy.DummyEquipmentItem;
import net.sf.anathema.equipment.character.IEquipmentItem;
import net.sf.anathema.equipment.presentation.IEquipmentStringBuilder;
import net.sf.anathema.hero.equipment.EquipmentHeroEvaluator;
import net.sf.anathema.hero.equipment.EquipmentObjectPresenter;
import net.sf.anathema.hero.equipment.EquipmentObjectView;
import net.sf.anathema.hero.equipment.EquipmentOptionsProvider;
import net.sf.anathema.hero.equipment.display.presenter.StatsView;
import net.sf.anathema.hero.health.model.HealthType;
import net.sf.anathema.hero.traits.model.TraitType;
import net.sf.anathema.library.identifier.SimpleIdentifier;
import net.sf.anathema.platform.resources.LocaleResources;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EquipmentItemPresenterTest
{
	private IEquipmentStringBuilder equipmentStringBuilder = (item, equipment) ->
	{
		if (equipment.getName ().getId ().equals ("Sword"))
		{
			return "Passt!";
		}
		throw new IllegalArgumentException ();
	}
	;
	
	@Test
	public void testNameOnlyEquipment () throws Exception
	{
		IEquipmentItem model = new DummyEquipmentItem ("First and Forsaken Weapon", null);
		EquipmentObjectView view = mock (EquipmentObjectView.class);
		initPresentation (model, view);
		verify (view).setItemTitle ("First and Forsaken Weapon");
	}
	
	@Test
	public void testEquipmentWithoutStats () throws Exception
	{
		EquipmentObjectView view = mock (EquipmentObjectView.class);
		DummyEquipmentItem model = new DummyEquipmentItem ("First and Forsaken Weapon", "Abyssal-Weapon mit Bums");
		initPresentation (model, view);
		verify (view).setItemTitle ("First and Forsaken Weapon");
		verify (view).setItemDescription ("Abyssal-Weapon mit Bums");
	}
	
	@Test
	public void testEquipmentWithCloseCombatStats () throws Exception
	{
		EquipmentObjectView view = mock (EquipmentObjectView.class);
		view.setItemTitle ("Title");
		StatsView isPrintSelectedModel = mock (StatsView.class);
		when (view.addStats ("Passt!")).thenReturn (isPrintSelectedModel);
		DummyEquipmentItem model = new DummyEquipmentItem ("Title", null);
		model.addEquipment (new DemoMeleeWeapon (new SimpleIdentifier ("Sword"), 2, 7, HealthType.Lethal, -1, 0));
		initPresentation (model, view);
	}
	
	@Test
	public void testPrintModelInitialization () throws Exception
	{
		EquipmentObjectView view = mock (EquipmentObjectView.class);
		view.setItemTitle ("Title");
		StatsView isPrintSelectedModel = mock (StatsView.class);
		when (view.addStats ("Passt!")).thenReturn (isPrintSelectedModel);
		DummyEquipmentItem model = new DummyEquipmentItem ("Title", null);
		model.addEquipment (new DemoMeleeWeapon (new SimpleIdentifier ("Sword"), 2, 7, HealthType.Lethal, -1, 0));
		initPresentation (model, view);
		Assert.assertFalse (isPrintSelectedModel.getSelected ());
	}
	
	private void initPresentation (IEquipmentItem model, EquipmentObjectView view)
	{
		EquipmentHeroEvaluator dataProvider = mock (EquipmentHeroEvaluator.class);
		EquipmentOptionsProvider optionProvider = mock (EquipmentOptionsProvider.class);
		when (dataProvider.getSpecialties (isA (TraitType.class))).thenReturn (Collections.emptyList ());
		new EquipmentObjectPresenter (equipmentStringBuilder, dataProvider, optionProvider, new LocaleResources ()).initPresentation (model, view);
	}
}
