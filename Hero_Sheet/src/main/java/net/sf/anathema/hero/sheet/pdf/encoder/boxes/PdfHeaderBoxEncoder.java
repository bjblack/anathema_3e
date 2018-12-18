package net.sf.anathema.hero.sheet.pdf.encoder.boxes;

import net.sf.anathema.hero.sheet.pdf.encoder.general.Bounds;
import net.sf.anathema.hero.sheet.pdf.encoder.graphics.SheetGraphics;
import net.sf.anathema.hero.sheet.pdf.page.IVoidStateFormatConstants;

import com.itextpdf.text.pdf.PdfContentByte;

import static net.sf.anathema.hero.sheet.pdf.encoder.boxes.BoundsEncoder.ARC_SPACE;
import static net.sf.anathema.hero.sheet.pdf.encoder.boxes.BoundsEncoder.HEADER_HEIGHT;

public class PdfHeaderBoxEncoder
{
	private static final int HEADER_FONT_PADDING = 3;
	private static final int HEADER_FONT_SIZE = IVoidStateFormatConstants.HEADER_FONT_SIZE;
	
	public void encodeHeaderBox (SheetGraphics graphics, Bounds bounds, String title)
	{
		PdfContentByte directContent = graphics.getDirectContent ();
		graphics.setFillColorBlack ();
		Bounds headerBounds = calculateHeaderBounds (bounds);
		directContent.rectangle (headerBounds.x + ARC_SPACE, headerBounds.y, headerBounds.width - 2 * ARC_SPACE, headerBounds.height);
		directContent.arc (headerBounds.x, headerBounds.y, headerBounds.x + 2 * ARC_SPACE, headerBounds.y + headerBounds.height, 0, 360);
		directContent.arc (headerBounds.getMaxX (), headerBounds.y, headerBounds.getMaxX () - 2 * ARC_SPACE, headerBounds.getMaxY (), 0, 360);
		directContent.fillStroke ();
		setFillColorWhite (directContent);
		directContent.setFontAndSize (graphics.getBaseFont (), HEADER_FONT_SIZE);
		directContent.beginText ();
		directContent.showTextAligned (PdfContentByte.ALIGN_CENTER, title, (int) headerBounds.getCenterX (), headerBounds.y + HEADER_FONT_PADDING, 0);
		directContent.endText ();
	}
	
	private Bounds calculateHeaderBounds (Bounds bounds)
	{
		return new Bounds (bounds.x, bounds.y + bounds.height - HEADER_HEIGHT, bounds.width, HEADER_HEIGHT);
	}
	
	private void setFillColorWhite (PdfContentByte directContent)
	{
		directContent.setRGBColorFill (255, 255, 255);
	}
}
