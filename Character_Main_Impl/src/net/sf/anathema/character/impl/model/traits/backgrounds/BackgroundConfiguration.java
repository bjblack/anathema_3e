package net.sf.anathema.character.impl.model.traits.backgrounds;

import java.util.ArrayList;
import java.util.List;

import net.disy.commons.core.util.Ensure;
import net.disy.commons.core.util.ObjectUtilities;
import net.sf.anathema.character.generic.additionalrules.IAdditionalRules;
import net.sf.anathema.character.generic.backgrounds.IBackgroundTemplate;
import net.sf.anathema.character.generic.framework.additionaltemplate.model.ITraitContext;
import net.sf.anathema.character.generic.impl.backgrounds.CustomizedBackgroundTemplate;
import net.sf.anathema.character.generic.rules.IExaltedEdition;
import net.sf.anathema.character.generic.template.ITemplateType;
import net.sf.anathema.character.generic.template.ITraitTemplateCollection;
import net.sf.anathema.character.generic.traits.ITraitTemplate;
import net.sf.anathema.character.library.trait.DefaultTrait;
import net.sf.anathema.character.library.trait.FriendlyValueChangeChecker;
import net.sf.anathema.character.library.trait.IModifiableTrait;
import net.sf.anathema.character.library.trait.rules.TraitRules;
import net.sf.anathema.character.model.background.IBackgroundConfiguration;
import net.sf.anathema.character.model.background.IBackgroundListener;
import net.sf.anathema.lib.collection.Predicate;
import net.sf.anathema.lib.registry.IIdentificateRegistry;

public class BackgroundConfiguration implements IBackgroundConfiguration {

  private final List<IModifiableTrait> backgrounds = new ArrayList<IModifiableTrait>();
  private final List<IBackgroundListener> listeners = new ArrayList<IBackgroundListener>();
  private final IIdentificateRegistry<IBackgroundTemplate> backgroundRegistry;
  private final IAdditionalRules additionalRules;
  private final ITraitTemplateCollection traitTemplates;
  private final ITraitContext context;
  private final ITemplateType templateType;
  private final IExaltedEdition edition;

  public BackgroundConfiguration(
      ITemplateType templateType,
      IAdditionalRules additionalRules,
      ITraitTemplateCollection traitTemplates,
      ITraitContext context,
      IIdentificateRegistry<IBackgroundTemplate> backgroundRegistry,
      IExaltedEdition edition) {
    this.templateType = templateType;
    this.context = context;
    this.backgroundRegistry = backgroundRegistry;
    this.additionalRules = additionalRules;
    this.traitTemplates = traitTemplates;
    this.edition = edition;
  }

  public IBackgroundTemplate[] getAllAvailableBackgroundTemplates() {
    List<IBackgroundTemplate> backgroundList = new ArrayList<IBackgroundTemplate>();
    for (IBackgroundTemplate backgroundTemplate : backgroundRegistry.getAll()) {
      if (backgroundTemplate.acceptsTemplate(templateType, edition) && !additionalRules.isRejected(backgroundTemplate)) {
        backgroundList.add(backgroundTemplate);
      }
    }
    return backgroundList.toArray(new IBackgroundTemplate[backgroundList.size()]);
  }

  public IModifiableTrait addBackground(String customBackgroundName) {
    Ensure.ensureNotNull(customBackgroundName);
    return addBackground(new CustomizedBackgroundTemplate(customBackgroundName));
  }

  public IModifiableTrait addBackground(final IBackgroundTemplate backgroundType) {
    Ensure.ensureNotNull(backgroundType);
    IModifiableTrait foundBackground = new Predicate<IModifiableTrait>() {
      public boolean evaluate(IModifiableTrait listBackground) {
        return ObjectUtilities.equals(backgroundType, listBackground.getType());
      }
    }.find(backgrounds);
    if (foundBackground != null) {
      return null;
    }
    ITraitTemplate traitTemplate = traitTemplates.getTraitTemplate(backgroundType);
    TraitRules rules = new TraitRules(backgroundType, traitTemplate, context.getLimitationContext());
    IModifiableTrait background = new DefaultTrait(rules, context.getTraitValueStrategy(), new FriendlyValueChangeChecker());
    backgrounds.add(background);
    fireBackgroundAddedEvent(background);
    return background;
  }

  public IModifiableTrait[] getBackgrounds() {
    return backgrounds.toArray(new IModifiableTrait[backgrounds.size()]);
  }

  public synchronized void addBackgroundListener(IBackgroundListener listener) {
    listeners.add(listener);
  }

  private synchronized void fireBackgroundAddedEvent(IModifiableTrait background) {
    List<IBackgroundListener> cloneListeners = new ArrayList<IBackgroundListener>(listeners);
    for (IBackgroundListener listener : cloneListeners) {
      listener.backgroundAdded(background);
    }
  }

  public void removeBackground(IModifiableTrait background) {
    backgrounds.remove(background);
    fireBackgroundRemovedEvent(background);
  }

  private synchronized void fireBackgroundRemovedEvent(IModifiableTrait background) {
    List<IBackgroundListener> cloneListeners = new ArrayList<IBackgroundListener>(listeners);
    for (IBackgroundListener listener : cloneListeners) {
      listener.backgroundRemoved(background);
    }
  }

  public IModifiableTrait getBackgroundByTemplate(IBackgroundTemplate type) {
    Ensure.ensureNotNull("Background type must not be null.", type); //$NON-NLS-1$
    for (IModifiableTrait background : getBackgrounds()) {
      if (type.equals(background.getType())) {
        return background;
      }
    }
    return null;
  }
}