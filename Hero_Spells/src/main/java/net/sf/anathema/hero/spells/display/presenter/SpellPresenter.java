package net.sf.anathema.hero.spells.display.presenter;

import net.sf.anathema.hero.experience.model.ExperienceModel;
import net.sf.anathema.hero.magic.display.magic.MagicLearnView;
import net.sf.anathema.hero.spells.data.CircleType;
import net.sf.anathema.hero.spells.data.Spell;
import net.sf.anathema.hero.spells.data.Spells;
import net.sf.anathema.hero.spells.model.CircleModel;
import net.sf.anathema.hero.spells.model.SpellsModel;
import net.sf.anathema.library.identifier.Identifier;
import net.sf.anathema.library.resources.Resources;
import net.sf.anathema.magic.description.model.MagicDescriptionProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SpellPresenter
{
	private final SpellsModel spellConfiguration;
	private final ButtonUpdateTrigger buttonUpdateTrigger;
	private final CombinedSpellAndMagicProperties properties;
	private final CircleModel circleModel;
	private final Resources resources;
	private final SpellView view;
	
	public SpellPresenter (CircleModel circleModel, Resources resources, SpellView view,
	MagicDescriptionProvider magicDescriptionProvider, ExperienceModel experienceModel, SpellsModel spellsModel, ButtonUpdateTrigger buttonUpdateTrigger)
	{
		this.circleModel = circleModel;
		this.spellConfiguration = spellsModel;
		this.buttonUpdateTrigger = buttonUpdateTrigger;
		this.properties = new CombinedSpellAndMagicProperties (resources, magicDescriptionProvider, spellConfiguration, experienceModel);
		this.resources = resources;
		this.view = view;
	}
	
	public void initPresentation ()
	{
		Collection<Identifier> circles = new ArrayList<> (circleModel.getCircles ());
		view.addCircleSelection (circles, properties);
		addMagicLearnView (view);
		view.addCircleSelectionListener (circleModel::selectCircle);
	}
	
	private void addMagicLearnView (final SpellView view)
	{
		final MagicLearnView magicLearnView = view.addMagicLearnView (properties);
		MagicLearnPresenter learnPresenter = new MagicLearnPresenter (magicLearnView);
		learnPresenter.initPresentation (properties);
		learnPresenter.addChangeListener (new MagicViewListener ()
		{
			@Override
			public void removeMagicRequested (Object[] removedSpells)
			{
				Spells spellList = convertToList (removedSpells);
				spellConfiguration.removeSpells (spellList);
			}
			
			@Override
			public void addMagicRequested (Object[] addedSpells)
			{
				Spells spellList = convertToList (addedSpells);
				spellConfiguration.addSpells (spellList);
			}
		}
		);
		circleModel.addSelectionListener (newValue ->
		{
			showAvailableSpells (magicLearnView);
			updateCircleInView (newValue, view);
			learnPresenter.updateButtons ();
		}
		);
		spellConfiguration.addChangeListener ( () -> refreshSpellListsInView (magicLearnView));
		refreshSpellListsInView (magicLearnView);
		updateCircleInView (circleModel.getSelectedCircle (), view);
		buttonUpdateTrigger.addChangeListener (learnPresenter::updateButtons);
	}
	
	private void updateCircleInView (CircleType newValue, SpellView view)
	{
		view.showSelectedCircle (newValue);
	}
	
	private Spells convertToList (Object[] addedSpells)
	{
		Spells spellList = new Spells ();
		for (Object spellObject : addedSpells)
		{
			spellList.add ( (Spell) spellObject);
		}
		return spellList;
	}
	
	private void refreshSpellListsInView (MagicLearnView magicLearnView)
	{
		showLearnedSpells (magicLearnView);
		showAvailableSpells (magicLearnView);
	}
	
	private void showAvailableSpells (MagicLearnView magicLearnView)
	{
		Spells availableSpells = spellConfiguration.getAvailableSpellsInCircle (circleModel.getSelectedCircle ());
		List<Spell> sortedList = new MagicSorter<Spell> (resources).sortAscending (availableSpells.asList ());
		magicLearnView.setAvailableMagic (sortedList);
	}
	
	private void showLearnedSpells (MagicLearnView magicLearnView)
	{
		Spells learnedSpells = spellConfiguration.getLearnedSpellsInCircles (circleModel.getCircles ());
		magicLearnView.setLearnedMagic (learnedSpells.asList ());
	}
}
