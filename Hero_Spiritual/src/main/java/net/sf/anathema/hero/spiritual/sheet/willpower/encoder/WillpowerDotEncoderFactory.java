package net.sf.anathema.hero.spiritual.sheet.willpower.encoder;

import net.sf.anathema.hero.sheet.pdf.content.BasicContent;
import net.sf.anathema.hero.sheet.pdf.encoder.EncoderIds;
import net.sf.anathema.hero.sheet.pdf.encoder.boxes.AbstractEncoderFactory;
import net.sf.anathema.hero.sheet.pdf.encoder.boxes.ContentEncoder;
import net.sf.anathema.hero.traits.sheet.encoder.DotBoxContentEncoder;
import net.sf.anathema.library.resources.Resources;

import static net.sf.anathema.hero.traits.model.types.CommonTraitTypes.Willpower;

@SuppressWarnings ("UnusedDeclaration")
public class WillpowerDotEncoderFactory extends AbstractEncoderFactory
{
	public WillpowerDotEncoderFactory ()
	{
		super (EncoderIds.WILLPOWER_DOTS);
	}
	
	@Override
	public ContentEncoder create (Resources resources, BasicContent content)
	{
		return new DotBoxContentEncoder (Willpower, 10, resources, "Willpower");
	}
	
	@Override
	public boolean supports (BasicContent content)
	{
		return true;
	}
}
