package net.sf.anathema.framework;

import net.sf.anathema.framework.extension.IAnathemaExtension;
import net.sf.anathema.framework.item.IItemType;
import net.sf.anathema.framework.item.IItemTypeRegistry;
import net.sf.anathema.framework.persistence.IRepositoryItemPersister;
import net.sf.anathema.framework.presenter.IItemMangementModel;
import net.sf.anathema.framework.presenter.IItemViewFactory;
import net.sf.anathema.framework.reporting.IReportRegistry;
import net.sf.anathema.framework.repository.IRepository;
import net.sf.anathema.lib.registry.IRegistry;
import net.sf.anathema.lib.workflow.wizard.selection.IWizardFactory;

public interface IAnathemaModel {

  public IRepository getRepository();

  public IReportRegistry getReportRegistry();
  
  public IItemTypeRegistry getItemTypeRegistry();

  public IRegistry<String, IAnathemaExtension> getExtensionPointRegistry();

  public IRegistry<IItemType, IRepositoryItemPersister> getPersisterRegistry();

  public IRegistry<IItemType, IItemViewFactory> getViewFactoryRegistry();

  public IItemMangementModel getItemManagement();

  public IRegistry<IItemType, IWizardFactory> getNewItemWizardFactoryRegistry();
}