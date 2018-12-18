package net.sf.anathema.library.model.trait;

import com.google.common.base.Strings;
import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.traits.model.TraitImpl;
import net.sf.anathema.hero.traits.model.TraitRules;
import net.sf.anathema.hero.traits.model.rules.ModificationType;
import net.sf.anathema.hero.traits.template.LimitationTemplate;
import net.sf.anathema.hero.traits.template.LimitationType;
import net.sf.anathema.hero.traits.template.TraitTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractPossessedOptionalTrait<O extends OptionalTraitOption> extends TraitImpl implements PossessedOptionalTrait<O>
{
	private final O optionBase;
	private final String description;
	private final List<String> suggestions = new ArrayList<> ();
	
	protected AbstractPossessedOptionalTrait (Hero hero, O optionBase, TraitRules rules, String description)
	{
		super (hero, rules);
		this.description = description;
		this.optionBase = optionBase;
	}
	
	protected static TraitRules createTraitRules (OptionalTraitOption base,
	Hero hero,
	ModificationType modification)
	{
		TraitTemplate template = createTraitTemplate (base, modification);
		return new OptionalTraitRulesImpl (base, template, hero);
	}
	
	private static TraitTemplate createTraitTemplate (OptionalTraitOption base, ModificationType modification)
	{
		TraitTemplate template = new TraitTemplate ();
		LimitationTemplate limitation = new LimitationTemplate ();
		limitation.type = LimitationType.Static;
		limitation.value = base.getMaximumValue ();
		template.minimumValue = base.getMinimumValue ();
		template.startValue = template.minimumValue;
		template.modificationType = modification;
		template.limitation = limitation;
		return template;
	}
	
	public String getId ()
	{
		return optionBase.getTraitType ().getId ();
	}
	
	public Collection<String> getSuggestions ()
	{
		return suggestions;
	}
	
	@Override
	public O getBaseOption ()
	{
		return optionBase;
	}
	
	@Override
	public String getDescription ()
	{
		return description;
	}
	
	@Override
	public String toString ()
	{
		return optionBase.getTraitType ().getId () + (!Strings.isNullOrEmpty (description) ? " (" + description + ")" : "");
	}
	
	@Override
	public boolean equals (Object obj)
	{
		if (! (obj instanceof PossessedOptionalTrait))
		{
			return false;
		}
		PossessedOptionalTrait<?> otherTrait = (PossessedOptionalTrait<?>) obj;
		if (!optionBase.equals (otherTrait.getBaseOption ()))
		{
			return false;
		}
		if (Strings.isNullOrEmpty (description) && Strings.isNullOrEmpty (otherTrait.getDescription ()))
		{
			return true;
		}
		return description != null && description.equals (otherTrait.getDescription ());
	}
}
