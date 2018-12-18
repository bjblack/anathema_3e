package net.sf.anathema.hero.sheet.pdf.encoder.graphics.shape;

import net.sf.anathema.hero.sheet.pdf.encoder.general.Bounds;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfContentByte;

public class Box
{
	private final Bounds bounds;
	private PdfContentByte directContent;
	
	public Box (Bounds bounds, PdfContentByte directContent)
	{
		this.bounds = bounds;
		this.directContent = directContent;
	}
	
	public void outline ()
	{
		outline (0.8f);
	}
	
	public void outlineTotalType ()
	{
		outline (0.75f);
	}
	
	public void outline (float lineWidth)
	{
		initializeGraphics (lineWidth);
		directContent.stroke ();
	}
	
	public void fill ()
	{
		initializeGraphics (0.8f);
		directContent.fillStroke ();
	}
	
	private void initializeGraphics (float lineWidth)
	{
		directContent.setColorStroke (BaseColor.BLACK);
		directContent.setLineWidth (lineWidth);
		directContent.rectangle (bounds.x, bounds.y, bounds.width, bounds.height);
	}
}
