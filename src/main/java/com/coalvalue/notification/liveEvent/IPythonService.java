package com.coalvalue.notification.liveEvent;

import com.coalvalue.notification.liveEvent.event;

import java.util.EventObject;

/**
 * Created by silence on 2018-07-15.
 */
public interface IPythonService {

    public String greet(event person);

    void handleEvent(EventObject e);
}
