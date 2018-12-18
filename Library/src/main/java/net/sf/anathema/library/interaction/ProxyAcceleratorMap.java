package net.sf.anathema.library.interaction;

import net.sf.anathema.library.interaction.model.Command;
import net.sf.anathema.library.interaction.model.Hotkey;

import java.util.HashMap;
import java.util.Map;

public class ProxyAcceleratorMap implements AcceleratorMap
{
	private final static AcceleratorMap NOT_YET_SET = null;
	private final Map<Hotkey, Command> preregisteredCommands = new HashMap<> ();
	private AcceleratorMap actual;
	
	@Override
	public void register (Hotkey hotkey, Command command)
	{
		if (actual == NOT_YET_SET)
		{
			preregisteredCommands.put (hotkey, command);
		}
		else
		{
			actual.register (hotkey, command);
		}
	}
	
	public void setActualMap (AcceleratorMap map)
	{
		this.actual = map;
		for (Hotkey hotkey : preregisteredCommands.keySet ())
		{
			actual.register (hotkey, preregisteredCommands.get (hotkey));
		}
	}
}
