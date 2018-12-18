package net.sf.anathema.hero.charms.display.tree;

import net.sf.anathema.magic.description.display.ShowMagicDetailListener;
import net.sf.anathema.platform.tree.display.NodeInteractionListener;

public class ShowDetailInteractionListener implements NodeInteractionListener
{
	private final ShowMagicDetailListener listener;
	
	public ShowDetailInteractionListener (ShowMagicDetailListener listener)
	{
		this.listener = listener;
	}
	
	@Override
	public void nodeSelected (String nodeId)
	{
		// nothing to do
	}
	
	@Override
	public void nodeDetailsDemanded (String nodeId)
	{
		listener.showDetail (nodeId);
	}
}
