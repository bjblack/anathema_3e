package net.sf.anathema.points.display.overview.presenter;

import net.sf.anathema.hero.individual.overview.OverviewCategory;

import java.util.LinkedHashMap;
import java.util.Map;

public class MappedOverviewCategories implements OverviewCategories
{
	private final Map<String, OverviewCategory> categoriesById = new LinkedHashMap<> ();
	
	@Override
	public OverviewCategory get (String categoryId)
	{
		return categoriesById.get (categoryId);
	}
	
	public void put (String categoryId, OverviewCategory category)
	{
		categoriesById.put (categoryId, category);
	}
}
