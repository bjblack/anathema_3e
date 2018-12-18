package net.sf.anathema.hero.dummy;

import net.sf.anathema.library.model.Condition;

public class DummyCondition implements Condition
{
	private boolean value;
	
	@Override
	public boolean isFulfilled ()
	{
		return value;
	}
	
	public void setValue (boolean value)
	{
		this.value = value;
	}
}
