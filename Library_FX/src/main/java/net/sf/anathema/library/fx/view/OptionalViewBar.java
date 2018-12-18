package net.sf.anathema.library.fx.view;

import javafx.scene.Node;
import javafx.scene.control.Button;

import net.miginfocom.layout.CC;
import net.sf.anathema.library.fx.layout.LayoutUtils;
import net.sf.anathema.library.view.OptionalView;

import org.tbee.javafx.scene.layout.MigPane;

import java.util.ArrayList;
import java.util.List;

public class OptionalViewBar
{
	private MigPane buttonBar = new MigPane (LayoutUtils.fillWithoutInsets ());
	private List<String> registeredIds = new ArrayList<> ();
	
	public void setView (String title, OptionalView view)
	{
		boolean isNewId = !registeredIds.contains (title);
		if (isNewId)
		{
			addButtonForTitle (title, view);
			registeredIds.add (title);
		}
	}
	
	private void addButtonForTitle (String title, final OptionalView view)
	{
		Button button = new Button ();
		button.setText (title);
		button.setOnAction (actionEvent -> view.toggle ());
		button.setRotate (90);
		button.translateYProperty ().bind (button.widthProperty ().divide (2));
		button.translateXProperty ().bind (button.heightProperty ());
		buttonBar.add (button, new CC ().alignY ("top"));
	}
	
	public Node getNode ()
	{
		return buttonBar;
	}
}
