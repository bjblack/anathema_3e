package net.sf.anathema.cards;

import net.sf.anathema.cards.data.ICardData;
import net.sf.anathema.cards.data.providers.ICardDataProvider;
import net.sf.anathema.cards.layout.ICardLayout;
import net.sf.anathema.framework.reporting.pdf.AbstractPdfReport;
import net.sf.anathema.hero.environment.report.ReportException;
import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.charms.sheet.magicreport.CharmFetcher;
import net.sf.anathema.library.resources.Resources;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import java.util.ArrayList;
import java.util.List;

public class CardReport extends AbstractPdfReport
{
	private Resources resources;
	private ICardLayout layout;
	private List<ICardDataProvider> cardDataProviders;
	
	public CardReport (Resources resources, ICardLayout layout, List<ICardDataProvider> cardProviders)
	{
		this.resources = resources;
		this.cardDataProviders = cardProviders;
		this.layout = layout;
	}
	
	@Override
	public String toString ()
	{
		return resources.getString ("CardsReport.Name");
	}
	
	@Override
	public void performPrint (Hero hero, Document document, PdfWriter writer) throws ReportException
	{
		try
		{
			PdfContentByte directContent = writer.getDirectContent ();
			document.setMargins (20, 20, document.topMargin (), document.bottomMargin ());
			
			// For now, only one style of report, that includes
			// all spells and charms
			List<ICardData> cardDataSet = new ArrayList<> ();
			for (ICardDataProvider provider : cardDataProviders)
			{
				cardDataSet.addAll (provider.getCards (hero, layout.getResourceProvider ()));
			}
			
			float documentWidth = document.right () - document.left ();
			float documentHeight = document.top () - document.bottom ();
			int numCols = (int) (documentWidth / layout.getCardWidth ());
			int numRows = (int) (documentHeight / layout.getCardHeight ());
			float horizontalGutter = (documentWidth - numCols * layout.getCardWidth ()) / (numCols - 1);
			float verticalGutter = (documentHeight - numRows * layout.getCardHeight ()) / (numRows - 1);
			int maxPosition = numRows * numCols;
			int position = 0;
			for (ICardData cardData : cardDataSet)
			{
				if (position == maxPosition || (cardData.wantsNewPage () && position > 0))
				{
					position = 0;
					document.newPage ();
				}
				
				float upperleftX = document.left () + (layout.getCardWidth () + horizontalGutter) * (position % numCols);
				float upperleftY = document.top () - (layout.getCardHeight () + verticalGutter) * (position / numCols);
				
				Card card = new Card (directContent, upperleftX, upperleftY, cardData);
				layout.drawCard (card);
				
				position++;
			}
		}
		catch (Exception e)
		{
			throw new ReportException (e);
		}
	}
	
	@Override
	public boolean supports (Hero hero)
	{
		return new CharmFetcher ().hasCharms (hero);
	}
}
