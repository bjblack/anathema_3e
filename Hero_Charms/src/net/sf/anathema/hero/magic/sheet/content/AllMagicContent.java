package net.sf.anathema.hero.magic.sheet.content;

import net.sf.anathema.character.main.magic.model.magic.IMagic;
import net.sf.anathema.character.main.magic.model.magic.IMagicStats;
import net.sf.anathema.character.main.magic.model.magic.IMagicVisitor;
import net.sf.anathema.hero.sheet.pdf.session.PageBreakChecker;
import net.sf.anathema.hero.sheet.pdf.session.ReportSession;
import net.sf.anathema.hero.magic.sheet.content.mnemonic.CharmsAndSorceryMnemonic;
import net.sf.anathema.hero.magic.sheet.content.mnemonic.MagicMnemonic;
import net.sf.anathema.hero.magic.sheet.content.stats.MagicStatsFactoryVisitor;
import net.sf.anathema.lib.resources.Resources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AllMagicContent extends AbstractMagicContent {

  private final MagicContentHelper helper;
  private ReportSession session;

  public AllMagicContent(ReportSession session, Resources resources) {
    super(resources);
    this.helper = new MagicContentHelper(session.getHero());
    this.session = session;
    storeMnemonicIfNecessary(session);
    session.setPageBreakChecker(new AllMagicPageBreakChecker());
  }

  @Override
  protected MagicMnemonic createMnemonic() {
    List<IMagicStats> printMagic = collectPrintMagic();
    Collections.sort(printMagic);
    return new CharmsAndSorceryMnemonic(printMagic);
  }

  @Override
  protected boolean knowsMnemonic(ReportSession session) {
    return session.knowsMnemonic(CharmsAndSorceryMnemonic.class);
  }

  @Override
  protected MagicMnemonic getMnemonic() {
    return session.retrieveMnemonic(CharmsAndSorceryMnemonic.class);
  }

  @Override
  public String getHeaderKey() {
    return "Charms";
  }

  private List<IMagicStats> collectPrintMagic() {
    List<IMagicStats> printStats = new ArrayList<>();
    addGenericCharmsForPrint(printStats);
    addConcreteLearnedMagicForPrint(printStats);
    return printStats;
  }

  private void addGenericCharmsForPrint(List<IMagicStats> printStats) {
    for (IMagicStats stats : helper.getGenericCharmStats()) {
      if (helper.shouldShowCharm(stats)) {
        printStats.add(stats);
      }
    }
  }

  private void addConcreteLearnedMagicForPrint(List<IMagicStats> printStats) {
    IMagicVisitor statsCollector = new MagicStatsFactoryVisitor(session.getHero(), printStats);
    for (IMagic magic : helper.getAllLearnedMagic()) {
      magic.accept(statsCollector);
    }
  }

  private class AllMagicPageBreakChecker implements PageBreakChecker {
    @Override
    public boolean isRequired() {
      return hasUnprintedCharms();
    }
  }
}

