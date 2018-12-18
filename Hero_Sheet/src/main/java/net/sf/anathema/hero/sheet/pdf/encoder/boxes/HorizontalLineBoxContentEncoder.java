package net.sf.anathema.hero.sheet.pdf.encoder.boxes;

import net.sf.anathema.hero.sheet.pdf.encoder.general.Bounds;
import net.sf.anathema.hero.sheet.pdf.encoder.graphics.SheetGraphics;
import net.sf.anathema.hero.sheet.pdf.page.IVoidStateFormatConstants;
import net.sf.anathema.hero.sheet.pdf.session.ReportSession;
import net.sf.anathema.library.resources.Resources;

import com.itextpdf.text.DocumentException;

public class HorizontalLineBoxContentEncoder implements ContentEncoder
{
	private static final float LINE_HEIGHT = IVoidStateFormatConstants.LINE_HEIGHT - 2f;
	private final int columnCount;
	private Resources resources;
	private final String headerKey;
	
	public HorizontalLineBoxContentEncoder (int columnCount, Resources resources, String headerKey)
	{
		this.columnCount = columnCount;
		this.resources = resources;
		this.headerKey = headerKey;
	}
	
	@Override
	public String getHeader (ReportSession session)
	{
		return resources.getString ("Sheet.Header." + headerKey);
	}
	
	@Override
	public void encode (SheetGraphics graphics, ReportSession reportSession, Bounds bounds) throws DocumentException
	{
		float columnWidth = (bounds.width - (columnCount - 1) * IVoidStateFormatConstants.TEXT_PADDING) / columnCount;
		for (int columnIndex = 0; columnIndex < columnCount; columnIndex++)
		{
			float columnX = bounds.x + columnIndex * columnWidth + columnIndex * IVoidStateFormatConstants.TEXT_PADDING;
			Bounds columnBounds = new Bounds (columnX, bounds.y + IVoidStateFormatConstants.TEXT_PADDING / 2f, columnWidth, bounds.height - IVoidStateFormatConstants.TEXT_PADDING / 2f);
			new HorizontalLineEncoder ().encodeLines (graphics, columnBounds, LINE_HEIGHT);
		}
	}
	
	@Override
	public boolean hasContent (ReportSession session)
	{
		return true;
	}
}
