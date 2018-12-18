package net.sf.anathema.hero.display.fx.perspective.content;

import javafx.scene.Node;

import net.sf.anathema.hero.application.perspective.model.HeroIdentifier;
import net.sf.anathema.library.fx.NodeHolder;
import net.sf.anathema.library.fx.layout.LayoutUtils;
import net.sf.anathema.library.fx.view.FxStack;

import org.tbee.javafx.scene.layout.MigPane;

public class StackView
{
	private final MigPane viewPanel = new MigPane (LayoutUtils.fillWithoutInsets ());
	private final FxStack stack = new FxStack (viewPanel);
	
	public void showView (HeroIdentifier identifier)
	{
		stack.show (identifier);
	}
	
	public void addView (HeroIdentifier identifier, NodeHolder node)
	{
		stack.add (identifier, node.getNode ());
		stack.show (identifier);
	}
	
	public Node getComponent ()
	{
		return viewPanel;
	}
}
