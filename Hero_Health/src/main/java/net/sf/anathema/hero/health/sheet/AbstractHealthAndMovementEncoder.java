package net.sf.anathema.hero.health.sheet;

import net.sf.anathema.hero.health.model.HealthLevelType;
import net.sf.anathema.hero.health.model.HealthModel;
import net.sf.anathema.hero.health.model.HealthModelFetcher;
import net.sf.anathema.hero.health.model.HealthType;
import net.sf.anathema.hero.sheet.pdf.encoder.boxes.ContentEncoder;
import net.sf.anathema.hero.sheet.pdf.encoder.general.Bounds;
import net.sf.anathema.hero.sheet.pdf.encoder.general.Position;
import net.sf.anathema.hero.sheet.pdf.encoder.graphics.SheetGraphics;
import net.sf.anathema.hero.sheet.pdf.encoder.table.ITableEncoder;
import net.sf.anathema.hero.sheet.pdf.page.IVoidStateFormatConstants;
import net.sf.anathema.hero.sheet.pdf.session.ReportSession;
import net.sf.anathema.library.resources.Resources;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfTemplate;

public abstract class AbstractHealthAndMovementEncoder implements ContentEncoder
{
	private final Resources resources;
	
	public AbstractHealthAndMovementEncoder (Resources resources)
	{
		this.resources = resources;
	}
	
	@SuppressWarnings ("unchecked")
	@Override
	public void encode (SheetGraphics graphics, ReportSession reportSession, Bounds bounds) throws DocumentException
	{
		Bounds tableBounds = new Bounds (bounds.x, bounds.y, (bounds.width * 0.66f), bounds.height);
		ITableEncoder tableEncoder = createTableEncoder ();
		tableEncoder.encodeTable (graphics, reportSession, tableBounds);
		float textX = tableBounds.getMaxX () + IVoidStateFormatConstants.TEXT_PADDING;
		Bounds textBounds = new Bounds (textX, bounds.y, bounds.x + bounds.width - textX, bounds.height - 2);
		HealthModel model = HealthModelFetcher.fetch (reportSession.getHero ());
		encodeText (model, graphics, textBounds);
	}
	
	protected abstract ITableEncoder createTableEncoder ();
	
	protected void encodeText (HealthModel model, SheetGraphics graphics, Bounds textBounds) throws DocumentException
	{
		Font headerFont = graphics.createCommentFont ();
		Font commentFont = graphics.createCommentFont ();
		Font commentTitleFont = new Font (commentFont);
		commentTitleFont.setStyle (Font.BOLD);
		Paragraph healthText = createHealthRulesPhrase (model, graphics, headerFont, commentFont, commentTitleFont);
		int leading = IVoidStateFormatConstants.COMMENT_FONT_SIZE + 1;
		float yLine = graphics.createSimpleColumn (textBounds).withLeading ( (float) leading).andTextPart (healthText).encode ().getYLine ();
		int rectangleOffset = AbstractHealthAndMovementTableEncoder.HEALTH_RECT_SIZE + 1;
		float additionalOffset = 2.5f;
		float rectYPosition = yLine - rectangleOffset - additionalOffset;
		float textYPosition = yLine - leading - additionalOffset;
		float xPosition = textBounds.x;
		PdfTemplate rectTemplate = HealthTemplateFactory.createRectTemplate (graphics.getDirectContent (), BaseColor.BLACK);
		graphics.getDirectContent ().addTemplate (rectTemplate, xPosition, rectYPosition);
		PdfTemplate bashingTemplate = HealthTemplateFactory.createBashingTemplate (graphics.getDirectContent (), BaseColor.GRAY);
		graphics.getDirectContent ().addTemplate (bashingTemplate, xPosition, rectYPosition);
		xPosition += rectangleOffset;
		String bashingString = createSpacedString (resources.getString ("Sheet.Health.Comment.MarkDamageBashing"));
		graphics.drawComment (bashingString, new Position (xPosition, textYPosition), Element.ALIGN_LEFT);
		xPosition += graphics.getTextMetrics ().getCommentTextWidth (bashingString);
		graphics.getDirectContent ().addTemplate (rectTemplate, xPosition, rectYPosition);
		PdfTemplate lethalTemplate = HealthTemplateFactory.createLethalTemplate (graphics.getDirectContent (), BaseColor.GRAY);
		graphics.getDirectContent ().addTemplate (lethalTemplate, xPosition, rectYPosition);
		xPosition += rectangleOffset;
		String lethalString = createSpacedString (resources.getString ("Sheet.Health.Comment.MarkDamageLethal"));
		graphics.drawComment (lethalString, new Position (xPosition, textYPosition), Element.ALIGN_LEFT);
		xPosition += graphics.getTextMetrics ().getCommentTextWidth (lethalString);
		graphics.getDirectContent ().addTemplate (rectTemplate, xPosition, rectYPosition);
		PdfTemplate aggravatedTemplate = HealthTemplateFactory.createAggravatedTemplate (graphics.getDirectContent (), BaseColor.GRAY);
		graphics.getDirectContent ().addTemplate (aggravatedTemplate, xPosition, rectYPosition);
		xPosition += rectangleOffset;
		String aggravatedString = createSpacedString (resources.getString ("Sheet.Health.Comment.MarkDamageAggravated"));
		graphics.drawComment (aggravatedString, new Position (xPosition, textYPosition), Element.ALIGN_LEFT);
		xPosition += graphics.getTextMetrics ().getCommentTextWidth (lethalString);
	}
	
