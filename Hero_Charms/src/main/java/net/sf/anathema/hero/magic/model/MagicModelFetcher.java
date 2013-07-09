package net.sf.anathema.hero.magic.model;

import net.sf.anathema.hero.model.Hero;

public class MagicModelFetcher {

  public static MagicModel fetch(Hero hero) {
    return hero.getModel(MagicModel.ID);
  }
}
