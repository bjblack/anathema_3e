package net.sf.anathema.library.markdown;

public class ReplacementConversion implements Conversion
{
	private String pattern;
	private String replacement;
	
	public ReplacementConversion (String pattern, String replacement)
	{
		this.pattern = pattern;
		this.replacement = replacement;
	}
	
	@Override
	public String convert (String original)
	{
		return original.replaceAll (pattern, replacement);
	}
}
