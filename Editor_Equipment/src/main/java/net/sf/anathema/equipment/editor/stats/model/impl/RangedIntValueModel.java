package net.sf.anathema.equipment.editor.stats.model.impl;

import net.sf.anathema.equipment.editor.stats.model.IIntValueModel;
import net.sf.anathema.library.event.IntegerChangedListener;
import net.sf.anathema.library.number.Range;

import org.jmock.example.announcer.Announcer;

public class RangedIntValueModel implements IIntValueModel
{
	private final Range range;
	private final Announcer<IntegerChangedListener> valueControl = Announcer.to (IntegerChangedListener.class);
	private int value;
	
	public RangedIntValueModel (Range range, int initialValue)
	{
		this.value = initialValue;
		this.range = range;
	}
	
	@Override
	public final void addIntValueChangeListener (IntegerChangedListener changeListener)
	{
		valueControl.addListener (changeListener);
	}
	
	@Override
	public int getValue ()
	{
		return value;
	}
	
	@Override
	public void setValue (int value)
	{
		value = range.getNearestValue (value);
		if (this.value == value)
		{
			return;
		}
		this.value = value;
		valueControl.announce ().valueChanged (value);
	}
	
	@Override
	public Integer getMaximum ()
	{
		return range.getUpperBound ();
	}
	
	@Override
	public Integer getMinimum ()
	{
		return range.getLowerBound ();
	}
}
