package net.sf.anathema.library.fx.dot;

import javafx.scene.Node;

import net.miginfocom.layout.CC;
import net.sf.anathema.library.fx.layout.LayoutUtils;

import org.tbee.javafx.scene.layout.MigPane;

public class SimpleDotViewPanel implements DotViewPanel
{
	private final MigPane pane = new MigPane (LayoutUtils.withoutInsets ().wrapAfter (3).fillX ().gridGap ("2", "0"));
	
	@Override
	public void remove (Node node)
	{
		pane.getChildren ().remove (node);
	}
	
	@Override
	public void add (Node node)
	{
		add (node, new CC ());
	}
	
	@Override
	public void add (final Node node, final CC constraints)
	{
		pane.add (node, constraints);
	}
	
	public Node getNode ()
	{
		return pane;
	}
}
