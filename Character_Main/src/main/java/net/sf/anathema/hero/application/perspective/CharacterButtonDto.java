package net.sf.anathema.hero.application.perspective;

import net.sf.anathema.hero.application.perspective.model.HeroIdentifier;
import net.sf.anathema.library.resources.RelativePath;

public class CharacterButtonDto
{
	public final HeroIdentifier identifier;
	public final String text;
	public final String details;
	public final RelativePath pathToImage;
	public final boolean isDirty;
	
	public CharacterButtonDto (HeroIdentifier identifier, String text, String details, RelativePath pathToImage, boolean isDirty)
	{
		this.identifier = identifier;
		this.text = text;
		this.details = details;
		this.pathToImage = pathToImage;
		this.isDirty = isDirty;
	}
}
