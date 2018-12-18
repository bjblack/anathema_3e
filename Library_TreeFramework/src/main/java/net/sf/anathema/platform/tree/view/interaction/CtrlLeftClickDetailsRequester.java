package net.sf.anathema.platform.tree.view.interaction;

import net.sf.anathema.library.number.Coordinate;
import net.sf.anathema.platform.tree.display.NodeInteractionListener;
import net.sf.anathema.platform.tree.view.container.Cascade;
import net.sf.anathema.platform.tree.view.container.NodeToggleListener;
import net.sf.anathema.platform.tree.view.draw.InteractiveGraphicsElement;

public class CtrlLeftClickDetailsRequester implements MouseClickClosure
{
	private final Cascade cascade;
	private final PolygonPanel polygonPanel;
	private final DetailsListener listener;
	
	public CtrlLeftClickDetailsRequester (Cascade cascade, PolygonPanel polygonPanel,
	NodeInteractionListener interactionListener)
	{
		this.cascade = cascade;
		this.polygonPanel = polygonPanel;
		this.listener = new DetailsListener (interactionListener);
	}
	
	@Override
	public void mouseClicked (MouseButton button, MetaKey key, Coordinate coordinate, int clickCount)
	{
		if (button != MouseButton.Primary || key != MetaKey.CTRL)
		{
			return;
		}
		polygonPanel.onElementAtPoint (coordinate).perform (new Closure ()
		{
			@Override
			public void execute (InteractiveGraphicsElement polygon)
			{
				cascade.addToggleListener (listener);
				polygon.toggle ();
				cascade.removeToggleListener (listener);
			}
		}
		);
	}
	
	private static class DetailsListener implements NodeToggleListener
	{
		private final NodeInteractionListener interactionListener;
		
		public DetailsListener (NodeInteractionListener interactionListener)
		{
			this.interactionListener = interactionListener;
		}
		
		@Override
		public void toggled (String id)
		{
			interactionListener.nodeDetailsDemanded (id);
		}
	}
}
