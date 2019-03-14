package com.coalvalue.configuration;



import com.alibaba.fastjson.JSON;
import com.coalvalue.enumType.DataSynchronizationTypeEnum;
import com.coalvalue.notification.*;
import com.coalvalue.protobuf.Hub;
import com.coalvalue.publicCommand.PrivateNotify;
import com.coalvalue.publicCommand.PublicNotify;
import com.coalvalue.repository.EventRepository;

import com.coalvalue.service.*;
import com.coalvalue.service.sync.DifferentialSyncService;
import com.coalvalue.task.*;
import com.google.protobuf.InvalidProtocolBufferException;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import reactor.bus.Event;
import reactor.bus.EventBus;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static com.coalvalue.configuration.WebSocketConfig.topic__COALPIT_DELIVERY_report;

@Component
public class MqttReceiver implements MqttCallbackExtended{//MqttCallback {
    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    protected transient Logger logger = LoggerFactory.getLogger(getClass());



    @Autowired
    EmployeeService employeeService;



@Autowired
TransportOperationService transportOperationService;
    @Autowired
    PrivateNotify privateNotify;

    @Autowired
    PublicNotify publicNotify;
    @Autowired
    EventBus eventBus;
    @Autowired
    EventRepository eventRepository;

    @Autowired
    DistributorService distributorService;
    @Autowired
    InventoryService inventoryService;
    @Autowired
    MqttPublishSample mqttPublishSample;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    AsynchronousDataSynchronizationService asynchronousDataSynchronizationService;



    @Autowired
    private DifferentialSyncService differentialSyncService;




    @Autowired
    InitTasks initTasks;
    @Autowired
    RegisterTasks registerTasks;



    Map<String, String> topics = new HashMap<>();

    @Override
public void connectionLost(Throwable cause) {
    // TODO Auto-generated method stub
    System.out.println("Connection lost -=========== attempting reconnect.");

        NotificationData notificationData = new NotificationData();

        eventBus.notify(ReactorEventConfig.notificationConsumer_status_offline_event, Event.wrap(notificationData));

/*        if(mqttPublishSample.scheduler.isShutdown()){
        }*/
        //mqttPublishSample.reconnect();

 /*       try {
            mqttPublishSample.reconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
*/
    }
/*

    @Scheduled(fixedDelay = 10000,   initialDelay=3000)
    public void checkLive() {
        LocalDateTime now = LocalDateTime.now();



    }
*/


@Override
public void messageArrived(String topic, MqttMessage messageByte)
        throws Exception {


        cachedThreadPool.execute(new Runnable() {

            @Override
            public void run() {

                System.out.println("收到信息=========="+topic+"========================================  "+topic);

                Hub.Message message = null;
                try {
                    message = Hub.Message.parseFrom(messageByte.getPayload());
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }



                if(topic.equals(mqttPublishSample.getImei())){

                    System.out.println("受到了 身份识别的 信息 来自 服务器的  进行身份 设置");


                    if(DataSynchronizationTypeEnum.Identity.getText().equals(message.getType())){

                        Hub.Identity identity = message.getIdentity();
                        String activationCode =identity.getActivationCode();

                        String status =identity.getStatus();
                        String objectUuid =identity.getObjectUuid();
                        String companyNo =identity.getCompanyNo();
                        String echo_session =identity.getEchoSession();
                        List<Hub.MqttTopic> topicsList =identity.getTopicsList();

                        topics = topicsList.stream().collect(Collectors.toMap(Hub.MqttTopic::getName, Hub.MqttTopic::getTopic));



                        initTasks.completeIdentity(echo_session,topicsList.stream().filter(e->e.getId()==0).findFirst().get().getTopic(),activationCode,status,objectUuid,companyNo,topicsList);

                    }


                }
                process(topic,message);









/*

                        if(DataSynchronizationTypeEnum.QrcodeScan.getText().equals(message.getType())){
                            NotificationData_configuration notificationData = new NotificationData_configuration();
                            notificationData.setObject(map);
                            eventBus.notify(ReactorEventConfig.notificationConsumer_configuration_event, Event.wrap(notificationData));
                        }
*/

/*
                        if(DataSynchronizationTypeEnum.Qrcode.getText().equals(message.getType())){
                            authQrcodeTasks.qrcodeArriaval(map);
                        }
*/




/*
                        if(DataSynchronizationTypeEnum.Follower.getText().equals(type)){
                            NotificationData notificationData = new NotificationData();
                            notificationData.setObject(map);
                            System.out.println("------------------------------ 调价信息啊啊啊啊："+ DataSynchronizationTypeEnum.Follower.getDisplayText());
                            eventBus.notify(ReactorEventConfig.notificationConsumer_follow_event, Event.wrap(notificationData));
                        }
*/


 /*                       if(DataSynchronizationTypeEnum.Configuration_qrcode.getText().equals(message.getType())){

                            String storageNo = (String)map.get("storageNo");
                            String content = (String)map.get("content");

                            qrcodeService.saveStorageQrocde(storageNo,content);




                        }*/




       /*                 if(DataSynchronizationTypeEnum.Image.getText().equals(message.getType())){

                            NotificationData_image notificationData = new NotificationData_image();
                            notificationData.setObject(map);
                            eventBus.notify(ReactorEventConfig.notificationConsumer_image_create_event, Event.wrap(notificationData));

                        }
*/


















                System.out.println("0000000000-000000000000000----------------------0"+message.toString());
                System.out.println("0000000000-000000000000000----------------------0"+messageByte.getQos());
                System.out.println("0000000000-000000000000000----------------------0"+messageByte.getId());

                System.out.println("====synchronizedType=  isRetained "+topic);




            }
        });







}

    public void process(String topic, Hub.Message message) {

        System.out.println("进入 process "+mqttPublishSample.getChannal_topic());
        if(topic.equals(mqttPublishSample.getChannal_topic())){
            System.out.println("====进入 私有 通道 "+mqttPublishSample.getChannal_topic());
            if(registerTasks.support(message)){
                registerTasks.process(message.getRegister());
            }
            if (privateNotify.support(message)) {
                privateNotify.decesion(message.getCommand());
            }


            if(registerTasks.surportSyncRequest(message)){
                registerTasks.decesionSyncRequest(message.getRegister());
            }

            if (differentialSyncService.surport(message)) {

                System.out.println("收到了 同步数据 ："+ message.getType());
                System.out.println("收到了 同步数据 ："+ message.getSeq());
                System.out.println("收到了 同步数据 ："+ message.getId());
                differentialSyncService.process(message.getSyncDifferential());
            }
        }




        if(topic.equals(mqttPublishSample.getRegion_topic())){
            Map map = new HashMap();
            map.put("type","type");
            simpMessagingTemplate.convertAndSend(topic__COALPIT_DELIVERY_report, JSON.toJSON(map));
        }



        if(topic.equals(mqttPublishSample.getPublic_topic())){
                publicNotify.decesion(message.getCommand());

        }




    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }


    @Override
    public void connectComplete(boolean reconnect, String serverURI) {

        logger.debug("连接主机后 发送 上线通知 ");
        NotificationData notificationData = new NotificationData();
        eventBus.notify(ReactorEventConfig.notificationConsumer_status_online_event, Event.wrap(notificationData));
        // 连接成功之后，处理相关逻辑
    }
}