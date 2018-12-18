package net.sf.anathema.hero.combat.sheet.social.encoder;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.sheet.pdf.content.stats.HeroStatsModifiers;
import net.sf.anathema.hero.sheet.pdf.content.stats.StatsModifiers;
import net.sf.anathema.hero.sheet.pdf.encoder.boxes.ContentEncoder;
import net.sf.anathema.hero.sheet.pdf.encoder.boxes.LabelledValueEncoder;
import net.sf.anathema.hero.sheet.pdf.encoder.general.Bounds;
import net.sf.anathema.hero.sheet.pdf.encoder.general.Position;
import net.sf.anathema.hero.sheet.pdf.encoder.graphics.SheetGraphics;
import net.sf.anathema.hero.sheet.pdf.encoder.graphics.TableCell;
import net.sf.anathema.hero.sheet.pdf.encoder.table.ITableEncoder;
import net.sf.anathema.hero.sheet.pdf.session.ReportSession;
import net.sf.anathema.hero.specialties.model.SingleSpecialty;
import net.sf.anathema.hero.traits.model.TraitMap;
import net.sf.anathema.hero.traits.model.TraitModelFetcher;
import net.sf.anathema.library.resources.Resources;

import static net.sf.anathema.hero.sheet.pdf.page.IVoidStateFormatConstants.COMMENT_FONT_SIZE;
import static net.sf.anathema.hero.traits.model.types.CommonTraitTypes.Awareness;
import static net.sf.anathema.hero.traits.model.types.CommonTraitTypes.Integrity;

public class SocialCombatStatsBoxEncoder implements ContentEncoder
{
	private final Resources resources;
	
	public SocialCombatStatsBoxEncoder (Resources resources)
	{
		this.resources = resources;
	}
	
	@SuppressWarnings ("unchecked")
	@Override
	public void encode (SheetGraphics graphics, ReportSession reportSession, Bounds bounds) throws DocumentException
	{
		HeroStatsModifiers modifiers = StatsModifiers.allStatsModifiers (reportSession.getHero ());
		float valueWidth = bounds.width;
		Bounds valueBounds = new Bounds (bounds.x, bounds.y + 3, valueWidth, bounds.height);
		float valueHeight = encodeValues (graphics, valueBounds, reportSession.getHero (), modifiers) - 5;
		Bounds attackTableBounds = new Bounds (bounds.x, bounds.y, valueWidth, bounds.height - valueHeight);
		
		ITableEncoder tableEncoder = new SocialCombatStatsTableEncoder (resources);
		float attackHeight = tableEncoder.encodeTable (graphics, reportSession, attackTableBounds);
		Bounds actionBounds = new Bounds (bounds.x, bounds.y, valueWidth / 2f, attackTableBounds.height - attackHeight);
		encodeActionTable (graphics, actionBounds);
		float center = bounds.x + valueWidth / 2f;
		Bounds commentBounds = new Bounds (center + 4, bounds.y, valueWidth / 2f, attackTableBounds.height - attackHeight);
		encodeDVTable (graphics, commentBounds);
		Position lineStart = new Position (center, bounds.y + 3);
		float lineEndY = bounds.y + 6 * COMMENT_FONT_SIZE;
		graphics.createVerticalLineByCoordinate (lineStart, lineEndY).encode ();
	}
	
	private void encodeDVTable (SheetGraphics graphics, Bounds bounds) throws DocumentException
	{
		Font font = graphics.createTableFont ();
		Font commentFont = graphics.createCommentFont ();
		float[] columnWidths = new float[]
		{
			4, 5
		}
		;
		PdfPTable table = new PdfPTable (columnWidths);
		table.setWidthPercentage (100);
		String header = resources.getString ("Sheet.SocialCombat.DVModifiers.Header");
		TableCell headerCell = createCommonActionsCell (new Phrase (header, font));
		headerCell.setColspan (columnWidths.length);
		headerCell.setPaddingTop (1.5f);
		table.addCell (headerCell);
		String actionSubHeader = resources.getString ("Sheet.SocialCombat.DVModifiers.Source");
		table.addCell (createCommonActionsCell (new Phrase (actionSubHeader, commentFont)));
		String speedSubHeader = resources.getString ("Sheet.SocialCombat.DVModifiers.Modifier");
		table.addCell (createCommonActionsCell (new Phrase (speedSubHeader, commentFont)));
		table.addCell (createCommonActionsCell (new Phrase (" ", commentFont)));
		table.addCell (createCommonActionsCell (new Phrase (" ", commentFont)));
		createCommonDVRow (graphics, table, "Appearance");
		createCommonDVRow (graphics, table, "Motivation");
		createCommonDVRow (graphics, table, "Virtue");
		createCommonDVRow (graphics, table, "Intimacy");
		graphics.createSimpleColumn (bounds).withElement (table).encode ();
	}
	
