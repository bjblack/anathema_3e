package net.sf.anathema.platform.tree.view;

import net.sf.anathema.platform.tree.display.NodeInteractionListener;
import net.sf.anathema.platform.tree.view.container.Cascade;
import net.sf.anathema.platform.tree.view.interaction.CtrlLeftClickDetailsRequester;
import net.sf.anathema.platform.tree.view.interaction.CursorChanger;
import net.sf.anathema.platform.tree.view.interaction.LeftClickPanner;
import net.sf.anathema.platform.tree.view.interaction.LeftClickSelector;
import net.sf.anathema.platform.tree.view.interaction.PolygonPanel;
import net.sf.anathema.platform.tree.view.interaction.RightClickCenterer;
import net.sf.anathema.platform.tree.view.interaction.RightClickResetter;
import net.sf.anathema.platform.tree.view.interaction.WheelScaler;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class InteractionTreeListeningTest
{
	PolygonPanel panel = mock (PolygonPanel.class);
	private Cascade cascade = mock (Cascade.class);
	private NodeInteractionListener interactionListener = mock (NodeInteractionListener.class);
	InteractionTreeListening listening = new InteractionTreeListening (cascade, panel, interactionListener);
	
	@Test
	public void addsPanListenerForDragAndClick () throws Exception
	{
		listening.initialize ();
		ArgumentCaptor<LeftClickPanner> captor = ArgumentCaptor.forClass (LeftClickPanner.class);
		verify (panel, atLeastOnce ()).addMouseMotionListener (captor.capture ());
		verify (panel).addMousePressListener (captor.getAllValues ().get (0));
	}
	
	@Test
	public void addsToggleListenerForLeftClick () throws Exception
	{
		listening.initialize ();
		verify (panel).addMouseClickListener (isA (LeftClickSelector.class));
	}
	
	@Test
	public void addsResetListenerForRightClick () throws Exception
	{
		listening.initialize ();
		verify (panel).addMouseClickListener (isA (RightClickResetter.class));
	}
	
	@Test
	public void addsCenterListenerForRightClick () throws Exception
	{
		listening.initialize ();
		verify (panel).addMouseClickListener (isA (RightClickCenterer.class));
	}
	
	@Test
	public void addsScalerForWheel () throws Exception
	{
		listening.initialize ();
		verify (panel).addMouseWheelListener (isA (WheelScaler.class));
	}
	
	@Test
	public void addsCursorChanger () throws Exception
	{
		listening.initialize ();
		verify (panel).addMouseMotionListener (isA (CursorChanger.class));
	}
	
	@Test
	public void addsDetailRequesterForCtrlLeftClick () throws Exception
	{
		listening.initialize ();
		verify (panel).addMouseClickListener (isA (CtrlLeftClickDetailsRequester.class));
	}
}
