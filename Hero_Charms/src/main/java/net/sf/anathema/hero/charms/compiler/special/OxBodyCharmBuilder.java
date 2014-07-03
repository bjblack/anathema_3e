package net.sf.anathema.hero.charms.compiler.special;

import net.sf.anathema.charm.data.reference.CharmName;
import net.sf.anathema.charm.template.special.OxBodyPick;
import net.sf.anathema.charm.template.special.OxBodyTechnique;
import net.sf.anathema.charm.template.special.SpecialCharmTemplate;
import net.sf.anathema.hero.charms.model.special.ISpecialCharm;
import net.sf.anathema.hero.charms.model.special.oxbody.OxBodyTechniqueCharm;
import net.sf.anathema.hero.traits.TraitTypeFinder;
import net.sf.anathema.hero.traits.model.TraitType;
import net.sf.anathema.hero.health.model.HealthLevelType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("UnusedDeclaration")
public class OxBodyCharmBuilder implements SpecialCharmBuilder {
  private static final String TAG_ZERO_HEALTH = "zeroHealthLevel";
  private static final String TAG_ONE_HEALTH = "oneHealthLevel";
  private static final String TAG_TWO_HEALTH = "twoHealthLevel";
  private static final String TAG_FOUR_HEALTH = "fourHealthLevel";
  private static final String TAG_INCAP_HEALTH = "incapHealthLevel";
  private static final String TAG_DYING_HEALTH = "dyingHealthLevel";

  private final TraitTypeFinder traitTypeFinder = new TraitTypeFinder();

  @Override
  public ISpecialCharm readCharm(SpecialCharmTemplate overallDto) {
    return createSpecialCharm(new CharmName(overallDto.charmId), overallDto.oxBodyTechnique);
  }

  private ISpecialCharm createSpecialCharm(CharmName id, OxBodyTechnique dto) {
    TraitType[] traitList = new TraitType[dto.traits.size()];
    for (int i = 0; i != traitList.length; i++) {
      traitList[i] = traitTypeFinder.getTrait(dto.traits.get(i));
    }

    LinkedHashMap<String, HealthLevelType[]> healthPicks = new LinkedHashMap<>();
    for (OxBodyPick pickDto : dto.picks) {
      String name = pickDto.id;
      List<HealthLevelType> healthLevels = new ArrayList<>();
      Map<String, HealthLevelType> healthTypeByString = getHealthTypeMap();
      for (String healthLevel : pickDto.healthLevels) {
        healthLevels.add(healthTypeByString.get(healthLevel));
       }
       healthPicks.put(name, healthLevels.toArray(new HealthLevelType[healthLevels.size()]));
    }
    return new OxBodyTechniqueCharm(id, traitList, healthPicks);
  }

  private Map<String, HealthLevelType> getHealthTypeMap() {
    Map<String, HealthLevelType> healthTypeByString = new HashMap<>();
    healthTypeByString.put(TAG_ZERO_HEALTH, HealthLevelType.ZERO);
    healthTypeByString.put(TAG_ONE_HEALTH, HealthLevelType.ONE);
    healthTypeByString.put(TAG_TWO_HEALTH, HealthLevelType.TWO);
    healthTypeByString.put(TAG_FOUR_HEALTH, HealthLevelType.FOUR);
    healthTypeByString.put(TAG_INCAP_HEALTH, HealthLevelType.INCAPACITATED);
    healthTypeByString.put(TAG_DYING_HEALTH, HealthLevelType.DYING);
    return healthTypeByString;
  }

  @Override
  public boolean supports(SpecialCharmTemplate overallDto) {
    return overallDto.oxBodyTechnique != null;
  }
}