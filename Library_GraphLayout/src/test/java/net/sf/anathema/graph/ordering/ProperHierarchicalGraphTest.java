package net.sf.anathema.graph.ordering;

import net.sf.anathema.graph.graph.IProperHierarchicalGraph;
import net.sf.anathema.graph.graph.LayeredGraph;
import net.sf.anathema.graph.graph.ProperHierarchicalGraph;
import net.sf.anathema.graph.nodes.IRegularNode;
import net.sf.anathema.graph.nodes.ISimpleNode;
import net.sf.anathema.graph.nodes.NodeFactory;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ProperHierarchicalGraphTest
{
	@Test
	public void testSingleNodeGraph () throws Exception
	{
		IRegularNode node = NodeFactory.createChildlessNode (1, "node");
		LayeredGraph graph = new ProperHierarchicalGraph (new ISimpleNode[]
		{
			node
		}
		, 1);
		assertNotNull (graph);
	}
	
	@Test
	public void testDualLayerGraph () throws Exception
	{
		ISimpleNode node = NodeFactory.createChildlessNode (2, "node");
		ISimpleNode root = NodeFactory.createSingleChildNode (1, node, "root");
		LayeredGraph graph = new ProperHierarchicalGraph (new ISimpleNode[]
		{
			root, node
		}
		, 2);
		assertEquals (root, graph.getNodesByLayer (1)[0]);
		assertEquals (node, graph.getNodesByLayer (2)[0]);
	}
	
	@Test
	public void testLongEdgeException () throws Exception
	{
		try
		{
			ISimpleNode node = NodeFactory.createChildlessNode (3, "node");
			ISimpleNode root = NodeFactory.createSingleChildNode (1, node, "root");
			new ProperHierarchicalGraph (new ISimpleNode[]
			{
				root, node
			}
			, 3);
			fail ();
		}
		catch (IllegalArgumentException e)
		{
			// nothing to do
		}
	}
	
	@Test
	public void testLayerOrderUnchanged () throws Exception
	{
		ISimpleNode node1 = NodeFactory.createChildlessNode (1, "node1");
		ISimpleNode node2 = NodeFactory.createChildlessNode (1, "node2");
		ISimpleNode node3 = NodeFactory.createChildlessNode (1, "node3");
		ISimpleNode[] originalNodeArray = new ISimpleNode[]
		{
			node1, node2, node3
		}
		;
		LayeredGraph graph = new ProperHierarchicalGraph (originalNodeArray, 1);
		ISimpleNode[] layerOneNodes = graph.getNodesByLayer (1);
		assertTrue (Arrays.equals (originalNodeArray, layerOneNodes));
	}
	
	@Test
	public void testSetNewLayerOrder () throws Exception
	{
		ISimpleNode node1 = NodeFactory.createChildlessNode (1, "node1");
		ISimpleNode node2 = NodeFactory.createChildlessNode (1, "node2");
		ISimpleNode node3 = NodeFactory.createChildlessNode (1, "node3");
		IProperHierarchicalGraph graph = new ProperHierarchicalGraph (new ISimpleNode[]
		{
			node1, node2, node3
		}
		, 1);
		ISimpleNode[] reorderedNodes = new ISimpleNode[]
		{
			node2, node3, node1
		}
		;
		graph.setNewLayerOrder (1, reorderedNodes);
		assertTrue (Arrays.equals (reorderedNodes, graph.getNodesByLayer (1)));
	}
	
	@Test
	public void testNodeExchangedException () throws Exception
	{
		ISimpleNode node1 = NodeFactory.createChildlessNode (1, "node1");
		ISimpleNode node2 = NodeFactory.createChildlessNode (1, "node2");
		ISimpleNode node3 = NodeFactory.createChildlessNode (1, "node3");
		ISimpleNode secondLayerNode = NodeFactory.createChildlessNode (2, "secondLayer");
		IProperHierarchicalGraph graph = new ProperHierarchicalGraph (new ISimpleNode[]
		{
			node1,
			node2,
			node3,
			secondLayerNode
		}
		, 2);
		try
		{
			ISimpleNode[] reorderedNodes = new ISimpleNode[]
			{
				node2, node3, secondLayerNode
			}
			;
			graph.setNewLayerOrder (1, reorderedNodes);
			fail ();
		}
		catch (Exception e)
		{
			assertTrue (Arrays.equals (new ISimpleNode[]
			{
				node1, node2, node3
			}
			, graph.getNodesByLayer (1)));
		}
	}
	
	@Test
	public void testNodeRemovedException () throws Exception
	{
		ISimpleNode node1 = NodeFactory.createChildlessNode (1, "node1");
		ISimpleNode node2 = NodeFactory.createChildlessNode (1, "node2");
		ISimpleNode node3 = NodeFactory.createChildlessNode (1, "node3");
		IProperHierarchicalGraph graph = new ProperHierarchicalGraph (new ISimpleNode[]
		{
			node1, node2, node3
		}
		, 1);
		try
		{
			ISimpleNode[] reorderedNodes = new ISimpleNode[]
			{
				node2, node3
			}
			;
			graph.setNewLayerOrder (1, reorderedNodes);
			fail ();
		}
		catch (Exception e)
		{
			assertTrue (Arrays.equals (new ISimpleNode[]
			{
				node1, node2, node3
			}
			, graph.getNodesByLayer (1)));
		}
	}
}
