package net.sf.anathema.hero.magic.sheet.content;

import net.sf.anathema.hero.magic.model.MagicModelFetcher;
import net.sf.anathema.hero.magic.sheet.content.mnemonic.MagicMnemonic;
import net.sf.anathema.hero.sheet.pdf.session.PageBreakChecker;
import net.sf.anathema.hero.sheet.pdf.session.ReportSession;
import net.sf.anathema.library.resources.Resources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AllMagicContent extends AbstractMagicContent
{
	private ReportSession session;
	
	public AllMagicContent (ReportSession session, Resources resources)
	{
		super (resources);
		this.session = session;
		storeMnemonicIfNecessary (session);
		session.setPageBreakChecker (new AllMagicPageBreakChecker ());
	}
	
	@Override
	protected MagicMnemonic createMnemonic ()
	{
		List<IMagicStats> printMagic = collectPrintMagic ();
		Collections.sort (printMagic, (stats1, stats2) ->
		{
			int result = stats1.getGroupName (getResources ()).compareTo (stats2.getGroupName (getResources ()));
			if (result != 0)
			{
				return result;
			}
			return stats1.getNameString (getResources ()).compareTo (stats2.getNameString (getResources ()));
		}
		);
		return new AllMagicMnemonic (printMagic);
	}
	
	@Override
	protected boolean knowsMnemonic (ReportSession session)
	{
		return session.knowsMnemonic (AllMagicMnemonic.class);
	}
	
	@Override
	protected MagicMnemonic getMnemonic ()
	{
		return session.retrieveMnemonic (AllMagicMnemonic.class);
	}
	
	@Override
	public String getHeaderKey ()
	{
		return "Charms";
	}
	
	private List<IMagicStats> collectPrintMagic ()
	{
		List<IMagicStats> printStats = new ArrayList<> ();
		MagicModelFetcher.fetch (session.getHero ()).addPrintMagic (printStats);
		return printStats;
	}
	
	private class AllMagicPageBreakChecker implements PageBreakChecker
	{
		@Override
		public boolean isRequired ()
		{
			return hasUnprintedCharms ();
		}
	}
}
