package net.sf.anathema.character.reporting.sheet.util.statstable;

import net.sf.anathema.character.generic.util.IStats;
import net.sf.anathema.lib.resources.IResources;

import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPTable;

public abstract class AbstractNameStatsGroup<T extends IStats> extends AbstractTextStatsGroup<T> {
  private final String title;
  private final IResources resources;

  public AbstractNameStatsGroup(IResources resources) {
    this.resources = resources;
    this.title = resources.getString(getHeaderResourceKey());
  }

  public final String getTitle() {
    return title;
  }

  public Float[] getColumnWeights() {
    return new Float[] { new Float(6) };
  }

  public final void addContent(PdfPTable table, Font font, T stats) {
    if (stats == null) {
      table.addCell(createTextCell(font, "")); //$NON-NLS-1$
    }
    else {
      String resourceKey = getResourceBase() + stats.getName().getId();
      table.addCell(createTextCell(font, resources.getString(resourceKey)));
    }
  }

  protected abstract String getHeaderResourceKey();

  protected abstract String getResourceBase();
}