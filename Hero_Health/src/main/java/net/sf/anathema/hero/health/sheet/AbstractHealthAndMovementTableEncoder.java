package net.sf.anathema.hero.health.sheet;

import net.sf.anathema.hero.health.model.HealthLevelType;
import net.sf.anathema.hero.health.model.HealthModelFetcher;
import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.sheet.pdf.encoder.general.Bounds;
import net.sf.anathema.hero.sheet.pdf.encoder.graphics.SheetGraphics;
import net.sf.anathema.hero.sheet.pdf.encoder.graphics.TableCell;
import net.sf.anathema.hero.sheet.pdf.encoder.table.ITableEncoder;
import net.sf.anathema.hero.sheet.pdf.encoder.table.TableColumns;
import net.sf.anathema.hero.sheet.pdf.encoder.table.TableEncodingUtilities;
import net.sf.anathema.hero.sheet.pdf.session.ReportSession;
import net.sf.anathema.hero.traits.model.TraitMap;
import net.sf.anathema.hero.traits.model.TraitModelFetcher;
import net.sf.anathema.library.resources.Resources;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public abstract class AbstractHealthAndMovementTableEncoder implements ITableEncoder<ReportSession>
{
	public static final int HEALTH_RECT_SIZE = 6;
	private static final int HEALTH_COLUMN_COUNT = 10;
	protected static float PADDING = 0.3f;
	private static final TableColumns HEALTH_LEVEL_COLUMNS = TableColumns.from (PADDING, 0.6f, 0.7f, PADDING);
	
	private final Resources resources;
	
	public AbstractHealthAndMovementTableEncoder (Resources resources)
	{
		this.resources = resources;
	}
	
	protected abstract TableColumns getMovementColumns ();
	
	@Override
	public float encodeTable (SheetGraphics graphics, ReportSession session, Bounds bounds) throws DocumentException
	{
		PdfPTable table = createTable (graphics, session);
		table.setWidthPercentage (100);
		graphics.createSimpleColumn (bounds).withElement (table).encode ();
		return table.getTotalHeight ();
	}
	
	protected TraitMap getTraits (Hero hero)
	{
		return TraitModelFetcher.fetch (hero);
	}
	
	private int getRowCount (HealthLevelType type)
	{
		if (type == HealthLevelType.TWO || type == HealthLevelType.ONE)
		{
			return 2;
		}
		return 1;
	}
	
	protected final PdfPTable createTable (SheetGraphics graphics, ReportSession session) throws DocumentException
	{
		try
		{
			PdfContentByte directContent = graphics.getDirectContent ();
			Image activeTemplate  = Image.getInstance (HealthTemplateFactory.createRectTemplate (directContent, BaseColor.BLACK));
			Image passiveTemplate = Image.getInstance (HealthTemplateFactory.createRectTemplate (directContent, BaseColor.LIGHT_GRAY));
			TableColumns columnWidth = createColumnWidth ();
			PdfPTable table = new PdfPTable (columnWidth.asArray ());
			addHeaders (graphics, table);
			for (HealthLevelType type : HealthLevelType.values ())
			{
				addHealthTypeRows (graphics, table, session.getHero (), activeTemplate, passiveTemplate, type);
			}
			return table;
		}
		catch (BadElementException e)
		{
			throw new DocumentException (e);
		}
	}
	
	private void addHealthTypeRows (SheetGraphics graphics, PdfPTable table, Hero hero, Image activeTemplate, Image passiveTemplate, HealthLevelType type)
	{
		int rowCount = getRowCount (type);
		for (int rowIndex = 0; rowIndex < rowCount; rowIndex++)
		{
			if (rowIndex == 0)
			{
				int painTolerance = HealthModelFetcher.fetch (hero).getPainToleranceLevel ();
				if (type == HealthLevelType.INCAPACITATED)
				{
					addIncapacitatedMovement (graphics, table);
				}
				else
				{
					addMovementCells (graphics, table, type, painTolerance, getTraits (hero));
				}
				addHealthTypeCells (graphics, table, type, painTolerance);
			}
			else
			{
				addSpaceCells (graphics, table, getMovementColumns ().countWeights () + HEALTH_LEVEL_COLUMNS.countWeights ());
			}
			addHealthCells (graphics, table, hero, type, rowIndex, activeTemplate, passiveTemplate);
		}
	}
	
	private void addHealthTypeCells (SheetGraphics graphics, PdfPTable table, HealthLevelType type, int painTolerance)
	{
		addSpaceCells (graphics, table, 1);
		addHealthPenaltyCells (graphics, table, type, painTolerance);
		addSpaceCells (graphics, table, 1);
	}
	
	private void addIncapacitatedMovement (SheetGraphics graphics, PdfPTable table)
	{
		Phrase commentPhrase = createIncapacitatedComment (graphics);
		TableCell cell = new TableCell (commentPhrase, Rectangle.NO_BORDER);
		cell.setColspan (getMovementColumns ().countWeights ());
		cell.setVerticalAlignment (Element.ALIGN_BOTTOM);
		table.addCell (cell);
	}
	
	protected abstract Phrase createIncapacitatedComment (SheetGraphics graphics);
	
	protected final void addSpaceCells (SheetGraphics graphics, PdfPTable table, int count)
	{
		for (int index = 0; index < count; index++)
		{
			table.addCell (createSpaceCell (graphics));
		}
	}
	
	protected final PdfPCell createHeaderCell (SheetGraphics graphics, String text, int columnSpan)
	{
		PdfPCell cell = new PdfPCell (new Phrase (text, createHeaderFont (graphics)));
		cell.setBorder (Rectangle.NO_BORDER);
		cell.setColspan (columnSpan);
		return cell;
	}
	
	private void addHeaders (SheetGraphics graphics, PdfPTable table)
	{
		addMovementHeader (graphics, table);
		table.addCell (createHeaderCell (graphics, resources.getString ("Sheet.Health.Levels"), 13));
	}
	
	protected abstract void addMovementHeader (SheetGraphics graphics, PdfPTable table);
	
	protected abstract void addMovementCells (SheetGraphics graphics, PdfPTable table, HealthLevelType level, int painTolerance, TraitMap collection);
	
	protected final PdfPCell createMovementCell (SheetGraphics graphics, int value, int minValue)
	{
		Font font = createDefaultFont (graphics);
		return TableEncodingUtilities.createContentCellTable (BaseColor.BLACK, String.valueOf (Math.max (value, minValue)), font, 0.5f, Rectangle.BOX, Element.ALIGN_CENTER);
	}
	
	protected void addHealthPenaltyCells (SheetGraphics graphics, PdfPTable table, HealthLevelType level, int painTolerance)
	{
		Font font = createDefaultFont (graphics);
		String healthPenText = resources.getString ("HealthLevelType." + level.getId () + ".Short");
		Phrase healthPenaltyPhrase = new Phrase (healthPenText, font);
		PdfPCell healthPdfPCell = new TableCell (healthPenaltyPhrase, Rectangle.NO_BORDER);
		if (level == HealthLevelType.INCAPACITATED)
		{
			healthPdfPCell.setColspan (2);
			table.addCell (healthPdfPCell);
		}
		else
		{
			table.addCell (healthPdfPCell);
			String painToleranceText = " ";
			if (painTolerance > 0)
			{
				int value = getPenalty (level, painTolerance);
				painToleranceText = "(" + (value == 0 ? "-" : "") + value + ")";
			}
			TableCell painToleranceCell = new TableCell (new Phrase (painToleranceText, font), Rectangle.NO_BORDER);
			painToleranceCell.setHorizontalAlignment (Element.ALIGN_CENTER);
			table.addCell (painToleranceCell);
		}
	}
	
	protected final int getPenalty (HealthLevelType level, int painTolerance)
	{
		return Math.min (0, level.getIntValue () + painTolerance);
	}
	
	private void addHealthCells (SheetGraphics graphics, PdfPTable table, Hero hero, HealthLevelType level, int row, Image activeImage, Image passiveImage)
	{
		int naturalCount = getNaturalHealthLevels (level, hero);
		if (row < naturalCount)
		{
			table.addCell (createHealthCell (activeImage));
		}
		else
		{
			addSpaceCells (graphics, table, 1);
		}
		int additionalCount = 9;
		for (int index = 0; index < additionalCount; index++)
		{
			int value = naturalCount + row * additionalCount + index + 1;
			if (value <=  HealthModelFetcher.fetch (hero).getHealthLevelTypeCount (level))
			{
				table.addCell (createHealthCell (activeImage));
			}
			else
			{
				table.addCell (createHealthCell (passiveImage));
			}
		}
	}
	
	private int getNaturalHealthLevels (HealthLevelType level, Hero hero)
	{
		return HealthModelFetcher.fetch (hero).getBasicHealthLevel (level);
	}
	
	private PdfPCell createHealthCell (Image image)
	{
		PdfPCell cell = new PdfPCell (image, false);
		cell.setVerticalAlignment (Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment (Element.ALIGN_CENTER);
		cell.setBorder (Rectangle.NO_BORDER);
		return cell;
	}
	
	private TableColumns createColumnWidth ()
	{
		TableColumns columns = new TableColumns ();
		TableColumns healthColumns = TableEncodingUtilities.createStandardColumnWidths (HEALTH_COLUMN_COUNT, 0.4f);
		columns.adopt (getMovementColumns ());
		columns.adopt (HEALTH_LEVEL_COLUMNS);
		columns.adopt (healthColumns);
		return columns;
	}
	
	protected final Resources getResources ()
	{
		return resources;
	}
	
	private PdfPCell createSpaceCell (SheetGraphics graphics)
	{
		PdfPCell spaceCell = new PdfPCell (new Phrase (" ", createDefaultFont (graphics)));
		spaceCell.setBorder (Rectangle.NO_BORDER);
		return spaceCell;
	}
	
	protected Font createCommentFont (SheetGraphics graphics)
	{
		return graphics.createCommentFont ();
	}
	
	protected Font createHeaderFont (SheetGraphics graphics)
	{
		return TableEncodingUtilities.createHeaderFont (graphics.getBaseFont ());
	}
	
	protected Font createDefaultFont (SheetGraphics graphics)
	{
		return graphics.createTableFont ();
	}
	
	@Override
	public boolean hasContent (ReportSession session)
	{
		return true;
	}
}
