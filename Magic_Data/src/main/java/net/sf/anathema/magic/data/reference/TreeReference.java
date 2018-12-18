package net.sf.anathema.magic.data.reference;

public class TreeReference
{
	public final CategoryReference category;
	public final TreeName name;
	
	public TreeReference (CategoryReference category, TreeName name)
	{
		this.category = category;
		this.name = name;
	}
	
	@Override
	public boolean equals (Object obj)
	{
		if (! (obj instanceof TreeReference))
		{
			return false;
		}
		TreeReference other = (TreeReference) obj;
		return other.name.equals (name) && other.category.equals (category);
	}
	
	@Override
	public int hashCode ()
	{
		return name.hashCode () * 3 + category.hashCode () * 7;
	}
	
	@Override
	public String toString ()
	{
		return category + ", " + name;
	}
}
