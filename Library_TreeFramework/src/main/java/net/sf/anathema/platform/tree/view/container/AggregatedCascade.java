package net.sf.anathema.platform.tree.view.container;

import net.sf.anathema.library.presenter.RGBColor;
import net.sf.anathema.platform.tree.display.NodePresentationProperties;
import net.sf.anathema.platform.tree.display.draw.ShapeWithPosition;
import net.sf.anathema.platform.tree.view.interaction.PolygonPanel;

import com.google.common.collect.Lists;

import java.util.List;

public class AggregatedCascade implements Cascade
{
	private final List<ContainerCascade> cascades = Lists.newArrayList ();
	
	@Override
	public void colorNode (String nodeId, RGBColor fillColor, RGBColor borderColor)
	{
		for (ContainerCascade cascade : cascades)
		{
			if (cascade.hasNode (nodeId))
			{
				cascade.colorNode (nodeId, fillColor, borderColor);
			}
		}
	}
	
	@Override
	public void addTo (PolygonPanel panel)
	{
		for (Cascade cascade : cascades)
		{
			cascade.addTo (panel);
		}
	}
	
	@Override
	public void addToggleListener (NodeToggleListener listener)
	{
		for (Cascade cascade : cascades)
		{
			cascade.addToggleListener (listener);
		}
	}
	
	@Override
	public void removeToggleListener (NodeToggleListener listener)
	{
		for (Cascade cascade : cascades)
		{
			cascade.removeToggleListener (listener);
		}
	}
	
	@Override
	public void initNodeNames (NodePresentationProperties properties)
	{
		for (Cascade cascade : cascades)
		{
			cascade.initNodeNames (properties);
		}
	}
	
	@Override
	public void determinePositionFor (String nodeId, ShapeWithPosition control)
	{
		for (ContainerCascade cascade : cascades)
		{
			cascade.determinePositionFor (nodeId, control);
		}
	}
	
	public void add (ContainerCascade cascade)
	{
		cascades.add (cascade);
	}
}
