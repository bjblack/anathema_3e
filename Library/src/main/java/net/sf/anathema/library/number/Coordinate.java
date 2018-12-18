package net.sf.anathema.library.number;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class Coordinate
{
	public final int x;
	public final int y;
	
	public Coordinate ()
	{
		this (0, 0);
	}
	
	public Coordinate (double x, double y)
	{
		this.x = (int) Math.round (x);
		this.y = (int) Math.round (y);
	}
	
	public Coordinate (int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Coordinate translate (int dx, int dy)
	{
		return new Coordinate (x + dx, y + dy);
	}
	
	@Override
	public boolean equals (Object obj)
	{
		return EqualsBuilder.reflectionEquals (this, obj);
	}
	
	@Override
	public int hashCode ()
	{
		return x * y;
	}
	
	@Override
	public String toString ()
	{
		return "Coordinate{" +
		"x=" + x +
		", y=" + y +
		'}';
	}
}
