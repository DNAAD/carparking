package com.coalvalue.notification;


import com.coalvalue.configuration.ReactorEventConfig;
import com.coalvalue.domain.entity.PlateRecognition;
import com.coalvalue.service.BehaviouralService;
import com.coalvalue.service.MqttService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.fn.Consumer;

@Service
public class NotificationConsumer_PlateRecognition implements Consumer<Event<NotificationData_plateRecognition>> {
 


    @Autowired
    private BehaviouralService behaviouralService;

    @Autowired
    private MqttService mqttService;


    @Override
    public void accept(Event<NotificationData_plateRecognition> notificationDataEvent) {


        NotificationData_plateRecognition notificationData = notificationDataEvent.getData();


        System.out.println("触发 车牌识别 事件 "+ ReactorEventConfig.notificationConsumer_plate_recognition_event);

        if(notificationDataEvent.getKey().equals(ReactorEventConfig.notificationConsumer_plate_recognition_event)){

            PlateRecognition deliveryOrder_from =(PlateRecognition) notificationData.getObject();



            behaviouralService.add_beingWeighed_Entrance(deliveryOrder_from.getDirection(),deliveryOrder_from);
            mqttService.publishToHost(deliveryOrder_from.getLicense());


        }


    }
}