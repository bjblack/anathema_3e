package net.sf.anathema.equipment.editor.view.fx;

import javafx.scene.Node;

import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.sf.anathema.equipment.editor.presenter.EquipmentStatsDialog;
import net.sf.anathema.equipment.editor.stats.view.fx.FxEditStatsDialog;
import net.sf.anathema.equipment.editor.view.EquipmentDescriptionPanel;
import net.sf.anathema.equipment.editor.view.EquipmentDetails;
import net.sf.anathema.equipment.editor.view.ToolListView;
import net.sf.anathema.equipment.stats.IEquipmentStats;
import net.sf.anathema.library.fx.layout.LayoutUtils;
import net.sf.anathema.library.fx.selection.SelectionViewFactory;
import net.sf.anathema.library.fx.view.StyledTitledPane;
import net.sf.anathema.library.presenter.AgnosticUIConfiguration;
import net.sf.anathema.platform.fx.environment.DialogFactory;

import org.tbee.javafx.scene.layout.MigPane;

public class FxEquipmentDetails implements EquipmentDetails
{
	private final FxToolListView<IEquipmentStats> listView = new FxToolListView<> ();
	private final FxEquipmentDescriptionPanel descriptionPanel;
	private MigPane outerPane = new MigPane (LayoutUtils.fillWithoutInsets ().wrapAfter (1), new AC ().grow ().fill ());
	private final DialogFactory dialogFactory;
	
	public FxEquipmentDetails (SelectionViewFactory selectionFactory, DialogFactory dialogFactory)
	{
		this.dialogFactory = dialogFactory;
		this.descriptionPanel = new FxEquipmentDescriptionPanel (selectionFactory);
	}
	
	public Node getNode ()
	{
		return outerPane;
	}
	
	@Override
	public ToolListView<IEquipmentStats> initStatsListView (final String title,
	AgnosticUIConfiguration<IEquipmentStats> configuration)
	{
		listView.setUiConfiguration (configuration);
		Node node = listView.getNode ();
		Node titledPane = StyledTitledPane.Create (title, node);
		outerPane.add (titledPane, new CC ().push ().grow ());
		return listView;
	}
	
	@Override
	public EquipmentDescriptionPanel addDescriptionPanel (final String title)
	{
		Node titledPane = StyledTitledPane.Create (title, descriptionPanel.getNode ());
		outerPane.add (titledPane, new CC ().grow ().push ());
		return descriptionPanel;
	}
	
	@Override
	public EquipmentStatsDialog createEquipmentStatsDialog ()
	{
		return new FxEditStatsDialog (dialogFactory);
	}
}
