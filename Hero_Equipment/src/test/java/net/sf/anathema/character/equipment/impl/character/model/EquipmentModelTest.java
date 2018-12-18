package net.sf.anathema.character.equipment.impl.character.model;

import net.sf.anathema.character.equipment.dummy.DummyEquipmentItem;
import net.sf.anathema.equipment.stats.IEquipmentStats;
import net.sf.anathema.hero.abilities.model.AbilitiesModelImpl;
import net.sf.anathema.hero.abilities.template.AbilitiesTemplate;
import net.sf.anathema.hero.attributes.model.AttributeModel;
import net.sf.anathema.hero.attributes.model.AttributeModelImpl;
import net.sf.anathema.hero.concept.dummy.DummyHeroConcept;
import net.sf.anathema.hero.dummy.DummyHero;
import net.sf.anathema.hero.dummy.DummyHeroEnvironment;
import net.sf.anathema.hero.dummy.models.DummySpiritualTraitModel;
import net.sf.anathema.hero.equipment.EquipmentModelImpl;
import net.sf.anathema.hero.sheet.pdf.content.stats.StatsModelImpl;
import net.sf.anathema.hero.specialties.model.SpecialtiesModelImpl;
import net.sf.anathema.hero.spiritual.model.pool.EssencePoolModelImpl;
import net.sf.anathema.hero.spiritual.template.EssencePoolTemplate;
import net.sf.anathema.hero.traits.dummy.DummyTraitModel;
import net.sf.anathema.hero.traits.model.TraitModelImpl;
import net.sf.anathema.hero.traits.template.Group;
import net.sf.anathema.hero.traits.template.GroupedTraitsTemplate;
import org.junit.Before;
import org.junit.Test;

import static net.sf.anathema.hero.traits.model.types.CommonTraitTypes.Essence;
import static org.mockito.Mockito.mock;

public class EquipmentModelTest
{
	private EquipmentModelImpl model;
	
	@Before
	public void setUp () throws Exception
	{
		DummyHero hero = createCharacterWithEssence (2);
		hero.addModel (new EssencePoolModelImpl (new EssencePoolTemplate ()));
		hero.addModel (new SpecialtiesModelImpl ());
		hero.addModel (new StatsModelImpl ());
		hero.addModel (new TraitModelImpl ());
		hero.addModel (new AbilitiesModelImpl (new AbilitiesTemplate ()));
		AttributeModel attributeModel = createAttributeModelWithStamina ();
		DummyHeroEnvironment context = new DummyHeroEnvironment ();
		attributeModel.initialize (context, hero);
		model = new EquipmentModelImpl ();
		model.initialize (context, hero);
	}
	
	private DummyHero createCharacterWithEssence (int currentValue)
	{
		DummyHero hero = new DummyHero ();
		DummySpiritualTraitModel traitModel = new DummySpiritualTraitModel ();
		hero.addModel (traitModel);
		hero.addModel (new DummyHeroConcept ());
		hero.addModel (new DummyTraitModel ());
		traitModel.getTrait (Essence).setCurrentValue (currentValue);
		return hero;
	}
	
	private AttributeModelImpl createAttributeModelWithStamina ()
	{
		GroupedTraitsTemplate template = new GroupedTraitsTemplate ();
		Group physicalAttributes = new Group ();
		physicalAttributes.id = "Physical";
		physicalAttributes.traits.add ("Stamina");
		template.groups.add (physicalAttributes);
		return new AttributeModelImpl (template);
	}
	
	@Test
	public void removesStatsWithoutNpe () throws Exception
	{
		DummyEquipmentItem fromItem = new DummyEquipmentItem ("from", "");
		IEquipmentStats stats = mock (IEquipmentStats.class);
		fromItem.addEquipment (stats);
		DummyEquipmentItem toItem = new DummyEquipmentItem ("to", "");
		model.transferOptions (fromItem, toItem);
	}
}
