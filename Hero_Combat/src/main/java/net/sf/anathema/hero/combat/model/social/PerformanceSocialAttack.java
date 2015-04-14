package net.sf.anathema.hero.combat.model.social;

import net.sf.anathema.hero.traits.TraitTypeFinder;
import net.sf.anathema.hero.traits.model.TraitMap;
import net.sf.anathema.hero.traits.model.TraitType;
import net.sf.anathema.hero.traits.model.types.AbilityType;

public class PerformanceSocialAttack extends AbstractSocialAttack {

  public PerformanceSocialAttack(TraitMap collection) {
    super(collection);
  }

  @Override
  public int getRate() {
    return 1;
  }

  @Override
  public int getSpeed() {
    return 6;
  }

  @Override
  public TraitType getName() {
    return new TraitTypeFinder().getTrait("Performance");
  }
}