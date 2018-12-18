package net.sf.anathema.platform.tree.view.visualizer;

import net.sf.anathema.library.number.Area;
import net.sf.anathema.platform.tree.document.components.ILayer;
import net.sf.anathema.platform.tree.document.components.IVisualizableNode;
import net.sf.anathema.platform.tree.document.components.VisualizableNodes;
import net.sf.anathema.platform.tree.document.visualizer.NodeDimensions;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AgnosticGraphFactoryTest
{
	private final NodeDimensions properties = mock (NodeDimensions.class);
	private final ILayer layer = mock (ILayer.class);
	private final AgnosticGraphFactory factory = new AgnosticGraphFactory (properties);
	
	@Before
	public void setUp () throws Exception
	{
		when (properties.getNodeDimension ()).thenReturn (new Area ());
		when (properties.getGapDimension ()).thenReturn (new Area ());
	}
	
	@Test
	public void createsSingleNode () throws Exception
	{
		addNodesToLayer (layer, 1);
		assertThat (factory.create (layer).isSingleNode (), is (true));
	}
	
	@Test
	public void createsNoSingleNodeForMultipleLayers () throws Exception
	{
		addNodesToLayer (layer, 1);
		ILayer nextLayer = mock (ILayer.class);
		addNodesToLayer (nextLayer, 1);
		assertThat (factory.create (layer, nextLayer).isSingleNode (), is (false));
	}
	
	@Test
	public void createsNoSingleNodeForMultipleNodes () throws Exception
	{
		addNodesToLayer (layer, 2);
		assertThat (factory.create (layer).isSingleNode (), is (false));
	}
	
	private void addNodesToLayer (ILayer layer, int amount)
	{
		List<IVisualizableNode> list = new ArrayList<> ();
		for (int i = 0; i < amount; i++)
		{
			list.add (mock (IVisualizableNode.class));
		}
		when (layer.getNodes ()).thenReturn (new VisualizableNodes (list));
	}
}
