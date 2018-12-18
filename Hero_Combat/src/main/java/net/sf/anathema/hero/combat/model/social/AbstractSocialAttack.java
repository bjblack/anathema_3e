package net.sf.anathema.hero.combat.model.social;

import net.sf.anathema.hero.combat.model.CharacterUtilities;
import net.sf.anathema.hero.combat.sheet.social.stats.ISocialCombatStats;
import net.sf.anathema.hero.traits.model.TraitMap;
import net.sf.anathema.hero.traits.model.TraitType;

import static net.sf.anathema.hero.traits.model.types.CommonTraitTypes.Charisma;
import static net.sf.anathema.hero.traits.model.types.CommonTraitTypes.Manipulation;

public abstract class AbstractSocialAttack implements ISocialCombatStats
{
	private final TraitMap collection;
	
	public AbstractSocialAttack (TraitMap collection)
	{
		this.collection = collection;
	}
	
	@Override
	public final int getDeceptionAttackValue ()
	{
		return CharacterUtilities.getSocialAttackValue (collection, Manipulation, getName ());
	}
	
	@Override
	public final int getHonestyAttackValue ()
	{
		return CharacterUtilities.getSocialAttackValue (collection, Charisma, getName ());
	}
	
	@Override
	public abstract TraitType getName ();
}