	private String createSpacedString (String string)
	{
		return " " + string + "   ";
	}
	
	protected final Resources getResources ()
	{
		return resources;
	}
	
	private Paragraph createHealthRulesPhrase (HealthModel model, SheetGraphics graphics, Font headerFont, Font commentFont, Font commentTitleFont)
	{
		Paragraph healthText = new Paragraph ();
		healthText.setAlignment (Element.ALIGN_JUSTIFIED_ALL);
		Chunk seperator = new Chunk (": ", commentTitleFont);
		Chunk newLine = new Chunk ("\n", commentFont);
		Chunk header = new Chunk (resources.getString ("Sheet.Health.Comment.Rules"), headerFont);
		healthText.add (header);
		healthText.add (newLine);
		healthText.add (graphics.createSymbolChunk ());
		healthText.add (new Chunk (resources.getString ("Sheet.Health.Comment.HealthHeader"), commentTitleFont));
		healthText.add (newLine);
		healthText.add (new Chunk (getHealthText (model), commentFont));
		healthText.add (newLine);
		healthText.add (graphics.createSymbolChunk ());
		healthText.add (new Chunk (resources.getString ("Sheet.Health.Comment.MarkDamageHeader"), commentTitleFont));
		return healthText;
	}
	
	private String getHealthText (HealthModel model)
	{
		String zeroBashing = model.getHealingTime (HealthLevelType.ZERO, HealthType.Bashing).text;
		String zeroLethal  = model.getHealingTime (HealthLevelType.ZERO, HealthType.Lethal).text;
		String oneBashing  = model.getHealingTime (HealthLevelType.ONE, HealthType.Bashing).text;
		String oneLethal   = model.getHealingTime (HealthLevelType.ONE, HealthType.Lethal).text;
		String twoBashing  = model.getHealingTime (HealthLevelType.TWO, HealthType.Bashing).text;
		String twoLethal   = model.getHealingTime (HealthLevelType.TWO, HealthType.Lethal).text;
		String fourBashing = model.getHealingTime (HealthLevelType.FOUR, HealthType.Bashing).text;
		String fourLethal  = model.getHealingTime (HealthLevelType.FOUR, HealthType.Lethal).text;
		return resources.getString ("Sheet.Health.Comment.HealthText",
		zeroBashing,
		zeroLethal,
		oneBashing,
		oneLethal,
		twoBashing,
		twoLethal,
		fourBashing,
		fourLethal);
	}
	
	@Override
	public String getHeader (ReportSession session)
	{
		return resources.getString ("Sheet.Header.MovementHealth");
	}
	
	@Override
	public boolean hasContent (ReportSession session)
	{
		return true;
	}
}
