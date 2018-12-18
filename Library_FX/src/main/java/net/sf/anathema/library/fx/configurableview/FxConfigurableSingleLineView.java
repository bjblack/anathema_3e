package net.sf.anathema.library.fx.configurableview;

import javafx.scene.Node;

import net.miginfocom.layout.CC;
import net.sf.anathema.library.fx.selection.ComboBoxSelectionView;
import net.sf.anathema.library.fx.selection.FxObjectSelectionView;
import net.sf.anathema.library.fx.text.FxTextView;
import net.sf.anathema.library.fx.tool.FxButtonTool;
import net.sf.anathema.library.interaction.model.Tool;
import net.sf.anathema.library.presenter.AgnosticUIConfiguration;
import net.sf.anathema.library.text.ITextView;
import net.sf.anathema.library.view.ObjectSelectionView;

import org.tbee.javafx.scene.layout.MigPane;

import static net.sf.anathema.library.fx.layout.LayoutUtils.withoutInsets;

public class FxConfigurableSingleLineView
{
	private MigPane pane = new MigPane (withoutInsets ());
	
	public ITextView addLineView (String labelText)
	{
		FxTextView view = FxTextView.SingleLine (labelText);
		pane.add (view.getNode (), new CC ().growX ());
		return view;
	}
	
	public Tool addEditAction ()
	{
		FxButtonTool interaction = FxButtonTool.ForToolbar ();
		pane.add (interaction.getNode (), new CC ().growX ().alignY ("center"));
		return interaction;
	}
	
	public <T> ObjectSelectionView<T> addSelectionView (String label, AgnosticUIConfiguration<T> uiConfiguration)
	{
		FxObjectSelectionView<T> selectionView = new ComboBoxSelectionView<> (label, uiConfiguration);
		pane.add (selectionView.getNode (), new CC ().growX ());
		return selectionView;
	}
	
	public Node getNode ()
	{
		return pane;
	}
}
