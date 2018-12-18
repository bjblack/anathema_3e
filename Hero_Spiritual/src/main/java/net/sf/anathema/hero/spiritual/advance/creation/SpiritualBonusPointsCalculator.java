package net.sf.anathema.hero.spiritual.advance.creation;

import net.sf.anathema.hero.spiritual.model.traits.SpiritualTraitModel;
import net.sf.anathema.hero.spiritual.model.traits.TraitCollectionUtilities;
import net.sf.anathema.hero.traits.model.Trait;
import net.sf.anathema.points.model.BonusPointCalculator;

import static net.sf.anathema.hero.traits.model.types.CommonTraitTypes.Willpower;

public class SpiritualBonusPointsCalculator implements BonusPointCalculator
{
	private final Trait willpower;
	private int willpowerBonusPoints;
	private SpiritualCreationData creationData;
	
	public SpiritualBonusPointsCalculator (SpiritualTraitModel spiritualTraits, SpiritualCreationData creationData)
	{
		this.creationData = creationData;
		this.willpower = TraitCollectionUtilities.getWillpower (spiritualTraits);
	}
	
	@Override
	public void recalculate ()
	{
		willpowerBonusPoints = calculateWillpowerPoints ();
	}
	
	@Override
	public int getBonusPointCost ()
	{
		return willpowerBonusPoints;
	}
	
	@Override
	public int getBonusPointsGranted ()
	{
		return 0;
	}
	
	private int calculateWillpowerPoints ()
	{
		int calculationBase = creationData.getCalculationBase (Willpower);
		return (willpower.getCreationValue () - calculationBase) * creationData.getWillpowerCost ();
	}
}
