package net.sf.anathema.character.equipment.character.view;

import javax.swing.Action;

import net.sf.anathema.character.equipment.character.model.IEquipmentTemplate;
import net.sf.anathema.framework.presenter.view.ISimpleTabView;
import net.sf.anathema.lib.gui.selection.IListObjectSelectionView;

public interface IEquipmentAdditionalView extends ISimpleTabView {

  public IListObjectSelectionView<IEquipmentTemplate> getEquipmentTemplatePickList();

  public IEquipmentObjectView addEquipmentObjectView();

  public void removeEquipmentObjectView(IEquipmentObjectView objectView);

  public void setSelectButtonAction(Action action);
}