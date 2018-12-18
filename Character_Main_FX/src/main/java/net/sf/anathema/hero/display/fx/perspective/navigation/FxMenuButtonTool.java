package net.sf.anathema.hero.display.fx.perspective.navigation;

import javafx.scene.control.SplitMenuButton;
import javafx.scene.image.ImageView;

import net.sf.anathema.library.fx.tool.AdjustSize;
import net.sf.anathema.library.fx.tool.FxBaseTool;
import net.sf.anathema.library.fx.tool.ImageClosure;
import net.sf.anathema.library.fx.tool.SetImage;
import net.sf.anathema.library.interaction.model.Tool;
import net.sf.anathema.library.interaction.view.MenuTool;
import net.sf.anathema.platform.fx.menu.FxMenuTool;


public class FxMenuButtonTool extends FxBaseTool implements MenuTool
{
	public static FxMenuButtonTool ForToolbar ()
	{
		ImageView mainIcon = new ImageView ();
		ImageView miniIcon = new ImageView ();
		SplitMenuButton button = new SplitMenuButton ();
		button.setGraphic (mainIcon);
		AdjustSize adjustSize = new AdjustSize (button);
		adjustSize.addExtraWidth (30);
		return new FxMenuButtonTool (button, miniIcon, adjustSize, new SetImage (mainIcon));
	}
	
	private final SplitMenuButton button;
	
	public FxMenuButtonTool (SplitMenuButton button, ImageView overlay, ImageClosure... actionsOnLoad)
	{
		super (button, overlay, actionsOnLoad);
		this.button = button;
	}
	
	@Override
	public void clearMenu ()
	{
		button.getItems ().clear ();
	}
	
	@Override
	public Tool addMenuEntry ()
	{
		FxMenuTool tool = new FxMenuTool ();
		button.getItems ().add (tool.getNode ());
		return tool;
	}
}
