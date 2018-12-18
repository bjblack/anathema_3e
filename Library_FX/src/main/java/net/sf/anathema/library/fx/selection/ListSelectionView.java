package net.sf.anathema.library.fx.selection;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

import net.sf.anathema.library.event.ObjectChangedListener;
import net.sf.anathema.library.fx.cell.ConfigurableListCellFactory;
import net.sf.anathema.library.fx.cell.ListCellFactory;
import net.sf.anathema.library.presenter.AgnosticUIConfiguration;
import net.sf.anathema.library.view.VetoableObjectSelectionView;
import net.sf.anathema.library.view.Vetor;

import org.jmock.example.announcer.Announcer;

import com.sun.javafx.collections.ObservableListWrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class ListSelectionView<T> implements VetoableObjectSelectionView<T>
{
	private final ListView<T> view = new ListView<> ();
	private final Announcer<ObjectChangedListener> announcer = new Announcer<> (ObjectChangedListener.class);
	
	@SuppressWarnings ("unchecked")
	public ListSelectionView ()
	{
		view.getSelectionModel ().setSelectionMode (SelectionMode.SINGLE);
		view.getSelectionModel ().selectedItemProperty ().addListener (new ChangeListener<T> ()
		{
			@Override
			public void changed (ObservableValue<? extends T> observableValue, T t, T newValue)
			{
				announcer.announce ().valueChanged (newValue);
			}
		}
		);
	}
	
	@Override
	public void addSelectionVetor (Vetor vetor)
	{
		//To change body of implemented methods use File | Settings | File Templates.
	}
	
	@Override
	public void removeSelectionVetor (Vetor vetor)
	{
		//To change body of implemented methods use File | Settings | File Templates.
	}
	
	@Override
	public void setCellRenderer (AgnosticUIConfiguration<T> renderer)
	{
		view.setCellFactory (new ConfigurableListCellFactory<> (renderer));
	}
	
	public void setCellRenderer (ListCellFactory<T> renderer)
	{
		view.setCellFactory (renderer);
	}
	
	@Override
	public void setSelectedObject (T object)
	{
		view.getSelectionModel ().select (object);
	}
	
	@Override
	public void addObjectSelectionChangedListener (ObjectChangedListener<T> listener)
	{
		announcer.addListener (listener);
	}
	
	@Override
	public void removeObjectSelectionChangedListener (ObjectChangedListener<T> listener)
	{
		announcer.removeListener (listener);
	}
	
	@Override
	public void setObjects (final T[] objects)
	{
		view.setItems (new ObservableListWrapper<> (Arrays.asList (objects)));
	}
	
	@Override
	public void setObjects (Collection<T> objects)
	{
		ArrayList<T> list = new ArrayList<> (objects);
		view.setItems (new ObservableListWrapper<> (list));
	}
	
	@Override
	public T getSelectedObject ()
	{
		return view.getSelectionModel ().getSelectedItem ();
	}
	
	@Override
	public void setEnabled (boolean enabled)
	{
		view.setDisable (!enabled);
	}
	
	@Override
	public void clearSelection ()
	{
		view.getSelectionModel ().clearSelection ();
	}
	
	public Node getNode ()
	{
		return view;
	}
}
