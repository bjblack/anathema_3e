package net.sf.anathema.equipment.editor.presenter;

import net.sf.anathema.equipment.editor.model.IEquipmentDatabaseManagement;
import net.sf.anathema.library.interaction.model.Command;
import net.sf.anathema.library.model.Condition;
import net.sf.anathema.library.resources.Resources;
import net.sf.anathema.library.view.Vetor;
import net.sf.anathema.platform.repositorytree.VetorFactory;

public class DiscardChangesVetor implements Vetor
{
	private final Condition preCondition;
	private final Resources resources;
	private final VetorFactory factory;
	
	public DiscardChangesVetor (IEquipmentDatabaseManagement model, VetorFactory factory, Resources resources)
	{
		this.preCondition = new DirtyEquipmentCondition (model);
		this.factory = factory;
		this.resources = resources;
	}
	
	@Override
	public void requestPermissionFor (Command command)
	{
		if (!preCondition.isFulfilled ())
		{
			command.execute ();
			return;
		}
		String messageText = resources.getString ("Equipment.Creation.UnsavedChangesMessage.Text");
		String title = resources.getString ("AnathemaCore.DialogTitle.ConfirmationDialog");
		Vetor vetor = factory.createVetor (title, messageText);
		vetor.requestPermissionFor (command);
	}
}
