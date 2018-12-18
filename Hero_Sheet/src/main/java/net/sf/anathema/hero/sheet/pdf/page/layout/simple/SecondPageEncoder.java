package net.sf.anathema.hero.sheet.pdf.page.layout.simple;

import net.sf.anathema.hero.sheet.pdf.encoder.graphics.SheetGraphics;
import net.sf.anathema.hero.sheet.pdf.page.PageEncoder;
import net.sf.anathema.hero.sheet.pdf.page.layout.Sheet;
import net.sf.anathema.hero.sheet.pdf.page.layout.SheetPage;
import net.sf.anathema.hero.sheet.pdf.page.layout.field.LayoutField;
import net.sf.anathema.hero.sheet.pdf.session.ReportSession;

import static net.sf.anathema.hero.sheet.pdf.encoder.EncoderIds.BACKGROUNDS;
import static net.sf.anathema.hero.sheet.pdf.encoder.EncoderIds.CHARMS_AND_SORCERY;
import static net.sf.anathema.hero.sheet.pdf.encoder.EncoderIds.EXPERIENCE;
import static net.sf.anathema.hero.sheet.pdf.encoder.EncoderIds.GENERIC_CHARMS;
import static net.sf.anathema.hero.sheet.pdf.encoder.EncoderIds.LANGUAGES;
import static net.sf.anathema.hero.sheet.pdf.encoder.EncoderIds.POSSESSIONS;

public class SecondPageEncoder implements PageEncoder
{
	public static final float BACKGROUND_HEIGHT = 104;
	public static final float LANGUAGE_HEIGHT = 60;
	
	@Override
	public void encode (Sheet sheet, SheetGraphics graphics, ReportSession session)
	{
		SheetPage page = sheet.startPortraitPage (graphics, session);
		LayoutField backgrounds = page.place (BACKGROUNDS).atStartOf (page).withHeight (BACKGROUND_HEIGHT).now ();
		LayoutField possessions = page.place (POSSESSIONS).rightOf (backgrounds).withSameHeight ().now ();
		LayoutField languages = page.place (LANGUAGES).rightOf (possessions).withHeight (LANGUAGE_HEIGHT).now ();
		page.place (EXPERIENCE).below (languages).alignBottomTo (backgrounds).now ();
		LayoutField genericCharms = page.place (GENERIC_CHARMS).below (backgrounds).withPreferredHeight ().andColumnSpan (3).now ();
		page.place (CHARMS_AND_SORCERY).below (genericCharms).fillToBottomOfPage ().andColumnSpan (3).now ();
		encodeAdditionalMagicPages (sheet, graphics, session);
	}
	
	private void encodeAdditionalMagicPages (Sheet sheet, SheetGraphics graphics, ReportSession session)
	{
		while (session.isPageBreakRequired ())
		{
			SheetPage page = sheet.startPortraitPage (graphics, session);
			page.place (CHARMS_AND_SORCERY).atStartOf (page).fillToBottomOfPage ().andColumnSpan (3).now ();
		}
	}
}