	private void createCommonDVRow (SheetGraphics graphics, PdfPTable table, String sourceId)
	{
		Font commentFont = graphics.createCommentFont ();
		String sourceName = resources.getString ("Sheet.SocialCombat.DVModifiers." + sourceId + ".Name");
		table.addCell (createCommonActionsCell (new Phrase (sourceName, commentFont)));
		String dvModifier = resources.getString ("Sheet.SocialCombat.DVModifiers." + sourceId + ".DV");
		table.addCell (createCommonActionsCell (new Phrase (dvModifier, commentFont)));
	}
	
	private float encodeActionTable (SheetGraphics graphics, Bounds bounds) throws DocumentException
	{
		Font font = graphics.createTableFont ();
		Font commentFont = graphics.createCommentFont ();
		float[] columnWidths = new float[]
		{
			4, 2.5f, 2f
		}
		;
		PdfPTable table = new PdfPTable (columnWidths);
		table.setWidthPercentage (100);
		String header = resources.getString ("Sheet.SocialCombat.CommonActions.Header");
		TableCell headerCell = createCommonActionsCell (new Phrase (header, font));
		headerCell.setColspan (columnWidths.length);
		headerCell.setPaddingTop (1.5f);
		table.addCell (headerCell);
		String actionSubheader = resources.getString ("Sheet.SocialCombat.CommonActions.Action");
		table.addCell (createCommonActionsCell (new Phrase (actionSubheader, commentFont)));
		String speedSubheader = resources.getString ("Sheet.SocialCombat.CommonActions.Speed");
		table.addCell (createCommonActionsCell (new Phrase (speedSubheader, commentFont)));
		String dvPenSubheader = resources.getString ("Sheet.SocialCombat.CommonActions.DV");
		table.addCell (createCommonActionsCell (new Phrase (dvPenSubheader, commentFont)));
		table.addCell (createCommonActionsCell (new Phrase (" ", commentFont)));
		table.addCell (createCommonActionsCell (new Phrase (" ", commentFont)));
		table.addCell (createCommonActionsCell (new Phrase (" ", commentFont)));
		addCommonActionsRow (graphics, table, "JoinDebate");
		addCommonActionsRow (graphics, table, "Attack");
		addCommonActionsRow (graphics, table, "Monologue");
		addCommonActionsRow (graphics, table, "Misc");
		graphics.createSimpleColumn (bounds).withElement (table).encode ();
		return table.getTotalHeight ();
	}
	
	private void addCommonActionsRow (SheetGraphics graphics, PdfPTable table, String actionId)
	{
		Font commentFont = graphics.createCommentFont ();
		String actionName = resources.getString ("Sheet.SocialCombat.CommonActions." + actionId + ".Name");
		table.addCell (createCommonActionsCell (new Phrase (actionName, commentFont)));
		String actionSpeed = resources.getString ("Sheet.SocialCombat.CommonActions." + actionId + ".Speed");
		table.addCell (createCommonActionsCell (new Phrase (actionSpeed, commentFont)));
		String actionDV = resources.getString ("Sheet.SocialCombat.CommonActions." + actionId + ".DV");
		table.addCell (createCommonActionsCell (new Phrase (actionDV, commentFont)));
	}
	
	private TableCell createCommonActionsCell (Phrase phrase)
	{
		TableCell cell = new TableCell (phrase, Rectangle.NO_BORDER);
		cell.setPadding (0);
		return cell;
	}
	
	private float encodeValues (SheetGraphics graphics, Bounds bounds, Hero hero, HeroStatsModifiers equipment)
	{
		TraitMap traitCollection = TraitModelFetcher.fetch (hero);
		SingleSpecialty awarenessSpecialty = new SingleSpecialty (hero, Awareness);
		SingleSpecialty integritySpecialty = new SingleSpecialty (hero, Integrity);
		String joinLabel = resources.getString ("Sheet.SocialCombat.JoinDebateBattle");
		String dodgeLabel = resources.getString ("Sheet.SocialCombat.DodgeMDV");
		String normalLabel = resources.getString ("Sheet.Combat.NormalSpecialty");
		Position upperLeftCorner = new Position (bounds.x, bounds.getMaxY ());
		LabelledValueEncoder encoder = new LabelledValueEncoder (2, upperLeftCorner, bounds.width, 3);
		// TODO
		return encoder.getHeight () + 1;
	}
	
	@Override
	public String getHeader (ReportSession session)
	{
		return resources.getString ("Sheet.Header.SocialCombat");
	}
	
	@Override
	public boolean hasContent (ReportSession session)
	{
		return true;
	}
}
