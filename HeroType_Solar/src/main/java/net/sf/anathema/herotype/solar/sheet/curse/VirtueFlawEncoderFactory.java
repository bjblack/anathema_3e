package net.sf.anathema.herotype.solar.sheet.curse;

import net.sf.anathema.hero.sheet.pdf.content.BasicContent;
import net.sf.anathema.hero.sheet.pdf.encoder.EncoderIds;
import net.sf.anathema.hero.sheet.pdf.encoder.boxes.AbstractEncoderFactory;
import net.sf.anathema.hero.sheet.pdf.encoder.boxes.ContentEncoder;
import net.sf.anathema.library.resources.Resources;

@SuppressWarnings ("UnusedDeclaration")
public class VirtueFlawEncoderFactory extends AbstractEncoderFactory
{
	public VirtueFlawEncoderFactory ()
	{
		super (EncoderIds.GREAT_CURSE);
	}
	
	@Override
	public ContentEncoder create (Resources resources, BasicContent content)
	{
		return new VirtueFlawEncoder ();
	}
	
	@Override
	public boolean supports (BasicContent content)
	{
		return content.isEssenceUser ();
	}
}
