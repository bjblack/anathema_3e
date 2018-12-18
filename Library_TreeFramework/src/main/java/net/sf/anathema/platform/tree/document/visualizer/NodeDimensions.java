package net.sf.anathema.platform.tree.document.visualizer;

import net.sf.anathema.library.number.Area;

public interface NodeDimensions
{
	Area getNodeDimension ();
	
	Area getGapDimension ();
	
	Area getVerticalLineDimension ();
}
