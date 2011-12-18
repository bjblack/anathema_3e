package net.sf.anathema.character.reporting.pdf.content;

import net.sf.anathema.character.generic.character.IGenericCharacter;
import net.sf.anathema.character.generic.character.IGenericDescription;
import net.sf.anathema.lib.resources.IResources;

public interface IReportContentFactory<C extends ISubContent> {

  C create(IGenericCharacter character, IGenericDescription description);
}
