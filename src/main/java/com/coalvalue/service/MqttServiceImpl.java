package com.coalvalue.service;



import com.coalvalue.configuration.MqttPublishSample;
import com.coalvalue.configuration.MqttReceiver;



import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by silence yuan on 2015/7/25.
 */

@Service("mqttService")
public class MqttServiceImpl extends BaseServiceImpl implements MqttService {



    @Autowired
    private MqttClient mqttClient;



    @Autowired
    MqttReceiver mqttReceiver;


    private boolean login = false;








    @Override
    public void publishToHost(String license) {
        MqttMessage message = new MqttMessage();
        message.setPayload(license
                .getBytes());
        try {
            if(mqttClient.isConnected()){

                mqttClient.publish("topic_plate", message);
            }

        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void publishTo_Delivery_web_server(String license) {
        MqttMessage message = new MqttMessage();
        message.setPayload(license
                .getBytes());
        try {
            if(mqttClient.isConnected()){

                mqttClient.publish(MqttPublishSample.topic_delivery_web_server, message);
            }

        } catch (MqttException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void publishTo_core_web_server(String license) {
        MqttMessage message = new MqttMessage();
        message.setPayload(license.getBytes());

        try {
            if(mqttClient.isConnected()){
                mqttClient.publish(MqttPublishSample.topic_delivery_web_server, message);
            }

        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void subscribe(String appId) {
        String topic_ =  "mqtt."+appId;
        String[] topics = new String[1];
        topics[0]= topic_;


        int[] qos = new int[1];
        qos[0] = MqttPublishSample.qos;

        try {
            logger.debug("===={}", mqttClient.toString());
            mqttClient.subscribe(topics, qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }
}
