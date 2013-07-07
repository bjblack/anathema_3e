package net.sf.anathema.character.main.presenter.magic.combo;

import net.sf.anathema.character.main.caste.CasteType;
import net.sf.anathema.character.main.magic.ICharm;
import net.sf.anathema.character.main.magic.description.MagicDescriptionProvider;
import net.sf.anathema.hero.charms.CharmsModel;
import net.sf.anathema.hero.charms.CharmsModelFetcher;
import net.sf.anathema.hero.combos.CombosModel;
import net.sf.anathema.hero.combos.CombosModelFetcher;
import net.sf.anathema.hero.concept.HeroConceptFetcher;
import net.sf.anathema.hero.experience.ExperienceModelFetcher;
import net.sf.anathema.character.main.magic.charms.options.DefaultCharmTemplateRetriever;
import net.sf.anathema.hero.model.Hero;

public class ComboConfigurationModel {

  private final Hero hero;
  private final MagicDescriptionProvider magicDescriptionProvider;

  public ComboConfigurationModel(Hero hero, MagicDescriptionProvider magicDescriptionProvider) {
    this.hero = hero;
    this.magicDescriptionProvider = magicDescriptionProvider;
  }

  public boolean isAlienCharmsAllowed() {
    CasteType caste = HeroConceptFetcher.fetch(hero).getCaste().getType();
    return DefaultCharmTemplateRetriever.getNativeTemplate(hero).isAllowedAlienCharms(caste);
  }

  public CharmsModel getCharmConfiguration() {
    return CharmsModelFetcher.fetch(hero);
  }

  public CombosModel getCombos() {
    return CombosModelFetcher.fetch(hero);
  }

  public MagicDescriptionProvider getMagicDescriptionProvider() {
    return magicDescriptionProvider;
  }

  public ICharm[] getLearnedCharms() {
    return CharmsModelFetcher.fetch(hero).getLearnedCharms(isExperienced());
  }

  public boolean isExperienced() {
    return ExperienceModelFetcher.fetch(hero).isExperienced();
  }
}
