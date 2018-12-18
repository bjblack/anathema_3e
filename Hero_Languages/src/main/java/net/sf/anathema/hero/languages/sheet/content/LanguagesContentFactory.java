package net.sf.anathema.hero.languages.sheet.content;

import net.sf.anathema.hero.sheet.pdf.content.ReportContentFactory;
import net.sf.anathema.hero.sheet.pdf.session.ReportSession;
import net.sf.anathema.library.dependencies.Produces;
import net.sf.anathema.library.resources.Resources;

@Produces (LanguagesContent.class)
public class LanguagesContentFactory implements ReportContentFactory<LanguagesContent>
{
	private Resources resources;
	
	public LanguagesContentFactory (Resources resources)
	{
		this.resources = resources;
	}
	
	@Override
	public LanguagesContent create (ReportSession session)
	{
		return new LanguagesContent (session.getHero (), resources);
	}
}
