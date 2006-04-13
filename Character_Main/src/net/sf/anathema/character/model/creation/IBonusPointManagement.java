package net.sf.anathema.character.model.creation;

import net.sf.anathema.character.generic.template.points.AttributeGroupPriority;
import net.sf.anathema.character.presenter.overview.IAdditionalSpendingModel;
import net.sf.anathema.character.presenter.overview.IOverviewModel;
import net.sf.anathema.character.presenter.overview.ISpendingModel;
import net.sf.anathema.character.presenter.overview.IValueModel;

public interface IBonusPointManagement {

  public void recalculate();

  /** Return the amount of unrestricted bonus points granted by additional models */
  public int getAdditionalGeneralBonusPoints();

  public ISpendingModel getVirtueModel();

  public ISpendingModel getBackgroundModel();

  public ISpendingModel getDefaultAbilityModel();

  public ISpendingModel getFavoredAbilityModel();

  public ISpendingModel getFavoredAbilityPickModel();

  public ISpendingModel getAttributeModel(AttributeGroupPriority priority);

  public ISpendingModel getFavoredCharmModel();

  public IAdditionalSpendingModel getDefaultCharmModel();

  public IValueModel<Integer> getAdditionalModelModel();

  public IAdditionalSpendingModel getTotalModel();

  public IOverviewModel[] getAllModels();

}