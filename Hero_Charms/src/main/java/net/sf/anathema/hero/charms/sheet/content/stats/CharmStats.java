package net.sf.anathema.hero.charms.sheet.content.stats;

import net.sf.anathema.magic.data.Charm;
import net.sf.anathema.hero.charms.sheet.content.CharmContentHelper;
import net.sf.anathema.library.resources.Resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CharmStats extends AbstractCharmStats
{
	protected final CharmContentHelper content;
	
	public CharmStats (Charm charm, CharmContentHelper content)
	{
		super (charm);
		this.content = content;
	}
	
	@Override
	protected Collection<String> getDetailKeys ()
	{
		Collection<String> detailKeys = super.getDetailKeys ();
		List<String> details = new ArrayList<> ();
		details.addAll (detailKeys);
		if (content.isSubEffectCharm (getMagic ()))
		{
			for (String subeffectId : content.getLearnedEffects (getMagic ()))
			{
				details.add (getMagic ().getName ().text + ".Subeffects." + subeffectId);
			}
		}
		return details;
	}
	
	@Override
	public String getNameString (Resources resources)
	{
		StringBuilder nameString = new StringBuilder ();
		nameString.append (resources.getString (getMagic ().getName ().text));
		int learnCount = content.getLearnCount (getMagic ());
		if (learnCount > 1)
		{
			nameString.append (" (");
			nameString.append (learnCount);
			nameString.append ("x)");
		}
		return nameString.toString ();
	}
}
