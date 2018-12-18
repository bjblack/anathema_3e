package net.sf.anathema.platform.messaging;

import net.sf.anathema.library.message.Message;
import net.sf.anathema.library.message.MessageToken;
import net.sf.anathema.library.message.MessageType;
import net.sf.anathema.library.message.Messaging;

public class NullMessaging implements Messaging
{
	@Override
	public MessageToken addPermanentMessage (MessageType messageType, String pattern, Object... arguments)
	{
		return new NullToken (this);
	}
	
	@Override
	public MessageToken addTemporaryMessage (MessageType messageType, String messagePattern, Object... arguments)
	{
		return new NullToken (this);
	}
	
	@Override
	public MessageToken addMessage (Message message)
	{
		return new NullToken (this);
	}
	
	@Override
	public MessageToken obtainInitialToken ()
	{
		return new NullToken (this);
	}
}
