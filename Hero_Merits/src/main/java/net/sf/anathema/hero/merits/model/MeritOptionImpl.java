package net.sf.anathema.hero.merits.model;

import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.merits.compiler.json.template.MeritTemplate;
import net.sf.anathema.hero.merits.compiler.json.template.requirements.MeritRequirementsTemplate;
import net.sf.anathema.hero.merits.compiler.template.mechanics.MeritMechanicalDetailTemplate;
import net.sf.anathema.hero.merits.model.mechanics.MechanicalDetail;
import net.sf.anathema.hero.merits.model.requirements.MeritRequirement;
import net.sf.anathema.hero.traits.model.TraitType;
import net.sf.anathema.library.model.OptionalEntryReference;
import net.sf.anathema.library.model.trait.AbstractOptionalTraitOption;

import java.util.ArrayList;
import java.util.List;

public class MeritOptionImpl extends AbstractOptionalTraitOption implements MeritOption
{
	private final String name;
	private final MeritCategory type;
	private final boolean allowRepurchase;
	private final boolean[] legalValues = new boolean[MAX_MERIT_RATING + 1];
	private final List<MeritRequirement> requirements = new ArrayList<> ();
	private final List<MechanicalDetail> mechanics = new ArrayList<> ();
	private final List<String> suggestions = new ArrayList<> ();
	private final TraitType traitType;
	
	public MeritOptionImpl (MeritTemplate template)
	{
		this.name = template.name;
		this.traitType = new TraitType (template.name);
		this.allowRepurchase = template.repurchases;
		this.type = MeritCategory.valueOf (template.type);
		this.suggestions.addAll (template.suggestions);
		parseLegalValues (template.values);
		parseRequirements (template.requirements);
		parseMechanics (template.mechanics);
	}
	
	private void parseRequirements (List<MeritRequirementsTemplate> templateRequirements)
	{
		for (MeritRequirementsTemplate template : templateRequirements)
		{
			requirements.add (template.generate ());
		}
	}
	
	private void parseLegalValues (String values)
	{
		for (String value : values.split (","))
		{
			value = value.trim ();
			if (value.contains ("-"))
			{
				String[] range = value.split ("-");
				int start = Integer.parseInt (range[0]);
				int end = Integer.parseInt (range[1]);
				
				assert (start >= 0 && start <= MAX_MERIT_RATING);
				assert (end > start && end <= MAX_MERIT_RATING);
				
				for (int i = start; i <= end; i++)
				{
					legalValues[i] = true;
				}
				continue;
			}
			int position = Integer.parseInt (value);
			assert (position >= 0 && position <= MAX_MERIT_RATING);
			legalValues[position] = true;
		}
	}
	
	private void parseMechanics (List<MeritMechanicalDetailTemplate> mechanicTemplates)
	{
		mechanicTemplates.forEach (mechanic -> mechanics.add (mechanic.generate ()));
	}
	
	@Override
	public List<String> getContingentTraitTypes ()
	{
		List<String> traits = new ArrayList<> ();
		for (MeritRequirement requirement : requirements)
		{
			for (String trait : requirement.getContingentTraitTypes ())
			{
				if (!traits.contains (trait))
				{
					traits.add (trait);
				}
			}
		}
		return traits;
	}
	
	@Override
	public boolean isHeroEligible (Hero hero)
	{
		for (MeritRequirement requirement : requirements)
		{
			if (!requirement.isSatisfied (hero))
			{
				return false;
			}
		}
		return true;
	}
	
	@Override
	public MeritCategory getCategory ()
	{
		return type;
	}
	
	@Override
	public boolean allowsRepurchase ()
	{
		return allowRepurchase;
	}
	
	@Override
	public int getMinimumValue ()
	{
		for (int i = 0; i <= MAX_MERIT_RATING; i++)
		{
			if (isLegalValue (i))
			{
				return i;
			}
		}
		return 5;
	}
	
	@Override
	public int getMaximumValue ()
	{
		for (int i = MAX_MERIT_RATING; i >= 0; i--)
		{
			if (isLegalValue (i))
			{
				return i;
			}
		}
		return 5;
	}
	
	@Override
	public TraitType getTraitType ()
	{
		return traitType;
	}
	
	@Override
	public boolean isReferencedBy (OptionalEntryReference reference)
	{
		return reference.name.equals (name);
	}
	
	@Override
	public boolean isLegalValue (int value)
	{
		if (value < 0 || value > MAX_MERIT_RATING)
		{
			return false;
		}
		return legalValues[value];
	}
	
	@Override
	public boolean equals (Object obj)
	{
		if (obj instanceof MeritOption)
		{
			return ( (MeritOption) obj).getTraitType ().equals (getTraitType ());
		}
		return false;
	}
	
	@Override
	public int hashCode ()
	{
		return getTraitType ().hashCode ();
	}
	
	@Override
	public String toString ()
	{
		return getTraitType ().toString ();
	}
	
	@Override
	public List<MechanicalDetail> getMechanics ()
	{
		return mechanics;
	}
}
