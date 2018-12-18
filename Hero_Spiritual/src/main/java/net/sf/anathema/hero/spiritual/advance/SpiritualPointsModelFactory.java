package net.sf.anathema.hero.spiritual.advance;

import net.sf.anathema.hero.environment.template.TemplateFactory;
import net.sf.anathema.hero.individual.model.HeroModelFactory;
import net.sf.anathema.hero.individual.model.SimpleModelTreeEntry;
import net.sf.anathema.hero.spiritual.model.traits.SpiritualTraitModel;
import net.sf.anathema.hero.spiritual.template.points.SpiritualPointsTemplate;
import net.sf.anathema.hero.spiritual.template.points.SpiritualPointsTemplateLoader;
import net.sf.anathema.points.model.PointsModel;

@SuppressWarnings ("UnusedDeclaration")
public class SpiritualPointsModelFactory extends SimpleModelTreeEntry implements HeroModelFactory
{
	public SpiritualPointsModelFactory ()
	{
		super (SpiritualPointsModel.ID, SpiritualTraitModel.ID, PointsModel.ID);
	}
	
	@SuppressWarnings ("unchecked")
	@Override
	public SpiritualPointsModel create (TemplateFactory templateFactory, String templateId)
	{
		SpiritualPointsTemplate template = SpiritualPointsTemplateLoader.loadTemplate (templateFactory, templateId);
		return new SpiritualPointsModel (template);
	}
}
