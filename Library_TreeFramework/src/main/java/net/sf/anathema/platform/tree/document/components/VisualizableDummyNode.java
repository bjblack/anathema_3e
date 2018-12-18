package net.sf.anathema.platform.tree.document.components;

import net.sf.anathema.graph.nodes.ISimpleNode;
import net.sf.anathema.library.collection.MultiEntryMap;
import net.sf.anathema.library.number.Area;

import java.util.Map;

public class VisualizableDummyNode extends AbstractSingleVisualizableNode
{
	@Override
	public void accept (IVisualizableNodeVisitor visitor)
	{
		visitor.visitDummyNode (this);
	}
	
	public VisualizableDummyNode (ISimpleNode contentNode, Map<ISimpleNode, IVisualizableNode> map,
	Area nodeDimension, MultiEntryMap<ISimpleNode, ISimpleNode> leafNodesByAncestors)
	{
		super (contentNode, map, nodeDimension, leafNodesByAncestors);
	}
	
	public String toString ()
	{
		return "Dummy";
	}
}
