package net.sf.anathema.herotype.solar.model.curse;

import net.sf.anathema.hero.environment.HeroEnvironment;
import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.library.event.ChangeListener;
import net.sf.anathema.library.event.GlobalChangeAdapter;

public abstract class AbstractLimitBreakModel implements LimitBreakModel
{
	@Override
	public void initialize (HeroEnvironment environment, Hero hero)
	{
	}
	
	public void addChangeListener (ChangeListener listener)
	{
		GlobalChangeAdapter<String> adapter = new GlobalChangeAdapter<> (listener);
		getLimitBreak ().getLimitTrait ().addCurrentValueListener (adapter);
	}
}
