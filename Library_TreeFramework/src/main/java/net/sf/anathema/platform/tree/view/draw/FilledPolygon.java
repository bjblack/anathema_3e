package net.sf.anathema.platform.tree.view.draw;

import net.sf.anathema.library.number.Coordinate;
import net.sf.anathema.library.presenter.RGBColor;
import net.sf.anathema.platform.tree.display.draw.AgnosticPolygon;
import net.sf.anathema.platform.tree.display.draw.ShapeWithPosition;
import net.sf.anathema.platform.tree.display.shape.Polygon;

import org.jmock.example.announcer.Announcer;

public class FilledPolygon implements InteractiveGraphicsElement, AgnosticPolygon
{
	private final Announcer<Runnable> toggleListeners = Announcer.to (Runnable.class);
	private final Polygon polygon = new Polygon ();
	private RGBColor fill = RGBColor.Transparent;
	private RGBColor stroke = RGBColor.Black;
	private RGBColor border = RGBColor.Black;
	private final TextWriter textWriter = new TextWriter (polygon);
	
	@Override
	public void paint (Canvas graphics)
	{
		new ShapeFiller (polygon, fill).fill (graphics);
		new ShapeDrawer (polygon, border).draw (graphics);
		textWriter.write (graphics);
	}
	
	@Override
	public boolean contains (Coordinate point)
	{
		return polygon.contains (point);
	}
	
	@Override
	public void toggle ()
	{
		toggleListeners.announce ().run ();
	}
	
	public void addPoint (int x, int y)
	{
		polygon.addPoint (x, y);
	}
	
	public void moveBy (int dx, int dy)
	{
		polygon.translate (dx, dy);
	}
	
	public void fill (RGBColor fill)
	{
		this.fill = fill;
	}
	
	public void setAlpha (int alpha)
	{
		RGBColor original = fill;
		this.fill = new RGBColor (original, alpha);
		setStroke (new RGBColor (stroke, alpha));
	}
	
	
	public void whenToggledDo (Runnable runnable)
	{
		toggleListeners.addListener (runnable);
	}
	
	public void setText (String text)
	{
		textWriter.setText (text);
	}
	
	public void position (ShapeWithPosition control)
	{
		control.placeBelow (polygon);
	}
	
	public void setStroke (RGBColor stroke)
	{
		this.stroke = stroke;
		textWriter.setStroke (stroke);
	}
	
	@Override
	public void setBorderColor (RGBColor borderColor)
	{
		this.border = borderColor;
	}
}
