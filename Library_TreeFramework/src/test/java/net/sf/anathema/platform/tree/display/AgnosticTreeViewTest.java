package net.sf.anathema.platform.tree.display;

import net.sf.anathema.library.presenter.RGBColor;
import net.sf.anathema.platform.tree.view.container.Cascade;
import net.sf.anathema.platform.tree.view.interaction.LeftClickPanner;
import net.sf.anathema.platform.tree.view.interaction.PolygonPanel;
import net.sf.anathema.platform.tree.view.interaction.ToolTipListener;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Matchers;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class AgnosticTreeViewTest
{
	private final PolygonPanel panel = mock (PolygonPanel.class);
	private final AgnosticTreeView treeView = new AgnosticTreeView (panel);
	Cascade cascade = mock (Cascade.class);
	
	@Test
	public void setsBackgroundColorOnCorrespondingNode () throws Exception
	{
		treeView.loadCascade (cascade, true);
		treeView.colorNode ("ID", RGBColor.White, RGBColor.Black);
		verify (cascade).colorNode ("ID", RGBColor.White, RGBColor.Black);
	}
	
	@Test
	public void triggersRepaintAfterColoring () throws Exception
	{
		treeView.loadCascade (cascade, true);
		treeView.colorNode ("ID", RGBColor.White, RGBColor.Black);
		InOrder inOrder = inOrder (cascade, panel);
		inOrder.verify (cascade).colorNode (anyString (), Matchers.any (RGBColor.class), eq (RGBColor.Black));
		inOrder.verify (panel).refresh ();
	}
	
	@Test
	public void setsBackgroundFillOnPolygonPanel () throws Exception
	{
		treeView.setCanvasBackground (RGBColor.Red);
		verify (panel).setBackground (RGBColor.Red);
	}
	
	@Test
	public void notifiesListenersOfLoading () throws Exception
	{
		CascadeLoadedListener listener = mock (CascadeLoadedListener.class);
		treeView.addCascadeLoadedListener (listener);
		treeView.loadCascade (cascade, true);
		verify (listener).cascadeLoaded ();
	}
	
	@Test
	public void zoomsOutALittleAfterNotificationSoAllSpecialControlsAreInitializedWhenItHappens () throws Exception
	{
		CascadeLoadedListener listener = mock (CascadeLoadedListener.class);
		treeView.addCascadeLoadedListener (listener);
		treeView.loadCascade (cascade, true);
		InOrder inOrder = inOrder (listener, panel);
		inOrder.verify (listener).cascadeLoaded ();
		inOrder.verify (panel).scale (0.75);
	}
	
	@Test
	public void paintsCascadeOnCanvas () throws Exception
	{
		treeView.loadCascade (cascade, true);
		verify (cascade).addTo (panel);
	}
	
	@Test
	public void clearsCanvasBeforeAdding () throws Exception
	{
		treeView.loadCascade (cascade, true);
		InOrder inOrder = inOrder (panel, cascade);
		inOrder.verify (panel).clear ();
		inOrder.verify (cascade).addTo (panel);
	}
	
	@Test
	public void isEmptyAfterClear () throws Exception
	{
		treeView.clear ();
		verify (panel).clear ();
	}
	
	@Test
	public void resetsPanelDuringClear () throws Exception
	{
		treeView.clear ();
		verify (panel).resetTransformation ();
	}
	
	@Test
	public void forgetsCascadeOnClear () throws Exception
	{
		treeView.loadCascade (cascade, true);
		treeView.clear ();
		reset (cascade);
		treeView.colorNode ("X", RGBColor.Black, RGBColor.Black);
		verifyZeroInteractions (cascade);
	}
	
	@Test
	public void initializesNodeNamesOnCascadesAndRepaints () throws Exception
	{
		NodePresentationProperties properties = mock (NodePresentationProperties.class);
		treeView.loadNodeNamesFrom (properties);
		treeView.loadCascade (cascade, true);
		treeView.initNodeNames ();
		InOrder inOrder = inOrder (cascade, panel);
		inOrder.verify (cascade).initNodeNames (properties);
		inOrder.verify (panel).refresh ();
	}
	
	@Test
	public void registersToolTipListener ()
	{
		ToolTipProperties properties = mock (ToolTipProperties.class);
		treeView.initToolTips (properties);
		verify (panel).addMouseMotionListener (isA (ToolTipListener.class));
	}
	
	@Test
	public void registersListenersOnPanel () throws Exception
	{
		verify (panel).addMousePressListener (isA (LeftClickPanner.class));
	}
}
