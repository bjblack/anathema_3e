package net.sf.anathema.hero.application.report;

import net.sf.anathema.hero.concept.model.description.HeroNameFetcher;
import net.sf.anathema.hero.environment.report.Report;
import net.sf.anathema.hero.environment.report.ReportException;
import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.library.interaction.model.Command;
import net.sf.anathema.platform.environment.DesktopEnvironment;
import net.sf.anathema.platform.environment.Environment;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PrintCommand implements Command
{
	public static final String PDF_EXTENSION = ".pdf";
	private final Environment environment;
	private final Report report;
	private final FileChooser fileChooser;
	private final Hero hero;
	
	public PrintCommand (Environment environment, Report report, FileChooser fileChooser, Hero hero)
	{
		this.environment = environment;
		this.report = report;
		this.fileChooser = fileChooser;
		this.hero = hero;
	}
	
	@Override
	public void execute ()
	{
		try
		{
			Path selectedFile = fileChooser.getPrintFile ();
			if (selectedFile == null)
			{
				return;
			}
			performPrint (report, selectedFile, hero);
			openFile (selectedFile);
		}
		catch (FileNotFoundException e)
		{
			handleAlreadyOpenException (e);
		}
		catch (AccessDeniedException e)
		{
			handleForbiddenLocation (e);
		}
		catch (IOException e)
		{
			handleFailedToOpenException (e);
		}
		catch (Exception e)
		{
			handleGeneralException (e);
		}
	}
	
	private void showMessage (Throwable e, String errorMessageKey)
	{
		String errorMessage = environment.getString (errorMessageKey);
		environment.handle (e, errorMessage);
	}
	
	private void performPrint (Report selectedReport, Path selectedFile, Hero hero) throws IOException, ReportException
	{
		try (OutputStream stream = Files.newOutputStream (selectedFile))
		{
			String name = new HeroNameFetcher ().getName (hero);
			selectedReport.print (name, hero, stream);
		}
	}
	
	private void openFile (Path selectedFile) throws IOException
	{
		if (DesktopEnvironment.isAutoOpenSupported ())
		{
			DesktopEnvironment.openOnDesktop (selectedFile);
		}
	}
	
	private void handleForbiddenLocation (AccessDeniedException e)
	{
		showMessage (e, "Anathema.Reporting.Message.PrintError.ForbiddenLocation");
	}
	
	private void handleAlreadyOpenException (FileNotFoundException e)
	{
		showMessage (e, "Anathema.Reporting.Message.PrintError.FileOpen");
	}
	
	private void handleGeneralException (Exception e)
	{
		showMessage (e, "Anathema.Reporting.Message.PrintError");
	}
	
	private void handleFailedToOpenException (IOException e)
	{
		showMessage (e, "Anathema.Reporting.Message.NoApplication");
	}
}
