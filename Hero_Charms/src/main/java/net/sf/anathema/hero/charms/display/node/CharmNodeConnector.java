package net.sf.anathema.hero.charms.display.node;

import net.sf.anathema.magic.data.Charm;
import net.sf.anathema.graph.nodes.IIdentifiedRegularNode;

import java.util.Collection;
import java.util.Map;

import static net.sf.anathema.hero.charms.display.prerequisites.ConcreteCharmParentIds.collectNodeIdsOfRenderingParents;

public class CharmNodeConnector
{
	public static void connectNodes (Collection<Charm> groupCharms, Map<String, IIdentifiedRegularNode> charmNodesById)
	{
		for (Charm charm : groupCharms)
		{
			IIdentifiedRegularNode childNode = charmNodesById.get (charm.getName ().text);
			for (String renderingParentNodeId : collectNodeIdsOfRenderingParents (charm))
			{
				IIdentifiedRegularNode parentNode = charmNodesById.get (renderingParentNodeId);
				connectNodes (childNode, parentNode);
			}
		}
	}
	
	private static void connectNodes (IIdentifiedRegularNode childNode, IIdentifiedRegularNode parentNode)
	{
		parentNode.addChild (childNode);
		childNode.addParent (parentNode);
	}
}
