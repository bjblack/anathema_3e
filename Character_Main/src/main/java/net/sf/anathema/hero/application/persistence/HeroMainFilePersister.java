package net.sf.anathema.hero.application.persistence;

import net.sf.anathema.hero.application.item.Item;
import net.sf.anathema.hero.concept.model.description.HeroNameFetcher;
import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.individual.splat.SplatType;
import net.sf.anathema.library.exception.PersistenceException;
import net.sf.anathema.library.io.InputOutput;
import net.sf.anathema.platform.repository.access.RepositoryReadAccess;
import net.sf.anathema.platform.repository.access.RepositoryWriteAccess;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HeroMainFilePersister
{
	private final Gson gson = new GsonBuilder ().setPrettyPrinting ().create ();
	
	public HeroMainFileDto load (RepositoryReadAccess readAccess)
	{
		InputStream inputStream = readAccess.openMainInputStream ();
		return load (inputStream);
	}
	
	public HeroMainFileDto load (InputStream inputStream)
	{
		try
		{
			String jsonString = InputOutput.toString (inputStream);
			return gson.fromJson (jsonString, HeroMainFileDto.class);
		}
		catch (IOException e)
		{
			throw new PersistenceException (e);
		}
	}
	
	public void save (RepositoryWriteAccess writeAccess, Item item)
	{
		HeroMainFileDto mainFileDto = convertHeroToDto (item);
		saveDtoAsJson (writeAccess, mainFileDto);
	}
	
	private void saveDtoAsJson (RepositoryWriteAccess writeAccess, HeroMainFileDto mainFileDto)
	{
		String mainFileJson = gson.toJson (mainFileDto);
		try (OutputStream outputStream = writeAccess.createMainOutputStream ())
		{
			InputOutput.write (mainFileJson, outputStream);
		}
		catch (IOException e)
		{
			throw new PersistenceException (e);
		}
	}
	
	private HeroMainFileDto convertHeroToDto (Item item)
	{
		Hero hero = (Hero) item.getItemData ();
		SplatType splatType = hero.getSplat ().getTemplateType ();
		return createDtoToSave (item, hero, splatType);
	}
	
	private HeroMainFileDto createDtoToSave (Item item, Hero hero, SplatType splatType)
	{
		HeroMainFileDto mainFileDto = new HeroMainFileDto ();
		mainFileDto.printName = new HeroNameFetcher ().getName (hero);
		mainFileDto.repositoryId = item.getRepositoryLocation ().getId ();
		mainFileDto.characterType.characterType = splatType.getHeroType ().getId ();
		mainFileDto.characterType.subType = splatType.getSubType ().getId ();
		return mainFileDto;
	}
}
