package net.sf.anathema.hero.traits.sheet.encoder;

import net.sf.anathema.hero.sheet.pdf.encoder.general.Position;
import net.sf.anathema.hero.sheet.pdf.encoder.graphics.SheetGraphics;
import net.sf.anathema.hero.sheet.pdf.session.ReportSession;

public interface AdditionalTraitLineEncoder
{
	float encode (SheetGraphics graphics, ReportSession session, Position position, float width, float height);
}
