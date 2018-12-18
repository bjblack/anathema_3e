package net.sf.anathema.equipment.editor.presenter;

import net.sf.anathema.equipment.editor.stats.presenter.dialog.OperationResultHandler;
import net.sf.anathema.library.message.Message;

public interface EquipmentStatsDialog
{
	void setCanFinish ();
	
	void setCannotFinish ();
	
	void setMessage (Message message);
	
	void setTitle (String title);
	
	void show (OperationResultHandler handler);
	
	EquipmentStatsView getEquipmentStatsView ();
}
