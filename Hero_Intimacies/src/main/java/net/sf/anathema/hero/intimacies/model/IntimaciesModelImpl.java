package net.sf.anathema.hero.intimacies.model;

import net.sf.anathema.hero.environment.HeroEnvironment;
import net.sf.anathema.hero.experience.model.ExperienceModelFetcher;
import net.sf.anathema.hero.individual.change.ChangeAnnouncer;
import net.sf.anathema.hero.individual.change.FlavoredChangeListener;
import net.sf.anathema.hero.individual.change.UnspecifiedChangeListener;
import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.individual.model.RemovableEntryChangeAdapter;
import net.sf.anathema.library.event.ChangeListener;
import net.sf.anathema.library.identifier.Identifier;
import net.sf.anathema.library.model.AbstractRemovableEntryModel;
import net.sf.anathema.library.model.RemovableEntryListener;

import org.jmock.example.announcer.Announcer;

import com.google.common.base.Strings;

public class IntimaciesModelImpl extends AbstractRemovableEntryModel<Intimacy> implements IntimaciesModel
{
	private final Announcer<ChangeListener> announcer = Announcer.to (ChangeListener.class);
	private String name;
	private Hero hero;
	private Strength strength = Strength.Defining;
	private Outlook outlook = Outlook.Positive;
	private Bond bond = Bond.Tie;
	
	@Override
	public Identifier getId ()
	{
		return ID;
	}
	
	@Override
	public void initialize (HeroEnvironment environment, Hero hero)
	{
		this.hero = hero;
	}
	
	@SuppressWarnings ("unchecked")
	@Override
	public void initializeListening (final ChangeAnnouncer announcer)
	{
		addModelChangeListener (new UnspecifiedChangeListener (announcer));
		addModelChangeListener ( (RemovableEntryListener) new RemovableEntryChangeAdapter<> (announcer));
	}
	
	@Override
	public void setCurrentName (String name)
	{
		this.name = name;
		fireEntryChanged ();
	}
	
	@Override
	public void setCurrentStrength (Strength strength)
	{
		this.strength = strength;
		fireEntryChanged ();
	}
	
	@Override
	public void setCurrentOutlook (Outlook outlook)
	{
		this.outlook = outlook;
		fireEntryChanged ();
	}
	
	@Override
	public void setCurrentBond (Bond bond)
	{
		this.bond = bond;
	}
	
	@Override
	public void addChangeListener (FlavoredChangeListener listener)
	{
		hero.getChangeAnnouncer ().addListener (listener);
	}
	
	@Override
	protected Intimacy createEntry ()
	{
		return new IntimacyImpl (name, strength, outlook, bond);
	}
	
	@Override
	protected boolean isEntryAllowed ()
	{
		return !Strings.isNullOrEmpty (name) && strength != null && outlook != null && bond != null;
	}
	
	@Override
	public void addModelChangeListener (ChangeListener listener)
	{
		announcer.addListener (listener);
	}
	
	@Override
	public boolean isCharacterExperienced ()
	{
		return ExperienceModelFetcher.fetch (hero).isExperienced ();
	}
	
	@Override
	public Strength[] getStrengths ()
	{
		return Strength.values ();
	}
	
	@Override
	public Bond[] getBonds ()
	{
		return Bond.values ();
	}
	
	@Override
	public Outlook[] getOutlooks ()
	{
		return Outlook.values ();
	}
	
	@Override
	public Strength getStrength ()
	{
		return strength;
	}
	
	@Override
	public Outlook getOutlook ()
	{
		return outlook;
	}
	
	@Override
	public Bond getBond ()
	{
		return bond;
	}
}
