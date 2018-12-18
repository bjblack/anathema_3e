package net.sf.anathema.equipment.stats.impl;

import net.sf.anathema.equipment.stats.IWeaponStats;
import net.sf.anathema.equipment.stats.ItemStatsSet;
import net.sf.anathema.hero.health.model.HealthType;
import net.sf.anathema.hero.traits.model.TraitType;
import net.sf.anathema.library.identifier.Identifier;
import net.sf.anathema.library.identifier.SimpleIdentifier;

import java.util.Collection;

public class WeaponStatsDecorator extends AbstractStats implements IWeaponStats
{
	private IWeaponStats stats;
	private TraitType ability;
	private Identifier name;
	
	public WeaponStatsDecorator (IWeaponStats stats, TraitType statsAbility)
	{
		this.stats = stats;
		this.ability = statsAbility;
		this.name = stats.getName ();
	}
	
	public WeaponStatsDecorator (IWeaponStats stats, String name)
	{
		this.stats = stats;
		this.ability = stats.getTraitType ();
		this.name = new SimpleIdentifier (name);
	}
	
	@Override
	public int getAccuracy ()
	{
		return stats.getAccuracy ();
	}
	
	@Override
	public int getDamage ()
	{
		return stats.getDamage ();
	}
	
	@Override
	public TraitType getDamageTraitType ()
	{
		return stats.getDamageTraitType ();
	}
	
	@Override
	public HealthType getDamageType ()
	{
		return stats.getDamageType ();
	}
	
	@Override
	public Integer getDefence ()
	{
		return stats.getDefence ();
	}
	
	@Override
	public int getMobilityPenalty ()
	{
		return stats.getMobilityPenalty ();
	}
	
	@Override
	public Collection<Identifier> getTags ()
	{
		return stats.getTags ();
	}
	
	@Override
	public TraitType getTraitType ()
	{
		return ability;
	}
	
	@Override
	public boolean inflictsNoDamage ()
	{
		return stats.inflictsNoDamage ();
	}
	
	@Override
	public boolean isRangedCombat ()
	{
		return stats.isRangedCombat ();
	}
	
	@Override
	public ItemStatsSet getViews ()
	{
		return ItemStatsSet.withSingleStat (this);
	}
	
	@Override
	public int getOverwhelmingValue ()
	{
		return stats.getOverwhelmingValue ();
	}
	
	@Override
	public Identifier getName ()
	{
		return name;
	}
	
	@Override
	public boolean equals (Object obj)
	{
		if (! (obj instanceof WeaponStatsDecorator))
		{
			return stats.equals (obj);
		}
		WeaponStatsDecorator view = (WeaponStatsDecorator) obj;
		return view.stats.equals (stats) && view.ability.equals (ability);
	}
	
	@Override
	public int hashCode ()
	{
		return stats.hashCode () + ability.hashCode ();
	}
	
	@Override
	public String getId ()
	{
		return name.getId () + "." + ability.getId ();
	}
	
	@Override
	public boolean representsItemForUseInCombat ()
	{
		return stats.representsItemForUseInCombat ();
	}
}
