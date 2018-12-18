package net.sf.anathema.herotype.solar.sheet.curse;

import net.sf.anathema.hero.sheet.pdf.encoder.boxes.AbstractContentEncoder;
import net.sf.anathema.hero.sheet.pdf.encoder.general.Bounds;
import net.sf.anathema.hero.sheet.pdf.encoder.general.Position;
import net.sf.anathema.hero.sheet.pdf.encoder.graphics.SheetGraphics;
import net.sf.anathema.hero.sheet.pdf.session.ReportSession;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;

import static net.sf.anathema.hero.sheet.pdf.page.IVoidStateFormatConstants.REDUCED_LINE_HEIGHT;

public class VirtueFlawEncoder extends AbstractContentEncoder<VirtueFlawContent>
{
	public VirtueFlawEncoder ()
	{
		super (VirtueFlawContent.class);
	}
	
	@Override
	public void encode (SheetGraphics graphics, ReportSession reportSession, Bounds bounds) throws DocumentException
	{
		VirtueFlawContent content = createContent (reportSession);
		VirtueFlawBoxEncoder traitEncoder = new VirtueFlawBoxEncoder ();
		float traitHeight = traitEncoder.encodeHeight (graphics, bounds, content.getLimitValue ());
		float traitInterval = traitHeight + 1f;
		Bounds textBounds = new Bounds (bounds.x, bounds.y, bounds.width, bounds.height - traitInterval);
		if (!content.isNameDefined () && !content.isConditionDefined ())
		{
			encodeLines (graphics, bounds, REDUCED_LINE_HEIGHT, textBounds.getMaxY ());
		}
		if (content.isNameDefined () && content.isConditionDefined ())
		{
			encodeNameAndConditionDefined (graphics, content, textBounds);
		}
		if (content.isNameDefined () && !content.isConditionDefined ())
		{
			encodeOnlyNameDefined (graphics, bounds, content, textBounds);
		}
		if (!content.isNameDefined () && content.isConditionDefined ())
		{
			encodeOnlyConditionDefined (graphics, content, textBounds);
		}
	}
	
	private void encodeNameAndConditionDefined (SheetGraphics graphics, VirtueFlawContent content, Bounds textBounds) throws DocumentException
	{
		Phrase phrase = new Phrase ();
		phrase.add (new Chunk (content.getLimitTrigger (), createConditionFont (graphics)));
		graphics.createSimpleColumn (textBounds).withLeading (REDUCED_LINE_HEIGHT).andTextPart (phrase).encode ();
	}
	
	private void encodeOnlyNameDefined (SheetGraphics graphics, Bounds bounds, VirtueFlawContent content, Bounds textBounds) throws DocumentException
	{
		Phrase phrase = new Phrase ();
		float baseLine = graphics.createSimpleColumn (textBounds).withLeading (REDUCED_LINE_HEIGHT).andTextPart (phrase).encode ().getYLine ();
		encodeLines (graphics, bounds, REDUCED_LINE_HEIGHT, baseLine);
	}
	
	private void encodeOnlyConditionDefined (SheetGraphics graphics, VirtueFlawContent content, Bounds textBounds) throws DocumentException
	{
		Phrase phrase = new Phrase ();
		Font undefinedFont = new Font (createNameFont (graphics));
		undefinedFont.setStyle (Font.UNDERLINE);
		phrase.add (new Chunk ("                                          : ", undefinedFont));
		phrase.add (new Chunk (": ", createNameFont (graphics)));
		phrase.add (new Chunk (content.getLimitTrigger (), createConditionFont (graphics)));
		graphics.createSimpleColumn (textBounds).withLeading (REDUCED_LINE_HEIGHT).andTextPart (phrase).encode ();
	}
	
	private void encodeLines (SheetGraphics graphics, Bounds bounds, float leading, float yPosition)
	{
		yPosition -= leading;
		while (yPosition > bounds.getMinY ())
		{
			graphics.createHorizontalLineByCoordinate (new Position (bounds.x, yPosition), bounds.getMaxX ()).encode ();
			yPosition -= leading;
		}
	}
	
	private Font createNameFont (SheetGraphics graphics)
	{
		Font newFont = graphics.createTableFont ();
		newFont.setStyle (Font.BOLD);
		return newFont;
	}
	
	private Font createConditionFont (SheetGraphics graphics)
	{
		return graphics.createTableFont ();
	}
}
