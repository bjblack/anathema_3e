package net.sf.anathema.library.fx;

import javafx.application.Platform;

public class FxThreading
{
	public static void runOnCorrectThread (Runnable runnable)
	{
		if (Platform.isFxApplicationThread ())
		{
			runnable.run ();
		}
		else
		{
			Platform.runLater (runnable);
		}
	}
}
