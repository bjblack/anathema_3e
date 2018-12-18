package net.sf.anathema.hero.abilities.persistence;

import net.sf.anathema.hero.abilities.model.AbilitiesModel;
import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.individual.persistence.AbstractModelJsonPersister;
import net.sf.anathema.hero.traits.model.TraitType;
import net.sf.anathema.hero.traits.persistence.TraitListPto;
import net.sf.anathema.hero.traits.persistence.TraitMapPersister;
import net.sf.anathema.library.identifier.Identifier;

@SuppressWarnings ("UnusedDeclaration")
public class AbilitiesPersister extends AbstractModelJsonPersister<TraitListPto, AbilitiesModel>
{
	public AbilitiesPersister ()
	{
		super ("abilities", TraitListPto.class);
	}
	
	@Override
	public Identifier getModelId ()
	{
		return AbilitiesModel.ID;
	}
	
	@Override
	protected void loadModelFromPto (Hero hero, AbilitiesModel model, TraitListPto pto)
	{
		TraitMapPersister traitMapPersister = createTraitMapPersistence (model);
		traitMapPersister.loadTraitMap (model, pto);
	}
	
	@Override
	protected TraitListPto saveModelToPto (AbilitiesModel model)
	{
		TraitMapPersister traitMapPersister = createTraitMapPersistence (model);
		return traitMapPersister.saveTraitMap (model);
	}
	
	private TraitMapPersister createTraitMapPersistence (AbilitiesModel model)
	{
		return new TraitMapPersister (model, TraitType::new);
	}
}
