package com.coalvalue.notification.liveEvent;

import java.util.EventObject;

class USBDisconnectedCommand implements Command
{
    @Override
public void execute(EventObject barDecodedCommandE)
	{
		System.out.print("USBDisconnectedCommand\n");
	}
};