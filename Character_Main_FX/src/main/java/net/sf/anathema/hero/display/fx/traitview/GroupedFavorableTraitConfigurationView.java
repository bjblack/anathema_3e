package net.sf.anathema.hero.display.fx.traitview;

import net.sf.anathema.hero.display.ExtensibleTraitView;
import net.sf.anathema.hero.framework.display.ColumnCount;

public interface GroupedFavorableTraitConfigurationView {

  void initGui(ColumnCount columnCount);

  void startNewTraitGroup(String groupLabel);

  ExtensibleTraitView addExtensibleTraitView(String string, int maximalValue);
}