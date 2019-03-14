package com.coalvalue.notification.liveEvent;

import java.util.EventObject;

class WeighOutCommand implements Command
{
    @Override
public void execute(EventObject barDecodedCommandE)
	{
		System.out.print("USBDisconnectedCommand\n");
	}

    public static Command instance() {
        return new WeighOutCommand();
    }
};