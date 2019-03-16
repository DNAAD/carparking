package com.coalvalue.notification;


import com.coalvalue.configuration.ApplicationReadyEventListener;
import com.coalvalue.configuration.ReactorEventConfig;
import com.coalvalue.enumType.EchoSessionTypeEnum;
import com.coalvalue.task.InitTasks;
import com.coalvalue.task.SystemStatusBroadcast;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.fn.Consumer;

@Service
public class NotificationConsumer_status implements Consumer<Event<NotificationData>> {
    private Logger logger = LoggerFactory.getLogger(NotificationConsumer_status.class);


    @Autowired
    private InitTasks initTasks;


    @Autowired
    private SystemStatusBroadcast systemStatusBroadcast;




    public static String topic__COALPIT_DELIVERY_report = "/topic/COALPIT_DELIVERY_report/1";



    @Override
    public void accept(Event<NotificationData> notificationDataEvent) {



        if(notificationDataEvent.getKey().equals(ReactorEventConfig.notificationConsumer_status_online_event)){
            logger.info("连接完成事件---- " + ":"+notificationDataEvent.getKey() );


            try {
                initTasks.identity(EchoSessionTypeEnum.Identity_bootup.getText());
            } catch (MqttException e) {
                e.printStackTrace();
            }


        }

        if(notificationDataEvent.getKey().equals(ReactorEventConfig.notificationConsumer_status_offline_event)){
            logger.info("与MQTT 连接断开，发送 "+"notificationConsumer_status_offline_event");

            systemStatusBroadcast.reportFualtInfo(1);





        }


    }




}