package net.sf.anathema.character.abyssal.reporting.extended;

import net.sf.anathema.character.reporting.pdf.rendering.general.box.IPdfContentBoxEncoder;
import net.sf.anathema.character.reporting.pdf.layout.extended.AbstractFirstEditionExaltPdfPartEncoder;
import net.sf.anathema.character.reporting.pdf.layout.extended.ExtendedEncodingRegistry;
import net.sf.anathema.lib.resources.IResources;

public class FirstEditionAbyssalPartEncoder extends AbstractFirstEditionExaltPdfPartEncoder {

  public FirstEditionAbyssalPartEncoder(IResources resources, ExtendedEncodingRegistry registry, int essenceMax) {
    super(resources, registry, essenceMax);
  }

  public IPdfContentBoxEncoder getGreatCurseEncoder() {
    return new AbyssalResonanceEncoder(getBaseFont(), getSymbolBaseFont(), getResources());
  }

  @Override
  public IPdfContentBoxEncoder getAnimaEncoder() {
    return new AbyssalAnimaEncoderFactory(getResources(), getBaseFont(), getBaseFont()).createAnimaEncoder();
  }
}
