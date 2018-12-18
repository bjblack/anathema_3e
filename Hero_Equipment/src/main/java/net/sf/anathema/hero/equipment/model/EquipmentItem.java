package net.sf.anathema.hero.equipment.model;

import net.sf.anathema.equipment.character.IEquipmentItem;
import net.sf.anathema.equipment.stats.IArtifactStats;
import net.sf.anathema.equipment.stats.IEquipmentStats;
import net.sf.anathema.equipment.stats.IWeaponStats;
import net.sf.anathema.equipment.stats.ItemStatsSet;
import net.sf.anathema.equipment.template.IEquipmentTemplate;
import net.sf.anathema.equipment.template.ItemCost;
import net.sf.anathema.library.event.ChangeListener;
import net.sf.anathema.library.lang.StringUtilities;

import org.jmock.example.announcer.Announcer;

import java.util.HashSet;
import java.util.Set;

public class EquipmentItem implements IEquipmentItem
{
	private final Set<IEquipmentStats> printedStats = new HashSet<> ();
	private final Announcer<ChangeListener> changeControl = Announcer.to (ChangeListener.class);
	private final IEquipmentTemplate template;
	private String customTitle = null;
	private String customDescription = null;
	
	public EquipmentItem (IEquipmentTemplate template)
	{
		this.template = template;
		printedStats.addAll (template.getStatsList ());
		initPrintStats ();
	}
	
	@Override
	public String getTitle ()
	{
		return customTitle != null ? customTitle : getTemplateId ();
	}
	
	@Override
	public String getDescription ()
	{
		return customDescription != null ? customDescription : getBaseDescription ();
	}
	
	@Override
	public String getTemplateId ()
	{
		return template.getName ();
	}
	
	@Override
	public String getBaseDescription ()
	{
		return template.getDescription ();
	}
	
	private String getNewValue (String input, String baseValue)
	{
		if (StringUtilities.isNullOrTrimmedEmpty (input) || input.equals (baseValue))
		{
			return null;
		}
		else
		{
			return input;
		}
	}
	
	@Override
	public void setPersonalization (IEquipmentItem item)
	{
		EquipmentItem template = (EquipmentItem) item;
		setPersonalization (template.customTitle, template.customDescription);
	}
	
	@Override
	public void setPersonalization (String title, String description)
	{
		this.customTitle = getNewValue (title, getTemplateId ());
		this.customDescription = getNewValue (description, getBaseDescription ());
		announceChange ();
	}
	
	@Override
	public ItemCost getCost ()
	{
		return template.getCost ();
	}
	
	@Override
	public ItemStatsSet getStats ()
	{
		return getViews ();
	}
	
	private ItemStatsSet getViews ()
	{
		ItemStatsSet statsSet = new ItemStatsSet ();
		for (IEquipmentStats stats : template.getStatsList ())
		{
			if (stats instanceof IWeaponStats)
			{
				statsSet.adopt ( ( (IWeaponStats) stats).getViews ());
			}
			else
			{
				statsSet.add (stats);
			}
		}
		return statsSet;
	}
	
	@Override
	public boolean isAttuned ()
	{
		for (IEquipmentStats stats : getViews ())
		{
			if (stats instanceof IArtifactStats && isPrintEnabled (stats))
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	@SuppressWarnings ("unchecked")
	public boolean isPrintEnabled (IEquipmentStats stats)
	{
		if (stats instanceof Proxy<?>)
		{
			stats = ( (Proxy<? extends IEquipmentStats>) stats).getUnderlying ();
		}
		return printedStats.contains (stats);
	}
	
	@Override
	@SuppressWarnings ("unchecked")
	public void setPrintEnabled (IEquipmentStats stats, boolean enabled)
	{
		if (stats instanceof Proxy<?>)
		{
			stats = ( (Proxy<? extends IEquipmentStats>) stats).getUnderlying ();
		}
		if (isPrintEnabled (stats) == enabled)
		{
			return;
		}
		if (enabled)
		{
			printedStats.add (stats);
		}
		else
		{
			printedStats.remove (stats);
		}
		announceChange ();
	}
	
	@Override
	public void setUnprinted ()
	{
		printedStats.clear ();
		announceChange ();
	}
	
	@Override
	public void setPrinted (String printedStatId)
	{
		for (IEquipmentStats view : getViews ())
		{
			if (view.getId ().equals (printedStatId))
			{
				setPrintEnabled (view, true);
				return;
			}
		}
	}
	
	@Override
	public IEquipmentStats getStat (String statId)
	{
		for (IEquipmentStats view : getViews ())
		{
			if (view.getId ().equals (statId))
			{
				return view;
			}
		}
		return null;
	}
	
	@Override
	public void addChangeListener (ChangeListener listener)
	{
		changeControl.addListener (listener);
	}
	
	@Override
	public void removeChangeListener (ChangeListener listener)
	{
		changeControl.removeListener (listener);
	}
	
	@Override
	public void setTitle (String title)
	{
		this.customTitle = getNewValue (title, getTemplateId ());
		announceChange ();
	}
	
	@Override
	public void setDescription (String description)
	{
		this.customDescription = getNewValue (description, getBaseDescription ());
		announceChange ();
	}
	
	private void announceChange ()
	{
		changeControl.announce ().changeOccurred ();
	}
	
	public String toString ()
	{
		return template.getName ();
	}
	
	private void initPrintStats ()
	{
		for (IEquipmentStats stat : getViews ())
		{
			printedStats.add (stat);
		}
	}
}
