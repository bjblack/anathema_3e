package net.sf.anathema.graph.nodes;

public class NodeFactory
{
	public static IIdentifiedRegularNode createChildlessNode (String id)
	{
		return new IdentifiedRegularNode (id);
	}
	
	public static IIdentifiedRegularNode createChildlessNode (int layer, String id)
	{
		IdentifiedRegularNode regularNode = new IdentifiedRegularNode (id);
		regularNode.setLayer (layer);
		return regularNode;
	}
	
	public static IIdentifiedRegularNode createSingleChildNode (int layer, ISimpleNode child, String id)
	{
		IdentifiedRegularNode regularNode = new IdentifiedRegularNode (id);
		regularNode.setLayer (layer);
		regularNode.addChild (child);
		return regularNode;
	}
	
	public static IIdentifiedRegularNode createMultiChildNode (int layer, ISimpleNode[] children, String id)
	{
		IdentifiedRegularNode regularNode = new IdentifiedRegularNode (id);
		regularNode.setLayer (layer);
		for (ISimpleNode child : children)
		{
			regularNode.addChild (child);
		}
		return regularNode;
	}
}
