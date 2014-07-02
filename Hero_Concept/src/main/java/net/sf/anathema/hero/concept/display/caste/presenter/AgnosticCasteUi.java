package net.sf.anathema.hero.concept.display.caste.presenter;

import net.sf.anathema.framework.environment.Resources;
import net.sf.anathema.hero.concept.CasteType;
import net.sf.anathema.hero.template.presentation.IPresentationProperties;
import net.sf.anathema.lib.file.RelativePath;
import net.sf.anathema.points.display.overview.presenter.SelectObjectConfiguration;

public class AgnosticCasteUi extends SelectObjectConfiguration<CasteType> {
  private final CasteUI casteUI;

  public AgnosticCasteUi(Resources resources, IPresentationProperties properties) {
    super(resources, (resources1, object) -> {
      String key = "Caste." + object.getId();
      return resources1.getString(key);
    });
    this.casteUI = new CasteUI(properties);
  }

  @Override
  protected RelativePath getIconForObject(CasteType value) {
    return casteUI.getSmallCasteIconPath(value);
  }
}