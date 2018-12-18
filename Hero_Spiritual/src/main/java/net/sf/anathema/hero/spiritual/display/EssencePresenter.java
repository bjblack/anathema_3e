package net.sf.anathema.hero.spiritual.display;

import net.sf.anathema.hero.spiritual.model.pool.EssencePoolModel;
import net.sf.anathema.hero.traits.display.TraitPresenter;
import net.sf.anathema.hero.traits.model.Trait;
import net.sf.anathema.library.presenter.Presenter;
import net.sf.anathema.library.resources.Resources;
import net.sf.anathema.library.view.IntValueView;
import net.sf.anathema.library.view.NullStyledValueView;
import net.sf.anathema.library.view.StyledValueView;

public class EssencePresenter implements Presenter
{
	private final SpiritualTraitsView view;
	private final EssencePoolModel essencePool;
	private final Trait essence;
	private final Resources resources;
	
	public EssencePresenter (Resources resources, EssencePoolModel essencePool, Trait essence,
	SpiritualTraitsView view)
	{
		this.resources = resources;
		this.essencePool = essencePool;
		this.essence = essence;
		this.view = view;
	}
	
	@Override
	public void initPresentation ()
	{
		IntValueView essenceView =
		view.addEssenceView (resources.getString ("Essence.Name"), essence.getMaximalValue ());
		essenceView.disableUserInput ();
		if (essencePool.isEssenceUser ())
		{
			String key = "EssencePool.Name.Personal";
			String personalPool = essencePool.getPersonalPool ();
			final StyledValueView<String> personalView = addPool (key, personalPool);
			final StyledValueView<String> peripheralView = createPeripheralPoolView ();
			final StyledValueView<String> attunementView = addPool ("EssencePool.Name.Attunement", essencePool.getAttunedPool ());
			essencePool.addPoolChangeListener ( () ->
			{
				personalView.setValue (essencePool.getPersonalPool ());
				listenToPeripheralChanges (peripheralView);
				attunementView.setValue (essencePool.getAttunedPool ());
			}
			);
		}
		new TraitPresenter (essence, essenceView).initPresentation ();
	}
	
	private void listenToPeripheralChanges (StyledValueView<String> peripheralView)
	{
		if (essencePool.hasPeripheralPool ())
		{
			peripheralView.setValue (essencePool.getPeripheralPool ());
		}
	}
	
	private StyledValueView<String> createPeripheralPoolView ()
	{
		if (essencePool.hasPeripheralPool ())
		{
			return addPool ("EssencePool.Name.Peripheral", essencePool.getPeripheralPool ());
		}
		return new NullStyledValueView<> ();
	}
	
	private StyledValueView<String> addPool (String labelKey, String pool)
	{
		StyledValueView<String> valueView = view.addPoolView (resources.getString (labelKey));
		valueView.setValue (pool);
		return valueView;
	}
}
