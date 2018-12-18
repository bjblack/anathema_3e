package net.sf.anathema.points.display.overview.view;

import javafx.scene.control.Label;

import net.miginfocom.layout.CC;
import net.sf.anathema.library.presenter.FontStyle;
import net.sf.anathema.library.presenter.RGBColor;
import net.sf.anathema.library.view.LabelledAllotmentView;

import org.tbee.javafx.scene.layout.MigPane;

public class FxAllotmentOverview implements LabelledAllotmentView
{
	private final Label titleLabel = new Label ();
	private final Label valueLabel = new Label ();
	private final Label separatorLabel = new Label ();
	private final Label alotmentLabel = new Label ();
	private final FontStyler styler = new FontStyler (titleLabel, valueLabel, separatorLabel, alotmentLabel);
	
	public FxAllotmentOverview (String labelText)
	{
		titleLabel.setText (labelText);
		separatorLabel.setText ("/");
	}
	
	public void addTo (final MigPane panel)
	{
		panel.add (titleLabel, new CC ().growX ().pushX ());
		panel.add (valueLabel, new CC ().growX ());
		panel.add (separatorLabel, new CC ().alignX ("right"));
		panel.add (alotmentLabel, new CC ().alignX ("right"));
	}
	
	@Override
	public void setAllotment (final int value)
	{
		alotmentLabel.setText (String.valueOf (value));
	}
	
	@Override
	public void setValue (final Integer value)
	{
		valueLabel.setText (String.valueOf (value));
	}
	
	@Override
	public void setTextColor (RGBColor color)
	{
		styler.setColor (color);
	}
	
	@Override
	public void setFontStyle (FontStyle style)
	{
		styler.setStyle (style);
	}
}
