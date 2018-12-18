package net.sf.anathema.graph.ordering;

import net.sf.anathema.graph.hierarchy.HierarchyBuilder;
import net.sf.anathema.graph.hierarchy.IHierachyBuilder;
import net.sf.anathema.graph.nodes.IRegularNode;
import net.sf.anathema.graph.nodes.ISimpleNode;
import net.sf.anathema.graph.nodes.NodeFactory;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

public class HierarchyBuilderTest
{
	@Test
	public void testSimpleDummyEdge () throws Exception
	{
		IRegularNode leaf = NodeFactory.createChildlessNode (3, "leaf");
		IRegularNode root = NodeFactory.createSingleChildNode (1, leaf, "root");
		IHierachyBuilder builder = new HierarchyBuilder ();
		ISimpleNode[] graph = builder.removeLongEdges (new IRegularNode[]
		{
			leaf, root
		}
		);
		assertEquals (3, graph.length);
		assertNotSame (leaf, root.getChildren ()[0]);
		assertEquals (leaf, root.getChildren ()[0].getChildren ()[0]);
	}
}
