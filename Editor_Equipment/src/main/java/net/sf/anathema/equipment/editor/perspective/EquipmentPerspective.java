package net.sf.anathema.equipment.editor.perspective;

import net.sf.anathema.equipment.database.IEquipmentDatabase;
import net.sf.anathema.equipment.database.gson.GsonEquipmentDatabase;
import net.sf.anathema.equipment.editor.database.EquipmentDatabaseManagement;
import net.sf.anathema.equipment.editor.model.IEquipmentDatabaseManagement;
import net.sf.anathema.equipment.editor.presenter.EquipmentDatabasePresenter;
import net.sf.anathema.equipment.editor.view.fx.FxEquipmentDatabaseView;
import net.sf.anathema.library.initialization.Weight;
import net.sf.anathema.library.resources.RelativePath;
import net.sf.anathema.library.resources.Resources;
import net.sf.anathema.platform.environment.Environment;
import net.sf.anathema.platform.frame.ApplicationModel;
import net.sf.anathema.platform.fx.environment.UiEnvironment;
import net.sf.anathema.platform.fx.perspective.Container;
import net.sf.anathema.platform.fx.perspective.UtilityPerspective;
import net.sf.anathema.platform.messaging.MessageCategory;
import net.sf.anathema.platform.utility.UtilityAutoCollector;
import net.sf.anathema.platform.utility.UtilityToggle;

@UtilityAutoCollector
@Weight (weight = 100)
public class EquipmentPerspective implements UtilityPerspective
{
	@Override
	public void configureToggle (UtilityToggle toggle)
	{
		toggle.setIcon (new RelativePath ("icons/EquipmentPerspective.png"));
		toggle.setTooltip ("EquipmentDatabase.Perspective.Name");
	}
	
	@Override
	public void initContent (Container container, ApplicationModel applicationModel, Environment environment, UiEnvironment uiEnvironment)
	{
		IEquipmentDatabaseManagement databaseManagement = createDatabaseManagement (applicationModel);
		initInFx (container, environment, databaseManagement,uiEnvironment);
	}
	
	@Override
	public MessageCategory getMessageCategory ()
	{
		return new MessageCategory ("Equipment");
	}
	
	private void initInFx (Container container, Resources resources, IEquipmentDatabaseManagement databaseManagement, UiEnvironment uiEnvironment)
	{
		FxEquipmentDatabaseView view = new FxEquipmentDatabaseView (uiEnvironment);
		new EquipmentDatabasePresenter (resources, databaseManagement, view.view).initPresentation ();
		container.setContent (view.utilityPane.getNode ());
	}
	
	private IEquipmentDatabaseManagement createDatabaseManagement (ApplicationModel model)
	{
		IEquipmentDatabase database = GsonEquipmentDatabase.CreateFrom (model);
		return new EquipmentDatabaseManagement (database);
	}
}
