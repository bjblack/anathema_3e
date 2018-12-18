package net.sf.anathema.platform.tree.document;

import net.sf.anathema.graph.SugiyamaLayout;
import net.sf.anathema.graph.graph.IProperHierarchicalGraph;
import net.sf.anathema.graph.nodes.IRegularNode;
import net.sf.anathema.platform.tree.document.visualizer.NodeDimensions;
import net.sf.anathema.platform.tree.document.visualizer.VisualizedGraph;
import net.sf.anathema.platform.tree.document.visualizer.VisualizedGraphFactory;
import net.sf.anathema.platform.tree.view.AgnosticCascadeBuilder;
import net.sf.anathema.platform.tree.view.container.Cascade;

import java.util.List;

public class GenericCascadeFactory implements CascadeFactory
{
	private final SugiyamaLayout layout = new SugiyamaLayout ();
	private final CascadeCreationStrategy creationStrategy;
	
	public GenericCascadeFactory (CascadeCreationStrategy cascadeStrategy)
	{
		this.creationStrategy = cascadeStrategy;
	}
	
	@Override
	public Cascade createCascade (IRegularNode[] nodes, NodeDimensions properties)
	{
		IProperHierarchicalGraph[] graphs = createHierarchicalGraphs (nodes);
		List<VisualizedGraph> visualizedGraphs = visualizeGraphs (properties, graphs);
		return buildCascadeObject (properties, visualizedGraphs);
	}
	
	private Cascade buildCascadeObject (NodeDimensions properties, List<VisualizedGraph> visualizedGraphs)
	{
		AgnosticCascadeBuilder cascadeBuilder = new AgnosticCascadeBuilder ();
		double firstRowWidth = 0;
		double firstRowHeight = 0;
		for (VisualizedGraph graph : visualizedGraphs)
		{
			if (graph.isSingleNode ())
			{
				firstRowWidth = properties.getGapDimension ().width;
				firstRowHeight = properties.getNodeDimension ().height + properties.getGapDimension ().height;
				break;
			}
		}
		double currentWidth = properties.getGapDimension ().width;
		double maximumHeight = 0;
		for (VisualizedGraph graph : visualizedGraphs)
		{
			graph.addTo (cascadeBuilder);
			if (graph.isSingleNode ())
			{
				graph.translateBy (firstRowWidth, 0);
				firstRowWidth += properties.getGapDimension ().width + graph.getDimension ().width;
			}
			else
			{
				graph.translateBy (currentWidth, firstRowHeight);
				currentWidth += properties.getGapDimension ().width + graph.getDimension ().width;
				maximumHeight = Math.max (maximumHeight, graph.getDimension ().height);
			}
		}
		maximumHeight += firstRowHeight;
		return cascadeBuilder.create ();
	}
	
	private List<VisualizedGraph> visualizeGraphs (NodeDimensions properties,
	IProperHierarchicalGraph[] graphs)
	{
		HierarchicalGraphVisualizer hierarchicalGraphVisualizer = createNodeToGraphConverter (properties);
		return hierarchicalGraphVisualizer.visualizeGraphs (graphs);
	}
	
	private IProperHierarchicalGraph[] createHierarchicalGraphs (IRegularNode[] nodes)
	{
		return layout.createProperHierarchicalGraphs (nodes);
	}
	
	private HierarchicalGraphVisualizer createNodeToGraphConverter (NodeDimensions properties)
	{
		PositionerFactory positionerFactory = new PositionerFactory (properties);
		VisualizedGraphFactory factoryForVisualizedGraphs = creationStrategy.getFactoryForVisualizedGraphs (properties);
		return new HierarchicalGraphVisualizer (positionerFactory, factoryForVisualizedGraphs);
	}
}
