package net.sf.anathema.hero.charms.compiler.json;

import net.sf.anathema.magic.template.CharmListTemplate;
import net.sf.anathema.magic.template.prerequisite.CharmPrerequisiteTemplate;
import net.sf.anathema.magic.template.special.SpecialCharmListTemplate;
import net.sf.anathema.magic.template.special.learning.Repurchase;
import net.sf.anathema.hero.application.environment.Inject;
import net.sf.anathema.hero.charms.compiler.CharmCacheImpl;
import net.sf.anathema.hero.charms.compiler.special.AdditionalCharmFactory;
import net.sf.anathema.hero.environment.initialization.ExtensibleDataSet;
import net.sf.anathema.hero.environment.initialization.ExtensibleDataSetCompiler;
import net.sf.anathema.hero.individual.persistence.GenericTemplateLoader;
import net.sf.anathema.library.initialization.ObjectFactory;
import net.sf.anathema.library.initialization.Weight;
import net.sf.anathema.library.resources.ResourceFile;
import net.sf.anathema.platform.dependencies.InterfaceFinder;
import net.sf.anathema.platform.persistence.PolymorphicTypeAdapterFactoryFactory;
import net.sf.anathema.platform.persistence.RuntimeTypeAdapterFactory;
import net.sf.anathema.platform.persistence.common.ValueTemplate;

import java.util.ArrayList;
import java.util.List;

@net.sf.anathema.platform.initialization.ExtensibleDataSetCompiler
@Weight (weight = 50)
public class CharmCacheCompiler implements ExtensibleDataSetCompiler
{
	private static final String Charm_File_Recognition_Pattern = ".+?\\.charms";
	private final List<ResourceFile> resourceFiles = new ArrayList<> ();
	
	@Inject
	public ObjectFactory objectFactory;
	@Inject
	public InterfaceFinder finder;
	
	@Override
	public String getName ()
	{
		return "Charms";
	}
	
	@Override
	public String getRecognitionPattern ()
	{
		return Charm_File_Recognition_Pattern;
	}
	
	@Override
	public void registerFile (ResourceFile resource)
	{
		resourceFiles.add (resource);
	}
	
	@Override
	public ExtensibleDataSet build ()
	{
		RuntimeTypeAdapterFactory[] charmFactories =
		new PolymorphicTypeAdapterFactoryFactory (finder).generateFactories (CharmPrerequisiteTemplate.class);
		RuntimeTypeAdapterFactory[] specialFactories =
		new PolymorphicTypeAdapterFactoryFactory (finder).generateFactories (Repurchase.class, ValueTemplate.class);
		GenericTemplateLoader<CharmListTemplate> charmsLoader = new GenericTemplateLoader<> (CharmListTemplate.class, charmFactories);
		GenericTemplateLoader<SpecialCharmListTemplate> specialsLoader = new GenericTemplateLoader<> (SpecialCharmListTemplate.class, specialFactories);
		CharmCacheBuilderImpl charmsBuilder = new CharmCacheBuilderImpl ();
		SpecialCharmsBuilder specialBuilder = new SpecialCharmsBuilder (objectFactory);
		resourceFiles.forEach (resourceFile ->
		{
			CharmListTemplate charmTemplate = charmsLoader.load (resourceFile);
			charmsBuilder.addTemplate (charmTemplate);
			SpecialCharmListTemplate specialsTemplate = specialsLoader.load (resourceFile);
			specialBuilder.addTemplate (specialsTemplate, new AdditionalCharmFactory (charmsBuilder, charmTemplate));
		}
		);
		CharmCacheImpl charmCache = charmsBuilder.createCache ();
		specialBuilder.addToCache (charmCache);
		return charmCache;
	}
}
