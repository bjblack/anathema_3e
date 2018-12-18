package net.sf.anathema.hero.charms.compiler.special.learning;

import net.sf.anathema.magic.template.special.SpecialCharmTemplate;
import net.sf.anathema.hero.charms.compiler.special.AdditionalCharmFactory;
import net.sf.anathema.hero.charms.compiler.special.CharmSpecialLearningBuilder;
import net.sf.anathema.hero.charms.compiler.special.ExistingMechanicTemplateSupplier;
import net.sf.anathema.hero.charms.model.special.CharmSpecialLearning;
import net.sf.anathema.library.initialization.DoNotInstantiateAutomatically;

@DoNotInstantiateAutomatically
public class NullSpecialCharmBuilder implements CharmSpecialLearningBuilder
{
	@Override
	public CharmSpecialLearning readCharm (SpecialCharmTemplate dto,
	AdditionalCharmFactory factory,
	ExistingMechanicTemplateSupplier supplier)
	{
		return null;
	}
	
	@Override
	public boolean supports (SpecialCharmTemplate dto)
	{
		return false;
	}
}
