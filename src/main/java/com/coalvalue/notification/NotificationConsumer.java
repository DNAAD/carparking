package com.coalvalue.notification;


import com.alibaba.fastjson.JSON;
import com.coalvalue.configuration.MqttPublishSample;
import com.coalvalue.configuration.ReactorEventConfig;
import com.coalvalue.domain.entity.*;
import com.coalvalue.enumType.DataSynchronizationTypeEnum;
import com.coalvalue.notification.liveEvent.NotificationData_sync;
import com.coalvalue.repository.FollowerRepository;
import com.coalvalue.repository.ReportDeliveryOrderRepository;
import com.coalvalue.service.BehaviouralService;
import com.coalvalue.service.LiveInformationService;
import com.coalvalue.task.LiveBroadcast;
import com.coalvalue.web.MobileDeliveryOrderScanController;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.fn.Consumer;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

import static com.coalvalue.configuration.WebSocketConfig.topic__COALPIT_DELIVERY_report;
import static com.coalvalue.configuration.WebSocketConfig.topic__COALPIT_DELIVERY_scan;
import static com.coalvalue.configuration.WebSocketConfig.topic__COALPIT_DELIVERY_workbench;

@Service
public class NotificationConsumer implements Consumer<Event<NotificationData>> {
 

    @Autowired
    private BehaviouralService behaviouralService;

    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private LiveInformationService liveInformationService;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private MqttClient mqttClient;

    @Autowired
    private MqttPublishSample mqttPublishSample;

    @Autowired
    private ReportDeliveryOrderRepository reportDeliveryOrderRepository;

    @Autowired
    private FollowerRepository followerRepository;

    @Autowired
    private LiveBroadcast liveBroadcast;

    @Autowired
    private EventBus eventBus;



    @Autowired
    private MobileDeliveryOrderScanController mobileDeliveryOrderScanController;


