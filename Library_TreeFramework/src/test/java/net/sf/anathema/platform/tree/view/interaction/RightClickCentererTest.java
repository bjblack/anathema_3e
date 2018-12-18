package net.sf.anathema.platform.tree.view.interaction;

import org.junit.Test;

import static net.sf.anathema.platform.tree.view.interaction.LeftClickTogglerTest.AnyCoordinate;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class RightClickCentererTest
{
	PolygonPanel panel = mock (PolygonPanel.class);
	RightClickCenterer centerer = new RightClickCenterer (panel);
	
	@Test
	public void centersPanelOnCursorPositionOnRightClick () throws Exception
	{
		centerer.mouseClicked (MouseButton.Secondary, MetaKey.NONE, AnyCoordinate, 1);
		verify (panel).centerOn (AnyCoordinate);
	}
	
	@Test
	public void doesNotReactOnLeftClick () throws Exception
	{
		centerer.mouseClicked (MouseButton.Primary, MetaKey.NONE, AnyCoordinate, 1);
		verifyZeroInteractions (panel);
	}
	
	@Test
	public void doesNotReactOnClickWithCtrl () throws Exception
	{
		centerer.mouseClicked (MouseButton.Secondary, MetaKey.CTRL, AnyCoordinate, 1);
		verifyZeroInteractions (panel);
	}
}
