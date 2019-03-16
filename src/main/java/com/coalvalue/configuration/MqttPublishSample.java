package com.coalvalue.configuration;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.coalvalue.protobuf.Hub;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.lang.Exception;
import java.lang.String;import java.lang.System;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service

public class MqttPublishSample {
    private Logger logger = LoggerFactory.getLogger(MqttPublishSample.class);



    public static String topic_delivery_web_server        = "delivery_web_server";



    public static int qos             = 1;
    public static int qos_2             = 1;
    @Value("${own.configuration.mqtt.broker.url}")
    public String broker;

  //  @Value("${imei}")
    @Value("868784021789953")


    public String imei;


    @Value("${own.configuration.mqtt.public_uuid_topic}")
    public String PUBLIC_UUID_TOPIC;


    @Value("${own.configuration.mqtt.uuid_topic_default}")
    public String UUID_TOPIC_default;





    @Autowired
    MqttClient mqttClient;


   @Autowired
   MqttReceiver mqttReceiver;
    @Autowired
    MqttConnectOptions mqttConnectOptions ;


    private String channal_topic;
    private String region_topic;
    private String public_topic;


    @Retryable(
            value = {MqttException.class,Exception.class},
            maxAttempts = 1000000, backoff = @Backoff(2000))

    public void reconnect() throws MqttException {
        try {


            logger.debug("开始正式连接.....");
            logger.debug("开始 yulinmei  的连接了啊啊啊啊 ， end --------------------------------------------- register service, to master");
            logger.debug(mqttConnectOptions.CONNECTION_TIMEOUT_DEFAULT+"开始 mqtt 连接了， end --------------------------------------------- register service, to master");
            logger.debug(mqttConnectOptions.getConnectionTimeout()+"开始 mqtt 连接了， end --------------------------------------------- register service, to master");

            logger.debug(LocalDateTime.now()+"");

            IMqttToken token = mqttClient.connectWithResult(mqttConnectOptions);
            logger.debug("连接结束，等待结果 啊啊 ");
            if (token.isComplete()) {

                logger.info("成功连接，成功连接！！！！！ ");
                logger.debug("定义主体 {} ", PUBLIC_UUID_TOPIC);
                logger.debug("定义主体 {} ", imei);
                System.out.println("Completion roker: " + broker);
                try {
                    System.out.println("Begin connect to roker: " + broker);


                    String[] topics_UUID = new String[2];
                    //topics_UUID[0]= UUID_TOPIC;
                    topics_UUID[0] = imei;
                    topics_UUID[1] = PUBLIC_UUID_TOPIC;

                    int[] qos_UUID = new int[2];
                    //qos_UUID[0] = MqttPublishSample.qos;
                    qos_UUID[0] = MqttPublishSample.qos_2;
                    qos_UUID[1] = MqttPublishSample.qos_2;
                    mqttClient.subscribe(topics_UUID, qos_UUID);
                    logger.info("订阅成功！"+ topics_UUID.toString());


                    String online = "online/" + imei;

                    MqttTopic onlinetopic = mqttClient.getTopic(online);
                    //setWill方法，如果项目中需要知道客户端是否掉线可以调用该方法。设置最终端口的通知消息
                    String onlineContent = imei + "online";
                    onlinetopic.publish(onlineContent.getBytes(), 1, true);


                    logger.info("通知上线！"+onlineContent);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("订阅，或上线通知失败！"+e.getMessage() + "  " + e.getClass().toString());
                }


            } else {
                logger.error(" 连接 "+mqttConnectOptions.getServerURIs() + " 异常失败");
                System.out.println("Completion ERROR roker: " + broker);

            }
        } catch (MqttException e) {
            e.printStackTrace();


            logger.debug(broker+"捕获MqttException mqtt 连接 依次 end --------------------------------------------- register service, to master");
            logger.debug(LocalDateTime.now()+"");
            //  reconnect();
            throw  e;


        }catch (Exception e_){
            e_.printStackTrace();
            logger.debug("其他错误啊啊啊啊 ");
            throw  e_;
        }

    }







    public void subscribe(String[] topics_uuid, int[] qos_uuid) throws MqttException {
        mqttClient.subscribe(topics_uuid, qos_uuid);

    }






    public void end() {
        if(mqttClient.isConnected()){
            try {
                mqttClient.disconnect();
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }

    }


    public String getUUID_TOPIC_default() {
        return UUID_TOPIC_default;
    }

    public void setUUID_TOPIC_default(String UUID_TOPIC_default) {
        this.UUID_TOPIC_default = UUID_TOPIC_default;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getChannal_topic() {

        return channal_topic;
    }

    public void setChannal_topic(String uuid_topic) {

        System.out.println("设置了 channal_topic"+ uuid_topic);
        this.channal_topic = uuid_topic;
    }

    public String getRegion_topic() {
        return region_topic;
    }

    public void setRegion_topic(String region_topic) {
        this.region_topic = region_topic;
    }

    public String getPublic_topic() {
        return public_topic;
    }

    public void setPublic_topic(String public_topic) {
        this.public_topic = public_topic;
    }

    public void reportStatus(List<Map> info) throws MqttException {



        System.out.println("发送了本地的状态信息啊啊 ");
        Hub.General requestIdentity = Hub.General.newBuilder()
                .setContent(JSONArray.toJSONString(info))
                .build();

        Hub.Message message = Hub.Message.newBuilder()
                .setSeq(UUID.randomUUID().toString())
                .setTimestamp(Timestamp.valueOf(LocalDateTime.now()).getTime())
                .setGeneral(requestIdentity)
                .build();

        String client_request        = status+ getChannal_topic();
        mqttClient.publish(client_request, message.toByteArray(),2,false);



    }
















    public static String lwtTopic_offline ="offline/";
    public static String online="online/";
    public static String request="request/";
    public static String image="image/";
    public static String principal="principal/";
    public static String queue="queue/";
    public static String status="status/";









    public void sendSyncComplete(LocalDateTime info) throws MqttException {



        System.out.println("发送了本地 此次同步完成信息 状态信息啊啊 ");
        Hub.SyncsComplete requestIdentity = Hub.SyncsComplete.newBuilder()
                .setLastSync(Timestamp.valueOf(info).getTime())
                .build();

        Hub.Message message = Hub.Message.newBuilder()
                .setSeq(UUID.randomUUID().toString())
                .setTimestamp(Timestamp.valueOf(LocalDateTime.now()).getTime())
                .setSyncsComplete(requestIdentity)
                .build();

        String client_request        = status+ getChannal_topic();
        mqttClient.publish(client_request, message.toByteArray(),2,false);



    }
}