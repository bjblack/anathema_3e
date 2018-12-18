package net.sf.anathema.hero.concept.display.caste.presenter;

import net.sf.anathema.hero.concept.model.concept.CasteType;
import net.sf.anathema.hero.environment.herotype.PresentationProperties;
import net.sf.anathema.library.resources.RelativePath;

public class CasteUI
{
	private final PresentationProperties properties;
	
	public CasteUI (PresentationProperties properties)
	{
		this.properties = properties;
	}
	
	public RelativePath getSmallCasteIconPath (CasteType type)
	{
		return properties.getSmallCasteIconResource (type.getId ());
	}
}
