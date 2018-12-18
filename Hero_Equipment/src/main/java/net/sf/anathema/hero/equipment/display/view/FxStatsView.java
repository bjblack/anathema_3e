package net.sf.anathema.hero.equipment.display.view;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;

import net.sf.anathema.hero.equipment.display.presenter.StatsView;
import net.sf.anathema.library.event.ChangeListener;
import net.sf.anathema.library.fx.layout.LayoutUtils;
import net.sf.anathema.library.model.BooleanModel;

import org.tbee.javafx.scene.layout.MigPane;

public class FxStatsView implements StatsView
{
	private final MigPane panel = new MigPane (LayoutUtils.withoutInsets ().wrapAfter (1).gridGapY ("0"));
	private final CheckBox box;
	private final BooleanModel isSelectedModel;
	
	public FxStatsView (String description)
	{
		this.isSelectedModel = new BooleanModel ();
		this.box = createCheckBox (isSelectedModel, description);
		panel.add (box);
	}
	
	@Override
	public void setSelected (boolean selected)
	{
		isSelectedModel.setValue (selected);
	}
	
	@Override
	public boolean getSelected ()
	{
		return isSelectedModel.getValue ();
	}
	
	@Override
	public void disable ()
	{
		box.disableProperty ().setValue (true);
	}
	
	@Override
	public void addChangeListener (ChangeListener changeListener)
	{
		isSelectedModel.addChangeListener (changeListener);
	}
	
	@Override
	public StatsView addOptionFlag (String label)
	{
		FxStatsView checkBoxStatsView = new FxStatsView ("   ..." + label);
		panel.add (checkBoxStatsView.getNode ());
		return checkBoxStatsView;
	}
	
	private CheckBox createCheckBox (final BooleanModel selectedModel, String description)
	{
		final CheckBox checkBox = new CheckBox (description);
		selectedModel.addChangeListener ( () -> checkBox.setSelected (selectedModel.getValue ()));
		checkBox.selectedProperty ().addListener ( (observableValue, aBoolean, newValue) -> selectedModel.setValue (newValue));
		return checkBox;
	}
	
	public Node getNode ()
	{
		return panel;
	}
}
