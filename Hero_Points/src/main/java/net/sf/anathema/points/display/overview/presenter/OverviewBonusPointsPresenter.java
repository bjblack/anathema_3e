package net.sf.anathema.points.display.overview.presenter;

import net.sf.anathema.hero.concept.model.description.HeroNameFetcher;
import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.library.message.Message;
import net.sf.anathema.library.message.MessageToken;
import net.sf.anathema.library.message.MessageType;
import net.sf.anathema.library.resources.Resources;
import net.sf.anathema.points.model.overview.SpendingModel;

import static java.text.MessageFormat.format;
import static net.sf.anathema.library.message.MessageDuration.Permanent;
import static net.sf.anathema.library.message.MessageType.Normal;
import static net.sf.anathema.library.message.MessageType.Warning;

class OverviewBonusPointsPresenter implements IOverviewSubPresenter
{
	private final MessageToken token;
	private Resources resources;
	private SpendingModel model;
	private Hero hero;
	
	public OverviewBonusPointsPresenter (Resources resources, SpendingModel model, MessageToken token, Hero hero)
	{
		this.resources = resources;
		this.model = model;
		this.token = token;
		this.hero = hero;
	}
	
	@Override
	public void update ()
	{
		String name = new HeroNameFetcher ().getName (hero);
		int spending = model.getValue ();
		int allotment = model.getAllotment ();
		String pattern;
		MessageType type;
		if (spending <= allotment)
		{
			pattern = resources.getString ("Overview.Creation.BonusPoints.Spent");
			type = Normal;
		}
		else
		{
			pattern = resources.getString ("Overview.Creation.BonusPoints.Overspent");
			type = Warning;
		}
		token.replaceMessage (new Message (format (pattern, name, spending, allotment), type, Permanent));
	}
}
