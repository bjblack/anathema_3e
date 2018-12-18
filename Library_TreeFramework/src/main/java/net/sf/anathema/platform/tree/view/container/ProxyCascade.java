package net.sf.anathema.platform.tree.view.container;

import net.sf.anathema.library.presenter.RGBColor;
import net.sf.anathema.platform.tree.display.NodePresentationProperties;
import net.sf.anathema.platform.tree.display.draw.ShapeWithPosition;
import net.sf.anathema.platform.tree.view.interaction.PolygonPanel;

public class ProxyCascade implements Cascade
{
	private Cascade delegate = new NullCascade ();
	
	public void setDelegate (Cascade cascade)
	{
		this.delegate = cascade;
	}
	
	@Override
	public void colorNode (String nodeId, RGBColor fillColor, RGBColor borderColor)
	{
		delegate.colorNode (nodeId, fillColor, borderColor);
	}
	
	@Override
	public void addTo (PolygonPanel panel)
	{
		delegate.addTo (panel);
	}
	
	@Override
	public void addToggleListener (NodeToggleListener listener)
	{
		delegate.addToggleListener (listener);
	}
	
	@Override
	public void removeToggleListener (NodeToggleListener listener)
	{
		delegate.removeToggleListener (listener);
	}
	
	@Override
	public void initNodeNames (NodePresentationProperties properties)
	{
		delegate.initNodeNames (properties);
	}
	
	@Override
	public void determinePositionFor (String nodeId, ShapeWithPosition control)
	{
		delegate.determinePositionFor (nodeId, control);
	}
	
	public void clear ()
	{
		this.delegate = new NullCascade ();
	}
}
