package net.sf.anathema.hero.magic.advance;

import net.sf.anathema.hero.magic.template.advance.MagicPointsTemplate;
import net.sf.anathema.hero.magic.template.advance.MagicPointsTemplateLoader;
import net.sf.anathema.hero.environment.template.TemplateFactory;
import net.sf.anathema.hero.individual.model.HeroModelFactory;
import net.sf.anathema.hero.individual.model.SimpleModelTreeEntry;
import net.sf.anathema.points.model.PointsModel;

@SuppressWarnings ("UnusedDeclaration")
public class MagicPointsModelFactory extends SimpleModelTreeEntry implements HeroModelFactory
{
	public MagicPointsModelFactory ()
	{
		super (MagicPointsModel.ID, PointsModel.ID);
	}
	
	@SuppressWarnings ("unchecked")
	@Override
	public MagicPointsModel create (TemplateFactory templateFactory, String templateId)
	{
		MagicPointsTemplate template = MagicPointsTemplateLoader.loadTemplate (templateFactory, templateId);
		return new MagicPointsModel (template);
	}
}
