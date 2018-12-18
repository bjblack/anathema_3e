package net.sf.anathema.exaltedengine.attributes;

import net.sf.anathema.characterengine.quality.Name;
import net.sf.anathema.characterengine.quality.Quality;
import net.sf.anathema.characterengine.quality.QualityListener;
import net.sf.anathema.characterengine.support.Announcer;
import net.sf.anathema.exaltedengine.numericquality.NumericValue;
import net.sf.anathema.exaltedengine.numericquality.QualityWithValue;

public class Attribute implements Quality, QualityWithValue
{
	private final NumericValue value;
	private final Name name;
	private final Announcer<QualityListener> announcer = Announcer.to (QualityListener.class);
	
	public Attribute (NumericValue value, Name name)
	{
		this.value = value;
		this.name = name;
	}
	
	@Override
	public void changeValueTo (NumericValue newValue)
	{
		if (value.equals (newValue))
		{
			return;
		}
		value.changeTo (newValue);
		announcer.announce ().eventOccurred ();
	}
	
	@Override
	public boolean hasValue (NumericValue value)
	{
		return value.equals (this.value);
	}
	
	public boolean isGreaterThan (NumericValue value)
	{
		return this.value.isGreaterThan (value);
	}
	
	public boolean isCalled (Name name)
	{
		return this.name.equals (name);
	}
	
	@Override
	public void registerObserver (QualityListener listener)
	{
		announcer.addListener (listener);
	}
	
	@Override
	public Quality copy ()
	{
		return new Attribute (value.copy (), name);
	}
	
	@Override
	public String toString ()
	{
		return "Attribute '" + name + "' at value " + value;
	}
}
