package com.coalvalue.notification.liveEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by silence on 2018-07-15.
 */

@Component
public class ServerInit {


    @Autowired
    HardwareEventInvoker hrdwrEvntInvokerObj ;

    @Autowired
    ObjectDetectedCommand objectDetectedCommand ;


    @Autowired
    BarDecodedCommand barDecodedCommand ;
    //---------------
    //
    // ---------------------------------------------------------------//
    @PostConstruct
    int main()
    {


        hrdwrEvntInvokerObj.registerEvent(EVENTS.USB_ATTACHED, barDecodedCommand);
        hrdwrEvntInvokerObj.registerEvent(EVENTS.USB_DETTACHED, barDecodedCommand);
        hrdwrEvntInvokerObj.registerEvent(EVENTS.WIFI_CONNECTED, barDecodedCommand);
        hrdwrEvntInvokerObj.registerEvent(EVENTS.WIFI_DISCONNECTED, barDecodedCommand);

        hrdwrEvntInvokerObj.registerEvent(EVENTS.CAMERA_OBJECT_DETECTED, objectDetectedCommand);

        // Fire the Events here.
        // In real enviornment these Events will be fired from inside the framework when actual
        // hardware action takes place. But here we will simulate by invoking them manually.

        // At the time of invokation we dont know which command is going to execute.
        // So. invoker is completely unaware of actual receiver.

/*        Map map = new HashMap<>();
        hrdwrEvntInvokerObj.handleEvent(EVENTS.USB_ATTACHED,new BarDecodedCommandE(map));
        hrdwrEvntInvokerObj.handleEvent(EVENTS.USB_DETTACHED, new BarDecodedCommandE(map));
        hrdwrEvntInvokerObj.handleEvent(EVENTS.WIFI_CONNECTED, new BarDecodedCommandE(map));
        hrdwrEvntInvokerObj.handleEvent(EVENTS.WIFI_DISCONNECTED, new BarDecodedCommandE(map));*/

        return 0;
    }
}
