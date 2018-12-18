package net.sf.anathema.hero.concept.sheet.anima.encoder;

import net.sf.anathema.hero.concept.model.concept.HeroConceptFetcher;
import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.individual.splat.HeroType;
import net.sf.anathema.hero.sheet.pdf.encoder.boxes.ContentEncoder;
import net.sf.anathema.hero.sheet.pdf.encoder.boxes.HorizontalLineEncoder;
import net.sf.anathema.hero.sheet.pdf.encoder.general.Bounds;
import net.sf.anathema.hero.sheet.pdf.encoder.general.ListUtils;
import net.sf.anathema.hero.sheet.pdf.encoder.general.Position;
import net.sf.anathema.hero.sheet.pdf.encoder.graphics.SheetGraphics;
import net.sf.anathema.hero.sheet.pdf.encoder.table.ITableEncoder;
import net.sf.anathema.hero.sheet.pdf.page.IVoidStateFormatConstants;
import net.sf.anathema.hero.sheet.pdf.session.ReportSession;
import net.sf.anathema.library.resources.Resources;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;

public class GenericAnimaEncoder implements ContentEncoder
{
	private final float fontSize;
	private final float lineHeight;
	private final Resources resources;
	private final ITableEncoder tableEncoder;
	
	public GenericAnimaEncoder (Resources resources, float fontSize, ITableEncoder encoder)
	{
		this.resources = resources;
		this.fontSize = fontSize;
		this.lineHeight = fontSize * 1.5f;
		this.tableEncoder = encoder;
	}
	
	@SuppressWarnings ("unchecked")
	@Override
	public void encode (SheetGraphics graphics, ReportSession reportSession, Bounds bounds) throws DocumentException
	{
		float powerHeight = bounds.getHeight () - AnimaTableEncoder.TABLE_HEIGHT - IVoidStateFormatConstants.TEXT_PADDING / 2f;
		Bounds animaPowerBounds = new Bounds (bounds.getMinX (), bounds.getMaxY () - powerHeight, bounds.getWidth (), powerHeight);
		encodeAnimaPowers (graphics, reportSession.getHero (), animaPowerBounds);
		Bounds animaTableBounds = new Bounds (bounds.getMinX (), bounds.getMinY (), bounds.getWidth (), AnimaTableEncoder.TABLE_HEIGHT);
		tableEncoder.encodeTable (graphics, reportSession, animaTableBounds);
	}
	
	private void encodeAnimaPowers (SheetGraphics graphics, Hero hero, Bounds bounds) throws DocumentException
	{
		Phrase phrase = new Phrase ("", graphics.createFont (fontSize));
		// Add standard powers for character type
		Chunk symbolChunk = graphics.createSymbolChunk ();
		HeroType heroType = hero.getSplat ().getTemplateType ().getHeroType ();
		ListUtils.addBulletedListText (resources, symbolChunk, "Sheet.AnimaPower." + heroType.getId (), phrase, false);
		String casteResourceKey = "Sheet.AnimaPower." + HeroConceptFetcher.fetch (hero).getCaste ().getType ().getId () + ".SecondEdition";
		if (resources.supportsKey (casteResourceKey))
		{
			phrase.add (symbolChunk);
			phrase.add (resources.getString (casteResourceKey) + "\n");
		}
		phrase.add (symbolChunk);
		float yPosition = graphics.createSimpleColumn (bounds).withLeading (lineHeight).andTextPart (phrase).encode ().getYLine ();
		Position lineStartPosition = new Position ( (bounds.getMinX () + graphics.getCaretSymbolWidth ()), yPosition);
		int lineCount = 1 + (int) ( (yPosition - bounds.getMinY ()) / lineHeight);
		new HorizontalLineEncoder ().encodeLines (graphics, lineStartPosition, bounds.getMinX (), bounds.getMaxX (), lineHeight, lineCount);
	}
	
	@Override
	public boolean hasContent (ReportSession session)
	{
		return true;
	}
	
	@Override
	public String getHeader (ReportSession session)
	{
		return resources.getString ("Sheet.Header.Anima");
	}
}
