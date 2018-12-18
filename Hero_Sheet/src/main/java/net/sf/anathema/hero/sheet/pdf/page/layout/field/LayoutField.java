package net.sf.anathema.hero.sheet.pdf.page.layout.field;

import net.sf.anathema.hero.sheet.pdf.encoder.boxes.BoxBoundsFactory;
import net.sf.anathema.hero.sheet.pdf.encoder.general.Bounds;
import net.sf.anathema.hero.sheet.pdf.encoder.graphics.GraphicsTemplate;
import net.sf.anathema.hero.sheet.pdf.encoder.graphics.SheetGraphics;
import net.sf.anathema.hero.sheet.pdf.page.layout.Body;

import static net.sf.anathema.hero.sheet.pdf.page.IVoidStateFormatConstants.PADDING;

public class LayoutField
{
	public static LayoutField CreateUpperLeftFieldWithHeightAndColumnSpan (Body body, float height, int columnSpan)
	{
		return new LayoutField (body, columnSpan, 0, height, 0);
	}
	
	private final Body body;
	private final int columnSpan;
	private final int columnIndex;
	private final float height;
	private final float fromTop;
	
	public LayoutField (Body body, int columnSpan, int columnIndex, float height, float fromTop)
	{
		this.body = body;
		this.columnSpan = columnSpan;
		this.columnIndex = columnIndex;
		this.height = height;
		this.fromTop = fromTop;
	}
	
	public Bounds createRenderBounds ()
	{
		return new Bounds (0, 0, getWidth (), height);
	}
	
	public GraphicsTemplate createRenderTemplate (SheetGraphics graphics)
	{
		return graphics.createTemplate (getWidth (), height);
	}
	
	public void addTemplateToParent (GraphicsTemplate template)
	{
		template.addToParentAt (getX (), getY ());
	}
	
	private float getX ()
	{
		return body.configuration.getLeftColumnX (columnIndex);
	}
	
	private float getY ()
	{
		return body.configuration.getY (fromTop, height);
	}
	
	private float getWidth ()
	{
		return body.configuration.getColumnWidth (columnSpan);
	}
	
	public float getFromTopBelow ()
	{
		return getBottomFromTop () + PADDING;
	}
	
	public float getBottomFromTop ()
	{
		return fromTop + height;
	}
	
	public float getAlignedHeight ()
	{
		return height;
	}
	
	public float getAlignedFromTop ()
	{
		return fromTop;
	}
	
	public int getColumnIndexBelow ()
	{
		return columnIndex;
	}
	
	public int getColumnIndexOnRight ()
	{
		return columnIndex + columnSpan;
	}
	
	public LayoutField createForFromTopAndHeightAndColumnSpanAndColumnIndex (float newFromTop, HeightStrategy heightStrategy, int newColumnSpan, int newColumnIndex)
	{
		float newWidth = BoxBoundsFactory.getContentWidth (body.configuration, newColumnSpan);
		float newHeight = heightStrategy.getHeight (newWidth);
		if (newHeight == 0)
		{
			return new LayoutField (body, newColumnSpan, newColumnIndex, newHeight, getBottomFromTop ());
		}
		return new LayoutField (body, newColumnSpan, newColumnIndex, newHeight, newFromTop);
	}
	
	public boolean isInvisible ()
	{
		return height == 0;
	}
	
	public float getRemainingColumnHeight (float fromTop)
	{
		return body.contentHeight - fromTop;
	}
}
