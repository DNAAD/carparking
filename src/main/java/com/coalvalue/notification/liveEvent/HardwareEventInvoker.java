package com.coalvalue.notification.liveEvent;

import org.springframework.stereotype.Component;

import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;

@Component
public class HardwareEventInvoker
{
    Map<EVENTS, Command > m_registeredCommands = new HashMap();

	/*
	 * This function will be called from framework to handle the event  
	 */
    public void handleEvent(EVENTS event, EventObject barDecodedCommandE)
	{
        Command cmdPtr = m_registeredCommands.get(event);
//		if( it != m_registeredCommands.end())
		{
			//Command cmdPtr = it->second;
			//if(cmdPtr)
				cmdPtr.execute(barDecodedCommandE);
			
		}
	}
	/*
	 * This function is called by the appication to register its command for Events.  
	 */
	void registerEvent(EVENTS event, Command cmdPtr)
	{
		m_registeredCommands.put(event, cmdPtr);
	}
};