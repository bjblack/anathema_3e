package net.sf.anathema.platform.fx.perspective;

import javafx.scene.Node;

import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.sf.anathema.library.fx.Stylesheet;

import org.tbee.javafx.scene.layout.MigPane;

public class UtilityPane
{
	private final MigPane content = new MigPane (new LC ().fill (), new AC ().index (1).shrink ().shrinkPrio (200));
	
	public UtilityPane (final String... styleSheetPaths)
	{
		for (String sheetPath : styleSheetPaths)
		{
			new Stylesheet (sheetPath).applyToParent (content);
		}
	}
	
	public void setNavigationComponent (final Node component)
	{
		content.add (component, new CC ().grow ().minWidth ("200").width ("200").maxWidth ("200"));
	}
	
	public void setContentComponent (final Node component)
	{
		content.add (component, new CC ().grow ().push ());
	}
	
	
	public void addStyleSheetClass (String styleClass)
	{
		content.getStyleClass ().add (styleClass);
	}
	
	public Node getNode ()
	{
		return content;
	}
}
