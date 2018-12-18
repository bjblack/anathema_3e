package net.sf.anathema.hero.application.template;

import net.sf.anathema.hero.environment.initialization.ExtensibleDataSet;
import net.sf.anathema.hero.environment.template.TemplateLoader;
import net.sf.anathema.library.identifier.Identifier;
import net.sf.anathema.library.resources.ResourceFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeroModelTemplateCache implements ExtensibleDataSet
{
	private final List<ResourceFile> templateResources = new ArrayList<> ();
	private Map<Identifier, Object> templateById = new HashMap<> ();
	
	public void add (ResourceFile resource)
	{
		templateResources.add (resource);
	}
	
	@SuppressWarnings ("unchecked")
	public <T> T loadTemplate (Identifier templateId, TemplateLoader<T> loader)
	{
		if (templateById.containsKey (templateId))
		{
			return (T) templateById.get (templateId);
		}
		ResourceFile file = getResourceFileFor (templateId);
		T template = loader.load (file);
		templateById.put (templateId, template);
		return template;
	}
	
	private ResourceFile getResourceFileFor (Identifier templateId)
	{
		for (ResourceFile file : templateResources)
		{
			if (file.getFileName ().endsWith ("/" + templateId.getId () + ".template"))
			{
				return file;
			}
		}
		throw new IllegalStateException ("No resource found for templateId " + templateId.getId ());
	}
}
