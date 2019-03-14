package com.coalvalue.python;

import java.util.EventObject;
import java.util.Map;

/**
 * Created by silence on 2018-07-15.
 */
public interface IPythonService {

    void execute();

    Map getInfo();

    void start();

    void stop();

    boolean isLive();

    String getId();

    String getConfigurationUrl();
}
