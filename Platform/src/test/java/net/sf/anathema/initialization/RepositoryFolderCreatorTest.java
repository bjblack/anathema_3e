package net.sf.anathema.initialization;

import net.sf.anathema.library.exception.PersistenceException;
import net.sf.anathema.platform.repository.RepositoryFolderCreator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class RepositoryFolderCreatorTest
{
	private DummyFileSystem dummyFileSystemAbstraction;
	
	@Before
	public void setUp ()
	{
		dummyFileSystemAbstraction = new DummyFileSystem ();
	}
	
	@Test
	public void testExistingFolder () throws Exception
	{
		dummyFileSystemAbstraction.addExistingFile (new File ("folder"));
		File repositoryFolder = createRepositoryFolder ("folder");
		Assert.assertFalse (dummyFileSystemAbstraction.wasCreated (repositoryFolder));
		Assert.assertEquals (repositoryFolder, new File ("folder"));
	}
	
	@Test
	public void testNonExistingFoldersWillBeCreated () throws Exception
	{
		File repositoryFolder = createRepositoryFolder ("nonExistingFolder");
		Assert.assertEquals (repositoryFolder, new File ("nonExistingFolder"));
		Assert.assertTrue (dummyFileSystemAbstraction.wasCreated (new File ("nonExistingFolder")));
	}
	
	@Test (expected = PersistenceException.class)
	public void testWriteProtectedFile () throws Exception
	{
		final String writeProtectedFilePath = "writeProtected";
		dummyFileSystemAbstraction.addWriteProtectedFile (new File (writeProtectedFilePath));
		createRepositoryFolder (writeProtectedFilePath);
	}
	
	@Test (expected = PersistenceException.class)
	public void testReadProtectedFile () throws Exception
	{
		final String readProtectedFilePath = "readProtected";
		dummyFileSystemAbstraction.addReadProtectedFile (new File (readProtectedFilePath));
		createRepositoryFolder (readProtectedFilePath);
	}
	
	private File createRepositoryFolder (final String repositoryPath)
	{
		return new RepositoryFolderCreator (dummyFileSystemAbstraction, () -> repositoryPath).createRepositoryFolder ();
	}
}
