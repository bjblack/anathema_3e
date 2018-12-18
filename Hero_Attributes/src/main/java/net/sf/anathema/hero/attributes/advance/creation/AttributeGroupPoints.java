package net.sf.anathema.hero.attributes.advance.creation;

import net.sf.anathema.hero.attributes.advance.AttributeGroupPriority;

public interface AttributeGroupPoints
{
	int getBonusPointsSpent (AttributeGroupPriority priority);
	
	Integer getFreebiesSpent (AttributeGroupPriority priority);
	
	int getFreebieCount (AttributeGroupPriority priority);
}
