package net.sf.anathema.hero.concept.display.description;

import net.sf.anathema.hero.concept.display.caste.presenter.NameGeneratorCommand;
import net.sf.anathema.hero.individual.view.HeroUI;
import net.sf.anathema.library.initialization.Weight;
import net.sf.anathema.library.interaction.model.Tool;
import net.sf.anathema.library.resources.Resources;
import net.sf.anathema.library.text.ITextualDescription;
import net.sf.anathema.namegenerator.exalted.domain.ThresholdNameGenerator;

@Weight (weight = 2)
@RegisteredNameEditAction
public class ThresholdNameAction implements NameEditAction
{
	private Resources resources;
	
	public ThresholdNameAction (Resources resources)
	{
		this.resources = resources;
	}
	
	@Override
	public void configure (Tool tool, ITextualDescription description)
	{
		tool.setIcon (new HeroUI ().getRandomThresholdNameIconPath ());
		tool.setTooltip (resources.getString ("CharacterDescription.Tooltip.ThresholdName"));
		tool.setCommand (new NameGeneratorCommand (description, new ThresholdNameGenerator ()));
	}
}
