package net.sf.anathema.platform.fx.initialization;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.sf.anathema.library.fx.tool.ImageContainer;
import net.sf.anathema.library.fx.tool.LoadImage;
import net.sf.anathema.library.fx.view.ViewFactory;
import net.sf.anathema.platform.frame.AnathemaViewProperties;
import net.sf.anathema.platform.frame.ApplicationFrame;
import net.sf.anathema.platform.frame.ApplicationFrameView;
import net.sf.anathema.platform.fx.menu.MainMenuBar;
import net.sf.anathema.platform.fx.messaging.PopInStatusBar;
import net.sf.anathema.platform.messaging.StatusBar;

import org.controlsfx.control.NotificationPane;
import org.tbee.javafx.scene.layout.MigPane;

public class FxApplicationFrame implements ApplicationFrameView
{
	private final Stage stage;
	private final AnathemaViewProperties properties;
	private final ViewFactory contentFactory;
	private final MainMenuBar menu;
	private final NotificationPane notificationPane = new NotificationPane ();
	private final PopInStatusBar statusBar = new PopInStatusBar (notificationPane);
	
	public FxApplicationFrame (Stage stage, AnathemaViewProperties viewProperties, ViewFactory factory)
	{
		this.stage = stage;
		this.properties = viewProperties;
		this.contentFactory = factory;
		this.menu = new MainMenuBar (properties.getMainMenuName (), properties.getHelpMenuName ());
		Parent contentPane = createContentPane ();
		notificationPane.setContent (contentPane);
		Scene scene = new Scene (notificationPane);
		scene.getStylesheets ().add ("skin/platform/anathema.css");
		stage.setScene (scene);
	}
	
	public void show ()
	{
		stage.setMaximized (true);
		stage.setTitle (properties.getDefaultFrameTitle ());
		stage.getIcons ().add (getFrameIcon ());
		stage.show ();
	}
	
	private Image getFrameIcon ()
	{
		ImageView imageView = new ImageView ();
		ImageContainer container = new LoadImage (properties.getFrameIcon ()).run ();
		container.displayIn (imageView);
		return imageView.getImage ();
	}
	
	@Override
	public ApplicationFrame getWindow ()
	{
		return this;
	}
	
	public MainMenuBar getMenuBar ()
	{
		return menu;
	}
	
	public StatusBar getStatusBar ()
	{
		return statusBar;
	}
	
	
	private Parent createContentPane ()
	{
		MigPane contentPane = new MigPane (new LC ().fill ().wrapAfter (1));
		contentPane.add (menu.getMenuBar (), new CC ().dockNorth ());
		contentPane.add (contentFactory.createContent (), new CC ().grow ().push ());
		return contentPane;
	}
}
