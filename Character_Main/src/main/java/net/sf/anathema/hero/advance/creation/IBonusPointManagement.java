package net.sf.anathema.hero.advance.creation;

import net.sf.anathema.hero.points.display.overview.SpendingModel;

public interface IBonusPointManagement {

  void recalculate();

  SpendingModel getTotalModel();
}