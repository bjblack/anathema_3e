package net.sf.anathema.platform.tree.document.visualizer;

import net.sf.anathema.graph.graph.LayeredGraph;
import net.sf.anathema.graph.nodes.ISimpleNode;
import net.sf.anathema.library.number.Area;
import net.sf.anathema.platform.tree.document.components.ILayer;
import net.sf.anathema.platform.tree.document.util.BackwardsIterable;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class SimplifiedBottomUpGraphVisualizer extends AbstractCascadeVisualizer
{
	public SimplifiedBottomUpGraphVisualizer (LayeredGraph graph, NodeDimensions properties)
	{
		super (new WideDummyTreePresentationProperties (properties), graph);
	}
	
	@Override
	public ILayer[] buildTree ()
	{
		ILayer[] layers = createLayers ();
		int width = 0;
		for (ILayer layer : layers)
		{
			width = Math.max (width, layer.getNodes ().getFirst ().getWidth ());
		}
		for (ILayer layer : new BackwardsIterable<> (layers))
		{
			layer.getNodes ().getFirst ().setPosition (width / 2);
		}
		for (ILayer layer : new BackwardsIterable<> (layers))
		{
			layer.unrollHorizontalMetanodes ();
		}
		return layers;
	}
	
	public boolean isApplicable ()
	{
		LayeredGraphEvaluation evaluation = new LayeredGraphEvaluation (getGraph ());
		int deepestLayer = getGraph ().getDeepestLayer ();
		for (int index = 1; index < deepestLayer; index++)
		{
			if (evaluation.layerHasLeaves (index))
			{
				return false;
			}
		}
		if (evaluation.numberOfNodesForLayer (deepestLayer) > 1)
		{
			if (!evaluation.isPrecededByEquiformLayer (deepestLayer) && !evaluation.isCompletelyConnectedToPrecedingLayer (
			deepestLayer))
			{
				return false;
			}
		}
		return true;
	}
	
	private ILayer[] createLayers ()
	{
		int layerCount = getGraph ().getDeepestLayer ();
		for (int layerIndex = layerCount - 1; layerIndex >= 0; layerIndex--)
		{
			createVisualizableNodes (layerIndex);
		}
		return createLayers (layerCount);
	}
	
	private void createVisualizableNodes (int layerIndex)
	{
		ISimpleNode[] layerNodes = getGraph ().getNodesByLayer (layerIndex + 1);
		if (layerNodes.length > 1)
		{
			Set<ISimpleNode> nodeSet = new LinkedHashSet<> ();
			nodeSet.addAll (Arrays.asList (layerNodes));
			getNodeFactory ().registerHorizontalMetaNode (nodeSet);
		}
		else
		{
			ISimpleNode singleNode = layerNodes[0];
			if (!isVisualizableNodeRegistered (singleNode))
			{
				getNodeFactory ().registerVisualizableNode (singleNode);
			}
		}
	}
	
	private static class WideDummyTreePresentationProperties implements NodeDimensions
	{
		private final NodeDimensions properties;
		
		public WideDummyTreePresentationProperties (NodeDimensions properties)
		{
			this.properties = properties;
		}
		
		@Override
		public Area getNodeDimension ()
		{
			return properties.getNodeDimension ();
		}
		
		@Override
		public Area getGapDimension ()
		{
			return properties.getGapDimension ();
		}
		
		@Override
		public Area getVerticalLineDimension ()
		{
			return properties.getNodeDimension ();
		}
	}
}
