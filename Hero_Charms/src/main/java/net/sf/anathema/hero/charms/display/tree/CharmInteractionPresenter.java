package net.sf.anathema.hero.charms.display.tree;

import net.sf.anathema.library.presenter.Presenter;
import net.sf.anathema.platform.tree.display.TreeView;

public interface CharmInteractionPresenter extends Presenter
{
	void operateOn (TreeView treeView);
}
