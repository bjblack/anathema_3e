package net.sf.anathema.platform.repositorytree;

import net.sf.anathema.library.event.ChangeListener;
import net.sf.anathema.platform.item.IItemType;
import net.sf.anathema.platform.item.ItemTypeCollection;
import net.sf.anathema.platform.repository.PrintNameFile;
import net.sf.anathema.platform.repository.Repository;
import net.sf.anathema.platform.repository.access.RepositoryFileAccess;
import net.sf.anathema.platform.repository.access.RepositoryWriteAccess;

import org.jmock.example.announcer.Announcer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RepositoryTreeModel implements IRepositoryTreeModel
{
	private final IItemType[] integratedItemTypes;
	private final Announcer<IRepositoryTreeModelListener> control = Announcer.to (IRepositoryTreeModelListener.class);
	private final Announcer<ChangeListener> changeControl = Announcer.to (ChangeListener.class);
	private final Repository repository;
	private final ItemTypeCollection itemTypes;
	private List<Object> currentlySelectedUserObjects = new ArrayList<> ();
	private final RepositoryFileAccessFactory repositoryFileAccessFactory;
	
	public RepositoryTreeModel (Repository repository, ItemTypeCollection itemTypes)
	{
		this.repository = repository;
		this.itemTypes = itemTypes;
		this.integratedItemTypes = createIntegratedItemTypes ();
		this.repositoryFileAccessFactory = new RepositoryFileAccessFactory (repository);
	}
	
	private IItemType[] createIntegratedItemTypes ()
	{
		List<IItemType> integratedItemTypes = new ArrayList<> ();
		for (IItemType itemType : itemTypes)
		{
			if (itemType.isIntegrated ())
			{
				integratedItemTypes.add (itemType);
			}
		}
		return integratedItemTypes.toArray (new IItemType[integratedItemTypes.size ()]);
	}
	
	@Override
	public IItemType[] getAllItemTypes ()
	{
		return integratedItemTypes;
	}
	
	@Override
	public Collection<PrintNameFile> getPrintNameFiles (IItemType itemType)
	{
		return repository.getPrintNameFileAccess ().collectAllPrintNameFiles (itemType);
	}
	
	@Override
	public void addRepositoryTreeModelListener (IRepositoryTreeModelListener listener)
	{
		control.addListener (listener);
	}
	
	@Override
	public boolean canSelectionBeDeleted ()
	{
		for (Object currentlySelectedUserObject : currentlySelectedUserObjects)
		{
			if (! (currentlySelectedUserObject instanceof PrintNameFile))
			{
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void deleteSelection ()
	{
		if (!canSelectionBeDeleted ())
		{
			return;
		}
		for (Object object : currentlySelectedUserObjects)
		{
			PrintNameFile file = (PrintNameFile) object;
			repository.deleteAssociatedItem (file);
			control.announce ().printNameFileRemoved (file);
		}
	}
	
	@Override
	public String getRepositoryPath ()
	{
		return repository.getRepositoryPath ();
	}
	
	@Override
	public void setSelectedObject (Collection<Object> objects)
	{
		currentlySelectedUserObjects.clear ();
		currentlySelectedUserObjects.addAll (objects);
		changeControl.announce ().changeOccurred ();
	}
	
	@Override
	public void addTreeSelectionChangeListener (ChangeListener changeListener)
	{
		changeControl.addListener (changeListener);
	}
	
	@Override
	public PrintNameFile[] getPrintNameFilesInSelection ()
	{
		List<PrintNameFile> files = new ArrayList<> ();
		for (Object object : currentlySelectedUserObjects)
		{
			if (isPrintNameFile (object))
			{
				files.add ( (PrintNameFile) object);
			}
		}
		return files.toArray (new PrintNameFile[files.size ()]);
	}
	
	@Override
	public String createUniqueId (final IItemType type, final String id)
	{
		return repository.createUniqueRepositoryId (new ConfigurableRepositoryData (id, type));
	}
	
	@Override
	public IItemType getItemTypeForId (String id)
	{
		return itemTypes.getById (id);
	}
	
	@Override
	public String getMainFilePath (IItemType type, String id)
	{
		return repository.getRepositoryFileResolver ().getMainFile (type.getRepositoryConfiguration (), id).getPath ();
	}
	
	@Override
	public RepositoryFileAccess getFileAccess (PrintNameFile printNameFile)
	{
		return repositoryFileAccessFactory.getFileAccess (printNameFile);
	}
	
	private boolean isPrintNameFile (Object object)
	{
		return object instanceof PrintNameFile;
	}
	
	@Override
	public RepositoryWriteAccess getWriteAccess (IItemType type, String id)
	{
		return repository.createWriteAccess (type, id);
	}
	
	@Override
	public void refreshItem (IItemType type, String id)
	{
		control.announce ().printNameFileAdded (repository.getPrintNameFileAccess ().getPrintNameFile (type, id));
	}
}
