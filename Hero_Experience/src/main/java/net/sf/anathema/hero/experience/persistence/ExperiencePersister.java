package net.sf.anathema.hero.experience.persistence;

import net.sf.anathema.hero.advance.experience.ExperiencePointEntry;
import net.sf.anathema.hero.experience.ExperienceModel;
import net.sf.anathema.hero.model.Hero;
import net.sf.anathema.hero.persistence.AbstractModelJsonPersister;
import net.sf.anathema.hero.points.PointsModel;
import net.sf.anathema.lib.util.Identifier;

@SuppressWarnings("UnusedDeclaration")
public class ExperiencePersister extends AbstractModelJsonPersister<ExperiencePto, ExperienceModel> {

  public ExperiencePersister() {
    super("experience", ExperiencePto.class);
  }

  @Override
  public Identifier getModelId() {
    return ExperienceModel.ID;
  }

  @Override
  protected void loadModelFromPto(Hero hero, ExperienceModel model, ExperiencePto pto) {
    model.setExperienced(pto.isExperienced);
  }

  private void loadPointsEntry(ExperiencePointsEntryPto pointsPto, ExperiencePointEntry entry) {
    entry.setExperiencePoints(pointsPto.points);
    entry.getTextualDescription().setText(pointsPto.description);
  }

  @Override
  protected ExperiencePto saveModelToPto(ExperienceModel model) {
    ExperiencePto pto = new ExperiencePto();
    pto.isExperienced = model.isExperienced();
    return pto;
  }

  private ExperiencePointsEntryPto createExperiencePointsPto(ExperiencePointEntry entry) {
    ExperiencePointsEntryPto pointsPto = new ExperiencePointsEntryPto();
    pointsPto.description = entry.getTextualDescription().getText();
    pointsPto.points = entry.getExperiencePoints();
    return pointsPto;
  }
}
