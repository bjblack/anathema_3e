package net.sf.anathema.hero.charms.display;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;

import net.sf.anathema.library.event.BooleanChangedListener;
import net.sf.anathema.library.view.BooleanView;
import net.sf.anathema.platform.tree.fx.FxSpecialContent;

public class FxCheckMenuItemView implements BooleanView, FxSpecialContent
{
	private final CheckMenuItem menuItem;
	
	public FxCheckMenuItemView (String label)
	{
		this.menuItem = new CheckMenuItem (label);
	}
	
	@Override
	public void addTo (ContextMenu menu)
	{
		menu.getItems ().add (menuItem);
	}
	
	@Override
	public void setSelected (boolean selected)
	{
		menuItem.setSelected (selected);
	}
	
	@Override
	public void addChangeListener (final BooleanChangedListener listener)
	{
		menuItem.selectedProperty ().addListener (new ChangeListener<Boolean> ()
		{
			@Override
			public void changed (ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean newValue)
			{
				listener.valueChanged (newValue);
			}
		}
		);
	}
}
