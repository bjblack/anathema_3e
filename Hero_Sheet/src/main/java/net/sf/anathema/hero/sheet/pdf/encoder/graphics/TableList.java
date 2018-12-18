package net.sf.anathema.hero.sheet.pdf.encoder.graphics;

import net.sf.anathema.hero.sheet.pdf.encoder.general.CellPadding;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class TableList
{
	private final Font font;
	private int index = 1;
	private final PdfPTable table;
	private final CellPadding padding;
	
	public TableList (Font font)
	{
		this (font, new CellPadding (0, 0, 1, 1));
	}
	
	public TableList (Font font, CellPadding padding)
	{
		this.padding = padding;
		this.table = new PdfPTable (new float[]
		{
			1, 9
		}
		);
		table.setWidthPercentage (100);
		this.font = font;
	}
	
	public void setIndex (int index)
	{
		this.index = index;
	}
	
	public void addHeader (Chunk chunk, boolean paddingTop)
	{
		addHeader (new Phrase (chunk), paddingTop);
	}
	
	public void addHeader (Phrase phrase, boolean paddingTop)
	{
		TableCell cell = new TableCell (phrase, Rectangle.NO_BORDER);
		cell.setPaddingLeft (1);
		cell.setPaddingRight (1);
		cell.setPaddingBottom (0);
		if (!paddingTop)
		{
			cell.setPaddingTop (0);
		}
		cell.setHorizontalAlignment (Element.ALIGN_LEFT);
		cell.setColspan (2);
		table.addCell (cell);
	}
	
	public void addItem (String text)
	{
		table.addCell (createCell (index++ + ".", Element.ALIGN_RIGHT));
		table.addCell (createCell (text, Element.ALIGN_LEFT));
	}
	
	public void addSubItem (String text)
	{
		table.addCell (createCell ("", Element.ALIGN_RIGHT));
		table.addCell (createCell (" " + text, Element.ALIGN_LEFT));
	}
	
	private PdfPCell createCell (String content, int horizontalAlignment)
	{
		TableCell cell = new TableCell (new Phrase (content, font), Rectangle.NO_BORDER);
		return configureCell (cell, horizontalAlignment);
	}
	
	private PdfPCell configureCell (PdfPCell cell, int horizontalAlignment)
	{
		cell.setPaddingTop (padding.getTopPadding ());
		cell.setPaddingBottom (padding.getBottomPadding ());
		cell.setPaddingLeft (1);
		cell.setPaddingRight (1);
		cell.setHorizontalAlignment (horizontalAlignment);
		return cell;
	}
	
	public PdfPTable getTable ()
	{
		return table;
	}
	
	public void addCell (TableCell cell)
	{
		table.addCell (cell);
	}
}
