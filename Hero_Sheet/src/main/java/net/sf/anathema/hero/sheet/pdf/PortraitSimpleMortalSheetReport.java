package net.sf.anathema.hero.sheet.pdf;

import net.sf.anathema.framework.reporting.pdf.AbstractPdfReport;
import net.sf.anathema.framework.reporting.pdf.PageSize;
import net.sf.anathema.hero.environment.HeroEnvironment;
import net.sf.anathema.hero.environment.report.ReportException;
import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.sheet.pdf.content.ReportContentRegistry;
import net.sf.anathema.hero.sheet.pdf.encoder.boxes.EncoderRegistry;
import net.sf.anathema.hero.sheet.pdf.encoder.graphics.SheetGraphics;
import net.sf.anathema.hero.sheet.pdf.page.PageConfiguration;
import net.sf.anathema.hero.sheet.pdf.page.PageEncoder;
import net.sf.anathema.hero.sheet.pdf.page.layout.Sheet;
import net.sf.anathema.hero.sheet.pdf.page.layout.simple.MortalPageEncoder;
import net.sf.anathema.hero.sheet.pdf.session.ReportSession;
import net.sf.anathema.hero.sheet.preferences.PageSizePreference;
import net.sf.anathema.library.resources.Resources;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

public class PortraitSimpleMortalSheetReport extends AbstractPdfReport
{
	private final Resources resources;
	private final PageSizePreference pageSizePreference;
	private HeroReportingRegistries reportingModuleObject;
	
	public PortraitSimpleMortalSheetReport (HeroEnvironment environment, PageSizePreference pageSizePreference)
	{
		this.resources = environment.getResources ();
		this.pageSizePreference = pageSizePreference;
		this.reportingModuleObject = new HeroReportingRegistries (environment.getObjectFactory (), resources);
	}
	
	@Override
	public String toString ()
	{
		return resources.getString ("CharacterModule.Reporting.Sheet.Name");
	}
	
	@Override
	public void performPrint (Hero hero, Document document, PdfWriter writer) throws ReportException
	{
		PageSize pageSize = pageSizePreference.getPageSize ();
		PdfContentByte directContent = writer.getDirectContent ();
		PageConfiguration configuration = PageConfiguration.ForPortrait (pageSize);
		try
		{
			PageEncoder encoder = new MortalPageEncoder (configuration);
			SheetGraphics graphics = SheetGraphics.WithHelvetica (directContent);
			ReportSession session = new ReportSession (getContentRegistry (), hero);
			Sheet sheet = new Sheet (document, getEncoderRegistry (), resources, pageSize);
			encoder.encode (sheet, graphics, session);
		}
		catch (Exception e)
		{
			throw new ReportException (e);
		}
	}
	
	private EncoderRegistry getEncoderRegistry ()
	{
		return getReportingModuleObject ().getEncoderRegistry ();
	}
	
	private ReportContentRegistry getContentRegistry ()
	{
		HeroReportingRegistries moduleObject = getReportingModuleObject ();
		return moduleObject.getContentRegistry ();
	}
	
	private HeroReportingRegistries getReportingModuleObject ()
	{
		return reportingModuleObject;
	}
	
	@Override
	public boolean supports (Hero hero)
	{
		return !hero.getSplat ().getTemplateType ().getHeroType ().isEssenceUser ();
	}
}
