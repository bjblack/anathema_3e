package net.sf.anathema.scribe.editor.model;

import net.sf.anathema.library.markdown.HtmlConverter;
import net.sf.anathema.library.markdown.HtmlText;
import net.sf.anathema.library.markdown.WikiText;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class UnderlineTextHtmlConversionTest
{
	private final HtmlConverter converter = new HtmlConverter ();
	
	@org.junit.Test
	public void convertsCompleteBoldText ()
	{
		assertConvertsTo (new WikiText ("++Urs++"), new HtmlText ("<u>Urs</u>"));
	}
	
	private void assertConvertsTo (WikiText wikiText, HtmlText htmlText)
	{
		HtmlText convertedText = converter.convert (wikiText);
		assertThat (convertedText.getHtmlText (), is ("<p>" + htmlText.getHtmlText () + "</p>\n"));
	}
}
