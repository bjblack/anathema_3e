package net.sf.anathema.hero.attributes.advance;

import net.sf.anathema.hero.attributes.model.AttributeModel;
import net.sf.anathema.hero.attributes.template.AttributePointsTemplate;
import net.sf.anathema.hero.attributes.template.AttributePointsTemplateLoader;
import net.sf.anathema.hero.environment.template.TemplateFactory;
import net.sf.anathema.hero.individual.model.HeroModelFactory;
import net.sf.anathema.hero.individual.model.SimpleModelTreeEntry;
import net.sf.anathema.points.model.PointsModel;

@SuppressWarnings ("UnusedDeclaration")
public class AttributePointsModelFactory extends SimpleModelTreeEntry implements HeroModelFactory
{
	public AttributePointsModelFactory ()
	{
		super (AttributePointsModel.ID, AttributeModel.ID, PointsModel.ID);
	}
	
	@SuppressWarnings ("unchecked")
	@Override
	public AttributePointsModel create (TemplateFactory templateFactory, String templateId)
	{
		AttributePointsTemplate template = AttributePointsTemplateLoader.loadTemplate (templateFactory, templateId);
		return new AttributePointsModel (template);
	}
}
