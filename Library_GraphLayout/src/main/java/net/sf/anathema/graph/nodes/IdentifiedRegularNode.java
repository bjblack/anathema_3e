package net.sf.anathema.graph.nodes;

import net.sf.anathema.library.identifier.SimpleIdentifier;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class IdentifiedRegularNode extends SimpleIdentifier implements IIdentifiedRegularNode
{
	private static final class NodeIndexComparator implements Comparator<ISimpleNode>
	{
		private final ISimpleNode[] orderedNodes;
		
		private NodeIndexComparator (ISimpleNode[] orderedNodes)
		{
			this.orderedNodes = orderedNodes;
		}
		
		@Override
		public int compare (ISimpleNode o1, ISimpleNode o2)
		{
			return indexOf (orderedNodes, o1) - indexOf (orderedNodes, o2);
		}
	}
	
	private final List<ISimpleNode> childList = new ArrayList<> ();
	private final List<ISimpleNode> parentList = new ArrayList<> ();
	private int layer = 0;
	private boolean lowerToChildren = false;
	
	public IdentifiedRegularNode (String id, IRegularNode... children)
	{
		super (id);
		Collections.addAll (childList, children);
	}
	
	@Override
	public void removeParent (ISimpleNode node)
	{
		parentList.remove (node);
	}
	
	@Override
	public void addParent (ISimpleNode parentNode)
	{
		parentList.add (parentNode);
	}
	
	@Override
	public ISimpleNode[] getParents ()
	{
		return parentList.toArray (new ISimpleNode[parentList.size ()]);
	}
	
	@Override
	public boolean isRootNode ()
	{
		return parentList.isEmpty ();
	}
	
	@Override
	public void setLayer (int newLayer)
	{
		layer = newLayer;
	}
	
	@Override
	public ISimpleNode[] getChildren ()
	{
		return childList.toArray (new ISimpleNode[childList.size ()]);
	}
	
	@Override
	public int getLayer ()
	{
		return layer;
	}
	
	@Override
	public void removeChild (ISimpleNode child)
	{
		childList.remove (child);
	}
	
	@Override
	public void addChild (ISimpleNode child)
	{
		childList.add (child);
	}
	
	@Override
	public void setLowerToChildren (boolean lower)
	{
		this.lowerToChildren = lower;
	}
	
	@Override
	public boolean getLowerToChildren ()
	{
		return lowerToChildren;
	}
	
	@Override
	public boolean isLeafNode ()
	{
		return childList.isEmpty ();
	}
	
	@Override
	public boolean hasMultipleParents ()
	{
		return parentList.size () > 1;
	}
	
	@Override
	public void reorderChildren (ISimpleNode[] childrenLayer)
	{
		Collections.sort (childList, new NodeIndexComparator (childrenLayer));
	}
	
	@Override
	public ISimpleNode[] getChildren (ISimpleNode[] childrenLayer)
	{
		ISimpleNode[] unsortedChildren = getChildren ();
		Arrays.sort (unsortedChildren, new NodeIndexComparator (childrenLayer));
		return unsortedChildren;
	}
	
	@Override
	public String toString ()
	{
		return getId () + " (Layer:" + getLayer () + ")";
	}
	
	private static <R> int indexOf (R[] array, R value)
	{
		int index = ArrayUtils.indexOf (array, value);
		if (index != ArrayUtils.INDEX_NOT_FOUND)
		{
			return index;
		}
		throw new IllegalArgumentException ("Value not contained in array: " + value);
	}
}
