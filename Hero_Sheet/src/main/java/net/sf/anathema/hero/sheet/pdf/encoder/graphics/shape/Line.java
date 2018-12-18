package net.sf.anathema.hero.sheet.pdf.encoder.graphics.shape;

import net.sf.anathema.hero.sheet.pdf.encoder.general.Position;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfContentByte;

public class Line
{
	private PdfContentByte directContent;
	private final Position startPoint;
	private final Position endPoint;
	private float lineWidth = 0f;
	
	public Line (PdfContentByte directContent, Position startPoint, Position endPoint)
	{
		this.directContent = directContent;
		this.startPoint = startPoint;
		this.endPoint = endPoint;
	}
	
	public void setLineWidth (float width)
	{
		this.lineWidth = width;
	}
	
	public void encode ()
	{
		encode (BaseColor.BLACK);
	}
	
	public void encode (BaseColor color)
	{
		directContent.setColorFill (color);
		directContent.setLineWidth (lineWidth);
		directContent.moveTo (startPoint.x, startPoint.y);
		directContent.lineTo (endPoint.x, endPoint.y);
		directContent.stroke ();
	}
}
