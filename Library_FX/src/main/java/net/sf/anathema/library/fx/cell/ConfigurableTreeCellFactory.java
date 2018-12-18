package net.sf.anathema.library.fx.cell;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.util.Callback;

import net.sf.anathema.library.presenter.AgnosticUIConfiguration;

public class ConfigurableTreeCellFactory<T> implements Callback<TreeView<T>, TreeCell<T>>
{
	private final AgnosticUIConfiguration<T> configuration;
	
	public ConfigurableTreeCellFactory (AgnosticUIConfiguration<T> configuration)
	{
		this.configuration = configuration;
	}
	
	@Override
	public TreeCell<T> call (TreeView<T> tTreeView)
	{
		return new UITreeCell<> (configuration);
	}
}
