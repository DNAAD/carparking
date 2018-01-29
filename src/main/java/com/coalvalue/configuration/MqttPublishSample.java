package com.coalvalue.configuration;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class MqttPublishSample {

    String topic        = "MQTT Examples";
    String content      = "Message from MqttPublishSample";
    public static int qos             = 2;
// String broker       = "tcp://yulinmei.cn:1883";

        String broker       = "tcp://192.168.30.38:1883";

    String clientId     = "storage_client";


    public  MqttAsyncClient mqttClient;

 /*       private class DemoCallback implements MqttCallback {

        public void connectionLost(Throwable cause) {
            System.out.println("Connection lost - attempting reconnect.");
           // mqttClient =  connect();

        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken arg0) {
            // Not needed in this simple demo
        }

        @Override
        public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
            mqttReceiver.messageArrived(arg0,arg1);
            // Not needed in this simple demo
        }



            public void connectComplete(boolean reconnect, String serverURI) {
                if (reconnect) {
                    System.out.println("Connection Reconnected! To: " + serverURI);
                } else {
                    System.out.println("Initial Connection! To: " + serverURI);
                    //  sync.doNotify();
                }
               // addSubscriptions();
            }
    }*/

    MemoryPersistence persistence = new MemoryPersistence();

    //DemoCallback demoCallback = new DemoCallback();
/*    @Autowired
    MqttReceiver mqttReceiver;*/

    @Lazy
   @Autowired
    MqttReceiver mqttReceiver;


@Bean
public MqttAsyncClient mqttClient() {



    mqttClient =  connect();

    return mqttClient;


}
    /**
     *
     */
/*    public void addSubscriptions() {
        try {
            // topics on m2m.io are in the form <domain>/<stuff>/<thing>
            mqttClient.subscribe(topic,0);

        } catch (MqttSecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MqttException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }*/

    public MqttAsyncClient  connect() {
        try {



            String[] brokerList = new String[1];
            brokerList[0] = broker;
           DisconnectedBufferOptions bufferOpts = new DisconnectedBufferOptions();
            bufferOpts.setBufferEnabled(true);
            bufferOpts.setBufferSize(100);            // 100 message buffer
            bufferOpts.setDeleteOldestMessages(true); // Purge oldest messages when buffer is full
            bufferOpts.setPersistBuffer(false);       // Do not buffer to disk*/

            //MqttClient sampleClient = new MqttClient(broker, clientId, persistence);

       //     MqttClient sampleClient = new MqttClient(brokerList[0], clientId, persistence);// new MqttClient(broker, clientId, persistence);
            MqttAsyncClient mqttAsyncClient = new MqttAsyncClient(brokerList[0], clientId, persistence);// new MqttClient(broker, clientId, persistence);

            mqttAsyncClient.setBufferOpts(bufferOpts);

            MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setAutomaticReconnect(true);
            mqttConnectOptions.setCleanSession(false);
            mqttConnectOptions.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);

            mqttConnectOptions.setKeepAliveInterval(30);
            mqttConnectOptions.setConnectionTimeout(60);

          //  mqttConnectOptions.setKeepAliveInterval(30);
/*
            reconnectPeriod: 1000,
                    connectTimeout: 30000,
                    resubscribeOnReconnect: true
*/


            System.out.println("BEGIN Connecting to broker: "+broker);
            IMqttToken mqttToken = mqttAsyncClient.connect(mqttConnectOptions);
            mqttAsyncClient.setCallback(mqttReceiver);
            System.out.println("waitForCompletion roker: "+broker);
            mqttToken.waitForCompletion();
            System.out.println("Completion roker: "+broker);
            if (mqttToken.isComplete())
            {
                String topic_ =  "mqtt."+"BNzuQbRWYegDtrXc6siw";

                System.out.println(" mqttAsyncClient.subscribe(topic, 0) "+ topic_);
                try {

                    //brokerList[1] = BROKER_URL2;
                    //connOpt.setServerURIs(brokerList);
                    //         mqttAsyncClient.connect(connOpt);


                    //mqttAsyncClient.subscribe(topic,2);
                    //     mqttAsyncClient.setCallback(mqttReceiver);

                    mqttAsyncClient.subscribe(topic_, MqttPublishSample.qos);
                    System.out.println(" mqttAsyncClient.subscribe(topic, 0) "+ topic_);
                } catch (MqttException e) {
                    e.printStackTrace();
                }

                if (mqttToken.getException() != null)
                {
                    // TODO: retry
                }
            }



            return mqttAsyncClient;

        }catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
            return null;
        }



   /*     // TODO Auto-generated method stub
        mqttClient = new MqttClient("mqtt://localhost", "pubsub-1");
        mqttClient.setCallback(callback);
        mqttClient.connect();
        mqttClient.subscribe(topics);*/
    }
}