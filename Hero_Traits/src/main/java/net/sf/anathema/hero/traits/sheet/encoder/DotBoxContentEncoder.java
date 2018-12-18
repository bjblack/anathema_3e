package net.sf.anathema.hero.traits.sheet.encoder;

import com.itextpdf.text.DocumentException;
import net.sf.anathema.hero.sheet.pdf.encoder.boxes.ContentEncoder;
import net.sf.anathema.hero.sheet.pdf.encoder.general.Bounds;
import net.sf.anathema.hero.sheet.pdf.encoder.general.Position;
import net.sf.anathema.hero.sheet.pdf.encoder.graphics.SheetGraphics;
import net.sf.anathema.hero.sheet.pdf.page.IVoidStateFormatConstants;
import net.sf.anathema.hero.sheet.pdf.session.ReportSession;
import net.sf.anathema.hero.traits.model.TraitModelFetcher;
import net.sf.anathema.hero.traits.model.TraitType;
import net.sf.anathema.hero.traits.sheet.content.PdfTraitEncoder;
import net.sf.anathema.library.resources.Resources;

public class DotBoxContentEncoder implements ContentEncoder
{
	private PdfTraitEncoder traitEncoder;
	private TraitType trait;
	private Resources resources;
	private final int traitMax;
	private String traitHeaderKey;
	
	public DotBoxContentEncoder (TraitType trait, int traitMax, Resources resources, String traitHeaderKey)
	{
		this.traitMax = traitMax;
		this.trait = trait;
		this.resources = resources;
		this.traitEncoder = PdfTraitEncoder.createLargeTraitEncoder ();
		this.traitHeaderKey = traitHeaderKey;
	}
	
	@Override
	public String getHeader (ReportSession session)
	{
		return resources.getString ("Sheet.Header." + traitHeaderKey);
	}
	
	@Override
	public void encode (SheetGraphics graphics, ReportSession reportSession, Bounds bounds) throws DocumentException
	{
		float width = bounds.width - IVoidStateFormatConstants.PADDING;
		float leftX = bounds.x + IVoidStateFormatConstants.PADDING / 2f;
		int value = TraitModelFetcher.fetch (reportSession.getHero ()).getTrait (trait).getCurrentValue ();
		float entryHeight = Math.max (bounds.height - IVoidStateFormatConstants.PADDING / 2f, traitEncoder.getTraitHeight ());
		float yPosition = bounds.getMaxY () - entryHeight;
		traitEncoder.encodeDotsCenteredAndUngrouped (graphics, new Position (leftX, yPosition), width, value, traitMax);
	}
	
	@Override
	public boolean hasContent (ReportSession session)
	{
		return true;
	}
}
