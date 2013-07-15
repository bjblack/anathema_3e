package net.sf.anathema.character.main.magic.model.charm.special;

import net.sf.anathema.character.main.magic.model.charm.Charm;
import net.sf.anathema.character.main.magic.model.charm.CharmSpecialist;
import net.sf.anathema.character.main.magic.model.charmtree.ICharmLearnableArbitrator;
import net.sf.anathema.lib.data.Condition;

import java.util.Map;

public class ComplexMultipleEffectCharm extends MultipleEffectCharm {
  Map<String, String> prereqEffectMap;

  public ComplexMultipleEffectCharm(String charmId, String[] effectIds, Map<String, String> prereqEffect) {
    super(charmId, effectIds);
    prereqEffectMap = prereqEffect;
  }

  @Override
  public SubEffects buildSubEffects(CharmSpecialist specialist, ICharmLearnableArbitrator arbitrator, Charm charm) {
    CollectionSubEffects subEffects = new CollectionSubEffects();
    for (String id : effectIds) {
      String prerequisiteEffect = prereqEffectMap.get(id);
      Condition condition = buildLearnCondition(arbitrator, charm, prerequisiteEffect, subEffects);
      subEffects.add(new SubEffectImpl(id, specialist.getExperience(), condition));
    }
    return subEffects;
  }

  private Condition buildLearnCondition(ICharmLearnableArbitrator arbitrator, Charm charm, String prereqEffect, CollectionSubEffects subEffects) {
    return new PrerequisiteLearnCondition(subEffects, arbitrator, charm, prereqEffect);
  }
}