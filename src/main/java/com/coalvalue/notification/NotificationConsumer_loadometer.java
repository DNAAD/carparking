package com.coalvalue.notification;


import com.coalvalue.configuration.ReactorEventConfig;
import com.coalvalue.domain.entity.*;
import com.coalvalue.service.*;
import com.coalvalue.task.LiveBroadcast;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.fn.Consumer;

@Service
public class NotificationConsumer_loadometer implements Consumer<Event<NotificationData_loadometer>> {



    @Autowired
    private BehaviouralService behaviouralService;

    public static String topic__COALPIT_DELIVERY_workbench = "/topic/COALPIT_DELIVERY_workbench/1";
    public static String topic__COALPIT_DELIVERY_report = "/topic/COALPIT_DELIVERY_report/1";

    @Autowired
    LiveBroadcast liveBroadcast;

    @Override
    public void accept(Event<NotificationData_loadometer> notificationDataEvent) {


        NotificationData_loadometer notificationData = notificationDataEvent.getData();

        System.out.println("ddddddddddddddddddd");
        if(notificationDataEvent.getKey().equals(ReactorEventConfig.notificationConsumer_create_instance_transport_out_on_weight_event)){
            System.out.println("dddddddddddddddddddnotificationConsumer_create_instance_transport_out_on_weight_eventdddd");
            PlateRecognition plateRecognition = (PlateRecognition)notificationData.getObject();
            InstanceTransport instanceTransport = notificationData.getInstanceTransport();

            behaviouralService.add_beingWeighed_Exit(plateRecognition.getDirection(), plateRecognition, instanceTransport);

            try {
                liveBroadcast.reportQueueEvent_TEST(instanceTransport,"out");
            } catch (MqttException e) {
                e.printStackTrace();
            }

        }



        if(notificationDataEvent.getKey().equals(ReactorEventConfig.notificationConsumer_delivery_order_in_on_weight_event)){
            System.out.println("dddddddddddddddddddnotificationnotificationConsumer_delivery_order_in_on_weight_eventdddd");
            PlateRecognition plateRecognition = (PlateRecognition)notificationData.getObject();
            ReportDeliveryOrder reportDeliveryOrder = notificationData.getDeliveryOrder();
            InstanceTransport instanceTransport = notificationData.getInstanceTransport();
            behaviouralService.add_beingWeighed_Entrance(plateRecognition.getDirection(), plateRecognition, reportDeliveryOrder);
            try {
                liveBroadcast.reportQueueEvent_TEST(instanceTransport,"in");
            } catch (MqttException e) {
                e.printStackTrace();
            }

        }


    }


}