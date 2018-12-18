package net.sf.anathema.platform.tree.display;

import net.sf.anathema.library.number.Coordinate;
import net.sf.anathema.library.presenter.RGBColor;
import net.sf.anathema.library.tooltip.StatefulTooltip;
import net.sf.anathema.platform.tree.display.transform.AgnosticTransform;
import net.sf.anathema.platform.tree.view.MouseBorderClosure;
import net.sf.anathema.platform.tree.view.draw.GraphicsElement;
import net.sf.anathema.platform.tree.view.draw.InteractiveGraphicsElement;
import net.sf.anathema.platform.tree.view.interaction.Executor;
import net.sf.anathema.platform.tree.view.interaction.MouseClickClosure;
import net.sf.anathema.platform.tree.view.interaction.MouseMotionClosure;
import net.sf.anathema.platform.tree.view.interaction.MousePressClosure;
import net.sf.anathema.platform.tree.view.interaction.MouseWheelClosure;
import net.sf.anathema.platform.tree.view.interaction.SpecialControlTrigger;

public interface DisplayPolygonPanel
{
	void refresh ();
	
	SpecialControlTrigger addSpecialControl ();
	
	void add (InteractiveGraphicsElement element);
	
	void add (GraphicsElement element);
	
	void changeCursor (Coordinate screenCoordinates);
	
	void clear ();
	
	Executor onElementAtPoint (Coordinate screenCoordinates);
	
	void addMousePressListener (MousePressClosure listener);
	
	void addMouseClickListener (MouseClickClosure listener);
	
	void addMouseWheelListener (MouseWheelClosure listener);
	
	void addMouseBorderListener (MouseBorderClosure listener);
	
	void addMouseMotionListener (MouseMotionClosure listener);
	
	void setBackground (RGBColor color);
	
	void showMoveCursor ();
	
	void resetAllTooltips ();
	
	void setTransformation (AgnosticTransform transform);
	
	int getWidth ();
	
	int getHeight ();
	
	void setToolTipText (String toolTip);
	
	StatefulTooltip createConfigurableTooltip ();
}
