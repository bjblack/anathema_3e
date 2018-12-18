package net.sf.anathema.scribe.editor.presenter;

import net.sf.anathema.library.markdown.HtmlText;

public interface ScrollPreview
{
	void setHtmlText (HtmlText text);
	
	void setTitle (String text);
	
	void setUnnamedScrollTitlePreview (String text);
}
