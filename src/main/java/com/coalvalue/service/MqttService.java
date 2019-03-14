package com.coalvalue.service;




/**
 * Created by silence yuan on 2015/7/25.
 */
public interface MqttService extends BaseService {



    void publishToHost(String license);

    void publishTo_Delivery_web_server(String license);

    void publishTo_core_web_server(String license);

    void subscribe(String appId);
}
