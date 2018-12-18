package net.sf.anathema.hero.application.perspective;

import net.sf.anathema.hero.application.report.DefaultReportFinder;
import net.sf.anathema.hero.environment.HeroEnvironment;
import net.sf.anathema.hero.environment.report.RegisteredReportFactory;
import net.sf.anathema.hero.environment.report.Report;
import net.sf.anathema.hero.environment.report.ReportFactory;
import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.library.resources.Resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HeroReportFinder implements DefaultReportFinder
{
	private final Resources resources;
	private final List<Report> reports = new ArrayList<> ();
	
	public HeroReportFinder (HeroEnvironment environment)
	{
		Collection<ReportFactory> factories = environment.getObjectFactory ().instantiateOrdered (RegisteredReportFactory.class);
		for (ReportFactory factory : factories)
		{
			reports.addAll (factory.createReport (environment));
		}
		this.resources = environment.getResources ();
	}
	
	public Report getDefaultReport (Hero hero)
	{
		String reportName = resources.getString ("CharacterModule.Reporting.Sheet.Name");
		for (Report report : getAllReports (hero))
		{
			if (reportName.equals (report.toString ()))
			{
				return report;
			}
		}
		return null;
	}
	
	public List<Report> getAllReports (Hero hero)
	{
		List<Report> supportedReports = new ArrayList<> ();
		for (Report report : reports)
		{
			if (report.supports (hero))
			{
				supportedReports.add (report);
			}
		}
		return supportedReports;
	}
}
