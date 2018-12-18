package net.sf.anathema.points.model.overview;

public interface IOverviewModelVisitor
{
	void visitStringValueModel (IValueModel<String> visitedModel);
	
	void visitIntegerValueModel (IValueModel<Integer> model);
	
	void visitAllotmentModel (SpendingModel visitedModel);
}
