package net.sf.anathema.platform.tree.document;

import net.sf.anathema.graph.graph.IProperHierarchicalGraph;
import net.sf.anathema.platform.tree.document.visualizer.BottomUpGraphPositioner;
import net.sf.anathema.platform.tree.document.visualizer.ICascadeVisualizer;
import net.sf.anathema.platform.tree.document.visualizer.InvertedTreePositioner;
import net.sf.anathema.platform.tree.document.visualizer.NodeDimensions;
import net.sf.anathema.platform.tree.document.visualizer.SingleNodePositioner;
import net.sf.anathema.platform.tree.document.visualizer.TreePositioner;

public class PositionerFactory
{
	private final NodeDimensions properties;
	
	public PositionerFactory (NodeDimensions properties)
	{
		this.properties = properties;
	}
	
	public ICascadeVisualizer createForBottomUp (IProperHierarchicalGraph graph)
	{
		return new BottomUpGraphPositioner (graph, properties);
	}
	
	public ICascadeVisualizer createForInvertedTree (IProperHierarchicalGraph graph)
	{
		return new InvertedTreePositioner (graph, properties);
	}
	
	public ICascadeVisualizer createForTree (IProperHierarchicalGraph graph)
	{
		return new TreePositioner (graph, properties);
	}
	
	public ICascadeVisualizer createForSingle (IProperHierarchicalGraph graph)
	{
		return new SingleNodePositioner (graph, properties);
	}
}
