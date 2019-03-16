package com.coalvalue.service.assistant.LocalMqtt;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public interface CallBack  {



        public void messageArrived(String topic, MqttMessage message)  ;




    }



