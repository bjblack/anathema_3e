package net.sf.anathema.namegenerator.presenter.view;

import javafx.scene.Node;

import net.sf.anathema.library.event.ChangeListener;
import net.sf.anathema.library.interaction.model.Command;

public interface NameGeneratorView
{
	void addNameGeneratorType (String label, Object type);
	
	void setResult (String result);
	
	Object getSelectedGeneratorType ();
	
	void addGeneratorTypeChangeListener (ChangeListener listener);
	
	void setSelectedGeneratorType (Object generatorType);
	
	void addGenerationAction (String label, Command command);
	
	Node getNode ();
}
