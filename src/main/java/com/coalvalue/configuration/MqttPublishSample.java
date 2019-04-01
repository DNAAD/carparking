package com.coalvalue.configuration;

import com.alibaba.fastjson.JSONArray;
import com.coalvalue.domain.pojo.IMEIconfig;
import com.coalvalue.domain.pojo.TopicQos;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service

public class MqttPublishSample {
    private Logger logger = LoggerFactory.getLogger(MqttPublishSample.class);


    public static String topic_delivery_web_server        = "delivery_web_server";



    public static int qos             = 1;
    public static int qos_2             = 1;


    private IMEIconfig imei;


    @Value("${own.configuration.mqtt.public_uuid_topic}")
    private String PUBLIC_UUID_TOPIC;


    @Value("${own.configuration.mqtt.uuid_topic_default}")
    private String UUID_TOPIC_default;





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


            logger.info("开始重连接，连接...");
            logger.info(mqttConnectOptions.CONNECTION_TIMEOUT_DEFAULT+"");
            logger.info(mqttConnectOptions.getConnectionTimeout()+"");


            IMqttToken token = mqttClient.connectWithResult(mqttConnectOptions);
            logger.debug("等待连接结果...");
            if (token.isComplete()) {




            } else {
                logger.error(" 连接 "+mqttConnectOptions.getServerURIs() + " 异常失败{}",mqttConnectOptions.getServerURIs());

            }
        } catch (MqttException e) {
            e.printStackTrace();

            logger.debug(mqttConnectOptions.getServerURIs()+"捕获MqttException mqtt 连接 依次 end --------------------------------------------- register service, to master");
            logger.debug(LocalDateTime.now()+"");
            logger.debug(LocalDateTime.now()+""+mqttConnectOptions.getServerURIs());
            throw  e;


        }catch (Exception e_){
            e_.printStackTrace();
            logger.debug("其他错误啊啊啊啊 ");
            throw  e_;
        }

    }







    public void subscribe(List<TopicQos> topicQos_add)  {


        List<TopicQos> topicQos = new ArrayList<>();
        topicQos.add(TopicQos.of(imei.getImei(),MqttPublishSample.qos_2));
        topicQos.add(TopicQos.of(PUBLIC_UUID_TOPIC,MqttPublishSample.qos_2));
        topicQos_add.forEach(e->topicQos.add(e));

        String[] topics_UUID = new String[topicQos.size()];
        int[] qos_UUID = new int[topicQos.size()];


        for(int i = 0; i< topicQos.size(); i++){
            topics_UUID[i] = topicQos.get(i).getTopic();
            qos_UUID[i] =topicQos.get(i).getQos();
        }


        try {
            mqttClient.subscribe(topics_UUID, qos_UUID);
        } catch (MqttException e) {
            e.printStackTrace();
        }
        logger.info("订阅成功！"+ topics_UUID.toString());



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

    public IMEIconfig getImei() {
        return imei;
    }

    public IMEIconfig setImei(IMEIconfig imei) {
        this.imei = imei;
        return this.imei;
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

    public void publish(String topic,byte[] message) throws MqttException {

        mqttClient.publish(topic, message,2,false);



    }


    public MqttClient getMqttClient() {
        return mqttClient;
    }


}