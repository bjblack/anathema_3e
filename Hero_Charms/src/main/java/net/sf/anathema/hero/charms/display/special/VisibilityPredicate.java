package net.sf.anathema.hero.charms.display.special;

import net.sf.anathema.magic.data.Charm;
import net.sf.anathema.magic.data.reference.CharmName;
import net.sf.anathema.hero.charms.display.presenter.CharmGroupInformer;
import net.sf.anathema.hero.charms.model.CharmMap;
import net.sf.anathema.hero.charms.model.CharmTree;

import com.google.common.base.Predicate;

public class VisibilityPredicate implements Predicate<String>
{
	private final CharmMap charmMap;
	private final CharmGroupInformer charmGroupInformer;
	
	public VisibilityPredicate (CharmMap charmMap, CharmGroupInformer informer)
	{
		this.charmMap = charmMap;
		this.charmGroupInformer = informer;
	}
	
	@Override
	public boolean apply (String charmId)
	{
		Charm charm = charmMap.getCharmById (new CharmName (charmId));
		return isVisible (charm);
	}
	
	private boolean isVisible (Charm charm)
	{
		if (!charmGroupInformer.hasGroupSelected ())
		{
			return false;
		}
		CharmTree group = charmGroupInformer.getCurrentTree ();
		return group.isCharmFromTree (charm);
	}
}
