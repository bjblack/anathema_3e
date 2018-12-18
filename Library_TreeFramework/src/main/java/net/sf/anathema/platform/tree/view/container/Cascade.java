package net.sf.anathema.platform.tree.view.container;

import net.sf.anathema.library.presenter.RGBColor;
import net.sf.anathema.platform.tree.display.NodePresentationProperties;
import net.sf.anathema.platform.tree.display.draw.ShapeWithPosition;
import net.sf.anathema.platform.tree.view.interaction.PolygonPanel;

public interface Cascade
{
	void colorNode (String nodeId, RGBColor fillColor, RGBColor borderColor);
	
	void addTo (PolygonPanel panel);
	
	void addToggleListener (NodeToggleListener listener);
	
	void removeToggleListener (NodeToggleListener listener);
	
	void initNodeNames (NodePresentationProperties properties);
	
	void determinePositionFor (String nodeId, ShapeWithPosition control);
}
