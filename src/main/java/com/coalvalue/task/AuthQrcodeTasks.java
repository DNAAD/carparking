package com.coalvalue.task;

import com.alibaba.fastjson.JSON;
import com.coalvalue.configuration.Constants;
import com.coalvalue.configuration.MqttPublishSample;
import com.coalvalue.enumType.DataSynchronizationTypeEnum;
import com.coalvalue.repository.ConfigurationRepository;
import com.coalvalue.service.*;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.coalvalue.configuration.WebSocketConfig.*;
@Component
public class AuthQrcodeTasks implements EnvironmentAware {



    protected transient Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    MqttClient mqttClient;


    @Autowired
    ConfigurationService configurationService;
    @Autowired
    LiveInformationService liveInformationService;

    @Autowired
    ConfigurationRepository configurationRepository;


    @Autowired
    StorageService storageService;

    @Autowired
    InventoryService inventoryService;

    @Autowired
    DistributorService distributorService;
    @Autowired
    MqttPublishSample mqttPublishSample;
    @Autowired
    SystemStatusBroadcast systemStatusBroadcast;

    ScheduledExecutorService service ;
    public void reportCloudDeliveryServer() {
        if(service == null || service.isTerminated()){
      /*      register();*/
            service = Executors.newSingleThreadScheduledExecutor();
            service.scheduleAtFixedRate(()-> register(), 1, 30, TimeUnit.SECONDS);

        }


        // 参数：1、任务体 2、首次执行的延时时间
        //      3、任务执行间隔 4、间隔时间单位


    }




    public void qrcodeArriaval(Map map) {
        happyGift((String)map.get("content"),(String)map.get("qrcodeType"));
    }




    Integer runTimes = 0;
    public void register() {

        if(mqttClient.isConnected() && systemStatusBroadcast.completeEastanblishChanel){


            if(systemStatusBroadcast.completeRegister){

                runTimes++;

                String client_request        = "request/"+ mqttPublishSample.getChannal_topic();
                logger.info("请求绑定 qrcode "+ mqttPublishSample.getChannal_topic());
                Map map = new HashMap<>();
                map.put("type", DataSynchronizationTypeEnum.Qrcode.getText());
                map.put("uuid", mqttPublishSample.getChannal_topic());

                map.put("qrcodeType",Constants.WX_QRCODE_TYPE_COMPANY_AUTO_SYSTEM_MANAGEMENT);
                try {
                    mqttClient.publish(client_request, JSON.toJSONString(map).getBytes(),2,false);
                } catch (MqttException e) {
                    e.printStackTrace();
                }


                if(runTimes == 5){
                    service.shutdown();
                    runTimes = 0;
                }

            }else{
                runTimes++;

                String client_request        = "request/"+ mqttPublishSample.getChannal_topic();
                logger.info("请求绑定 qrcode "+ mqttPublishSample.getChannal_topic());
                Map map = new HashMap<>();
                map.put("type", DataSynchronizationTypeEnum.Qrcode.getText());
                map.put("uuid", mqttPublishSample.getChannal_topic());
                //Constants.WX_QRCODE_TYPE_COMPANY_BIND_AUTO_SYSTEM

                map.put("qrcodeType",Constants.WX_QRCODE_TYPE_COMPANY_BIND_AUTO_SYSTEM);
                try {
                    mqttClient.publish(client_request, JSON.toJSONString(map).getBytes(),2,false);
                } catch (MqttException e) {
                    e.printStackTrace();
                }


                if(runTimes == 5){
                    service.shutdown();
                    runTimes = 0;
                }
            }




        }

    }


    @Override
    public void setEnvironment(Environment environment) {

    }

    public void happyGift(String ddddd, String qrcodeType) {
        Map content = new HashMap();


        if(Constants.WX_QRCODE_TYPE_COMPANY_BIND_AUTO_SYSTEM.equals(qrcodeType)){
            content.put("type", DataSynchronizationTypeEnum.Qrcode);
            content.put("content",ddddd);
            content.put("title","绑定系统");


            simpMessagingTemplate.convertAndSend(topic__COALPIT_DELIVERY_auth_bind, JSON.toJSON(content));
        }


        if(Constants.WX_QRCODE_TYPE_COMPANY_AUTO_SYSTEM_MANAGEMENT.equals(qrcodeType)){
            content.put("type", DataSynchronizationTypeEnum.Qrcode);
            content.put("content",ddddd);
            content.put("title","管理");
            simpMessagingTemplate.convertAndSend(topic__COALPIT_DELIVERY_auth_bind, JSON.toJSON(content));
        }

    }


















    public void generateBindAuthCode() {
        runTimes++;

        String client_request        = "request/"+ mqttPublishSample.getChannal_topic();
        logger.info("请求绑定 qrcode "+ mqttPublishSample.getChannal_topic());
        Map map = new HashMap<>();
        map.put("type", DataSynchronizationTypeEnum.Qrcode.getText());
        map.put("uuid", mqttPublishSample.getChannal_topic());
        try {
            mqttClient.publish(client_request, JSON.toJSONString(map).getBytes(),2,false);
        } catch (MqttException e) {
            e.printStackTrace();
        }


        if(runTimes == 5){
            service.shutdown();
            runTimes = 0;
        }
    }
}

