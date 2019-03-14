package com.coalvalue.service.assistant;

import com.coalvalue.notification.liveEvent.event;

import java.util.EventObject;

/**
 * Created by silence on 2018-07-15.
 */
public interface AssistantService {

    void start();

    void stop();

    String status();

    String info();

    boolean isProcessAlive(Process process);

    String doInBackground(Object... params);

    String restart();
}
