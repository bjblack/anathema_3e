package net.sf.anathema.hero.charms.display.view;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import net.miginfocom.layout.CC;
import net.sf.anathema.hero.charms.display.special.BooleanSelectionSpecialNodeView;
import net.sf.anathema.hero.charms.display.special.CategorizedSpecialView;
import net.sf.anathema.hero.charms.display.special.ToggleButtonSpecialNodeView;
import net.sf.anathema.library.fx.NodeHolder;
import net.sf.anathema.library.fx.selection.ComboBoxSelectionView;
import net.sf.anathema.library.presenter.AgnosticUIConfiguration;
import net.sf.anathema.library.view.ObjectSelectionView;
import net.sf.anathema.platform.tree.display.AgnosticPolygonPanel;
import net.sf.anathema.platform.tree.display.AgnosticTreeView;
import net.sf.anathema.platform.tree.display.CategorizedSpecialNodeView;
import net.sf.anathema.platform.tree.display.ContentFactory;
import net.sf.anathema.platform.tree.display.TreeView;
import net.sf.anathema.platform.tree.fx.FxPolygonPanel;
import net.sf.anathema.platform.tree.view.MouseBorderClosure;

import org.tbee.javafx.scene.layout.MigPane;

import java.util.Collection;

import static net.sf.anathema.library.fx.layout.LayoutUtils.fillWithoutInsets;
import static net.sf.anathema.library.fx.layout.LayoutUtils.withoutInsets;

public class FxCharmView implements CharmView, NodeHolder
{
	private final MigPane selectionPanel = new MigPane (withoutInsets ().wrapAfter (4));
	private final MigPane content = new MigPane (fillWithoutInsets ().wrapAfter (1));
	private final FxPolygonPanel viewComponent = new FxPolygonPanel ();
	private final AgnosticTreeView treeView = new AgnosticTreeView (new AgnosticPolygonPanel (viewComponent));
	
	public FxCharmView ()
	{
		content.add (selectionPanel, new CC ().growX ().pushX ());
		content.add (viewComponent.getNode (), new CC ().grow ().push ());
	}
	
	@Override
	public TreeView addTreeView ()
	{
		return treeView;
	}
	
	@Override
	public <T> ComboBoxSelectionView<T> addSelectionView (String title, AgnosticUIConfiguration<T> uiConfig)
	{
		final BorderPane borderPane = new BorderPane ();
		ComboBoxSelectionView<T> selectionView = new ComboBoxSelectionView<> (title, uiConfig);
		borderPane.centerProperty ().set (selectionView.getNode ());
		selectionPanel.add (borderPane);
		return selectionView;
	}
	
	@Override
	public <T> ObjectSelectionView<T> addSelectionViewAndSizeItFor (String title, AgnosticUIConfiguration<T> uiConfig,
	Collection<T> objects)
	{
		return addSelectionView (title, uiConfig);
	}
	
	@Override
	public void whenCursorLeavesCharmAreaResetAllPopups ()
	{
		viewComponent.addMouseBorderListener (new MouseBorderClosure ()
		{
			@Override
			public void mouseEntered ()
			{
				//nothing to do
			}
			
			@Override
			public void mouseExited ()
			{
				viewComponent.resetAllTooltips ();
			}
		}
		);
	}
	
	@Override
	public void registerSpecialType (Class contentClass, ContentFactory factory)
	{
		treeView.registerSpecialType (contentClass, factory);
	}
	
	@Override
	public ToggleButtonSpecialNodeView createToggleButtonSpecialView ()
	{
		return new BooleanSelectionSpecialNodeView ();
	}
	
	@Override
	public CategorizedSpecialNodeView createCategorizedSpecialView ()
	{
		return new CategorizedSpecialView ();
	}
	
	@Override
	public Node getNode ()
	{
		return content;
	}
}
