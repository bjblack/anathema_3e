package net.sf.anathema.library.fx.dot;

import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import jfxtras.scene.control.ListSpinner;
import org.jmock.example.announcer.Announcer;

public class DotSelectionSpinner
{
	private final Announcer<ChangeListener> announcer = new Announcer<> (ChangeListener.class);
	private ListSpinner<Integer> spinner;
	@SuppressWarnings ("unchecked")
	private ChangeListener<Integer> announcingListener = (observableValue, oldValue, newValue) -> announcer.announce ().changed (observableValue, oldValue, newValue);
	
	public DotSelectionSpinner (final int lowerBound, final int upperBound)
	{
		spinner = new ListSpinner<> (lowerBound, upperBound);
		spinner.getStyleClass ().add ("dots");
		spinner.valueProperty ().addListener (announcingListener);
	}
	
	public Node getNode ()
	{
		return spinner;
	}
	
	public void setValue (int value)
	{
		spinner.setValue (value);
	}
	
	public void setValueSilently (int value)
	{
		spinner.valueProperty ().removeListener (announcingListener);
		setValue (value);
		spinner.valueProperty ().addListener (announcingListener);
	}
	
	public void addListener (ChangeListener<Integer> listener)
	{
		announcer.addListener (listener);
	}
	
	public void removeListener (ChangeListener<Integer> listener)
	{
		announcer.removeListener (listener);
	}
	
	public int getValue ()
	{
		return spinner.getValue ();
	}
}
