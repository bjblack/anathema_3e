package net.sf.anathema.equipment.editor.stats.model;

import net.sf.anathema.equipment.stats.IEquipmentStats;
import net.sf.anathema.library.event.ChangeListener;

import java.util.List;

public interface StatsEditModel
{
	void addStatsChangeListener (ChangeListener changeListener);
	
	EquipmentStatsFactory getStatsCreationFactory ();
	
	List<IEquipmentStats> getStats ();
	
	void addStatistics (IEquipmentStats equipmentStats);
	
	void replaceSelectedStatistics (IEquipmentStats newStats);
	
	void removeSelectedStatistics ();
	
	void selectStats (IEquipmentStats selected);
	
	IEquipmentStats getSelectedStats ();
	
	void whenSelectedStatsChanges (ChangeListener changeListener);
	
	boolean hasSelectedStats ();
	
	void clearStatsSelection ();
}
