package net.sf.anathema.library.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.anathema.hero.environment.HeroEnvironment;
import net.sf.anathema.hero.individual.change.ChangeAnnouncer;
import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.individual.model.RemovableEntryChangeAdapter;
import net.sf.anathema.hero.traits.model.Trait;
import net.sf.anathema.hero.traits.model.event.TraitValueChangedListener;
import net.sf.anathema.library.event.ChangeListener;

import org.jmock.example.announcer.Announcer;

public abstract class AbstractOptionalTraitModel<
	C extends OptionalTraitCategory,
	O extends OptionalTraitOption,
	T extends KnownOptionalTrait<O>>
	extends AbstractRemovableEntryModel<T>
	implements OptionalTraitsModel<C, O, T> {
	
  private final Map<OptionalTraitReference, Collection<String>> suggestions = new HashMap<>();
	
	private final Announcer<ChangeListener> currentCategoryChangeAnnouncer = Announcer.to(ChangeListener.class);
	private final Announcer<ChangeListener> currentOptionChangeAnnouncer = Announcer.to(ChangeListener.class);
	
	private final boolean hasCategories;
	protected Hero hero;
	protected OptionalTraitCache<C, O> cache;
	private ChangeAnnouncer change;
	private String currentDescription = "";
	private C currentCategory;
	private O currentOption;
	
	protected AbstractOptionalTraitModel(boolean hasCategories) {
		this.hasCategories = hasCategories;
	}
	
	@Override
  public void initialize(HeroEnvironment environment, Hero hero) {
    this.hero = hero;
    cache = initCache(environment);
    

    if (hasCategories) {
    	currentCategory = getAvailableCategories().get(0);
    }
  }
	
	protected abstract OptionalTraitCache<C, O> initCache(HeroEnvironment environment);
	
	@SuppressWarnings("unchecked")
  @Override
  public void initializeListening(final ChangeAnnouncer announcer) {
    addModelChangeListener((RemovableEntryListener) new RemovableEntryChangeAdapter<>(announcer));
    change = announcer;
  }
	
	@Override
	public boolean hasCategories() {
		return hasCategories;
	}
	
	@Override
	public void resetCurrentEntry() {
		currentDescription = "";
    selectFirstOption();
	}
	
	public void selectFirstOption() {
    List<O> currentOptions = getCurrentTraitOptions();
    if (currentOptions.isEmpty()) {
      setSelectedTraitOption(getNullOption());
      return;
    }
    setSelectedTraitOption(currentOptions.get(0));
  }
	
	@Override
	public List<T> getKnownTraits() {
		return getEntries();
	}
	
	@Override
  protected T createEntry() {
    T trait = createKnownTrait(getSelectedTraitOption(), currentDescription, hero);
    trait.addCurrentValueListener(new TraitValueChangedListener(change, trait));
    return trait;
  }
	
	protected abstract T createKnownTrait(O option, String description, Hero hero);
	
	@Override
	public List<O> getAllTraitOptions() {
		return cache.getAllOptions();
	}
	
	@Override
	public List<O> getCurrentTraitOptions() {
		List<O> options = hasCategories ? cache.getAllOptionsForCategory(currentCategory) :
			cache.getAllOptions();
    options.removeIf(item -> !isAllowedOption(item));
    return options;
	}
	
	protected abstract boolean isAllowedOption(O option);

	@Override
	public O getSelectedTraitOption() {
		return currentOption;
	}
	
	@Override
	public void setSelectedTraitOption(O option) {
		if (option == null) {
      option = getNullOption();
    }
		if (option.equals(currentOption)) {
			return;
		}
    this.currentOption = option;
    currentOptionChangeAnnouncer.announce().changeOccurred();
    fireEntryChanged();
	}
	
	@Override
	public void whenSelectedOptionChanges(ChangeListener listener) {
		currentOptionChangeAnnouncer.addListener(listener);
	}
	
	@Override
  public O findOptionByReference(OptionalTraitReference reference) {
    return cache.getOptionByReference(reference);
  }
	
	@Override
  public void setCurrentDescription(String description) {
    this.currentDescription = description;
    fireEntryChanged();
  }
	

  public void addSuggestions(OptionalTraitReference merit, Collection<String> suggestionsForReference) {
    suggestions.put(merit, suggestionsForReference);
  }
	
	@Override
	public Collection<String> getSuggestedDescriptions() {
		return currentOption.getSuggestions();
	}
	
	protected boolean knowsTrait(O option) {
		return getKnownTraits().stream().anyMatch(trait -> trait.getBaseOption().equals(option));
	}
	
	protected abstract O getNullOption();
	
	@Override
	public List<C> getAvailableCategories() {
		return new ArrayList<>();
	}
	
	@Override
	public C getCurrentCategory() {
		return currentCategory;
	}
	
	@Override
	public void setCurrentCategory(C category) {
		if (category.equals(currentCategory)) {
			return;
		}
		currentCategory = category;
		currentCategoryChangeAnnouncer.announce().changeOccurred();
		fireEntryChanged();
	}
	
	@Override
	public void whenCategoryChanges(ChangeListener listener) {
		currentCategoryChangeAnnouncer.addListener(listener);
	}
	
	@Override
	public List<Trait> getContingentTraits() {
		return new ArrayList<>();
	}
}
