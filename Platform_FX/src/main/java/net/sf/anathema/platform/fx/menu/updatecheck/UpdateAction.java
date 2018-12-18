package net.sf.anathema.platform.fx.menu.updatecheck;

import net.sf.anathema.library.interaction.model.Command;
import net.sf.anathema.library.resources.Resources;
import net.sf.anathema.platform.fx.environment.UiEnvironment;
import net.sf.anathema.platform.fx.updatecheck.FxUpdateView;
import net.sf.anathema.platform.fx.updatecheck.UpdateModel;
import net.sf.anathema.platform.fx.updatecheck.UpdatePresenter;
import net.sf.anathema.platform.fx.updatecheck.UpdateView;

public class UpdateAction implements Command
{
	private Resources resources;
	private UiEnvironment uiEnvironment;
	
	public UpdateAction (Resources resources, UiEnvironment uiEnvironment)
	{
		this.resources = resources;
		this.uiEnvironment = uiEnvironment;
	}
	
	@Override
	public void execute ()
	{
		String title = resources.getString ("Help.UpdateCheck.Title");
		String currentVersionLabel = resources.getString ("Help.UpdateCheck.CurrentVersion") + ":";
		String latestVersionLabel = resources.getString ("Help.UpdateCheck.LatestVersion") + ":";
		UpdateView view = new FxUpdateView (title, currentVersionLabel, latestVersionLabel, uiEnvironment);
		UpdateModel model = new UpdateModel ();
		UpdatePresenter updatePresenter = new UpdatePresenter (resources, view, model);
		updatePresenter.initPresentation ();
	}
}
