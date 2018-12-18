package net.sf.anathema.hero.sheet.pdf.page;

import net.sf.anathema.hero.sheet.pdf.encoder.graphics.SheetGraphics;
import net.sf.anathema.hero.sheet.pdf.page.layout.Sheet;
import net.sf.anathema.hero.sheet.pdf.session.ReportSession;

import com.itextpdf.text.DocumentException;

public interface PageEncoder
{
	int FIRST_PAGE_CONTENT_HEIGHT = 755;
	
	void encode (Sheet sheet, SheetGraphics graphics, ReportSession session) throws DocumentException;
}
