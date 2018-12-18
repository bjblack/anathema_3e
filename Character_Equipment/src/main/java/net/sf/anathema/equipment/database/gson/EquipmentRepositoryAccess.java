package net.sf.anathema.equipment.database.gson;

import net.sf.anathema.platform.item.IItemType;
import net.sf.anathema.platform.repository.Repository;
import net.sf.anathema.platform.repository.RepositoryStringAccess;

import java.nio.file.Path;
import java.util.Collection;

public class EquipmentRepositoryAccess implements EquipmentAccess
{
	private final Repository repository;
	private final IItemType equipmentType;
	private final RepositoryStringAccess repositoryStringAccess;
	
	public EquipmentRepositoryAccess (Repository repository, IItemType equipmentType)
	{
		this.repository = repository;
		this.equipmentType = equipmentType;
		this.repositoryStringAccess = new RepositoryStringAccess (repository, equipmentType);
	}
	
	@Override
	public Collection<Path> listAllFiles ()
	{
		return repository.getRepositoryFileResolver ().listAllFiles (equipmentType.getRepositoryConfiguration ());
	}
	
	@SuppressWarnings ("ResultOfMethodCallIgnored")
	@Override
	public void delete (String id)
	{
		repository.getRepositoryFileResolver ().getMainFile (equipmentType.getRepositoryConfiguration (), id).delete ();
	}
	
	@Override
	public boolean exists (String templateId)
	{
		return repository.knowsItem (equipmentType, templateId);
	}
	
	@Override
	public String read (String id)
	{
		return repositoryStringAccess.read (id);
	}
	
	@Override
	public void write (String id, String json)
	{
		repositoryStringAccess.write (id, json);
	}
}
