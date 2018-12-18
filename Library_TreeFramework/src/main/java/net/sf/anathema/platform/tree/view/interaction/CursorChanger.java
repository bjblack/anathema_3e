package net.sf.anathema.platform.tree.view.interaction;

import net.sf.anathema.library.number.Coordinate;

public class CursorChanger implements MouseMotionClosure
{
	private final PolygonPanel polygonPanel;
	
	public CursorChanger (PolygonPanel polygonPanel)
	{
		this.polygonPanel = polygonPanel;
	}
	
	@Override
	public void mouseDragged (MouseButton button, Coordinate coordinate)
	{
		polygonPanel.showMoveCursor ();
	}
	
	@Override
	public void mouseMoved (Coordinate coordinate)
	{
		polygonPanel.changeCursor (coordinate);
	}
}