    @Override
    public void accept(Event<NotificationData> notificationDataEvent) {


        NotificationData notificationData = notificationDataEvent.getData();
        System.out.println("notificationDataEvent---- " + ":"+notificationDataEvent.getKey() );
        if(notificationDataEvent.getKey().equals(ReactorEventConfig.notificationConsumer_delivery_order_authorize_complete_event)){

            List<String> deliveryOrderNos =notificationData.getDeliveryOrderNos();
            String storageNo =notificationData.getStorageNo();

            List<ReportDeliveryOrder> reportDeliveryOrders = new ArrayList<>();
            for(String no : deliveryOrderNos){
                ReportDeliveryOrder reportDeliveryOrder = reportDeliveryOrderRepository.findByNo(no);
                if(reportDeliveryOrder!= null){
                    System.out.println("notificationDataEvent---- " + ":"+topic__COALPIT_DELIVERY_scan+reportDeliveryOrder.getStorageNo());
                    reportDeliveryOrders.add(reportDeliveryOrder);
                }

            }
            mobileDeliveryOrderScanController.add_beingWeighed_Entrance(reportDeliveryOrders);


            Map content = new HashMap();

            content.put("type", "authorizeComplete");

            content.put("distributor","333");
            content.put("plateNumber", "4444");

            //    simpMessagingTemplate.convertAndSend("/topic/storage/" + map.get("id"), JSON.toJSON(content));
            simpMessagingTemplate.convertAndSend(topic__COALPIT_DELIVERY_scan+storageNo,JSON.toJSON(content));




            for(ReportDeliveryOrder deliveryOrder_from : reportDeliveryOrders){
                Map content_ = new HashMap();

                content_.put("id", 34);

                content_.put("type", "Delivery_order_auth_scan");

                content_.put("distributor",deliveryOrder_from.getCompanyName());
                content_.put("plateNumber",deliveryOrder_from.getLicense());
                content_.put("productName",deliveryOrder_from.getProductName());


                //    simpMessagingTemplate.convertAndSend("/topic/storage/" + map.get("id"), JSON.toJSON(content));
                simpMessagingTemplate.convertAndSend(topic__COALPIT_DELIVERY_workbench,JSON.toJSON(content_));
            }



        }

        if(notificationDataEvent.getKey().equals(ReactorEventConfig.notificationConsumer_create_delivery_order_event)){




            ReportDeliveryOrder deliveryOrder_from =(ReportDeliveryOrder) notificationData.getObject();
            Map map = new HashMap();
            Map content = new HashMap();

            map.put("id", 34);

            map.put("type", "canvassing_add");

            content.put("distributor",deliveryOrder_from.getCompanyName());
            content.put("plateNumber",deliveryOrder_from.getLicense());
            content.put("productName",deliveryOrder_from.getProductName());

            map.put("content", content);

            //    simpMessagingTemplate.convertAndSend("/topic/storage/" + map.get("id"), JSON.toJSON(content));
            simpMessagingTemplate.convertAndSend(topic__COALPIT_DELIVERY_workbench,JSON.toJSON(content));


            //liveInformationService.update(inventory,deliveryOrder_from);
            behaviouralService.add_delivery_order(notificationData);

            try {
                liveBroadcast.reportQueueEvent_TEST(deliveryOrder_from,"in");
            } catch (MqttException e) {
                e.printStackTrace();
            }

        }


        if(notificationDataEvent.getKey().equals(ReactorEventConfig.notificationConsumer_report_event)){
            String message =(String) notificationData.getObject();


            Map content = new HashMap();
            content.put("message",message);




            //    simpMessagingTemplate.convertAndSend("/topic/storage/" + map.get("id"), JSON.toJSON(content));
            simpMessagingTemplate.convertAndSend(topic__COALPIT_DELIVERY_report,JSON.toJSON(content));




            System.out.println("behavioural---- " + ":" );
        }

        if(notificationDataEvent.getKey().equals(ReactorEventConfig.notificationConsumer_input_tareweight_event)){
            ReportDeliveryOrder reportDeliveryOrder =(ReportDeliveryOrder) notificationData.getObject();
            Inventory inventory =(Inventory) notificationData.getInventory();

            InstanceTransport instanceTransport =(InstanceTransport) notificationData.getInstanceTransport();


            Map content = new HashMap();
            content.put("message",reportDeliveryOrder.getLicense());


            simpMessagingTemplate.convertAndSend(topic__COALPIT_DELIVERY_report,JSON.toJSON(content));


            System.out.println("behavioural---- " + ":" );

            liveInformationService.update(inventory,instanceTransport);









        }


        if(notificationDataEvent.getKey().equals(ReactorEventConfig.notificationConsumer_input_netweight_event)){



            InventoryTransfer inventoryTransfer = notificationData.getInventoryTransfer();

            logger.info("输入净重，出站{}",inventoryTransfer.getUuid());
            AdvancedPaymentTransfer advancedPaymentTransfer = notificationData.getAdvancedPaymentTransfer();
            Inventory inventory = notificationData.getInventory();
            InstanceTransport instanceTransport = notificationData.getInstanceTransport();

            ReportDeliveryOrder deliveryOrder = notificationData.getDeliveryOrder();



            Map content = new HashMap();
            content.put("no",deliveryOrder.getNo());

            content.put("weightNoteNo", instanceTransport.getInventoryNo());
            content.put("netWeight", instanceTransport.getNetWeight());
            content.put("tareWeight", instanceTransport.getTareWeight());

            content.put("outboundTime", instanceTransport.getOutboundTime());



            simpMessagingTemplate.convertAndSend(topic__COALPIT_DELIVERY_report,JSON.toJSON(content));


            content = new HashMap();
            content.put("no",inventoryTransfer.getNo());
            content.put("deliveryOrderNo",inventoryTransfer.getDeliveryOrderNo());
            content.put("weightNoteNo", instanceTransport.getInventoryNo());
            content.put("netWeight", instanceTransport.getNetWeight());
            content.put("tareWeight", instanceTransport.getTareWeight());
            content.put("taxAmount", inventoryTransfer.getAmount());
            content.put("unitPrice", inventoryTransfer.getUnitPrice());
            content.put("amount", inventoryTransfer.getAmount());
            content.put("outboundTime", instanceTransport.getOutboundTime());
            content.put("outboundTime", instanceTransport.getOutboundTime());
            content.put("weighmanName", inventoryTransfer.getWeighmanName());
            content.put("weighmanNo", inventoryTransfer.getWeighmanNo());

            String client_request        = "request/"+ mqttPublishSample.getChannal_topic();
            Map result = new HashMap();
            result.put("type", DataSynchronizationTypeEnum.Delivery_order.getText());
            result.put("content", content);
            try {
                mqttClient.publish(client_request, JSON.toJSONString(result).getBytes(), 2, false);
            } catch (MqttException e) {
                e.printStackTrace();
            }
           // liveBroadcast.calculate(instanceTransport,inventoryTransfer);
           // synchronizationDeliveryOrderService.syncImmediately(deliveryOrder,content);



            NotificationData_sync notificationData_sync = new NotificationData_sync();
            eventBus.notify(ReactorEventConfig.notificationConsumer_syncImmediately_event, Event.wrap(notificationData_sync));

        }




        if(notificationDataEvent.getKey().equals(ReactorEventConfig.notificationConsumer_follow_event)){

            Map map = (Map)notificationData.getObject();

            String companyNo = (String)map.get("companyNo");
            String nickname =(String) map.get("nickname");
            String openId = (String)map.get("openId");
            Long eventTime =(Long) map.get("eventTime");

            Map content = new HashMap();
            content.put("companyNo",companyNo);
            content.put("type", DataSynchronizationTypeEnum.Follower.getText());
            content.put("nickname",nickname);


            LocalDateTime triggerTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(eventTime), TimeZone.getDefault().toZoneId());
            content.put("eventTime", triggerTime.toString());
            saveFolower(openId,nickname,triggerTime);
            System.out.println("发送 给 前端 ---- " + ":" +content.toString());

            simpMessagingTemplate.convertAndSend(topic__COALPIT_DELIVERY_report,JSON.toJSON(content));










        }



    }


    @Transactional
    void saveFolower(String openId, String nickname, LocalDateTime eventTime){

        Follower follower = followerRepository.findByOpenId(openId);
        if(follower== null){
            follower = new Follower();
            follower.setStatus("");
            follower.setAttendTime(eventTime);
            follower.setOpenId(openId);
            follower.setNickName(nickname);

            followerRepository.save(follower);
        }else{
            follower.setNickName(nickname);
            follower.setAttendTime(eventTime);
            followerRepository.save(follower);
        }

    }
}