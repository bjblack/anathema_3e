package net.sf.anathema.hero.application.persistence;

import net.sf.anathema.hero.application.item.HeroItem;
import net.sf.anathema.hero.application.item.HeroItemDataImp;
import net.sf.anathema.hero.application.item.Item;
import net.sf.anathema.hero.concept.model.description.HeroNameFetcher;
import net.sf.anathema.hero.environment.HeroEnvironment;
import net.sf.anathema.hero.environment.template.SplatTypeImpl;
import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.individual.model.HeroModel;
import net.sf.anathema.hero.individual.persistence.HeroModelPersister;
import net.sf.anathema.hero.individual.splat.HeroSplat;
import net.sf.anathema.hero.individual.splat.HeroType;
import net.sf.anathema.library.exception.PersistenceException;
import net.sf.anathema.library.identifier.Identifier;
import net.sf.anathema.library.identifier.SimpleIdentifier;
import net.sf.anathema.library.message.MessageToken;
import net.sf.anathema.library.message.Messaging;
import net.sf.anathema.platform.repository.access.RepositoryReadAccess;
import net.sf.anathema.platform.repository.access.RepositoryWriteAccess;

import static net.sf.anathema.library.message.MessageType.Information;

public class HeroItemPersister implements RepositoryItemPersister
{
	private final HeroEnvironment generics;
	private final Messaging messaging;
	private final HeroPersisterList persisterList;
	
	public HeroItemPersister (HeroEnvironment generics, Messaging messaging)
	{
		this.generics = generics;
		this.messaging = messaging;
		this.persisterList = new HeroPersisterList (generics.getObjectFactory ());
	}
	
	@Override
	public Item createNew (HeroSplat template) throws PersistenceException
	{
		return createCharacterInItem (template, new NewCharacterInitializer ());
	}
	
	@Override
	public void save (RepositoryWriteAccess writeAccess, Item item) throws PersistenceException
	{
		Hero hero = (Hero) item.getItemData ();
		String name = new HeroNameFetcher ().getName (hero);
		MessageToken token = messaging.addTemporaryMessage (Information, "CharacterPersistence.SavingCharacter", name);
		saveModels (writeAccess, hero);
		new HeroMainFilePersister ().save (writeAccess, item);
		token.replaceMessage (Information, "CharacterPersistence.SavingCharacterDone", name);
	}
	
	@Override
	public Item load (RepositoryReadAccess readAccess) throws PersistenceException
	{
		HeroMainFileDto mainFileDto = new HeroMainFilePersister ().load (readAccess);
		HeroSplat template = loadHeroTemplate (mainFileDto);
		CharacterInitializer initializer = new LoadingCharacterInitializer (readAccess, persisterList, messaging);
		Item item = createCharacterInItem (template, initializer);
		item.getRepositoryLocation ().setId (mainFileDto.repositoryId);
		return item;
	}
	
	private HeroSplat loadHeroTemplate (HeroMainFileDto mainFileDto)
	{
		HeroType heroType = generics.getHeroTypes ().findById (mainFileDto.characterType.characterType);
		Identifier subtype = new SimpleIdentifier (mainFileDto.characterType.subType);
		SplatTypeImpl templateType = new SplatTypeImpl (heroType, subtype);
		return generics.getTemplateRegistry ().getTemplate (templateType);
	}
	
	private void saveModels (RepositoryWriteAccess writeAccess, Hero hero)
	{
		for (HeroModelPersister persister : persisterList.iterator (hero))
		{
			HeroModel heroModel = hero.getModel (persister.getModelId ());
			if (heroModel != null)
			{
				persister.setMessaging (messaging);
				persister.save (heroModel, new HeroModelSaverImpl (writeAccess));
			}
		}
	}
	
	private Item createCharacterInItem (HeroSplat template, CharacterInitializer initializer)
	{
		HeroItemDataImp character = new HeroItemDataImp (template, generics);
		initializer.initialize (character);
		return initItem (character);
	}
	
	private Item initItem (HeroItemDataImp character)
	{
		character.markReadyForWork ();
		return new HeroItem (character);
	}
}
