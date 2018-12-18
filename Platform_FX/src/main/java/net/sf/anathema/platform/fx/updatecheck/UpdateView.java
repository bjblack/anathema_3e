package net.sf.anathema.platform.fx.updatecheck;

import de.idos.updates.Version;

import net.sf.anathema.library.interaction.model.Tool;

public interface UpdateView
{
	void show ();
	
	void showDescription (String message);
	
	void showLatestVersion (String versionNumber);
	
	void showInstalledVersion (Version installedVersion);
	
	void showChangelog (String text);
	
	void enableUpdate ();
	
	void disableUpdate ();
	
	void showFilesToDownload (int numberOfElements);
	
	void showExpectedFileSize (int intSize);
	
	void showProgressOnFile (int progress);
	
	void showFilesAlreadyLoaded (int elementsHandled);
	
	void showProgressMessage (String string);
	
	Tool addTool ();
	
	void refresh ();
}
