package net.sf.anathema.hero.application.persistence;

import net.sf.anathema.hero.individual.persistence.HeroModelSaver;
import net.sf.anathema.platform.repository.access.RepositoryWriteAccess;

import java.io.OutputStream;

public class HeroModelSaverImpl implements HeroModelSaver
{
	private final RepositoryWriteAccess writeAccess;
	
	public HeroModelSaverImpl (RepositoryWriteAccess writeAccess)
	{
		this.writeAccess = writeAccess;
	}
	
	@Override
	public OutputStream openOutputStream (String persistenceId)
	{
		return writeAccess.createSubOutputStream (persistenceId);
	}
}
