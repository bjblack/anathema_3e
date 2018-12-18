package net.sf.anathema.platform.fx.messaging;

import net.sf.anathema.library.message.MessageType;
import net.sf.anathema.library.resources.RelativePath;

public class MessageTypeImagePaths
{
	private static final RelativePath errorPath = new RelativePath ("icons/error.gif");
	private static final RelativePath warningPath = new RelativePath ("icons/warning.gif");
	private static final RelativePath infoPath = new RelativePath ("icons/info.gif");
	private static final RelativePath questionPath = new RelativePath ("icons/question.gif");
	
	public RelativePath getIconPath (MessageType type)
	{
		switch (type)
		{
			case Error:
			return errorPath;
			case Warning:
			return warningPath;
			case Question:
			return questionPath;
			case Normal:
			case Information:
			default:
			return infoPath;
		}
	}
}
