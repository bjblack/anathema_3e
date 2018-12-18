package net.sf.anathema.graph.util;

import net.sf.anathema.graph.graph.LayeredGraph;
import net.sf.anathema.graph.graph.ProperHierarchicalGraph;
import net.sf.anathema.graph.nodes.ISimpleNode;
import net.sf.anathema.graph.nodes.NodeFactory;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IncidentMatrixBuilderTest
{
	@Test
	public void testOneRootOneLeafOneConnection () throws Exception
	{
		ISimpleNode leaf = NodeFactory.createChildlessNode (2, "leaf");
		ISimpleNode root = NodeFactory.createSingleChildNode (1, leaf, "root");
		LayeredGraph graph = new ProperHierarchicalGraph (new ISimpleNode[]
		{
			root, leaf
		}
		, 2);
		boolean[][] incidentMatrix = getIncidentMatrix (graph);
		assertEquals (true, incidentMatrix[0][0]);
	}
	
	@Test
	public void testTwoRootsOneLeafOneConnection () throws Exception
	{
		ISimpleNode leaf = NodeFactory.createChildlessNode (2, "leaf");
		ISimpleNode root1 = NodeFactory.createChildlessNode (1, "root1");
		ISimpleNode root2 = NodeFactory.createSingleChildNode (1, leaf, "root2");
		LayeredGraph graph = new ProperHierarchicalGraph (new ISimpleNode[]
		{
			root1, root2, leaf
		}
		, 2);
		boolean[][] incidentMatrix = getIncidentMatrix (graph);
		assertEquals (false, incidentMatrix[0][0]);
		assertEquals (true, incidentMatrix[1][0]);
	}
	
	private boolean[][] getIncidentMatrix (LayeredGraph graph)
	{
		return IncidentMatrixUtilities.buildMatrix (graph.getNodesByLayer (1), graph.getNodesByLayer (2));
	}
}
