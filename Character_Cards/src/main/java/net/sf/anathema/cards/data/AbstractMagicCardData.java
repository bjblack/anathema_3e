package net.sf.anathema.cards.data;

import net.sf.anathema.cards.layout.ICardReportResourceProvider;
import net.sf.anathema.magic.data.cost.CostImpl;
import net.sf.anathema.magic.data.cost.HealthCostImpl;
import net.sf.anathema.hero.magic.display.MagicDisplayLabeler;
import net.sf.anathema.hero.magic.display.tooltip.ScreenDisplayInfoContributor;
import net.sf.anathema.hero.magic.display.tooltip.source.MagicSourceContributor;
import net.sf.anathema.library.resources.Resources;
import net.sf.anathema.magic.data.Magic;
import net.sf.anathema.magic.description.model.MagicDescription;

import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;

public abstract class AbstractMagicCardData implements ICardData
{
	private Magic magic;
	private Resources resources;
	private MagicDescription description;
	private ICardReportResourceProvider resourceProvider;
	
	public AbstractMagicCardData (Magic magic, MagicDescription description, ICardReportResourceProvider resourceProvider, Resources resources)
	{
		this.magic = magic;
		this.resources = resources;
		this.resourceProvider = resourceProvider;
		this.description = description;
	}
	
	protected Resources getResources ()
	{
		return resources;
	}
	
	protected ICardReportResourceProvider getResourceProvider ()
	{
		return resourceProvider;
	}
	
	@Override
	public String getTitle ()
	{
		return new MagicDisplayLabeler (getResources ()).getLabelForMagic (magic);
	}
	
	@Override
	public String getSource ()
	{
		String source = new MagicSourceContributor<> (resources).createSourceString (magic);
		source = source.replaceAll (resources.getString ("CardsReport.MoEP.Long"), resources.getString ("CardsReport.MoEP.Short"));
		return source;
	}
	
	protected Phrase getCostPhrase (boolean semicolon)
	{
		String cost = new ScreenDisplayInfoContributor (resources).createCostString (magic);
		return new Phrase (cost + (semicolon ? ": " : ""), resourceProvider.getBoldFont ());
	}
	
	protected boolean hasDescription ()
	{
		return !description.isEmpty ();
	}
	
	protected void addDescriptionPhrases (Paragraph phrases)
	{
		for (String string : description.getParagraphs ())
		{
			phrases.add (new Paragraph (string, resourceProvider.getNormalFont ()));
		}
	}
	
	protected boolean hasCost (Magic magic)
	{
		return magic.getTemporaryCost ().getEssenceCost () != CostImpl.NULL_COST ||
		magic.getTemporaryCost ().getWillpowerCost () != CostImpl.NULL_COST ||
		magic.getTemporaryCost ().getHealthCost () != HealthCostImpl.NULL_HEALTH_COST ||
		magic.getTemporaryCost ().getXPCost () != CostImpl.NULL_COST;
	}
	
	@Override
	public boolean wantsNewPage ()
	{
		return false;
	}
}
