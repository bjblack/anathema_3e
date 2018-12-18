package net.sf.anathema.equipment.editor.stats.model.impl;

import net.sf.anathema.equipment.editor.stats.model.IEquipmentStatisticsModel;
import net.sf.anathema.library.event.ChangeListener;
import net.sf.anathema.library.text.ITextualDescription;
import net.sf.anathema.library.text.SimpleTextualDescription;

import org.jmock.example.announcer.Announcer;

public class EquipmentStatisticsModel implements IEquipmentStatisticsModel
{
	private final ITextualDescription name = new SimpleTextualDescription ();
	private final Announcer<ChangeListener> announcer = Announcer.to (ChangeListener.class);
	
	public EquipmentStatisticsModel ()
	{
		name.addTextChangedListener (text -> EquipmentStatisticsModel.this.announceValidationChange ());
	}
	
	@Override
	public final ITextualDescription getName ()
	{
		return name;
	}
	
	@Override
	public void addValidListener (ChangeListener listener)
	{
		announcer.addListener (listener);
	}
	
	@Override
	public boolean isValid ()
	{
		return isNameDefined ();
	}
	
	protected void announceValidationChange ()
	{
		announcer.announce ().changeOccurred ();
	}
	
	private boolean isNameDefined ()
	{
		return !name.isEmpty ();
	}
}
