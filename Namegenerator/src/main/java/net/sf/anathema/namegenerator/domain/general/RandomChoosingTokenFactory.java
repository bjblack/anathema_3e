package net.sf.anathema.namegenerator.domain.general;

import net.sf.anathema.library.RandomUtilities;

import com.google.common.base.Preconditions;

public class RandomChoosingTokenFactory implements INameTokenFactory
{
	private String[] tokens;
	
	public RandomChoosingTokenFactory (String[] tokens)
	{
		Preconditions.checkArgument (tokens.length > 0, "At least one token must be given.");
		this.tokens = tokens;
	}
	
	@Override
	public final String createToken ()
	{
		return RandomUtilities.choose (tokens);
	}
}
