package net.sf.anathema.hero.display.fx.creation;

import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;

import net.sf.anathema.library.fx.tool.ImageContainer;
import net.sf.anathema.library.fx.tool.LoadImage;
import net.sf.anathema.library.fx.tool.SetImage;
import net.sf.anathema.library.interaction.model.Command;
import net.sf.anathema.library.interaction.model.Hotkey;
import net.sf.anathema.library.interaction.model.Tool;
import net.sf.anathema.library.resources.RelativePath;

import org.controlsfx.control.action.Action;

import java.util.function.Consumer;

public class ControlsFxTool implements Tool
{
	private ConfigurableControlsFxAction action = new ConfigurableControlsFxAction ("");
	
	@Override
	public void setIcon (RelativePath relativePath)
	{
		ImageContainer image = new LoadImage (relativePath).run ();
		ImageView imageView = new ImageView ();
		new SetImage (imageView).run (image);
		action.graphicProperty ().setValue (imageView);
	}
	
	@Override
	public void setOverlay (RelativePath relativePath)
	{
		//nothing to do
	}
	
	@Override
	public void setTooltip (String text)
	{
		action.longTextProperty ().setValue (text);
	}
	
	@Override
	public void setText (String text)
	{
		action.textProperty ().setValue (text);
	}
	
	@Override
	public void enable ()
	{
		action.disabledProperty ().setValue (false);
	}
	
	@Override
	public void disable ()
	{
		action.disabledProperty ().setValue (true);
	}
	
	@Override
	public void setCommand (Command command)
	{
		action.setCommand (command);
	}
	
	@Override
	public void setHotkey (Hotkey s)
	{
		//nothing to do
	}
	
	public Action getAction ()
	{
		return action;
	}
}
