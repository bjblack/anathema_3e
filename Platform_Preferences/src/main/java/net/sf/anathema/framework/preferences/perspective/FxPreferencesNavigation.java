package net.sf.anathema.framework.preferences.perspective;

import javafx.scene.control.Button;

import net.sf.anathema.library.fx.NodeHolder;
import net.sf.anathema.library.initialization.InitializationException;
import net.sf.anathema.library.interaction.AcceleratorMap;
import net.sf.anathema.platform.fx.navigation.Navigation;
import net.sf.anathema.platform.preferences.PreferenceView;

import java.util.ArrayList;

public class FxPreferencesNavigation extends Navigation implements PreferencesNavigation
{
	private final ArrayList<PreferenceView> availableViews;
	private final FxPreferencesView preferencesView;
	
	public FxPreferencesNavigation (ArrayList<PreferenceView> preferenceViews, FxPreferencesView preferencesView, AcceleratorMap uiEnvironment)
	{
		super (uiEnvironment);
		this.availableViews = preferenceViews;
		this.preferencesView = preferencesView;
	}
	
	@Override
	public PreferenceView addSection (String title, Class viewClass)
	{
		for (PreferenceView view : availableViews)
		{
			if (viewClass.isInstance (view))
			{
				Button trigger = new Button (title);
				addElementToNavigation (trigger);
				trigger.setOnAction (actionEvent -> preferencesView.show (title, (NodeHolder) view));
				return view;
			}
		}
		throw new InitializationException ("Unsupported preference view: " + viewClass.getName ());
	}
}
