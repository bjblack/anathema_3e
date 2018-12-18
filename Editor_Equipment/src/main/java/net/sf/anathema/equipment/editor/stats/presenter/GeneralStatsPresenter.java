package net.sf.anathema.equipment.editor.stats.presenter;

import net.sf.anathema.equipment.editor.presenter.EquipmentStatsDialog;
import net.sf.anathema.equipment.editor.presenter.EquipmentStatsView;
import net.sf.anathema.equipment.editor.stats.model.IEquipmentStatisticsCreationModel;
import net.sf.anathema.equipment.editor.stats.model.IEquipmentStatisticsModel;
import net.sf.anathema.library.message.Message;
import net.sf.anathema.library.message.MessageType;
import net.sf.anathema.library.resources.Resources;
import net.sf.anathema.library.text.ITextView;
import net.sf.anathema.library.text.ITextualDescription;
import net.sf.anathema.library.text.TextualPresentation;

public class GeneralStatsPresenter
{
	private final EquipmentStatsView view;
	private EquipmentStatsDialog dialog;
	private final IEquipmentStatisticsModel model;
	private IEquipmentStatisticsCreationModel overallModel;
	private Resources resources;
	
	public GeneralStatsPresenter (EquipmentStatsView view, EquipmentStatsDialog dialog, IEquipmentStatisticsModel model,
	IEquipmentStatisticsCreationModel overallModel, Resources resources)
	{
		this.view = view;
		this.dialog = dialog;
		this.model = model;
		this.overallModel = overallModel;
		this.resources = resources;
	}
	
	public void initPresentation ()
	{
		initBasicPresentation ();
		initNamePresentation ();
		initCompletionPresentation ();
	}
	
	private void initBasicPresentation ()
	{
		dialog.setMessage (getDefaultMessage ());
		dialog.setTitle (getPageDescription ());
	}
	
	private void initCompletionPresentation ()
	{
		model.addValidListener (GeneralStatsPresenter.this::updateValidity);
		updateValidity ();
	}
	
	private void updateValidity ()
	{
		if (model.isValid () && isNameUnique ())
		{
			dialog.setCanFinish ();
		}
		else
		{
			dialog.setCannotFinish ();
		}
	}
	
	private void initNamePresentation ()
	{
		ITextualDescription name = model.getName ();
		if (name.isEmpty ())
		{
			name.setText (getDefaultName ());
		}
		ITextView textView = view.addLineTextView (getNameLabel ());
		new TextualPresentation ().initView (textView, name);
	}
	
	
	private boolean isNameUnique ()
	{
		return overallModel.isNameUnique (model.getName ().getText ());
	}
	
	public Message getDefaultMessage ()
	{
		return new Message (resources.getString ("Equipment.Creation.Stats.DefaultMessage"), MessageType.Normal);
	}
	
	public String getPageDescription ()
	{
		return resources.getString ("Equipment.Creation.Stats.PageTitle");
	}
	
	public String getDefaultName ()
	{
		return resources.getString ("Equipment.Creation.Stats.DefaultName");
	}
	
	public String getNameLabel ()
	{
		return resources.getString ("Equipment.Creation.Stats.Name") + ":";
	}
}
