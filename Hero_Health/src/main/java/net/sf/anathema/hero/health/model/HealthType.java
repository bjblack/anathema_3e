package net.sf.anathema.hero.health.model;

import net.sf.anathema.library.identifier.Identifier;

public enum HealthType implements Identifier
{
	Bashing
	{
		@Override
		public void accept (IHealthTypeVisitor visitor)
		{
			visitor.visitBashing (this);
		}
	}
	,
	Lethal
	{
		@Override
		public void accept (IHealthTypeVisitor visitor)
		{
			visitor.visitLethal (this);
		}
	}
	,
	Aggravated
	{
		@Override
		public void accept (IHealthTypeVisitor visitor)
		{
			visitor.visitAggravated (this);
		}
	}
	;
	
	@Override
	public String getId ()
	{
		return name ();
	}
	
	@Override
	public String toString ()
	{
		return getId ();
	}
	
	public abstract void accept (IHealthTypeVisitor visitor);
}
