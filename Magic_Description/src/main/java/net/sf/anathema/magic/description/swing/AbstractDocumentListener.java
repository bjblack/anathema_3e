package net.sf.anathema.magic.description.swing;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public abstract class AbstractDocumentListener implements DocumentListener
{
	@Override
	public final void insertUpdate (DocumentEvent e)
	{
		updateText (e);
	}
	
	@Override
	public final void removeUpdate (DocumentEvent e)
	{
		updateText (e);
	}
	
	@Override
	public final void changedUpdate (DocumentEvent e)
	{
		updateText (e);
	}
	
	protected abstract void updateText (DocumentEvent e);
}
