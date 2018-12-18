package net.sf.anathema.hero.magic.sheet.content.stats;

import net.sf.anathema.hero.magic.display.tooltip.CostStringBuilder;
import net.sf.anathema.hero.magic.display.tooltip.HealthCostStringBuilder;
import net.sf.anathema.hero.magic.display.tooltip.MagicInfoStringBuilder;
import net.sf.anathema.hero.magic.sheet.content.IMagicStats;
import net.sf.anathema.library.identifier.Identifier;
import net.sf.anathema.library.identifier.SimpleIdentifier;
import net.sf.anathema.library.resources.Resources;
import net.sf.anathema.magic.data.Magic;

public abstract class AbstractMagicStats<T extends Magic> implements IMagicStats
{
	public static MagicInfoStringBuilder createMagicInfoStringBuilder (Resources resources)
	{
		CostStringBuilder essenceBuilder = new CostStringBuilder (resources, "CharacterSheet.Charm.Mote");
		CostStringBuilder sorcerousMoteBuilder = new CostStringBuilder (resources, "CharacterSheet.Charm.SorcerousMote");
		CostStringBuilder willpowerBuilder = new CostStringBuilder (resources, "CharacterSheet.Charm.Willpower");
		HealthCostStringBuilder healthBuilder = new HealthCostStringBuilder (resources, "CharacterSheet.Charm.HealthLevel");
		CostStringBuilder experienceBuilder = new CostStringBuilder (resources, "CharacterSheet.Charm.ExperiencePoints");
		return new MagicInfoStringBuilder (resources, essenceBuilder, sorcerousMoteBuilder, willpowerBuilder, healthBuilder, experienceBuilder);
	}
	
	private final T magic;
	
	public AbstractMagicStats (T magic)
	{
		this.magic = magic;
	}
	
	@Override
	public Identifier getName ()
	{
		return new SimpleIdentifier (magic.getName ().text);
	}
	
	@Override
	public String getCostString (Resources resources)
	{
		MagicInfoStringBuilder infoBuilder = createMagicInfoStringBuilder (resources);
		return infoBuilder.createCostString (magic);
	}
	
	protected final T getMagic ()
	{
		return magic;
	}
}
