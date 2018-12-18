package net.sf.anathema.hero.intimacies.display;

import javafx.scene.Node;

import net.sf.anathema.library.event.ObjectChangedListener;
import net.sf.anathema.library.fx.configurableview.FxConfigurableSingleLineView;
import net.sf.anathema.library.interaction.model.Tool;
import net.sf.anathema.library.presenter.AgnosticUIConfiguration;
import net.sf.anathema.library.text.ITextView;
import net.sf.anathema.library.view.ObjectSelectionView;

public class FxIntimacyEntryView implements IntimacyEntryView
{
	private final FxConfigurableSingleLineView view = new FxConfigurableSingleLineView ();
	private final ITextView textView;
	
	public FxIntimacyEntryView (String labelText)
	{
		this.textView = view.addLineView (labelText);
	}
	
	@Override
	public void addTextChangeListener (ObjectChangedListener<String> listener)
	{
		textView.addTextChangedListener (listener);
	}
	
	public Tool addTool ()
	{
		return view.addEditAction ();
	}
	
	@Override
	public void clear ()
	{
		textView.setText (null);
	}
	
	@Override
	public <T> ObjectSelectionView<T> addSelection (AgnosticUIConfiguration<T> uiConfiguration)
	{
		return view.addSelectionView ("", uiConfiguration);
	}
	
	public Node getNode ()
	{
		return view.getNode ();
	}
}
