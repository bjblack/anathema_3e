package net.sf.anathema.library.fx.dot;

import net.sf.anathema.library.interaction.model.ToggleTool;
import net.sf.anathema.library.interaction.model.Tool;
import net.sf.anathema.library.view.IntValueView;

public interface ExtensibleDotView
{
	IntValueView getIntValueView ();
	
	ToggleTool addToggleInFront ();
	
	ToggleTool addToggleBehind ();
	
	Tool addToolBehind ();
	
	void remove ();
}
