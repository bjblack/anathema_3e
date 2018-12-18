package net.sf.anathema.hero.equipment.sheet.rendering.arsenal;

import net.sf.anathema.hero.sheet.pdf.content.SubBoxContent;
import net.sf.anathema.hero.sheet.pdf.encoder.boxes.AbstractContentEncoder;
import net.sf.anathema.hero.sheet.pdf.encoder.general.Bounds;
import net.sf.anathema.hero.sheet.pdf.encoder.graphics.SheetGraphics;
import net.sf.anathema.hero.sheet.pdf.session.ReportSession;

import com.itextpdf.text.DocumentException;

public class WeaponEncoder<C extends SubBoxContent> extends AbstractContentEncoder<C>
{
	private final WeaponryTableEncoder tableEncoder;
	
	@SuppressWarnings ("unchecked")
	public WeaponEncoder (Class<C> contentClass)
	{
		super (contentClass);
		this.tableEncoder = new WeaponryTableEncoder (contentClass);
	}
	
	@SuppressWarnings ("unchecked")
	@Override
	public void encode (SheetGraphics graphics, ReportSession session, Bounds bounds) throws DocumentException
	{
		tableEncoder.encodeTable (graphics, session, bounds);
	}
}
