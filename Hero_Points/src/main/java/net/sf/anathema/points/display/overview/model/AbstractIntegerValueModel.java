package net.sf.anathema.points.display.overview.model;

import net.sf.anathema.points.model.overview.IOverviewModelVisitor;
import net.sf.anathema.points.model.overview.IValueModel;

public abstract class AbstractIntegerValueModel extends AbstractOverviewModel implements IValueModel<Integer>
{
	public AbstractIntegerValueModel (String categoryId, String id)
	{
		super (categoryId, id);
	}
	
	@Override
	public void accept (IOverviewModelVisitor visitor)
	{
		visitor.visitIntegerValueModel (this);
	}
}
