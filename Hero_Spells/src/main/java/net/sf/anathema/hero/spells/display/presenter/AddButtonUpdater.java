package net.sf.anathema.hero.spells.display.presenter;

import net.sf.anathema.hero.magic.display.magic.MagicLearnProperties;
import net.sf.anathema.hero.magic.display.magic.MagicLearnView;
import net.sf.anathema.library.interaction.model.Tool;

public class AddButtonUpdater implements ButtonUpdater
{
	private final MagicLearnProperties properties;
	private final Tool tool;
	private final MagicLearnView view;
	
	public AddButtonUpdater (MagicLearnProperties properties, Tool tool, MagicLearnView view)
	{
		this.properties = properties;
		this.tool = tool;
		this.view = view;
	}
	
	public void updateButton ()
	{
		boolean available = properties.isMagicSelectionAvailable (view.getSelectedAvailableValues ());
		if (available)
		{
			tool.enable ();
		}
		else
		{
			tool.disable ();
		}
	}
}
