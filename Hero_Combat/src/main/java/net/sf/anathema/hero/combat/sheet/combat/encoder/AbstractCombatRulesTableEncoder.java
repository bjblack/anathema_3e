package net.sf.anathema.hero.combat.sheet.combat.encoder;

import net.sf.anathema.hero.combat.sheet.combat.content.QualifiedText;
import net.sf.anathema.hero.combat.sheet.combat.content.TextType;
import net.sf.anathema.hero.sheet.pdf.encoder.general.Bounds;
import net.sf.anathema.hero.sheet.pdf.encoder.graphics.SheetGraphics;
import net.sf.anathema.hero.sheet.pdf.encoder.graphics.TableCell;
import net.sf.anathema.hero.sheet.pdf.encoder.table.AbstractTableEncoder;
import net.sf.anathema.hero.sheet.pdf.session.ReportSession;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public abstract class AbstractCombatRulesTableEncoder extends AbstractTableEncoder<ReportSession>
{
	@Override
	protected final PdfPTable createTable (SheetGraphics graphics, ReportSession session, Bounds bounds)
	{
		float cellPadding = 0.05f;
		PdfPTable table = new PdfPTable (new float[]
		{
			1f, cellPadding, 1.1f, cellPadding, 1f
		}
		);
		addFirstCell (graphics, session, table);
		table.addCell (createSpaceCell (graphics));
		addSecondCell (graphics, session, table);
		table.addCell (createSpaceCell (graphics));
		addThirdCell (graphics, session, table);
		return table;
	}
	
	protected abstract void addFirstCell (SheetGraphics graphics, ReportSession reportSession, PdfPTable table);
	
	protected abstract void addSecondCell (SheetGraphics graphics, ReportSession reportSession, PdfPTable table);
	
	protected abstract void addThirdCell (SheetGraphics graphics, ReportSession reportSession, PdfPTable table);
	
	private PdfPCell createSpaceCell (SheetGraphics graphics)
	{
		return new TableCell (new Phrase (" ", graphics.createTextFont ()), Rectangle.NO_BORDER);
	}
	
	protected PdfPCell createContentCell (Phrase phrase)
	{
		return new TableCell (phrase, Rectangle.BOX);
	}
	
	protected void addAsCell (SheetGraphics graphics, PdfPTable table, QualifiedText[] textChunks)
	{
		Phrase knockdownAndStunningPhrase = new Phrase ("");
		for (QualifiedText text : textChunks)
		{
			knockdownAndStunningPhrase.add (createChunk (graphics, text));
		}
		table.addCell (createContentCell (knockdownAndStunningPhrase));
	}
	
	private Chunk createChunk (SheetGraphics graphics, QualifiedText text)
	{
		Font font = text.type == TextType.Comment ? graphics.createCommentFont () : graphics.createTextFont ();
		return new Chunk (text.text, font);
	}
}
