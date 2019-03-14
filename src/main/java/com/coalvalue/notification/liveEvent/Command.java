package com.coalvalue.notification.liveEvent;

import java.util.EventObject;

/**
 * Created by silence on 2018-07-15.
 */
public interface Command {

    void execute(EventObject barDecodedCommandE);
}
