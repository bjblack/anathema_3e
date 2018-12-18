package net.sf.anathema.platform.tree.view.interaction;

import net.sf.anathema.library.number.Coordinate;
import net.sf.anathema.library.presenter.RGBColor;
import net.sf.anathema.library.tooltip.StatefulTooltip;
import net.sf.anathema.platform.tree.view.draw.GraphicsElement;
import net.sf.anathema.platform.tree.view.draw.InteractiveGraphicsElement;

public interface PolygonPanel
{
	double RECOMMENDED_DEFAULT_SCALE = 0.75d;
	
	void refresh ();
	
	SpecialControlTrigger addSpecialControl ();
	
	void add (InteractiveGraphicsElement element);
	
	void add (GraphicsElement element);
	
	void scale (double scale);
	
	void scaleToPoint (double scale, Coordinate coordinate);
	
	void translate (int x, int y);
	
	void translateRelativeToScale (int x, int y);
	
	void resetTransformation ();
	
	void changeCursor (Coordinate screenCoordinates);
	
	void clear ();
	
	Executor onElementAtPoint (Coordinate screenCoordinates);
	
	void centerOn (Coordinate coordinate);
	
	void addMousePressListener (MousePressClosure listener);
	
	void addMouseClickListener (MouseClickClosure listener);
	
	void addMouseMotionListener (MouseMotionClosure listener);
	
	void addMouseWheelListener (MouseWheelClosure listener);
	
	void setToolTipText (String toolTip);
	
	void setBackground (RGBColor color);
	
	void showMoveCursor ();
	
	StatefulTooltip createToolTip ();
}
