package net.sf.anathema.exaltedengine.attributes;

import net.sf.anathema.characterengine.engine.QualityFactory;
import net.sf.anathema.characterengine.quality.Name;
import net.sf.anathema.characterengine.quality.Quality;
import net.sf.anathema.exaltedengine.numericquality.NumericValue;

public class AttributeFactory implements QualityFactory
{
	@SuppressWarnings ("FieldCanBeLocal")
	private final int startValue = 1;
	
	@Override
	public Quality create (Name name)
	{
		return new Attribute (new NumericValue (startValue), name);
	}
}
