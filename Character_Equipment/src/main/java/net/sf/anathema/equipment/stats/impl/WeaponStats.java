package net.sf.anathema.equipment.stats.impl;

import net.sf.anathema.equipment.stats.IWeaponStats;
import net.sf.anathema.equipment.stats.IWeaponTag;
import net.sf.anathema.equipment.stats.ItemStatsSet;
import net.sf.anathema.equipment.stats.WeaponTag;
import net.sf.anathema.equipment.stats.data.WeaponStatisticsTable;
import net.sf.anathema.hero.health.model.HealthType;
import net.sf.anathema.hero.traits.model.TraitType;
import net.sf.anathema.library.identifier.Identifier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static net.sf.anathema.equipment.stats.WeaponTag.Artifact;
import static net.sf.anathema.equipment.stats.WeaponTag.Heavy;
import static net.sf.anathema.equipment.stats.WeaponTag.Lethal;
import static net.sf.anathema.equipment.stats.WeaponTag.Light;
import static net.sf.anathema.equipment.stats.WeaponTag.Medium;
import static net.sf.anathema.equipment.stats.data.WeaponStatisticsTable.forArtifactWeapons;
import static net.sf.anathema.equipment.stats.data.WeaponStatisticsTable.forMundaneWeapons;
import static net.sf.anathema.hero.traits.model.types.CommonTraitTypes.MartialArts;
import static net.sf.anathema.hero.traits.model.types.CommonTraitTypes.Melee;
import static net.sf.anathema.hero.traits.model.types.CommonTraitTypes.Strength;

public class WeaponStats extends AbstractCombatStats implements IWeaponStats
{
	private final List<IWeaponTag> tags = new ArrayList<> ();
	
	@Override
	public int getAccuracy ()
	{
		return getWeaponStatsEntry ().getAccuracy ();
	}
	
	@Override
	public int getDamage ()
	{
		return getWeaponStatsEntry ().getDamage ();
	}
	
	@Override
	public TraitType getDamageTraitType ()
	{
		return Strength;
	}
	
	@Override
	public HealthType getDamageType ()
	{
		if (hasTag (Lethal))
		{
			return HealthType.Lethal;
		}
		else
		{
			return HealthType.Bashing;
		}
	}
	
	@Override
	public Integer getDefence ()
	{
		return getWeaponStatsEntry ().getDefense ();
	}
	
	@Override
	public Collection<Identifier> getTags ()
	{
		return new ArrayList<> (tags);
	}
	
	protected final boolean hasTag (WeaponTag tag)
	{
		return tags.contains (tag);
	}
	
	@Override
	public boolean inflictsNoDamage ()
	{
		return false;
	}
	
	public void addTag (IWeaponTag tag)
	{
		tags.add (tag);
	}
	
	public void removeTag (IWeaponTag tag)
	{
		tags.remove (tag);
	}
	
	@Override
	public boolean isRangedCombat ()
	{
		for (WeaponTag tag : WeaponTag.getRangedWeaponTypeTags ())
		{
			if (hasTag (tag))
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String getId ()
	{
		return getName ().getId ();
	}
	
	@Override
	public TraitType getTraitType ()
	{
		return isMartialArtsOnlyWeapon () ? MartialArts : Melee;
	}
	
	private boolean isMartialArtsOnlyWeapon ()
	{
		return hasTag (WeaponTag.Natural);
	}
	
	@Override
	public int getMobilityPenalty ()
	{
		return 0;
	}
	
	@Override
	public ItemStatsSet getViews ()
	{
		if (isMartialArtsOnlyWeapon ())
		{
			return ItemStatsSet.withSingleStat (this);
		}
		return ItemStatsSet.from (new WeaponStatsDecorator (this, Melee), new WeaponStatsDecorator (this, MartialArts));
	}
	
	@Override
	public int getOverwhelmingValue ()
	{
		return getWeaponStatsEntry ().getOverwhelming ();
	}
	
	private WeaponStatisticsTable.WeaponStatisticsEntry getWeaponStatsEntry ()
	{
		if (isArtifact ())
		{
			return forArtifactWeapons ().forSize (getSize ());
		}
		else
		{
			return forMundaneWeapons ().forSize (getSize ());
		}
	}
	
	private boolean isArtifact ()
	{
		return hasTag (Artifact);
	}
	
	private WeaponTag getSize ()
	{
		if (hasTag (Light))
		{
			return Light;
		}
		if (hasTag (Medium))
		{
			return Medium;
		}
		return Heavy;
	}
}
