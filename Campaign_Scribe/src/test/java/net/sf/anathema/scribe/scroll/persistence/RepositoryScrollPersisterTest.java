package net.sf.anathema.scribe.scroll.persistence;

import net.sf.anathema.platform.repository.Repository;
import net.sf.anathema.platform.repository.RepositoryIdData;
import net.sf.anathema.platform.repository.printname.ReferenceAccess;
import net.sf.anathema.platform.repository.printname.RepositoryId;
import net.sf.anathema.platform.repository.printname.SimpleRepositoryId;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Date;

import static net.sf.anathema.scribe.scroll.ScrollItemType.ITEM_TYPE;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RepositoryScrollPersisterTest
{
	private final Repository repository = mock (Repository.class);
	private final Date date = new Date ();
	private final Clock clock = new StaticClock (date);
	private final RepositoryScrollPersister persister = new RepositoryScrollPersister (repository, clock);
	private final static String createdId = "createdId";
	
	@Test
	public void createsNewScrollWithIdFromRepository () throws Exception
	{
		when (repository.createUniqueRepositoryId (isA (RepositoryIdData.class))).thenReturn (createdId);
		Scroll scroll = createScroll ();
		RepositoryId expectedId = new SimpleRepositoryId (createdId);
		assertThat (scroll.repositoryId, is (expectedId));
	}
	
	@Ignore
	@Test
	public void freshPersisterHasNoScrolls () throws Exception
	{
		ReferenceAccess access = DummyAccess.empty ();
		//when(repository.createReferenceAccess(eq(ITEM_TYPE), any(ReferenceBuilder.class))).thenReturn(access);
		assertThat (persister.hasAny (), is (false));
	}
	
	@Ignore
	@Test
	public void hasScrollsIfRepositoryDiscoversSome () throws Exception
	{
		ReferenceAccess access = DummyAccess.filled ();
		//when(repository.createReferenceAccess(eq(ITEM_TYPE), any(ReferenceBuilder.class))).thenReturn(access);
		assertThat (persister.hasAny (), is (true));
	}
	
	@Test
	public void requestsIdWithScrollItemType () throws Exception
	{
		RepositoryIdData data = createScrollAndCaptureData ();
		assertThat (data.getItemType (), is (ITEM_TYPE));
	}
	
	@Test
	public void suggestsTimeFromClockAsId () throws Exception
	{
		RepositoryIdData data = createScrollAndCaptureData ();
		assertThat (data.getIdProposal (), is (String.valueOf (date.getTime ())));
	}
	
	private RepositoryIdData createScrollAndCaptureData ()
	{
		ArgumentCaptor<RepositoryIdData> captor = ArgumentCaptor.forClass (RepositoryIdData.class);
		when (repository.createUniqueRepositoryId (captor.capture ())).thenReturn (createdId);
		createScroll ();
		return captor.getValue ();
	}
	
	private Scroll createScroll ()
	{
		return persister.newScroll ();
	}
}
