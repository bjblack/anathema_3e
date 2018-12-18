package net.sf.anathema.hero.attributes.template;

import net.sf.anathema.hero.environment.template.TemplateFactory;
import net.sf.anathema.hero.environment.template.TemplateLoader;
import net.sf.anathema.hero.individual.persistence.GenericTemplateLoader;
import net.sf.anathema.library.identifier.Identifier;
import net.sf.anathema.library.identifier.SimpleIdentifier;

public class AttributePointsTemplateLoader
{
	public static AttributePointsTemplate loadTemplate (TemplateFactory templateFactory, String templateName)
	{
		Identifier templateId = new SimpleIdentifier (templateName);
		TemplateLoader<AttributePointsTemplate> loader = new GenericTemplateLoader<> (AttributePointsTemplate.class);
		return templateFactory.loadModelTemplate (templateId, loader);
	}
}
