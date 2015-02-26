package net.sf.anathema.hero.merits.persistence;

import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.individual.persistence.AbstractModelJsonPersister;
import net.sf.anathema.hero.merits.model.Merit;
import net.sf.anathema.hero.merits.model.MeritOption;
import net.sf.anathema.hero.merits.model.MeritsModel;
import net.sf.anathema.hero.traits.model.state.NullTraitStateMap;
import net.sf.anathema.hero.traits.persistence.TraitPersister;
import net.sf.anathema.library.identifier.Identifier;
import net.sf.anathema.library.model.OptionalTraitReference;

@SuppressWarnings("UnusedDeclaration")
public class MeritPersister extends AbstractModelJsonPersister<MeritsPto, MeritsModel> {

  private final TraitPersister traitPersister = new TraitPersister(new NullTraitStateMap());

  public MeritPersister() {
    super("merits", MeritsPto.class);
  }

  @Override
  protected void loadModelFromPto(Hero hero, MeritsModel model, MeritsPto pto) {
    for (MeritPto meritPto : pto.merits) {
      MeritOption option = model.findOptionByReference(new OptionalTraitReference(meritPto.meritOption));
      model.setSelectedTraitOption(option);
      model.setCurrentDescription(meritPto.description);
      Merit newMerit = model.commitSelection();
      traitPersister.load(newMerit, meritPto.rating);
    }
    model.resetCurrentEntry();
  }

  @Override
  protected MeritsPto saveModelToPto(MeritsModel heroModel) {
    MeritsPto meritsList = new MeritsPto();
    for (Merit merit : heroModel.getEntries()) {
      meritsList.merits.add(createMeritsPto(merit));
    }
    return meritsList;
  }

  private MeritPto createMeritsPto(Merit merit) {
    MeritPto pto = new MeritPto();
    pto.meritOption = merit.getBaseOption().getId();
    pto.description = merit.getDescription();
    traitPersister.save(merit, pto.rating);
    return pto;
  }

  @Override
  public Identifier getModelId() {
    return MeritsModel.ID;
  }
}
