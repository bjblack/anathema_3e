package net.sf.anathema.hero.equipment.display.presenter;

import net.sf.anathema.equipment.database.NullClosure;
import net.sf.anathema.library.collection.Closure;

public class ProxyClosure<S> implements Closure<S>
{
	private Closure<S> delegate = new NullClosure<> ();
	
	public void setDelegate (Closure<S> closure)
	{
		this.delegate = closure;
	}
	
	@Override
	public void execute (S value)
	{
		delegate.execute (value);
	}
}
