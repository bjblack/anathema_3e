package net.sf.anathema.hero.application.creation;

import net.sf.anathema.hero.individual.change.ChangeAnnouncer;
import net.sf.anathema.hero.individual.change.ChangeAnnouncerImpl;
import net.sf.anathema.hero.individual.history.ChangeHistoryImpl;
import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.individual.model.HeroModel;
import net.sf.anathema.hero.individual.splat.HeroSplat;
import net.sf.anathema.library.identifier.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HeroImpl implements Hero
{
	private final ChangeAnnouncer changeAnnouncer;
	private Map<String, HeroModel> modelsById = new HashMap<> ();
	private boolean fullyLoaded = false;
	private final HeroSplat template;
	private List<HeroModel> orderedModels = new ArrayList<> ();
	private ChangeHistoryImpl history = new ChangeHistoryImpl (this);
	
	public HeroImpl (HeroSplat template)
	{
		this.template = template;
		this.changeAnnouncer = new ChangeAnnouncerImpl (history);
	}
	
	@Override
	public HeroSplat getSplat ()
	{
		return template;
	}
	
	@Override
	public ChangeAnnouncer getChangeAnnouncer ()
	{
		return changeAnnouncer;
	}
	
	@Override
	public <M extends HeroModel> M getModel (Identifier id)
	{
		return (M) modelsById.get (id.getId ());
	}
	
	@Override
	public boolean isFullyLoaded ()
	{
		return fullyLoaded;
	}
	
	public void setFullyLoaded (boolean fullyLoaded)
	{
		this.fullyLoaded = fullyLoaded;
	}
	
	public void addModel (HeroModel model)
	{
		orderedModels.add (model);
		modelsById.put (model.getId ().getId (), model);
	}
	
	@Override
	public Iterator<HeroModel> iterator ()
	{
		return orderedModels.iterator ();
	}
}
