package net.sf.anathema.platform.tree.view.container;

import net.sf.anathema.library.presenter.RGBColor;
import net.sf.anathema.platform.tree.display.NodePresentationProperties;
import net.sf.anathema.platform.tree.display.draw.ShapeWithPosition;
import net.sf.anathema.platform.tree.view.interaction.PolygonPanel;

public class NullCascade implements Cascade
{
	@Override
	public void colorNode (String nodeId, RGBColor fillColor, RGBColor borderColor)
	{
		// TODO Auto-generated method stub
	}
	
	@Override
	public void addTo (PolygonPanel panel)
	{
		//nothing to do
	}
	
	@Override
	public void addToggleListener (NodeToggleListener listener)
	{
		//nothing to do
	}
	
	@Override
	public void removeToggleListener (NodeToggleListener listener)
	{
		//nothing to do
	}
	
	@Override
	public void initNodeNames (NodePresentationProperties properties)
	{
		//nothing to do
	}
	
	@Override
	public void determinePositionFor (String nodeId, ShapeWithPosition control)
	{
		//Nothing to do
	}
}
