package net.sf.anathema.hero.intimacies.sheet.encoder;

import net.sf.anathema.hero.sheet.pdf.content.BasicContent;
import net.sf.anathema.hero.sheet.pdf.encoder.EncoderIds;
import net.sf.anathema.hero.sheet.pdf.encoder.boxes.AbstractEncoderFactory;
import net.sf.anathema.hero.sheet.pdf.encoder.boxes.ContentEncoder;
import net.sf.anathema.library.resources.Resources;

@SuppressWarnings ("UnusedDeclaration")
public class SimpleIntimaciesEncoderFactory extends AbstractEncoderFactory
{
	public SimpleIntimaciesEncoderFactory ()
	{
		super (EncoderIds.INTIMACIES_SIMPLE);
	}
	
	@Override
	public ContentEncoder create (Resources resources, BasicContent content)
	{
		return new SimpleIntimaciesEncoder ();
	}
	
	@Override
	public boolean supports (BasicContent content)
	{
		return true;
	}
}
