package net.sf.anathema.hero.combat.model.social;

import net.sf.anathema.hero.traits.model.TraitMap;
import net.sf.anathema.hero.traits.model.TraitType;

import static net.sf.anathema.hero.traits.model.types.CommonTraitTypes.Investigation;

public class InvestigationSocialAttack extends AbstractSocialAttack
{
	public InvestigationSocialAttack (TraitMap collection)
	{
		super (collection);
	}
	
	@Override
	public int getRate ()
	{
		return 2;
	}
	
	@Override
	public int getSpeed ()
	{
		return 5;
	}
	
	@Override
	public TraitType getName ()
	{
		return Investigation;
	}
}
