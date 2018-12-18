package net.sf.anathema.hero.spiritual.model.pool;

import net.sf.anathema.library.event.ChangeListener;

public interface EssencePoolStrategy
{
	int getFullPersonalPool ();
	
	int getFullPeripheralPool ();
	
	int getExtendedPersonalPool ();
	
	int getExtendedPeripheralPool ();
	
	int getStandardPersonalPool ();
	
	int getStandardPeripheralPool ();
	
	int getUnmodifiedPersonalPool ();
	
	int getUnmodifiedPeripheralPool ();
	
	int getAttunementExpenditures ();
	
	void addPoolChangeListener (ChangeListener listener);
}
