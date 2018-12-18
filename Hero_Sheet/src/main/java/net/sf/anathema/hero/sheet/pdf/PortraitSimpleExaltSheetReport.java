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
import net.sf.anathema.hero.sheet.pdf.page.PageRegistry;
import net.sf.anathema.hero.sheet.pdf.page.layout.Sheet;
import net.sf.anathema.hero.sheet.pdf.page.layout.simple.FirstPageEncoder;
import net.sf.anathema.hero.sheet.pdf.page.layout.simple.SecondPageEncoder;
import net.sf.anathema.hero.sheet.pdf.session.ReportSession;
import net.sf.anathema.hero.sheet.preferences.PageSizePreference;
import net.sf.anathema.library.resources.Resources;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import java.util.ArrayList;
import java.util.List;

public class PortraitSimpleExaltSheetReport extends AbstractPdfReport
{
	private final Resources resources;
	private final PageSizePreference pageSizePreference;
	private HeroReportingRegistries moduleObject;
	
	public PortraitSimpleExaltSheetReport (HeroEnvironment environment, PageSizePreference pageSizePreference)
	{
		this.resources = environment.getResources ();
		this.pageSizePreference = pageSizePreference;
		this.moduleObject = new HeroReportingRegistries (environment.getObjectFactory (), resources);
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
			List<PageEncoder> encoderList = new ArrayList<> ();
			encoderList.add (new FirstPageEncoder (configuration));
			ReportSession session = new ReportSession (getContentRegistry (), hero);
			encoderList.addAll (findAdditionalPages (pageSize, session));
			encoderList.add (new SecondPageEncoder ());
			Sheet sheet = new Sheet (document, getEncoderRegistry (), resources, pageSize);
			for (PageEncoder encoder : encoderList)
			{
				SheetGraphics graphics = SheetGraphics.WithHelvetica (directContent);
				encoder.encode (sheet, graphics, session);
			}
		}
		catch (Exception e)
		{
			throw new ReportException (e);
		}
	}
	
	private List<PageEncoder> findAdditionalPages (PageSize pageSize, ReportSession session)
	{
		PageRegistry additionalPageRegistry = getReportingModuleObject ().getAdditionalPageRegistry ();
		return additionalPageRegistry.createEncoders (pageSize, getEncoderRegistry (), resources, session);
	}
	
	private EncoderRegistry getEncoderRegistry ()
	{
		return getReportingModuleObject ().getEncoderRegistry ();
	}
	
	private HeroReportingRegistries getReportingModuleObject ()
	{
		return moduleObject;
	}
	
	private ReportContentRegistry getContentRegistry ()
	{
		HeroReportingRegistries moduleObject = getReportingModuleObject ();
		return moduleObject.getContentRegistry ();
	}
	
	@Override
	public boolean supports (Hero hero)
	{
		return hero.getSplat ().getTemplateType ().getHeroType ().isEssenceUser ();
	}
}
