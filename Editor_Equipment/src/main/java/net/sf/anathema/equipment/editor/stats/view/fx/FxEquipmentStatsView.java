package net.sf.anathema.equipment.editor.stats.view.fx;

import javafx.scene.Node;
import javafx.scene.control.Label;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.sf.anathema.equipment.editor.presenter.EquipmentStatsView;
import net.sf.anathema.library.fx.configurableview.IIntegerSpinner;
import net.sf.anathema.library.fx.configurableview.IntegerSpinner;
import net.sf.anathema.library.fx.text.FxTextView;
import net.sf.anathema.library.fx.tool.FxCheckToggleTool;
import net.sf.anathema.library.interaction.model.ToggleTool;
import net.sf.anathema.library.text.ITextView;
import net.sf.anathema.library.view.BooleanView;

import org.tbee.javafx.scene.layout.MigPane;

public class FxEquipmentStatsView implements EquipmentStatsView
{
	private final MigPane panel = new MigPane (new LC ().wrapAfter (4).fill ().insets ("2"));
	
	public Node getNode ()
	{
		return panel;
	}
	
	public ITextView addLineTextView (String label)
	{
		FxTextView textView = FxTextView.SingleLine (label);
		panel.add (textView.getNode (), new CC ().spanX ().push ().grow ());
		return textView;
	}
	
	public ToggleTool addToggleTool ()
	{
		FxCheckToggleTool tool = FxCheckToggleTool.create ();
		panel.add (tool.getNode (), new CC ().spanX (2));
		return tool;
	}
	
	public BooleanView addBooleanSelector (String label)
	{
		FxBooleanView booleanView = new FxBooleanView ();
		panel.add (new Label (label));
		panel.add (booleanView.getNode (), new CC ().growX ().pushX ());
		return booleanView;
	}
	
	public IIntegerSpinner addIntegerSpinner (String label)
	{
		IntegerSpinner spinner = new IntegerSpinner (1);
		panel.add (new Label (label));
		panel.add (spinner.getNode (), new CC ().growX ().pushX ());
		return spinner;
	}
}
