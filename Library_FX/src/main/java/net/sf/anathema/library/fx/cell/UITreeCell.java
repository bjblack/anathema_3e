package net.sf.anathema.library.fx.cell;

import javafx.scene.control.TreeCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import net.sf.anathema.library.fx.tooltip.ConfigurableFxTooltip;
import net.sf.anathema.library.presenter.AgnosticUIConfiguration;
import net.sf.anathema.library.resources.RelativePath;
import net.sf.anathema.library.resources.ResourceLoader;

import java.io.InputStream;

public class UITreeCell<T> extends TreeCell<T>
{
	private static final Image NO_IMAGE = null;
	private AgnosticUIConfiguration<T> configuration;
	
	public UITreeCell (AgnosticUIConfiguration<T> configuration)
	{
		this.configuration = configuration;
	}
	
	@Override
	public void updateItem (T item, boolean empty)
	{
		super.updateItem (item, empty);
		setText (configuration.getLabel (item));
		setTooltip (item);
		setImage (item);
	}
	
	private void setTooltip (T item)
	{
		ConfigurableFxTooltip configurableTooltip = new ConfigurableFxTooltip ();
		configuration.configureTooltip (item, configurableTooltip);
		configurableTooltip.configure (this);
	}
	
	private void setImage (T item)
	{
		Image image = loadImageForItem (item);
		setGraphic (new ImageView (image));
	}
	
	private Image loadImageForItem (T item)
	{
		RelativePath relativePath = configuration.getIconsRelativePath (item);
		if (relativePath == AgnosticUIConfiguration.NO_ICON)
		{
			return NO_IMAGE;
		}
		ResourceLoader resourceLoader = new ResourceLoader ();
		InputStream imageStream = resourceLoader.loadResource (relativePath);
		return new Image (imageStream, 16, 16, true, true);
	}
}
