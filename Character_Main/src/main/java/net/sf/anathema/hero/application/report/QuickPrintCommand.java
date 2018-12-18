package net.sf.anathema.hero.application.report;

import net.sf.anathema.hero.environment.report.Report;
import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.library.interaction.model.Command;
import net.sf.anathema.platform.environment.Environment;

public class QuickPrintCommand implements Command
{
	private final Environment environment;
	private final DefaultReportFinder finder;
	private final Hero hero;
	
	public QuickPrintCommand (Environment environment, DefaultReportFinder finder, Hero hero)
	{
		this.environment = environment;
		this.finder = finder;
		this.hero = hero;
	}
	
	@Override
	public void execute ()
	{
		Report report = finder.getDefaultReport (hero);
		if (report == null)
		{
			return;
		}
		QuickFileChooser fileChooser = new QuickFileChooser (environment, hero);
		new PrintCommand (environment, report, fileChooser, hero).execute ();
	}
}
