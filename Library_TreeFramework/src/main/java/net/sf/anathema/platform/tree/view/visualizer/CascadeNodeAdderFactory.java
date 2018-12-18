package net.sf.anathema.platform.tree.view.visualizer;

import net.sf.anathema.library.number.Area;
import net.sf.anathema.platform.tree.document.visualizer.NodeAdder;
import net.sf.anathema.platform.tree.document.visualizer.NodeAdderFactory;

public class CascadeNodeAdderFactory implements NodeAdderFactory
{
	@Override
	public NodeAdder create (String id, Area dimension, int xPosition, int yPosition)
	{
		return new AgnosticNodeAdder (id, xPosition, yPosition);
	}
}
