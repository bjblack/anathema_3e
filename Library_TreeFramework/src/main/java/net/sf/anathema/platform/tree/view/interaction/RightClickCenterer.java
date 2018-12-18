package net.sf.anathema.platform.tree.view.interaction;

import net.sf.anathema.library.number.Coordinate;

public class RightClickCenterer implements MouseClickClosure
{
	private final PolygonPanel polygonPanel;
	
	public RightClickCenterer (PolygonPanel polygonPanel)
	{
		this.polygonPanel = polygonPanel;
	}
	
	@Override
	public void mouseClicked (MouseButton button, MetaKey key, Coordinate coordinate, int clickCount)
	{
		if (button != MouseButton.Secondary)
		{
			return;
		}
		if (key != MetaKey.NONE)
		{
			return;
		}
		polygonPanel.centerOn (coordinate);
	}
}
