package net.sf.anathema.platform.tree.view.visualizer;

import net.sf.anathema.library.number.Area;
import net.sf.anathema.platform.tree.document.visualizer.VisualizedGraph;
import net.sf.anathema.platform.tree.view.AgnosticCascadeBuilder;
import net.sf.anathema.platform.tree.view.container.DefaultContainerCascade;

public class AgnosticVisualizedGraph implements VisualizedGraph
{
	private final DefaultContainerCascade container;
	private final Area dimension;
	private final boolean containsSingleNode;
	
	public AgnosticVisualizedGraph (DefaultContainerCascade container, Area dimension, boolean containsSingleNode)
	{
		this.container = container;
		this.dimension = dimension;
		this.containsSingleNode = containsSingleNode;
	}
	
	@Override
	public Area getDimension ()
	{
		return dimension;
	}
	
	@Override
	public boolean isSingleNode ()
	{
		return containsSingleNode;
	}
	
	@Override
	public void translateBy (double x, double y)
	{
		container.moveBy (x, y);
	}
	
	@Override
	public void addTo (AgnosticCascadeBuilder builder)
	{
		builder.add (container);
	}
}
