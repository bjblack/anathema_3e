package net.sf.anathema.character.equipment.dummy;

import net.sf.anathema.equipment.stats.IWeaponStats;
import net.sf.anathema.equipment.stats.ItemStatsSet;
import net.sf.anathema.equipment.stats.WeaponTag;
import net.sf.anathema.equipment.stats.impl.AbstractCombatStats;
import net.sf.anathema.hero.health.model.HealthType;
import net.sf.anathema.hero.traits.model.TraitType;
import net.sf.anathema.library.identifier.Identifier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static net.sf.anathema.hero.traits.model.types.CommonTraitTypes.Melee;
import static net.sf.anathema.hero.traits.model.types.CommonTraitTypes.Strength;

public class DemoMeleeWeapon extends AbstractCombatStats implements IWeaponStats
{
	private final int accuracy;
	private final int damage;
	private final HealthType healthType;
	private final Integer defense;
	private final int mobility;
	private final Collection<Identifier> tags = new ArrayList<> ();
	private Identifier name;
	
	public DemoMeleeWeapon (Identifier name, int accuracy, int damage, HealthType healthType, int defense, int mobility,
	WeaponTag... tags)
	{
		this.name = name;
		this.accuracy = accuracy;
		this.damage = damage;
		this.healthType = healthType;
		this.defense = defense;
		this.mobility = mobility;
		Collections.addAll (this.tags, tags);
	}
	
	@Override
	public int getAccuracy ()
	{
		return accuracy;
	}
	
	@Override
	public int getDamage ()
	{
		return damage;
	}
	
	@Override
	public HealthType getDamageType ()
	{
		return healthType;
	}
	
	@Override
	public Integer getDefence ()
	{
		return defense;
	}
	
	@Override
	public int getMobilityPenalty ()
	{
		return mobility;
	}
	
	@Override
	public Collection<Identifier> getTags ()
	{
		return tags;
	}
	
	@Override
	public TraitType getTraitType ()
	{
		return Melee;
	}
	
	@Override
	public TraitType getDamageTraitType ()
	{
		return Strength;
	}
	
	@Override
	public boolean inflictsNoDamage ()
	{
		return false;
	}
	
	@Override
	public Identifier getName ()
	{
		return name;
	}
	
	@Override
	public boolean isRangedCombat ()
	{
		return false;
	}
	
	@Override
	public ItemStatsSet getViews ()
	{
		return ItemStatsSet.withSingleStat (this);
	}
	
	@Override
	public int getOverwhelmingValue ()
	{
		return 1;
	}
	
	@Override
	public String getId ()
	{
		return getName ().getId ();
	}
}
