package net.sf.anathema.hero.spells.compiler;

import net.sf.anathema.magic.data.cost.CostList;
import net.sf.anathema.magic.data.reference.SpellName;
import net.sf.anathema.hero.magic.compiler.json.CostParser;
import net.sf.anathema.hero.spells.data.Spell;
import net.sf.anathema.hero.spells.data.SpellImpl;
import net.sf.anathema.magic.data.source.SourceBookImpl;
import net.sf.anathema.magic.data.source.SourceListImpl;

import java.util.ArrayList;
import java.util.List;

public class SpellBuilder
{
	public List<Spell> buildSpells (SpellListTemplate listTemplate)
	{
		List<Spell> spells = new ArrayList<> ();
		listTemplate.spells.forEach ( (name, template) ->
		{
			CostList costList = new CostParser ().parse (template.cost);
			List<String> keywords = template.keywords;
			String duration = template.duration;
			SourceListImpl sourceList = new SourceListImpl ();
			
			template.source.forEach (source -> sourceList.addSource (new SourceBookImpl (source)));
			spells.add (new SpellImpl (new SpellName (name), template.circle, costList, sourceList, duration, keywords));
		}
		);
		return spells;
	}
}
