package net.sf.anathema.hero.equipment.display.view;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import net.sf.anathema.equipment.character.IEquipmentItem;
import net.sf.anathema.hero.equipment.display.presenter.EquipmentItemRenderer;
import net.sf.anathema.library.fx.cell.ListCellFactory;


public class EquipmentListCellFactory implements ListCellFactory<IEquipmentItem>
{
	private EquipmentItemRenderer renderer;
	
	public EquipmentListCellFactory (EquipmentItemRenderer renderer)
	{
		this.renderer = renderer;
	}
	
	@Override
	public ListCell<IEquipmentItem> call (ListView<IEquipmentItem> iEquipmentItemListView)
	{
		return new EquipmentCell (renderer);
	}
}
