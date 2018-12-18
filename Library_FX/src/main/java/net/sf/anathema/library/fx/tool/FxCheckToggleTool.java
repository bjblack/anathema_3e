package net.sf.anathema.library.fx.tool;

import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;

import net.sf.anathema.library.interaction.model.Command;
import net.sf.anathema.library.interaction.model.ToggleTool;
import net.sf.anathema.library.view.Style;

public class FxCheckToggleTool extends FxBaseTool implements ToggleTool
{
	public static FxCheckToggleTool create ()
	{
		return new FxCheckToggleTool (new CheckBox ());
	}
	
	
	private final CheckBox box;
	
	public FxCheckToggleTool (final CheckBox box)
	{
		super (box, new ImageView ());
		this.box = box;
	}
	
	@Override
	public void setCommand (Command command)
	{
		Command deselectAndProcess = new DeselectCheckboxAndProcess (command);
		super.setCommand (deselectAndProcess);
	}
	
	@Override
	public void select ()
	{
		box.setSelected (true);
	}
	
	@Override
	public void deselect ()
	{
		box.setSelected (false);
	}
	
	public void setStyleClass (String styleClass)
	{
		box.getStyleClass ().add (styleClass);
	}
	
	@Override
	public void setStyle (Style style)
	{
	}
	
	private class DeselectCheckboxAndProcess implements Command
	{
		private Command command;
		
		public DeselectCheckboxAndProcess (Command command)
		{
			this.command = command;
		}
		
		@Override
		public void execute ()
		{
			deselect ();
			command.execute ();
		}
	}
}
