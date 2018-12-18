package net.sf.anathema.hero.sheet.pdf.encoder.table;

import net.sf.anathema.hero.sheet.pdf.page.IVoidStateFormatConstants;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class TableEncodingUtilities
{
	public static TableColumns createStandardColumnWeights (int columnCount)
	{
		return createStandardColumnWidths (columnCount, 1);
	}
	
	public static TableColumns createStandardColumnWidths (int columnCount, float value)
	{
		TableColumns columns = new TableColumns ();
		for (int index = 0; index < columnCount; index++)
		{
			columns.add (value);
		}
		return columns;
	}
	
	public static PdfPCell createContentCellTable (BaseColor borderColor, String text, Font font, float borderWidth, int border, int alignment)
	{
		return createContentCellTable (borderColor, text, font, borderWidth, border, alignment, true);
	}
	
	public static PdfPCell createContentCellTable (BaseColor borderColor, String text, Font font, float borderWidth, int border, int alignment,
	boolean enabled)
	{
		PdfPCell innerCell = new PdfPCell (new Phrase (text, font));
		innerCell.setBorderColor (borderColor);
		innerCell.setBorderWidth (borderWidth);
		innerCell.setBorder (border);
		innerCell.setHorizontalAlignment (alignment);
		innerCell.setPaddingTop (0.5f);
		if (border != Rectangle.BOX)
		{
			innerCell.setPaddingLeft (0);
			innerCell.setPaddingRight (0);
		}
		if (!enabled)
		{
			innerCell.setBackgroundColor (BaseColor.LIGHT_GRAY);
		}
		
		PdfPTable cellTable = new PdfPTable (1);
		cellTable.setWidthPercentage (100);
		cellTable.addCell (innerCell);
		
		PdfPCell outerCell = new PdfPCell ();
		outerCell.addElement (cellTable);
		outerCell.setBorder (Rectangle.NO_BORDER);
		outerCell.setPadding (1);
		return outerCell;
	}
	
	public static Font createHeaderFont (BaseFont baseFont)
	{
		return createCommentFont (baseFont);
	}
	
	public static Font createCommentFont (BaseFont baseFont)
	{
		return new Font (baseFont, IVoidStateFormatConstants.COMMENT_FONT_SIZE, Font.NORMAL, BaseColor.BLACK);
	}
}
