package com.coalvalue.notification.liveEvent;


import com.alibaba.fastjson.JSON;
import com.coalvalue.domain.entity.ReportDeliveryOrder;
import com.coalvalue.notification.liveEvent.BarDecodedCommandE;
import com.coalvalue.notification.liveEvent.Command;
import com.coalvalue.python.ObjectDetectionDeepLearningPythonService;
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
class ObjectDetectedCommand implements Command
{



    @Autowired
    ObjectDetectionDeepLearningPythonService objectDetectionDeepLearningPythonService;


    @Autowired
    private BehaviouralService behaviouralService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Override
public void execute(EventObject eventObject)
	{
        ObjectedDecodedCommandE barDecodedCommandE  = (ObjectedDecodedCommandE)eventObject;
		System.out.print("USBDisconnectedCommand\n");
        String type = barDecodedCommandE.getType();
        String data = barDecodedCommandE.getData();

        String[] firest = data.split(":");
        for(String s: firest){
            System.out.print("发送信息\n"+s);
            if(s.equals("person")){
                objectDetectionDeepLearningPythonService.handler();

            }


        }

        Map content = new HashMap<>();//beingWeighed_lists.get(license);
        content.put("distributor","DDDDD");
        content.put("plateNumber",data);
        content.put("productName","DDDDDDDDDD");

        simpMessagingTemplate.convertAndSend(NotificiationService.topic__COALPIT_DELIVERY_report, JSON.toJSON(content));




    }


}