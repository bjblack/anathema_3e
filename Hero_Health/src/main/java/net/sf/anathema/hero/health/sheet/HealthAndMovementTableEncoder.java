package net.sf.anathema.hero.health.sheet;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import net.sf.anathema.hero.health.model.HealthLevelType;
import net.sf.anathema.hero.sheet.pdf.content.stats.StatsModifiers;
import net.sf.anathema.hero.sheet.pdf.encoder.general.Bounds;
import net.sf.anathema.hero.sheet.pdf.encoder.graphics.SheetGraphics;
import net.sf.anathema.hero.sheet.pdf.encoder.table.TableColumns;
import net.sf.anathema.hero.sheet.pdf.session.ReportSession;
import net.sf.anathema.hero.traits.model.TraitMap;
import net.sf.anathema.library.resources.Resources;

import static net.sf.anathema.hero.traits.model.types.CommonTraitTypes.Athletics;
import static net.sf.anathema.hero.traits.model.types.CommonTraitTypes.Dexterity;
import static net.sf.anathema.hero.traits.model.types.CommonTraitTypes.Strength;

public class HealthAndMovementTableEncoder extends AbstractHealthAndMovementTableEncoder
{
	private int mobilityPenalty;
	
	public HealthAndMovementTableEncoder (Resources resources)
	{
		super (resources);
	}
	
	@Override
	public final float encodeTable (SheetGraphics graphics, ReportSession session, Bounds bounds) throws DocumentException
	{
		mobilityPenalty = Math.min (0, StatsModifiers.allStatsModifiers (session.getHero ()).getMobilityPenalty ());
		return super.encodeTable (graphics, session, bounds);
	}
	
	@Override
	protected final TableColumns getMovementColumns ()
	{
		return TableColumns.from (1f, PADDING, 1f, PADDING, 1f, 1f);
	}
	
	@Override
	protected final Phrase createIncapacitatedComment (SheetGraphics graphics)
	{
		return new Phrase (getResources ().getString ("Sheet.Movement.Comment.Mobility"), createCommentFont (graphics));
	}
	
	@Override
	protected final void addMovementHeader (SheetGraphics graphics, PdfPTable table)
	{
		table.addCell (createHeaderCell (graphics, getResources ().getString ("Sheet.Movement.Move"), 2));
		table.addCell (createHeaderCell (graphics, getResources ().getString ("Sheet.Movement.Dash"), 2));
		table.addCell (createHeaderCell (graphics, getResources ().getString ("Sheet.Movement.Jump"), 3));
	}
	
	@Override
	protected final void addMovementCells (SheetGraphics graphics, PdfPTable table, HealthLevelType level, int painTolerance, TraitMap collection)
	{
		int woundPenalty = getPenalty (level, painTolerance);
		int dex = collection.getTrait (Dexterity).getCurrentValue ();
		int str = collection.getTrait (Strength).getCurrentValue ();
		int athletics = collection.getTrait (Athletics).getCurrentValue ();
		
		// minimum move is 1, minimum dash is 2, minimum jump h/v, swim, & climb is unknown
		int move = Math.max (dex + woundPenalty + mobilityPenalty, 1);
		int dash = Math.max (dex + woundPenalty + mobilityPenalty + 6, 2);
		int verticalJump = str + athletics + woundPenalty + mobilityPenalty;
		int horizontalJump = verticalJump * 2;
		
		table.addCell (createMovementCell (graphics, move, 1));
		addSpaceCells (graphics, table, 1);
		table.addCell (createMovementCell (graphics, dash, 2));
		
		addSpaceCells (graphics, table, 1);
		table.addCell (createMovementCell (graphics, horizontalJump, 0));
		table.addCell (createMovementCell (graphics, verticalJump, 0));
	}
	
	@Override
	public boolean hasContent (ReportSession session)
	{
		return true;
	}
}
