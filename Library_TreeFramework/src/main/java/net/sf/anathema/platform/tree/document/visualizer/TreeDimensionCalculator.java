package net.sf.anathema.platform.tree.document.visualizer;

import net.sf.anathema.library.number.Area;
import net.sf.anathema.platform.tree.document.components.ILayer;

public class TreeDimensionCalculator
{
	private final NodeDimensions dimension;
	
	public TreeDimensionCalculator (NodeDimensions dimensions)
	{
		this.dimension = dimensions;
	}
	
	public Area getTreeDimension (ILayer... layers)
	{
		return new Area (getTreeWidth (layers), getTreeHeight (layers));
	}
	
	public int getTreeWidth (ILayer... layers)
	{
		int width = 0;
		for (ILayer layer : layers)
		{
			width = Math.max (width, layer.getWidth ());
		}
		return width;
	}
	
	public int getTreeHeight (ILayer... layers)
	{
		return layers.length * dimension.getNodeDimension ().height + (layers.length - 1) * dimension.getGapDimension ().height;
	}
}
