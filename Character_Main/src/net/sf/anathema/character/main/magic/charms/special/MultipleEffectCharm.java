package net.sf.anathema.character.main.magic.charms.special;

import com.google.common.base.Objects;
import net.sf.anathema.character.main.magic.ICharm;
import net.sf.anathema.character.main.magic.charms.ICharmLearnableArbitrator;
import net.sf.anathema.character.main.magic.charms.CharmSpecialist;
import net.sf.anathema.lib.data.Condition;

import java.util.ArrayList;
import java.util.List;

public class MultipleEffectCharm implements IMultipleEffectCharm {

  private final String charmId;
  protected final String[] effectIds;

  public MultipleEffectCharm(String charmId, String[] effectIds) {
    this.charmId = charmId;
    this.effectIds = effectIds;
  }

  @Override
  public void accept(ISpecialCharmVisitor visitor) {
    visitor.visitMultipleEffectCharm(this);
  }

  @Override
  public String getCharmId() {
    return charmId;
  }

  @Override
  public SubEffects buildSubeffects(CharmSpecialist specialist, ICharmLearnableArbitrator arbitrator, ICharm charm) {
    List<ISubeffect> effectList = new ArrayList<>();
    for (String id : effectIds) {
      effectList.add(new Subeffect(id, specialist.getExperience(), buildLearnCondition(arbitrator, charm)));
    }
    return new ArraySubEffects(effectList.toArray(new ISubeffect[effectList.size()]));
  }

  private Condition buildLearnCondition(ICharmLearnableArbitrator arbitrator, ICharm charm) {
    return new ArbitratorLearnCondition(arbitrator, charm);
  }

  public String toString() {
    StringBuilder list = new StringBuilder();
    for (String effect : effectIds) {
      boolean isLastEffect = Objects.equal(effect, effectIds[effectIds.length - 1]);
      list.append(effect);
      list.append(isLastEffect ? "" : ",");
    }
    return "[" + getCharmId() + ";" + list + "]";
  }
}