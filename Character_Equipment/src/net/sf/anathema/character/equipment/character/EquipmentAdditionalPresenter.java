package net.sf.anathema.character.equipment.character;

import java.awt.Component;

import net.disy.commons.swing.action.SmartAction;
import net.sf.anathema.character.equipment.character.model.IEquipmentItem;
import net.sf.anathema.character.equipment.character.model.IEquipmentObjectCollection;
import net.sf.anathema.character.equipment.character.model.IEquipmentTemplate;
import net.sf.anathema.character.equipment.character.view.IEquipmentAdditionalView;
import net.sf.anathema.character.equipment.character.view.IEquipmentObjectView;
import net.sf.anathema.lib.control.collection.CollectionAdapter;
import net.sf.anathema.lib.control.objectvalue.IObjectValueChangedListener;
import net.sf.anathema.lib.gui.IPresenter;
import net.sf.anathema.lib.gui.selection.IListObjectSelectionView;
import net.sf.anathema.lib.resources.IResources;

public class EquipmentAdditionalPresenter implements IPresenter {

  private final IResources resources;
  private final IEquipmentObjectCollection model;
  private final IEquipmentAdditionalView view;

  public EquipmentAdditionalPresenter(
      IResources resources,
      IEquipmentObjectCollection model,
      IEquipmentAdditionalView view) {
    this.resources = resources;
    this.model = model;
    this.view = view;
  }

  public void initPresentation() {
    final IListObjectSelectionView<IEquipmentTemplate> equipmentTemplatePickList = view.getEquipmentTemplatePickList();
    equipmentTemplatePickList.setCellRenderer(new EquipmentObjectCellRenderer());
    equipmentTemplatePickList.setObjects(model.getAvailableTemplates());
    final SmartAction addAction = new SmartAction("Add") {
      @Override
      protected void execute(Component parentComponent) {
        IEquipmentTemplate selectedObject = equipmentTemplatePickList.getSelectedObject();
        model.addEquipmentObject(selectedObject);
      }
    };
    equipmentTemplatePickList.addObjectSelectionChangedListener(new IObjectValueChangedListener<IEquipmentTemplate>() {
      public void valueChanged(IEquipmentTemplate newValue) {
        addAction.setEnabled(view.getEquipmentTemplatePickList().isObjectSelected());
      }
    });
    model.addEquipmentObjectListener(new CollectionAdapter<IEquipmentItem>() {
      @Override
      public void itemAdded(IEquipmentItem item) {
        initEquipmentObjectPresentation(item);
      }
    });
    addAction.setEnabled(view.getEquipmentTemplatePickList().isObjectSelected());
    view.setSelectButtonAction(addAction);
  }

  private void initEquipmentObjectPresentation(IEquipmentItem selectedObject) {
    IEquipmentObjectView objectView = view.addEquipmentObjectView();
    IEquipmentStringBuilder resourceBuilder = new EquipmentStringBuilder(resources);
    new EquipmentObjectPresenter(selectedObject, objectView, resourceBuilder).initPresentation();
  }
}