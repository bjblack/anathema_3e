package net.sf.anathema.platform.tree.display;

import net.sf.anathema.library.presenter.RGBColor;
import net.sf.anathema.platform.tree.view.container.Cascade;

public interface TreeView
{
	void addNodeInteractionListener (NodeInteractionListener listener);
	
	void colorNode (String nodeId, RGBColor color, RGBColor border);
	
	void addCascadeLoadedListener (CascadeLoadedListener listener);
	
	void setCanvasBackground (RGBColor color);
	
	void loadCascade (Cascade cascade, boolean resetView);
	
	void addSpecialControl (String nodeId, SpecialControl control);
	
	void initNodeNames ();
	
	void clear ();
	
	void registerSpecialType (Class contentClass, ContentFactory factory);
	
	void initToolTips (ToolTipProperties properties);
	
	void loadNodeNamesFrom (NodePresentationProperties nodePresentationProperties);
}
