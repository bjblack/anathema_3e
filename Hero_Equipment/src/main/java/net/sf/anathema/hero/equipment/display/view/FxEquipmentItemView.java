package net.sf.anathema.hero.equipment.display.view;

import javafx.scene.Node;
import javafx.scene.control.TitledPane;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.sf.anathema.hero.equipment.EquipmentObjectView;
import net.sf.anathema.hero.equipment.display.presenter.StatsView;
import net.sf.anathema.library.collection.Closure;
import net.sf.anathema.library.fx.layout.LayoutUtils;
import net.sf.anathema.library.fx.text.FxTextView;

import org.tbee.javafx.scene.layout.MigPane;

public class FxEquipmentItemView implements EquipmentObjectView
{
	private final TitledPane border = new TitledPane ();
	private final FxTextView titleLabel = FxTextView.SingleLine ("");
	private final FxTextView descriptionLabel = FxTextView.SingleLine ("");
	private final MigPane elementPane = new MigPane (LayoutUtils.fillWithoutInsets ().wrapAfter (1));
	private final MigPane body = new MigPane (new LC ().fill ().wrapAfter (1));
	
	public FxEquipmentItemView ()
	{
		body.add (titleLabel.getNode (), new CC ().grow ());
		body.add (descriptionLabel.getNode (), new CC ().grow ());
		body.add (elementPane);
		border.setContent (body);
		border.setCollapsible (false);
		disablePersonalization ();
	}
	
	@Override
	public void setItemTitle (String title)
	{
		titleLabel.setText (title);
	}
	
	@Override
	public void setItemDescription (String text)
	{
		descriptionLabel.setText (text);
	}
	
	@Override
	public void clear ()
	{
		setItemTitle ("");
		setItemDescription ("");
		clearStats ();
	}
	
	@Override
	public void clearStats ()
	{
		elementPane.getChildren ().clear ();
	}
	
	@Override
	public StatsView addStats (String description)
	{
		FxStatsView statsView = new FxStatsView (description);
		elementPane.add (statsView.getNode ());
		return statsView;
	}
	
	@Override
	public void enablePersonalization ()
	{
		titleLabel.setEnabled (true);
		descriptionLabel.setEnabled (true);
	}
	
	@Override
	public void disablePersonalization ()
	{
		titleLabel.setEnabled (false);
		titleLabel.removeAllListeners ();
		descriptionLabel.setEnabled (false);
		descriptionLabel.removeAllListeners ();
	}
	
	public Node getNode ()
	{
		return border;
	}
	
	@Override
	public void setTitle (String title)
	{
		titleLabel.setText (title);
	}
	
	@Override
	public void setDescription (String description)
	{
		descriptionLabel.setText (description);
	}
	
	@Override
	public void whenTitleChanges (Closure<String> closure)
	{
		titleLabel.addTextChangedListener (closure::execute);
	}
	
	@Override
	public void whenDescriptionChanges (Closure<String> closure)
	{
		descriptionLabel.addTextChangedListener (closure::execute);
	}
}
