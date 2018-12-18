package net.sf.anathema.hero.charms.display.special;

import net.sf.anathema.library.event.BooleanChangedListener;
import net.sf.anathema.library.view.BooleanView;

import org.jmock.example.announcer.Announcer;

public class ProxyBooleanView implements BooleanView
{
	private final String label;
	private final Announcer<BooleanChangedListener> listeners = new Announcer<> (BooleanChangedListener.class);
	private BooleanView actualView;
	private boolean selected = false;
	
	public ProxyBooleanView (String label)
	{
		this.label = label;
	}
	
	public void setActualView (BooleanView actualView)
	{
		this.actualView = actualView;
		actualView.addChangeListener (new BooleanChangedListener ()
		{
			@Override
			public void valueChanged (boolean newValue)
			{
				listeners.announce ().valueChanged (newValue);
			}
		}
		);
		actualView.setSelected (selected);
	}
	
	@Override
	public void setSelected (boolean selected)
	{
		if (actualView != null)
		{
			actualView.setSelected (selected);
		}
		if (selected == this.selected)
		{
			return;
		}
		this.selected = selected;
		listeners.announce ().valueChanged (selected);
	}
	
	@Override
	public void addChangeListener (BooleanChangedListener listener)
	{
		if (actualView != null)
		{
			actualView.addChangeListener (listener);
			return;
		}
		listeners.addListener (listener);
	}
	
	public String getLabel ()
	{
		return label;
	}
}
