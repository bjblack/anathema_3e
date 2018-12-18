package net.sf.anathema.hero.display.fx.perspective;

import net.sf.anathema.hero.application.perspective.CharacterButtonPresenter;
import net.sf.anathema.hero.application.perspective.HeroRoster;
import net.sf.anathema.hero.application.perspective.ShowOnSelect;
import net.sf.anathema.hero.application.perspective.model.CharacterItemModel;
import net.sf.anathema.hero.application.perspective.model.ItemSystemModel;
import net.sf.anathema.library.interaction.model.ToggleTool;
import net.sf.anathema.library.resources.RelativePath;
import net.sf.anathema.library.resources.Resources;

public class HeroRosterPresenter
{
	private final ItemSystemModel model;
	private final HeroesStanceView view;
	private final ShowOnSelect selector;
	private final Resources resources;
	
	public HeroRosterPresenter (ItemSystemModel model, HeroesStanceView view, ShowOnSelect selector, Resources resources)
	{
		this.model = model;
		this.view = view;
		this.selector = selector;
		this.resources = resources;
	}
	
	public void initPresentation ()
	{
		ToggleTool catalogueTool = view.getFrontInteractionView ().addToggleTool ();
		catalogueTool.setText (resources.getString ("CharacterSystem.Tools.Roster.Name"));
		catalogueTool.setIcon (new RelativePath ("icons/King-icon.png"));
		catalogueTool.setTooltip (resources.getString ("CharacterSystem.Tools.Roster.Tooltip"));
		HeroRoster roster = view.createHeroRoster ();
		catalogueTool.setCommand ( () ->
		{
			roster.clear ();
			showAllHeroesInRoster (roster);
			view.getStackView ().showView (roster.getIdentifier ());
			catalogueTool.select ();
		}
		);
	}
	
	private void showAllHeroesInRoster (HeroRoster roster)
	{
		for (CharacterItemModel character : model.getAllKnownHeroes ())
		{
			addHeroToRoster (roster, character);
		}
	}
	
	private void addHeroToRoster (HeroRoster roster, CharacterItemModel character)
	{
		new CharacterButtonPresenter (resources, selector, character, roster).initPresentation ();
	}
}
