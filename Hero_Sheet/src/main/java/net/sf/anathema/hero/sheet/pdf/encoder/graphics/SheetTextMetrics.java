package net.sf.anathema.hero.sheet.pdf.encoder.graphics;

import com.itextpdf.text.pdf.BaseFont;

import static net.sf.anathema.hero.sheet.pdf.page.IVoidStateFormatConstants.COMMENT_FONT_SIZE;
import static net.sf.anathema.hero.sheet.pdf.page.IVoidStateFormatConstants.FONT_SIZE;
import static net.sf.anathema.hero.sheet.pdf.page.IVoidStateFormatConstants.TABLE_FONT_SIZE;

public class SheetTextMetrics implements TextMetrics
{
	private BaseFont baseFont;
	
	public SheetTextMetrics (BaseFont baseFont)
	{
		this.baseFont = baseFont;
	}
	
	@Override
	public final float getDefaultTextWidth (String text)
	{
		return baseFont.getWidthPoint (text, FONT_SIZE);
	}
	
	@Override
	public final float getCommentTextWidth (String text)
	{
		return baseFont.getWidthPoint (text, COMMENT_FONT_SIZE);
	}
	
	@Override
	public float getTableTextWidth (String text)
	{
		return baseFont.getWidthPoint (text, TABLE_FONT_SIZE);
	}
}
