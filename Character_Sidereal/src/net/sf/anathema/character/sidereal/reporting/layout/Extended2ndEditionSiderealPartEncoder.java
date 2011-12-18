package net.sf.anathema.character.sidereal.reporting.layout;

import net.sf.anathema.character.reporting.pdf.rendering.general.box.IPdfContentBoxEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.page.IPdfPageEncoder;
import net.sf.anathema.character.reporting.pdf.layout.extended.AbstractSecondEditionExaltPdfPartEncoder;
import net.sf.anathema.character.reporting.pdf.layout.extended.ExtendedEncodingRegistry;
import net.sf.anathema.character.reporting.pdf.rendering.page.PdfPageConfiguration;
import net.sf.anathema.character.sidereal.reporting.rendering.anima.SiderealAnimaEncoderFactory;
import net.sf.anathema.character.sidereal.reporting.rendering.SiderealFlawedFateEncoder;
import net.sf.anathema.lib.resources.IResources;

public class Extended2ndEditionSiderealPartEncoder extends AbstractSecondEditionExaltPdfPartEncoder {

  public Extended2ndEditionSiderealPartEncoder(IResources resources, ExtendedEncodingRegistry registry, int essenceMax) {
    super(resources, registry, essenceMax);

  }

  public IPdfContentBoxEncoder getGreatCurseEncoder() {
      return new SiderealFlawedFateEncoder(getBaseFont(), getResources());
  }

  @Override
  public IPdfPageEncoder[] getAdditionalPages(PdfPageConfiguration configuration) {
    return new IPdfPageEncoder[] { new Extended2ndEditionSiderealDetailsPageEncoder(
        getResources(),
        getEssenceMax(),
        getBaseFont(),
        getSymbolBaseFont(),
        getFontSize(),
        configuration) };
  }

  @Override
  public IPdfContentBoxEncoder getAnimaEncoder() {
    return new SiderealAnimaEncoderFactory(getResources(), getBaseFont(), getBaseFont()).createAnimaEncoder();
  }
}
