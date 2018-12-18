package net.sf.anathema.platform.tree.view.interaction;

import net.sf.anathema.library.number.Coordinate;

public class RightClickResetter implements MouseClickClosure
{
	private final PolygonPanel polygonPanel;
	
	public RightClickResetter (PolygonPanel polygonPanel)
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
		if (key != MetaKey.CTRL)
		{
			return;
		}
		polygonPanel.resetTransformation ();
		new DefaultScaler (polygonPanel).scale ();
	}
}
