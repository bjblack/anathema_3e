package net.sf.anathema.hero.charms.compiler.special.learning;

import net.sf.anathema.hero.traits.model.TraitType;
import net.sf.anathema.magic.data.prerequisite.RequiredTraitType;
import net.sf.anathema.magic.data.prerequisite.TraitPrerequisite;
import net.sf.anathema.magic.data.reference.CharmName;
import net.sf.anathema.magic.template.special.SpecialCharmTemplate;
import net.sf.anathema.magic.template.special.learning.Repurchase;
import net.sf.anathema.magic.template.special.learning.RepurchaseVisitor;
import net.sf.anathema.magic.template.special.learning.Requirement;
import net.sf.anathema.magic.template.special.learning.StaticRepurchase;
import net.sf.anathema.magic.template.special.learning.Tier;
import net.sf.anathema.magic.template.special.learning.TierRepurchase;
import net.sf.anathema.magic.template.special.learning.TraitRepurchase;
import net.sf.anathema.hero.charms.compiler.special.AdditionalCharmFactory;
import net.sf.anathema.hero.charms.compiler.special.CharmSpecialLearningBuilder;
import net.sf.anathema.hero.charms.compiler.special.ExistingMechanicTemplateSupplier;
import net.sf.anathema.hero.charms.model.special.CharmSpecialLearning;
import net.sf.anathema.hero.charms.model.special.learning.multilearn.CharmTier;
import net.sf.anathema.hero.charms.model.special.learning.multilearn.TraitCharmTier;
import net.sf.anathema.hero.charms.model.special.learning.multilearn.TraitDependentMultiLearnableCharm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings ("UnusedDeclaration")
//TODO (Urs): What's up with all the unused code?
public class RepurchaseCharmBuilder implements CharmSpecialLearningBuilder
{
	@Override
	public CharmSpecialLearning readCharm (SpecialCharmTemplate overallDto,
	AdditionalCharmFactory factory,
	final ExistingMechanicTemplateSupplier supplier)
	{
		final Repurchase repurchaseDto = overallDto.repurchase;
		final CharmName charmName = new CharmName (overallDto.charmId);
		final CharmSpecialLearning[] result = new CharmSpecialLearning[1];
		final Map<String, Integer> minimums = factory.getCurrentMinimums (overallDto.charmId);
		
		repurchaseDto.visit (new RepurchaseVisitor ()
		{
			@Override
			public void visitTraitRepurchase (TraitRepurchase repurchase)
			{
				List<Tier> tiers = new ArrayList<> ();
				int startingPoint = minimums.get (repurchase.limitingTrait) + 1;
				for (int minimum = startingPoint; minimum <= AdditionalCharmFactory.TRAIT_MAX; minimum++)
				{
					Tier tier = createBaseTier (minimums);
					tier.requirements.stream ().filter (requirement -> requirement.traitType.equals (repurchase.limitingTrait)).
					findFirst ().get ().traitValue = minimum;
					tiers.add (tier);
				}
				factory.generateCharms (overallDto.charmId, tiers, supplier);
				//result[0] = createTraitMultiLearnable(charmName, repurchase);
			}
			
			@Override
			public void visitTierRepurchase (TierRepurchase repurchase)
			{
				factory.generateCharms (overallDto.charmId, repurchase.tiers, supplier);
			}
			
			@Override
			public void visitStaticRepurchase (StaticRepurchase repurchase)
			{
				List<Tier> tiers = new ArrayList<> ();
				for (int i = 0; i != repurchase.limit; i++)
				{
					tiers.add (new Tier ());
				}
				factory.generateCharms (overallDto.charmId, tiers, supplier);
			}
		}
		);
		
		return result[0];
		/*Repurchase repurchase = overallDto.repurchase;
		if (repurchase.isEssenceRepurchase) {
		return new EssenceFixedMultiLearnableCharm(new CharmName(overallDto.charmId));
		}
		CharmName charmName = new CharmName(overallDto.charmId);
		if (repurchase.traitRepurchase != null) {
		return createTraitMultiLearnable(charmName, repurchase.traitRepurchase);
		}
		if (repurchase.staticRepurchase != null) {
		return new StaticMultiLearnableCharm(charmName, repurchase.staticRepurchase.limit);
		}
		return createTierMultiLearnable(charmName, repurchase);*/
	}
	
	private Tier createBaseTier (Map<String, Integer> minimums)
	{
		Tier tier = new Tier ();
		minimums.forEach ( (trait, value) -> tier.requirements.add (new Requirement (trait, value)));
		return tier;
	}
	
	private CharmSpecialLearning createTierMultiLearnable (CharmName id, Repurchase repurchase)
	{
		/*TierRepurchase dto = repurchase.tierRepurchase;
		List<CharmTier> tiers = new ArrayList<>();
		for (Tier tier : dto.tiers) {
		tiers.add(createTier(tier));
		}
		return new TieredMultiLearnableCharm(id, tiers.toArray(new CharmTier[tiers.size()]));*/
		return null;
	}
	
	private CharmTier createTier (Tier dto)
	{
		TraitCharmTier traitCharmTier = new TraitCharmTier ();
		for (Requirement requirement : dto.requirements)
		{
			RequiredTraitType traitType = new RequiredTraitType (requirement.traitType);
			traitCharmTier.addRequirement (new TraitPrerequisite (traitType, requirement.traitValue));
		}
		return traitCharmTier;
	}
	
	private TraitDependentMultiLearnableCharm createTraitMultiLearnable (CharmName id, TraitRepurchase dto)
	{
		TraitType trait = new TraitType (dto.limitingTrait);
		int modifier = 0; //dto.modifier;
		int absoluteMax = 0; //dto.absoluteMax;
		return new TraitDependentMultiLearnableCharm (id, absoluteMax, trait, modifier);
	}
	
	@Override
	public boolean supports (SpecialCharmTemplate overallDto)
	{
		return overallDto.repurchase != null;
	}
}
