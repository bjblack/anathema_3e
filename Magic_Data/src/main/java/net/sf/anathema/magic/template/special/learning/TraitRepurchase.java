package net.sf.anathema.magic.template.special.learning;

import net.sf.anathema.platform.persistence.JsonType;

@JsonType ("trait")
public class TraitRepurchase implements Repurchase
{
	public String limitingTrait;
	
	@Override
	public void visit (RepurchaseVisitor visitor)
	{
		visitor.visitTraitRepurchase (this);
	}
}
