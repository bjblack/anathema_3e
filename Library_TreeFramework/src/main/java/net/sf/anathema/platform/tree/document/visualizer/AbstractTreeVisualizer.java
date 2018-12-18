package net.sf.anathema.platform.tree.document.visualizer;

import net.sf.anathema.graph.graph.LayeredGraph;
import net.sf.anathema.graph.nodes.ISimpleNode;
import net.sf.anathema.platform.tree.document.components.ILayer;
import net.sf.anathema.platform.tree.document.components.IVisualizableNode;

public abstract class AbstractTreeVisualizer extends AbstractCascadeVisualizer
{
	public AbstractTreeVisualizer (NodeDimensions dimensions, LayeredGraph graph)
	{
		super (dimensions, graph);
	}
	
	private void createVisualizableNodes (int layerIndex)
	{
		ISimpleNode[] layerNodes = getGraph ().getNodesByLayer (layerIndex + 1);
		for (ISimpleNode currentNode : layerNodes)
		{
			getNodeFactory ().registerVisualizableNode (currentNode);
		}
	}
	
	@Override
	public ILayer[] buildTree ()
	{
		int layerCount = getGraph ().getDeepestLayer ();
		for (int layerIndex = layerCount - 1; layerIndex >= 0; layerIndex--)
		{
			createVisualizableNodes (layerIndex);
		}
		ILayer[] layers = createLayers (layerCount);
		setPositionRecursively (getInitialLayer (layers).getNodes ().getFirst (), 0);
		return layers;
	}
	
	private int setPositionRecursively (IVisualizableNode node, int rightSide)
	{
		IVisualizableNode[] relatives = getRelatives (node);
		if (relatives.length == 0)
		{
			node.setPosition (rightSide + node.getWidth () / 2);
			return node.getRightSide () + getProperties ().getGapDimension ().width;
		}
		for (IVisualizableNode relative : relatives)
		{
			rightSide = setPositionRecursively (relative, rightSide);
		}
		node.setPosition ( (relatives[0].getPosition () + relatives[relatives.length - 1].getPosition ()) / 2);
		return rightSide;
	}
	
	protected abstract ILayer getInitialLayer (ILayer[] layers);
	
	protected abstract IVisualizableNode[] getRelatives (IVisualizableNode node);
}
