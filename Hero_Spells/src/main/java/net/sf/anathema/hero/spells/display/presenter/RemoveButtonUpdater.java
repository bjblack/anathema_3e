package net.sf.anathema.hero.spells.display.presenter;

import net.sf.anathema.hero.magic.display.magic.MagicLearnProperties;
import net.sf.anathema.hero.magic.display.magic.MagicLearnView;
import net.sf.anathema.library.interaction.model.Tool;

import java.util.List;

public class RemoveButtonUpdater implements ButtonUpdater
{
	private final MagicLearnProperties properties;
	private final Tool tool;
	private final MagicLearnView view;
	
	public RemoveButtonUpdater (MagicLearnProperties properties, Tool tool, MagicLearnView view)
	{
		this.properties = properties;
		this.tool = tool;
		this.view = view;
	}
	
	@Override
	public void updateButton ()
	{
		List selectedValues = view.getSelectedLearnedValues ();
		boolean allowed = properties.isRemoveAllowed (selectedValues);
		if (allowed)
		{
			tool.enable ();
		}
		else
		{
			tool.disable ();
		}
	}
}
