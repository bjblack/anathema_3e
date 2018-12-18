package net.sf.anathema.scribe.scroll.persistence;

import net.sf.anathema.library.exception.PersistenceException;
import net.sf.anathema.platform.repository.Repository;
import net.sf.anathema.platform.repository.access.RepositoryReadAccess;
import net.sf.anathema.platform.repository.access.RepositoryWriteAccess;
import net.sf.anathema.platform.repository.printname.ReferenceAccess;
import net.sf.anathema.platform.repository.printname.ReferenceBuilder;
import net.sf.anathema.platform.repository.printname.RepositoryId;
import net.sf.anathema.platform.repository.printname.SimpleRepositoryId;
import net.sf.anathema.scribe.scroll.ScrollItemType;
import net.sf.anathema.scribe.scroll.gson.ScrollGson;
import net.sf.anathema.scribe.scroll.gson.ScrollReferenceBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

public class RepositoryScrollPersister implements ScrollPersister
{
	private final Repository model;
	private final Clock clock;
	private final ScrollGson scrollGson = new ScrollGson ();
	
	public RepositoryScrollPersister (Repository model, Clock clock)
	{
		this.model = model;
		this.clock = clock;
	}
	
	@Override
	public void saveScroll (Scroll scroll)
	{
		try (OutputStream stream = createOutputStreamFor (scroll))
		{
			scrollGson.save (scroll, stream);
		}
		catch (IOException e)
		{
			throw new PersistenceException (e);
		}
	}
	
	private OutputStream createOutputStreamFor (Scroll scroll)
	{
		String repositoryId = scroll.repositoryId.getStringRepresentation ();
		RepositoryWriteAccess writeAccess = model.createWriteAccess (ScrollItemType.ITEM_TYPE, repositoryId);
		return writeAccess.createMainOutputStream ();
	}
	
	@Override
	public Scroll loadScroll (RepositoryId id)
	{
		RepositoryReadAccess readAccess = model.openReadAccess (ScrollItemType.ITEM_TYPE, id.getStringRepresentation ());
		try (InputStream inputStream =  readAccess.openMainInputStream ())
		{
			return scrollGson.load (inputStream);
		}
		catch (IOException e)
		{
			throw new PersistenceException (e);
		}
	}
	
	@Override
	public Scroll newScroll ()
	{
		ScrollDto scrollDto = new ScrollDto ("", "");
		RepositoryId id = createRepositoryId ();
		return new Scroll (scrollDto, id);
	}
	
	@Override
	public Collection<ScrollReference> listAll ()
	{
		ReferenceBuilder<ScrollReference> builder = new ScrollReferenceBuilder ();
		ReferenceAccess<ScrollReference> access = model.createReferenceAccess (ScrollItemType.ITEM_TYPE, builder);
		return access.collectAllItemReferences ();
	}
	
	@Override
	public boolean hasAny ()
	{
		return !listAll ().isEmpty ();
	}
	
	public RepositoryId createRepositoryId ()
	{
		TimedRepositoryData repositoryData = new TimedRepositoryData (clock);
		String repositoryId = model.createUniqueRepositoryId (repositoryData);
		return new SimpleRepositoryId (repositoryId);
	}
}
