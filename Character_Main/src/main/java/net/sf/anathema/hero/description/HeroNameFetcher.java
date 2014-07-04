package net.sf.anathema.hero.description;

import net.sf.anathema.hero.application.item.HeroItem;
import net.sf.anathema.hero.model.Hero;
import net.sf.anathema.lib.workflow.textualdescription.ITextualDescription;

public class HeroNameFetcher {

  public String getName(Hero hero) {
    HeroDescription heroDescription = HeroDescriptionFetcher.fetch(hero);
    if (heroDescription == HeroDescriptionFetcher.NOT_REGISTERED){
      return HeroItem.DEFAULT_PRINT_NAME;
    }
    ITextualDescription nameDescription = heroDescription.getName();
    if (nameDescription.isEmpty()){
      return HeroItem.DEFAULT_PRINT_NAME;
    }
    return nameDescription.getText();
  }
}