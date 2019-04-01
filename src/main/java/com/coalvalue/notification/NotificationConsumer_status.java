package com.coalvalue.notification;


import com.coalvalue.configuration.ApplicationReadyEventListener;
import com.coalvalue.configuration.ReactorEventConfig;
import com.coalvalue.configuration.state.RegEventEnum;
import com.coalvalue.configuration.state.RegStatusEnum;
import com.coalvalue.enumType.EchoSessionTypeEnum;
import com.coalvalue.task.InitTasks;
import com.coalvalue.task.SystemStatusBroadcast;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.fn.Consumer;

@Service
public class NotificationConsumer_status implements Consumer<Event<NotificationData>> {
    private Logger logger = LoggerFactory.getLogger(NotificationConsumer_status.class);


    @Autowired
    private InitTasks initTasks;
    @Autowired
    private StateMachine<RegStatusEnum, RegEventEnum> stateMachine;


    @Autowired
    private SystemStatusBroadcast systemStatusBroadcast;


    private Integer count = 0;

    public static String topic__COALPIT_DELIVERY_report = "/topic/COALPIT_DELIVERY_report/1";



    @Override
    public void accept(Event<NotificationData> notificationDataEvent) {



        if(notificationDataEvent.getKey().equals(ReactorEventConfig.notificationConsumer_status_online_event)){
            logger.info("连接完成事件---- " + ":"+notificationDataEvent.getKey() );


   /*         if(stateMachine.getState().getId().name().equals(RegStatusEnum.CONNECTED.name())){

                System.out.println("------------------ 首次成功连接， 那么 要进入，连接识别，和 注册阶段啊啊。");
                Message<RegEventEnum> messageState = MessageBuilder
                        .withPayload(RegEventEnum.IDENTITY)
                        .setHeader("ORDER_ENTITY_KEY", "order")
                        .build();
                stateMachine.sendEvent(messageState);



                try {
                    initTasks.identity(EchoSessionTypeEnum.Identity_bootup.getText());
                } catch (MqttException e) {
                    e.printStackTrace();
                }

                count++;
            }else{


                System.out.println("------------------ 以及在识别，注册之中的步骤了，不能重复识别");
            }
*/


        }

        if(notificationDataEvent.getKey().equals(ReactorEventConfig.notificationConsumer_status_offline_event)){
            logger.info("与MQTT 连接断开，发送 "+"notificationConsumer_status_offline_event");

            systemStatusBroadcast.reportFualtInfo(1);





        }


    }




}