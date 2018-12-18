package net.sf.anathema.equipment.database.gson;

import net.sf.anathema.equipment.database.IEquipmentDatabase;
import net.sf.anathema.equipment.editor.module.EquipmentItemType;
import net.sf.anathema.equipment.template.IEquipmentTemplate;
import net.sf.anathema.library.event.ChangeListener;
import net.sf.anathema.library.io.Filenames;
import net.sf.anathema.platform.frame.ApplicationModel;
import net.sf.anathema.platform.item.IItemType;
import net.sf.anathema.platform.repository.Repository;

import org.jmock.example.announcer.Announcer;

import com.google.common.collect.Lists;

import java.nio.file.Path;
import java.util.List;

public class GsonEquipmentDatabase implements IEquipmentDatabase
{
	public static final String DATABASE_FOLDER = "equipment";
	
	public static GsonEquipmentDatabase CreateFrom (ApplicationModel anathemaModel)
	{
		Repository repository = anathemaModel.getRepository ();
		IItemType itemType = new EquipmentItemType ().getItemType ();
		return new GsonEquipmentDatabase (new EquipmentRepositoryAccess (repository, itemType));
	}
	
	private final Announcer<ChangeListener> availableTemplatesChangeControl = Announcer.to (ChangeListener.class);
	private final EquipmentGson gson = new EquipmentGson ();
	private final EquipmentAccess access;
	
	public GsonEquipmentDatabase (EquipmentAccess access)
	{
		this.access = access;
	}
	
	@Override
	public String[] getAllAvailableTemplateIds ()
	{
		List<String> ids = Lists.newArrayList ();
		for (Path file : access.listAllFiles ())
		{
			String id = Filenames.getBaseName (file.getFileName ().toString ());
			ids.add (loadExistingTemplate (id).getName ());
		}
		return ids.toArray (new String[ids.size ()]);
	}
	
	@Override
	public IEquipmentTemplate loadTemplate (String templateId)
	{
		String id = FilenameCleaner.clean (templateId);
		if (!access.exists (id))
		{
			return null;
		}
		return loadExistingTemplate (id);
	}
	
	@Override
	public void saveTemplate (IEquipmentTemplate template)
	{
		save (template);
		availableTemplatesChangeControl.announce ().changeOccurred ();
	}
	
	public void saveTemplateNoOverwrite (IEquipmentTemplate template)
	{
		if (loadTemplate (template.getName ()) == null)
		{
			saveTemplate (template);
		}
	}
	
	@Override
	public void addAvailableTemplateChangeListener (ChangeListener listener)
	{
		availableTemplatesChangeControl.addListener (listener);
	}
	
	@Override
	public void deleteTemplate (String editTemplateId)
	{
		delete (editTemplateId);
		availableTemplatesChangeControl.announce ().changeOccurred ();
	}
	
	@Override
	public void updateTemplate (String editTemplateId, IEquipmentTemplate saveTemplate)
	{
		delete (editTemplateId);
		saveTemplate (saveTemplate);
	}
	
	private void delete (String editTemplateId)
	{
		String id = FilenameCleaner.clean (editTemplateId);
		if (access.exists (id))
		{
			access.delete (id);
		}
	}
	
	private IEquipmentTemplate loadExistingTemplate (String templateId)
	{
		String id = FilenameCleaner.clean (templateId);
		String json = access.read (id);
		return gson.fromJson (json);
	}
	
	private void save (IEquipmentTemplate template)
	{
		String id = FilenameCleaner.clean (template.getName ());
		String json = gson.toJson (template);
		access.write (id, json);
	}
	
	public boolean isEmpty ()
	{
		return getAllAvailableTemplateIds ().length == 0;
	}
}
