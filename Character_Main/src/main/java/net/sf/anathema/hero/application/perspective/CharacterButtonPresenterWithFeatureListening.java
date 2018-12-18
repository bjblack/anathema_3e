package net.sf.anathema.hero.application.perspective;

import net.sf.anathema.hero.application.perspective.model.CharacterItemModel;
import net.sf.anathema.hero.application.perspective.model.HeroIdentifier;
import net.sf.anathema.library.event.ChangeListener;
import net.sf.anathema.library.resources.Resources;

public class CharacterButtonPresenterWithFeatureListening extends CharacterButtonPresenter
{
	private CharacterItemModel character;
	private UpdatingHeroesGridView view;
	private ChangeListener featureChangeListener = () -> view.updateButton (extractButtonDto ());
	
	public CharacterButtonPresenterWithFeatureListening (Resources resources, Selector<HeroIdentifier> selector, CharacterItemModel character, UpdatingHeroesGridView view)
	{
		super (resources, selector, character, view);
		this.character = character;
		this.view = view;
	}
	
	public void initPresentation ()
	{
		super.initPresentation ();
		initDescriptiveFeatureListening ();
	}
	
	private void initDescriptiveFeatureListening ()
	{
		character.whenFeaturesChange (featureChangeListener);
	}
	
	public void stopPresentation ()
	{
		character.stopListeningForFeatureChange (featureChangeListener);
	}
}
