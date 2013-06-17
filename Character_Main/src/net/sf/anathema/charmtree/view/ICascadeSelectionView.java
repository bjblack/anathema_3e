package net.sf.anathema.charmtree.view;

import net.sf.anathema.lib.control.ObjectValueListener;
import net.sf.anathema.lib.gui.AgnosticUIConfiguration;
import net.sf.anathema.lib.gui.action.SmartAction;
import net.sf.anathema.lib.util.Identifier;
import net.sf.anathema.platform.tree.presenter.view.CascadeLoadedListener;
import net.sf.anathema.platform.tree.presenter.view.NodeProperties;
import net.sf.anathema.platform.tree.presenter.view.ToolTipProperties;

import javax.swing.JComponent;

public interface ICascadeSelectionView {

  CharmTreeRenderer getCharmTreeRenderer();

  void addCascadeLoadedListener(CascadeLoadedListener listener);

  void addCharmTypeSelector(String title, Identifier[] types, AgnosticUIConfiguration uiConfig);

  void addCharmTypeSelectionListener(ObjectValueListener<Identifier> selectionListener);

  //todo: Action
  void addCharmFilterButton(SmartAction action, String titleText);

  void fillCharmGroupBox(Identifier[] charmGroups);

  void fillCharmTypeBox(Identifier[] cascadeTypes);

  void addCharmGroupSelector(String title, AgnosticUIConfiguration uiConfig, ICharmGroupChangeListener selectionListener, Identifier[] allPotentialGroups);

  void addCharmCascadeHelp(String helpText);

  //todo: Component
  JComponent getCharmComponent();

  void initGui(ToolTipProperties treeProperties, NodeProperties properties);

  void whenCursorLeavesCharmAreaResetAllPopups();
}