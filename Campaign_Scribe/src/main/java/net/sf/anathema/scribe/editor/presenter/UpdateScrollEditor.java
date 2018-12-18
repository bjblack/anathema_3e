package net.sf.anathema.scribe.editor.presenter;

import net.sf.anathema.library.markdown.HtmlText;
import net.sf.anathema.library.markdown.WikiText;
import net.sf.anathema.library.resources.Resources;
import net.sf.anathema.scribe.editor.model.ScrollChangedListener;

public class UpdateScrollEditor implements ScrollChangedListener
{
	private final ScrollEditor editor;
	private final ScrollPreview preview;
	private final Resources resources;
	
	public UpdateScrollEditor (ScrollEditor editor, ScrollPreview preview, Resources resources)
	{
		this.editor = editor;
		this.preview = preview;
		this.resources = resources;
	}
	
	@Override
	public void contentChanged (WikiText wikiText, HtmlText htmlText)
	{
		preview.setHtmlText (htmlText);
		editor.setWikiText (wikiText);
	}
	
	@Override
	public void nameChanged (String name)
	{
		editor.setTitle (name);
		String title = name.isEmpty () ? resources.getString ("Scribe.UnnamedScroll.Title") : name;
		preview.setTitle (title);
	}
}
