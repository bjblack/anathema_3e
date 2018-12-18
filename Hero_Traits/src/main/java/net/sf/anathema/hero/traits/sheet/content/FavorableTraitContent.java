package net.sf.anathema.hero.traits.sheet.content;

import net.sf.anathema.hero.concept.model.concept.CasteType;
import net.sf.anathema.hero.sheet.pdf.content.AbstractSubBoxContent;
import net.sf.anathema.hero.traits.TraitTypeList;
import net.sf.anathema.hero.traits.model.GroupedTraitsModel;
import net.sf.anathema.hero.traits.model.Trait;
import net.sf.anathema.hero.traits.model.TraitMap;
import net.sf.anathema.hero.traits.model.TraitType;
import net.sf.anathema.hero.traits.model.lists.IdentifiedTraitTypeList;
import net.sf.anathema.library.identifier.Identifier;
import net.sf.anathema.library.resources.Resources;

public abstract class FavorableTraitContent extends AbstractSubBoxContent
{
	private GroupedTraitsModel model;
	
	public FavorableTraitContent (GroupedTraitsModel model, Resources resources)
	{
		super (resources);
		this.model = model;
	}
	
	public abstract TraitTypeList getMarkedTraitTypes ();
	
	public abstract IdentifiedTraitTypeList[] getIdentifiedTraitTypeGroups ();
	
	public abstract TraitMap getTraitMap ();
	
	public int getTraitMax ()
	{
		return model.getTraitMaximum ();
	}
	
	@Override
	public boolean hasContent ()
	{
		return true;
	}
	
	@Override
	public abstract String getHeaderKey ();
	
	public String getGroupLabel (Identifier groupId)
	{
		String groupIdAsString = groupId.getId ();
		String resourceKey = groupId instanceof CasteType ? "Caste." + groupIdAsString : getGroupNamePrefix () + groupIdAsString;
		return getString (resourceKey);
	}
	
	public boolean hasGroupLabel ()
	{
		return getGroupNamePrefix () == null;
	}
	
	protected abstract String getGroupNamePrefix ();
	
	public String getTraitLabel (TraitType traitType)
	{
		return getString (getTraitTypePrefix () + traitType.getId ());
	}
	
	protected abstract String getTraitTypePrefix ();
	
	public String getMobilityPenaltyText ()
	{
		return " : " + getString (getMarkerCommentKey ());
	}
	
	public abstract String getMarkerCommentKey ();
	
	public boolean isCasteOrFavored (Trait trait)
	{
		return model.getState (trait).isCheapened ();
	}
}
