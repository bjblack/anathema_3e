package net.sf.anathema.hero.charms.model.options;

import net.sf.anathema.magic.data.Charm;
import net.sf.anathema.magic.data.reference.CategoryReference;
import net.sf.anathema.magic.data.reference.CharmName;
import net.sf.anathema.magic.data.reference.TreeName;
import net.sf.anathema.magic.data.reference.TreeReference;
import net.sf.anathema.hero.charms.compiler.CharmProvider;
import net.sf.anathema.hero.charms.model.CharmTree;
import net.sf.anathema.hero.charms.model.CharmTreeImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class CharmTreeCategoryImpl implements CharmTreeCategory
{
	public static CharmTreeCategory CreateFor (CharmOptionCheck check, CharmProvider provider, CategoryReference reference)
	{
		Collection<Charm> charms = provider.getCharms (reference);
		return new CharmTreeCategoryImpl (check, charms, reference);
	}
	
	private final Map<CharmName, Charm> charmById = new HashMap<> ();
	private CharmOptionCheck optionCheck;
	private Collection<Charm> allCharms;
	private CategoryReference category;
	
	public CharmTreeCategoryImpl (CharmOptionCheck optionCheck, Collection<Charm> allCharms, CategoryReference category)
	{
		this.optionCheck = optionCheck;
		this.allCharms = allCharms;
		this.category = category;
		for (Charm charm : allCharms)
		{
			charmById.put (charm.getName (), charm);
		}
	}
	
	@Override
	public final Charm getCharmById (CharmName charmID)
	{
		return charmById.get (charmID);
	}
	
	@Override
	public boolean exists (CharmName charmId)
	{
		return charmById.containsKey (charmId);
	}
	
	public final Collection<Charm> getAllCharms ()
	{
		return allCharms;
	}
	
	private void addCharmTreesFor (Collection<TreeName> treeNameList, List<CharmTree> treeList, Collection<Charm> charms)
	{
		for (Charm charm : charms)
		{
			TreeName treeName = charm.getTreeReference ().name;
			if (!treeNameList.contains (treeName) && optionCheck.isValidOptionForHeroType (charm))
			{
				treeNameList.add (treeName);
				List<Charm> treeCharms = getAllCharmsForTree (treeName);
				treeList.add (new CharmTreeImpl (new TreeReference (category, treeName), treeCharms));
			}
		}
	}
	
	@Override
	public boolean isEmpty ()
	{
		return allCharms.isEmpty ();
	}
	
	@Override
	public final Collection<CharmTree> getAllCharmTrees ()
	{
		Set<TreeName> treeNameList = new HashSet<> ();
		List<CharmTree> treeList = new ArrayList<> ();
		addCharmTreesFor (treeNameList, treeList, getAllCharms ());
		return treeList;
	}
	
	public final List<Charm> getAllCharmsForTree (TreeName treeName)
	{
		List<Charm> groupCharms = new ArrayList<> ();
		for (Charm charm : getAllCharms ())
		{
			if (charm.getTreeReference ().name.equals (treeName))
			{
				groupCharms.add (charm);
			}
		}
		return groupCharms;
	}
	
	@Override
	public CategoryReference getReference ()
	{
		return category;
	}
}
