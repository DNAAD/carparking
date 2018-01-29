package com.coalvalue.notification;


import com.alibaba.fastjson.JSON;
import com.coalvalue.domain.entity.ReportDeliveryOrder;
import com.coalvalue.repository.BehaviouralRepository;
import com.coalvalue.service.BehaviouralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.bus.Event;
import reactor.fn.Consumer;

import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationConsumer implements Consumer<Event<NotificationData>> {
 



    RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private BehaviouralService behaviouralService;

    @Autowired
    private BehaviouralRepository behaviouralRepository;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    public static String topic__COALPIT_DELIVERY_workbench = "/topic/COALPIT_DELIVERY_workbench/1";
    @Override
    public void accept(Event<NotificationData> notificationDataEvent) {


        NotificationData notificationData = notificationDataEvent.getData();
        behaviouralService.add_delivery_order(notificationData);
        ReportDeliveryOrder deliveryOrder_from = notificationData.getObject();
        Map map = new HashMap();
        Map content = new HashMap();

        map.put("id", 34);

        map.put("type", "canvassing_add");

        content.put("distributor",deliveryOrder_from.getCompanyName());
        content.put("plateNumber",deliveryOrder_from.getPlateNumber());
        content.put("productName",deliveryOrder_from.getProductName());

        map.put("content", content);

    //    simpMessagingTemplate.convertAndSend("/topic/storage/" + map.get("id"), JSON.toJSON(content));
        simpMessagingTemplate.convertAndSend(topic__COALPIT_DELIVERY_workbench,JSON.toJSON(content));




        System.out.println("behavioural---- " + ":" );


    }
}