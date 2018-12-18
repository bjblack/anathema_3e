package net.sf.anathema.platform.fx.updatecheck;

import de.idos.updates.Version;
import de.idos.updates.store.ProgressReportAdapter;

import net.sf.anathema.library.resources.Resources;
import net.sf.anathema.platform.updatecheck.UpdateChangelog;
import net.sf.anathema.platform.updatecheck.UpdateState;

import static net.sf.anathema.platform.updatecheck.UpdateState.CheckFailed;
import static net.sf.anathema.platform.updatecheck.UpdateState.Checking;

public class VersionDiscoveryReport extends ProgressReportAdapter
{
	private Resources resources;
	private final Version installed;
	private UpdateModel model;
	private final UpdateView view;
	
	public VersionDiscoveryReport (UpdateModel model, UpdateView view, Resources resources, Version installed)
	{
		this.model = model;
		this.view = view;
		this.resources = resources;
		this.installed = installed;
		view.showLatestVersion ("?.?.?");
	}
	
	@Override
	public void lookingUpLatestAvailableVersion ()
	{
		view.showLatestVersion ("?.?.?");
		model.setState (Checking);
	}
	
	@Override
	public void latestAvailableVersionIs (Version available)
	{
		boolean isUpdateAvailable = updateAvailable (available);
		String messageKey = determineMessageToShow (isUpdateAvailable);
		view.showLatestVersion (available.asString ());
		view.showDescription (resources.getString (messageKey));
		model.setState (UpdateState.CheckSuccessful);
		if (isUpdateAvailable)
		{
			view.enableUpdate ();
		}
		else
		{
			view.disableUpdate ();
		}
		UpdateChangelog changelog = new UpdateChangelog (installed, available);
		view.showChangelog (changelog.getText ());
	}
	
	private String determineMessageToShow (boolean available)
	{
		if (available)
		{
			return "Help.UpdateCheck.Outdated";
		}
		else
		{
			return "Help.UpdateCheck.UpToDate";
		}
	}
	
	@Override
	public void versionLookupFailed ()
	{
		showErrorState ();
	}
	
	@Override
	public void versionLookupFailed (Exception e)
	{
		showErrorState ();
	}
	
	private void showErrorState ()
	{
		view.showLatestVersion ("?.?.?");
		model.setState (CheckFailed);
	}
	
	private boolean updateAvailable (Version available)
	{
		return available.isGreaterThan (installed);
	}
}
