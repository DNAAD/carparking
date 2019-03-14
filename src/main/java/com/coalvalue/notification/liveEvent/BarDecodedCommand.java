package com.coalvalue.notification.liveEvent;


import com.alibaba.fastjson.JSON;
import com.coalvalue.domain.entity.ReportDeliveryOrder;
import com.coalvalue.service.BehaviouralService;
import com.coalvalue.service.DeliveryOrderService;
import com.coalvalue.service.NotificiationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.EventObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
class BarDecodedCommand implements Command
{



    @Autowired
    DeliveryOrderService deliveryOrderService;
    @Autowired
    private BehaviouralService behaviouralService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Override
public void execute(EventObject eventObject)
	{
        BarDecodedCommandE barDecodedCommandE  = (BarDecodedCommandE)eventObject;
		System.out.print("USBDisconnectedCommand\n");
        String type = barDecodedCommandE.getType();
        String data = barDecodedCommandE.getData();
        List<ReportDeliveryOrder> reportDeliveryOrder  = deliveryOrderService.findByValidQrcode(data);

        if(reportDeliveryOrder.size()!= 0){
            ReportDeliveryOrder deliveryOrder = reportDeliveryOrder.get(0);
            behaviouralService.add_verified(deliveryOrder);
        }

        Map content = new HashMap<>();//beingWeighed_lists.get(license);
        content.put("distributor","DDDDD");
        content.put("plateNumber",data);
        content.put("productName","DDDDDDDDDD");

        simpMessagingTemplate.convertAndSend(NotificiationService.topic__COALPIT_DELIVERY_report, JSON.toJSON(content));




    }


};