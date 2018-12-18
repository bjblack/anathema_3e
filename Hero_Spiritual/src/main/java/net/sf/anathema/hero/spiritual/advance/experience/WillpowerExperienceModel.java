package net.sf.anathema.hero.spiritual.advance.experience;

import net.sf.anathema.hero.spiritual.model.traits.SpiritualTraitModel;
import net.sf.anathema.points.display.overview.model.AbstractIntegerValueModel;

import static net.sf.anathema.hero.traits.model.types.CommonTraitTypes.Willpower;

public class WillpowerExperienceModel extends AbstractIntegerValueModel
{
	private final SpiritualTraitModel spiritualTraits;
	private final SpiritualExperienceCalculator calculator;
	
	public WillpowerExperienceModel (SpiritualTraitModel spiritualTraits, SpiritualExperienceCalculator calculator)
	{
		super ("Experience", "Willpower");
		this.spiritualTraits = spiritualTraits;
		this.calculator = calculator;
	}
	
	@Override
	public Integer getValue ()
	{
		return getWillpowerCosts ();
	}
	
	private int getWillpowerCosts ()
	{
		return calculator.getWillpowerCosts (spiritualTraits.getTrait (Willpower));
	}
}
