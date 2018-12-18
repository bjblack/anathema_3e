package net.sf.anathema.hero.spiritual.sheet.willpower.content;

import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.sheet.pdf.content.AbstractSubBoxContent;
import net.sf.anathema.hero.sheet.pdf.encoder.general.ListUtils;
import net.sf.anathema.hero.spiritual.model.traits.SpiritualTraitModelFetcher;
import net.sf.anathema.hero.traits.sheet.content.BulletList;
import net.sf.anathema.library.resources.Resources;

import java.util.List;

import static net.sf.anathema.hero.traits.model.types.CommonTraitTypes.Willpower;

public class WillpowerContent extends AbstractSubBoxContent
{
	private Hero hero;
	
	public WillpowerContent (Resources resources, Hero hero)
	{
		super (resources);
		this.hero = hero;
	}
	
	@Override
	public String getHeaderKey ()
	{
		return "Willpower";
	}
	
	public int getWillpowerValue ()
	{
		return SpiritualTraitModelFetcher.fetch (hero).getTrait (Willpower).getCurrentValue ();
	}
	
	public String getWillpowerSpendingNote ()
	{
		return getString ("Sheet.WillpowerSpendingNote");
	}
	
	public BulletList getWillpowerSpendingRules ()
	{
		return createBulletList ("Sheet.WillpowerSpendingRules");
	}
	
	public BulletList getWillpowerRegainingRules ()
	{
		return createBulletList ("Sheet.WillpowerRegainingRules");
	}
	
	private BulletList createBulletList (String resourceBase)
	{
		String header = ListUtils.getRequiredString (getResources (), resourceBase);
		List<String> items = ListUtils.getAvailableLineItems (getResources (), resourceBase);
		return new BulletList (header, items);
	}
}
