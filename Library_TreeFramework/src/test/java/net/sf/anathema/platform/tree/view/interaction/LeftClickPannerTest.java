package net.sf.anathema.platform.tree.view.interaction;

import net.sf.anathema.library.number.Coordinate;

import org.junit.Test;

import static net.sf.anathema.platform.tree.view.interaction.LeftClickTogglerTest.AnyCoordinate;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class LeftClickPannerTest
{
	PolygonPanel panel = mock (PolygonPanel.class);
	LeftClickPanner panner = new LeftClickPanner (panel);
	
	
	@Test
	public void activatesOnLeftButtonOnly () throws Exception
	{
		panner.mousePressed (AnyCoordinate);
		panner.mouseDragged (MouseButton.Secondary, new Coordinate (15, 15));
		verifyZeroInteractions (panel);
	}
	
	@Test
	public void translatesByDifferenceMoved () throws Exception
	{
		panner.mousePressed (new Coordinate (10, 10));
		panner.mouseDragged (MouseButton.Primary, new Coordinate (15, 15));
		verifyTranslation (5, 5);
	}
	
	@Test
	public void translatesByDifferenceFromLastDrag () throws Exception
	{
		panner.mousePressed (new Coordinate (10, 10));
		panner.mouseDragged (MouseButton.Primary, new Coordinate (15, 15));
		panner.mouseDragged (MouseButton.Primary, new Coordinate (25, 25));
		verifyTranslation (10, 10);
	}
	
	@Test
	public void repaintsAfterTranslation () throws Exception
	{
		panner.mousePressed (new Coordinate (10, 10));
		panner.mouseDragged (MouseButton.Primary, new Coordinate (15, 15));
		verify (panel).translateRelativeToScale (anyInt (), anyInt ());
		verify (panel).refresh ();
	}
	
	private void verifyTranslation (int x, int y)
	{
		verify (panel).translateRelativeToScale (x, y);
	}
}
