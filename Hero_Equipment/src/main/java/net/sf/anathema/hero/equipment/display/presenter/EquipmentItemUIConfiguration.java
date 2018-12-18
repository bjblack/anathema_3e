package net.sf.anathema.hero.equipment.display.presenter;

import net.sf.anathema.equipment.database.IEquipmentTemplateProvider;
import net.sf.anathema.equipment.template.IEquipmentTemplate;
import net.sf.anathema.library.presenter.AbstractUIConfiguration;
import net.sf.anathema.library.resources.Resources;
import net.sf.anathema.library.tooltip.ConfigurableTooltip;

public class EquipmentItemUIConfiguration extends AbstractUIConfiguration<String>
{
	private final IEquipmentTemplateProvider templateProvider;
	private final EquipmentTemplateTooltipBuilder tooltipBuilder;
	
	public EquipmentItemUIConfiguration (IEquipmentTemplateProvider provider, Resources resources)
	{
		templateProvider = provider;
		tooltipBuilder = new EquipmentTemplateTooltipBuilder (resources);
	}
	
	@Override
	protected String labelForExistingValue (String value)
	{
		return value;
	}
	
	@Override
	protected void configureTooltipForExistingValue (String value, ConfigurableTooltip configurableTooltip)
	{
		IEquipmentTemplate template = templateProvider.loadTemplate (value);
		if (template == null)
		{
			configurableTooltip.showNoTooltip ();
		}
		else
		{
			tooltipBuilder.configureTooltip (template, configurableTooltip);
		}
	}
}
