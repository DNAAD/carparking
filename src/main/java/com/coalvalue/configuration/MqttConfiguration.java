package com.coalvalue.configuration;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;


@Configuration
//@ComponentScan(lazyInit = true)

public class MqttConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(MqttConfiguration.class);

    @Value("${own.configuration.mqtt.uuid_topic}")
    public String UUID_TOPIC;


    @Value("${own.configuration.mqtt.broker.url}")
    private String broker;


    public static String clientId     ;






    MemoryPersistence persistence = new MemoryPersistence();



    @Lazy
   @Autowired
    MqttReceiver mqttReceiver;





    @Primary
    @Bean("mqttClient")
    public MqttClient mqttClient() {

        try {



            logger.info("初始化 yulinmei mqtt 链接  broker url:{}",broker);
            String[] brokerList = new String[1];
            brokerList[0] = broker;
            DisconnectedBufferOptions bufferOpts = new DisconnectedBufferOptions();
            bufferOpts.setBufferEnabled(true);
            bufferOpts.setBufferSize(100);            // 100 message buffer
            bufferOpts.setDeleteOldestMessages(true); // Purge oldest messages when buffer is full
            bufferOpts.setPersistBuffer(false);       // Do not buffer to disk*//*


            //MqttClient sampleClient = new MqttClient(broker, clientId, persistence);

            clientId = imei+"storage_space_client_id";

            clientId = clientId+ UUID_TOPIC;//UUID.randomUUID();

            MqttClient mqttAsyncClient = new MqttClient(brokerList[0], clientId, persistence);// new MqttClient(broker, clientId, persistence);

            //mqttAsyncClient.setBufferOpts(bufferOpts);
            mqttAsyncClient.getDebug();

            mqttAsyncClient.setCallback(mqttReceiver);
            mqttAsyncClient.getDebug().dumpBaseDebug();
            mqttAsyncClient.getDebug().dumpClientComms();





/*
            reconnectPeriod: 1000,
                    connectTimeout: 30000,
                    resubscribeOnReconnect: true
*/



            //    System.out.println("BEGIN Connecting to broker: "+broker);


            //  System.out.println("waitForCompletion roker: "+broker);




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





    }
























    @Value("${own.configuration.mqtt.public_uuid_topic}")
    public String PUBLIC_UUID_TOPIC;


    @Value("${own.configuration.mqtt.uuid_topic_default}")
    public String UUID_TOPIC_default;





    public static int qos             = 1;
    public static int qos_2             = 1;


    //  @Value("${imei}")
    @Value("868784021789953")


    public String imei;





    @Bean
    public MqttConnectOptions mqttConnectOptions() {


        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();


        mqttConnectOptions.setAutomaticReconnect(true);
        //So the reconnect delay will be: 1, 2, 4, 8, 16, 32, 64, 128, 128, 128, 128, ...
        //mqttConnectOptions.setCleanSession(true);

        mqttConnectOptions.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);

        mqttConnectOptions.setKeepAliveInterval(58);// 3o秒

        mqttConnectOptions.setConnectionTimeout(60);// 3o秒

        mqttConnectOptions.setCleanSession(true);

        //
        String lwtTopic = "offline/"+imei;
        //MqttTopic topic = mqttClient.getTopic(lwtTopic);
        //setWill方法，如果项目中需要知道客户端是否掉线可以调用该方法。设置最终端口的通知消息
        String offlineContent = imei+"close";
        mqttConnectOptions.setWill(lwtTopic, offlineContent.getBytes(), 2, true);
        //mqttConnectOptions.setConnectionTimeout(30);
/*        try {
            reconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }*/


        logger.debug("初始化 mqtt 的 参数,准备 连接开化寺");
/*        try {
            reconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
*/
return mqttConnectOptions;

    }






}