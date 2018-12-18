package net.sf.anathema.hero.sheet.pdf.encoder.graphics.shape;

import net.sf.anathema.hero.sheet.pdf.encoder.general.Position;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfContentByte;

public abstract class AbstractShape implements Shape
{
	protected PdfContentByte directContent;
	
	public AbstractShape (PdfContentByte directContent)
	{
		this.directContent = directContent;
	}
	
	protected PdfContentByte getDirectContent ()
	{
		return directContent;
	}
	
	protected abstract void configureShape (Position lowerLeft);
	
	@Override
	public void encodeOutlined (Position lowerLeft)
	{
		configureDirectContent ();
		configureShape (lowerLeft);
		getDirectContent ().stroke ();
	}
	
	@Override
	public void encodeFilled (Position lowerLeft)
	{
		configureDirectContent ();
		configureShape (lowerLeft);
		getDirectContent ().fillStroke ();
	}
	
	private void configureDirectContent ()
	{
		directContent.setColorFill (BaseColor.BLACK);
		directContent.setLineWidth (0.8f);
	}
}
