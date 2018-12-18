package net.sf.anathema.hero.magic.display.magic;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

import net.miginfocom.layout.CC;
import net.sf.anathema.library.event.ChangeListener;
import net.sf.anathema.library.fx.cell.ConfigurableListCellFactory;
import net.sf.anathema.library.fx.tool.FxButtonTool;
import net.sf.anathema.library.interaction.model.Tool;

import org.tbee.javafx.scene.layout.MigPane;

import com.sun.javafx.collections.ObservableListWrapper;

import java.util.List;

import static net.sf.anathema.library.fx.layout.LayoutUtils.withoutInsets;

public class FxMagicLearnView implements MagicLearnView
{
	private final ListView availableMagicList = new ListView ();
	private final ListView learnedMagicList = new ListView ();
	private final MigPane centerButtons = new MigPane (withoutInsets ().wrapAfter (1));
	private final MigPane endButtons = new MigPane (withoutInsets ().wrapAfter (1));
	private final MigPane content = new MigPane (withoutInsets ().fill ());
	
	
	public FxMagicLearnView (MagicLearnProperties properties)
	{
		availableMagicList.setCellFactory (new ConfigurableListCellFactory (properties.getMagicRenderer ()));
		availableMagicList.getSelectionModel ().setSelectionMode (SelectionMode.SINGLE);
		learnedMagicList.setCellFactory (new ConfigurableListCellFactory (properties.getMagicRenderer ()));
		content.add (availableMagicList, new CC ().grow ().push ());
		content.add (centerButtons);
		content.add (learnedMagicList, new CC ().grow ().push ());
		content.add (endButtons);
	}
	
	@Override
	public void setAvailableMagic (final List magics)
	{
		availableMagicList.setItems (new ObservableListWrapper (magics));
	}
	
	@Override
	public void setLearnedMagic (List magics)
	{
		learnedMagicList.setItems (new ObservableListWrapper (magics));
	}
	
	@Override
	public Tool addMainTool ()
	{
		return addToolTo (centerButtons);
	}
	
	@Override
	public List getSelectedLearnedValues ()
	{
		return learnedMagicList.getSelectionModel ().getSelectedItems ();
	}
	
	@Override
	public List getSelectedAvailableValues ()
	{
		return availableMagicList.getSelectionModel ().getSelectedItems ();
	}
	
	@Override
	public void addAvailableMagicSelectedListener (final ChangeListener changeListener)
	{
		addChangeListener (changeListener, availableMagicList);
	}
	
	@Override
	public void addLearnedMagicSelectedListener (final ChangeListener changeListener)
	{
		addChangeListener (changeListener, learnedMagicList);
	}
	
	private void addChangeListener (final ChangeListener changeListener, ListView list)
	{
		list.getSelectionModel ().selectedItemProperty ().addListener ( (observableValue, o, o2) -> changeListener.changeOccurred ());
	}
	
	private Tool addToolTo (final MigPane target)
	{
		final FxButtonTool tool = FxButtonTool.ForAnyPurpose ();
		target.add (tool.getNode ());
		return tool;
	}
	
	public Node getNode ()
	{
		return content;
	}
}
