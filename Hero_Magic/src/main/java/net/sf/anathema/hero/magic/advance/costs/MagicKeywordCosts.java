package net.sf.anathema.hero.magic.advance.costs;

import net.sf.anathema.magic.data.attribute.MagicAttribute;

import java.util.HashMap;
import java.util.Map;

public class MagicKeywordCosts
{
	private Map<String, Integer> keywordCosts;
	
	public MagicKeywordCosts (Map<String, Integer> keywordCosts)
	{
		this.keywordCosts = keywordCosts == null ? new HashMap<> () : keywordCosts;
	}
	
	public boolean hasCostFor (Iterable<MagicAttribute> attributes)
	{
		return getCostAttribute (attributes) != null;
	}
	
	public int getCostFor (Iterable<MagicAttribute> attributes)
	{
		MagicAttribute costAttribute = getCostAttribute (attributes);
		return keywordCosts.get (costAttribute.getId ());
	}
	
	private MagicAttribute getCostAttribute (Iterable<MagicAttribute> attributes)
	{
		for (MagicAttribute attribute : attributes)
		{
			if (keywordCosts.containsKey (attribute.getId ()))
			{
				return attribute;
			}
		}
		return null;
	}
}
