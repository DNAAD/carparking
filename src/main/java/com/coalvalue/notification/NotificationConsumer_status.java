package com.coalvalue.notification;


import com.coalvalue.configuration.ReactorEventConfig;
import com.coalvalue.enumType.EchoSessionTypeEnum;
import com.coalvalue.task.InitTasks;
import com.coalvalue.task.SystemStatusBroadcast;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.fn.Consumer;

@Service
public class NotificationConsumer_status implements Consumer<Event<NotificationData>> {


    @Autowired
    private InitTasks initTasks;


    @Autowired
    private SystemStatusBroadcast systemStatusBroadcast;




    public static String topic__COALPIT_DELIVERY_report = "/topic/COALPIT_DELIVERY_report/1";



    @Override
    public void accept(Event<NotificationData> notificationDataEvent) {


        NotificationData notificationData = notificationDataEvent.getData();
        System.out.println("notificationDataEvent---- " + ":"+notificationDataEvent.getKey() );
        if(notificationDataEvent.getKey().equals(ReactorEventConfig.notificationConsumer_status_online_event)){

            System.out.print("上线了， "+"notificationConsumer_status_online_event");

  /*          stateMachine.sendEvent(RegEventEnum.CONNECT);
            stateMachine.sendEvent(RegEventEnum.IDENTITY);
*/
            try {
                initTasks.identity(EchoSessionTypeEnum.Identity_bootup.getText());
            } catch (MqttException e) {
                e.printStackTrace();
            }


        }

        if(notificationDataEvent.getKey().equals(ReactorEventConfig.notificationConsumer_status_offline_event)){


            systemStatusBroadcast.reportFualtInfo(1);
            System.out.print("掉线了， "+"notificationConsumer_status_offline_event");





        }


    }




}