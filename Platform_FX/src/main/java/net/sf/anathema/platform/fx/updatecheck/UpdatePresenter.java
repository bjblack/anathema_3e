package net.sf.anathema.platform.fx.updatecheck;

import de.idos.updates.NumericVersion;
import de.idos.updates.UpdateSystem;
import de.idos.updates.Updater;
import de.idos.updates.Version;
import de.idos.updates.configuration.ConfiguredUpdateSystem;

import net.sf.anathema.library.interaction.model.Tool;
import net.sf.anathema.library.resources.Resources;

public class UpdatePresenter
{
	private final Resources resources;
	private final UpdateView view;
	private UpdateModel model;
	
	public UpdatePresenter (Resources resources, UpdateView view, UpdateModel model)
	{
		this.resources = resources;
		this.view = view;
		this.model = model;
	}
	
	public void initPresentation ()
	{
		initStateListening ();
		Version currentVersion = getCurrentVersion ();
		UpdateSystem updateSystem = createUpdateSystem (currentVersion);
		Updater updater = updateSystem.checkForUpdates ();
		Version installedVersion = updateSystem.getInstalledVersion ();
		view.showInstalledVersion (installedVersion);
		prepareForInstallation (view, updater);
		updateSystem.reportAllProgressTo (new VersionDiscoveryReport (model, view, resources, installedVersion));
		updateSystem.reportAllProgressTo (new InstallationProgressReport (resources, view, model));
		updateSystem.reportAllProgressTo (new DialogUpdater (view));
		updateSystem.reportAllProgressTo (new ConfigureAnathema (updateSystem));
		view.disableUpdate ();
		view.showChangelog (resources.getString ("Help.UpdateCheck.LoadingChangelog"));
		runUpdateCheck (updater);
		view.show ();
	}
	
	private void initStateListening ()
	{
		model.whenStateChanges (state ->
		{
			switch (state)
			{
				case Checking:
				view.showDescription (getString ("Help.UpdateCheck.Checking"));
				return;
				case CheckSuccessful:
				view.showDescription (getString ("Help.UpdateCheck.Success"));
				return;
				case CheckFailed:
				view.showDescription (getString ("Help.UpdateCheck.Failure"));
				return;
				case InstallationRunning:
				view.showDescription (getString ("Help.UpdateInstallation.Running"));
				return;
				case InstallationDone:
				view.showDescription (getString ("Help.UpdateInstallation.Completed"));
			}
		}
		);
	}
	
	
	private UpdateSystem createUpdateSystem (Version currentVersion)
	{
		return ConfiguredUpdateSystem.loadProperties ().andIfTheInstalledVersionIsUnknownUse (currentVersion).create ();
	}
	
	private void prepareForInstallation (UpdateView view, Updater updater)
	{
		Tool tool = view.addTool ();
		tool.setText ("Install update");
		tool.setCommand ( () -> runInThread ( () ->
		{
			view.disableUpdate ();
			updater.updateToLatestVersion ();
		}
		));
	}
	
	private void runUpdateCheck (Updater updater)
	{
		runInThread (new CheckForUpdate (updater));
	}
	
	private void runInThread (Runnable target)
	{
		new Thread (target).start ();
	}
	
	private Version getCurrentVersion ()
	{
		net.sf.anathema.platform.Version version = new net.sf.anathema.platform.Version (resources);
		return new NumericVersion (version.getMajorVersion (), version.getMinorVersion (), version.getRevision ());
	}
	
	private String getString (String key)
	{
		return resources.getString (key);
	}
}
