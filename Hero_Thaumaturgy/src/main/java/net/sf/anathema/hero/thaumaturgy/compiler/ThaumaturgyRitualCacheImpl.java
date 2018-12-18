package net.sf.anathema.hero.thaumaturgy.compiler;

import net.sf.anathema.hero.thaumaturgy.compiler.json.template.RitualTemplate;
import net.sf.anathema.hero.thaumaturgy.model.RitualImpl;
import net.sf.anathema.hero.thaumaturgy.model.ThaumaturgyRitual;
import net.sf.anathema.library.model.OptionalEntryCategory;
import net.sf.anathema.library.model.OptionalEntryReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThaumaturgyRitualCacheImpl implements ThaumaturgyRitualCache
{
	private final Map<String, ThaumaturgyRitual> rituals = new HashMap<> ();
	
	public void addRitual (RitualTemplate option)
	{
		rituals.put (option.name, new RitualImpl (option));
	}
	
	@Override
	public List<ThaumaturgyRitual> getAllOptions ()
	{
		List<ThaumaturgyRitual> options = new ArrayList<> (rituals.values ());
		options.sort ( (m1, m2) -> m1.getTraitType ().getId ().compareTo (m2.getTraitType ().getId ()));
		return options;
	}
	
	@Override
	public ThaumaturgyRitual getOptionByReference (OptionalEntryReference reference)
	{
		return rituals.get (reference.name);
	}
	
	@Override
	public List<ThaumaturgyRitual> getAllOptionsForCategory (OptionalEntryCategory category)
	{
		return new ArrayList<> ();
	}
}
