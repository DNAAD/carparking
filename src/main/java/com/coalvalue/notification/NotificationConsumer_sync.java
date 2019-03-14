package com.coalvalue.notification;

import static com.coalvalue.configuration.WebSocketConfig.*;
import com.alibaba.fastjson.JSON;
import com.coalvalue.configuration.MqttPublishSample;
import com.coalvalue.configuration.ReactorEventConfig;
import com.coalvalue.domain.entity.*;
import com.coalvalue.enumType.DataSynchronizationTypeEnum;
import com.coalvalue.notification.liveEvent.NotificationData_sync;
import com.coalvalue.repository.*;
import com.coalvalue.service.sync.DifferentialSyncService;
import com.coalvalue.web.MobileIndexController;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.bus.Event;
import reactor.fn.Consumer;

import java.util.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Service
public class NotificationConsumer_sync implements Consumer<Event<NotificationData_sync>> {


    @Autowired
    private DifferentialSyncService differentialSyncService;


    @Autowired
    private MqttPublishSample mqttPublishSample;



    @Autowired
    private MqttClient mqttClient;


    @Autowired
    private QuotationPlanRepository quotationPlanRepository;


    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;




    @Override
    public void accept(Event<NotificationData_sync> notificationDataEvent) {


        NotificationData_sync notificationData = notificationDataEvent.getData();
        System.out.println("notificationDataEvent---- " + ":"+notificationDataEvent.getKey() );
        if(notificationDataEvent.getKey().equals(ReactorEventConfig.notificationConsumer_sync_event)){
            differentialSyncService.syncImmediately();
        }

        if(notificationDataEvent.getKey().equals(ReactorEventConfig.notificationConsumer_sync_response_event)){



            ;
            Map map = (Map)notificationData.getObject();
            String content  = (String)map.get("sync");






            System.out.println("topic_event_sync_server_OfflineDataSync_prefix---- " + ":"+content );


            //  sync = "[{\"id\":134,\"createBy\":0,\"modifyBy\":0,\"createDate\":{\"month\":\"SEPTEMBER\",\"year\":2018,\"dayOfYear\":257,\"dayOfMonth\":14,\"dayOfWeek\":\"FRIDAY\",\"hour\":19,\"minute\":17,\"nano\":0,\"second\":29,\"monthValue\":9,\"chronology\":{\"id\":\"ISO\",\"calendarType\":\"iso8601\"}},\"modifyDate\":{\"month\":\"SEPTEMBER\",\"year\":2018,\"dayOfYear\":257,\"dayOfMonth\":14,\"dayOfWeek\":\"FRIDAY\",\"hour\":19,\"minute\":17,\"nano\":0,\"second\":29,\"monthValue\":9,\"chronology\":{\"id\":\"ISO\",\"calendarType\":\"iso8601\"}},\"version\":0,\"itemId\":69,\"itemType\":\"Posting\",\"timestamp\":{\"month\":\"SEPTEMBER\",\"year\":2018,\"dayOfYear\":257,\"dayOfMonth\":14,\"dayOfWeek\":\"FRIDAY\",\"hour\":19,\"minute\":17,\"nano\":0,\"second\":29,\"monthValue\":9,\"chronology\":{\"id\":\"ISO\",\"calendarType\":\"iso8601\"}},\"syncStatus\":\"SyncPending\",\"distributorNo\":\"8502\"}]";
            try{
                List<SyncProperties> syncPropertieses = JSON.parseArray(content,SyncProperties.class);
                get(syncPropertieses);


            }catch (Exception e){
                e.printStackTrace();
            }







        }
        if(notificationDataEvent.getKey().equals(ReactorEventConfig.notificationConsumer_sync_compare_than_sync_event)){

            Map map = (Map)notificationData.getObject();
            String sync  = (String)map.get("sync");

            Map syncPropertiesesMap = JSON.parseObject(sync,Map.class);

            System.out.println("notificationConsumer_sync_compare_than_sync_event---- " + ":"+sync );

            //  sync = "[{\"id\":134,\"createBy\":0,\"modifyBy\":0,\"createDate\":{\"month\":\"SEPTEMBER\",\"year\":2018,\"dayOfYear\":257,\"dayOfMonth\":14,\"dayOfWeek\":\"FRIDAY\",\"hour\":19,\"minute\":17,\"nano\":0,\"second\":29,\"monthValue\":9,\"chronology\":{\"id\":\"ISO\",\"calendarType\":\"iso8601\"}},\"modifyDate\":{\"month\":\"SEPTEMBER\",\"year\":2018,\"dayOfYear\":257,\"dayOfMonth\":14,\"dayOfWeek\":\"FRIDAY\",\"hour\":19,\"minute\":17,\"nano\":0,\"second\":29,\"monthValue\":9,\"chronology\":{\"id\":\"ISO\",\"calendarType\":\"iso8601\"}},\"version\":0,\"itemId\":69,\"itemType\":\"Posting\",\"timestamp\":{\"month\":\"SEPTEMBER\",\"year\":2018,\"dayOfYear\":257,\"dayOfMonth\":14,\"dayOfWeek\":\"FRIDAY\",\"hour\":19,\"minute\":17,\"nano\":0,\"second\":29,\"monthValue\":9,\"chronology\":{\"id\":\"ISO\",\"calendarType\":\"iso8601\"}},\"syncStatus\":\"SyncPending\",\"distributorNo\":\"8502\"}]";
            try{
/*
                List<SyncProperties> syncPropertieses_ConfigurationResponse = JSON.parseArray((String)syncPropertiesesMap.get("ConfigurationResponse"),SyncProperties.class);
                syncConfigurationResponse.sync(syncPropertieses_ConfigurationResponse);
*/

/*
                List<SyncProperties> syncPropertieses_QuotationPlan = JSON.parseArray((String)syncPropertiesesMap.get("QuotationPlan"),SyncProperties.class);
                syncQuotationPlan.sync(syncPropertieses_QuotationPlan);

*/

/*
                List<SyncProperties> syncPropertieses_ReportDeliveryOrder = JSON.parseArray((String)syncPropertiesesMap.get("ReportDeliveryOrder"),SyncProperties.class);
                if(syncPropertieses_ReportDeliveryOrder!= null){
                    syncReportDeliveryOrder.sync(syncPropertieses_ReportDeliveryOrder);
                }
*/

/*                List<SyncProperties> syncPropertieses_Employee = JSON.parseArray((String)syncPropertiesesMap.get("Employee"),SyncProperties.class);
                if(syncPropertieses_Employee.size()!= 0){
                    syncEmployee.sync(syncPropertieses_Employee);
                }*/
/*                List<SyncProperties> syncPropertieses_inventory = JSON.parseArray((String)syncPropertiesesMap.get("Inventory"),SyncProperties.class);
                if(syncPropertieses_inventory != null){
                    syncInventory.sync(syncPropertieses_inventory);
                }*/
/*                List<SyncProperties> syncPropertieses_storage = JSON.parseArray((String)syncPropertiesesMap.get("Storage"),SyncProperties.class);
                if(syncPropertieses_storage != null){
                    syncStorage.sync(syncPropertieses_storage);
                }*/
/*
                List<SyncProperties> syncPropertieses_syncProduct = JSON.parseArray((String)syncPropertiesesMap.get("Product"),SyncProperties.class);
                if(syncPropertieses_syncProduct != null){
                    syncProduct.sync(syncPropertieses_syncProduct);
                }
*/




                Optional<Map> dynamic = MobileIndexController.dynamic.stream().filter(e->e.get("name").equals("welcome")).findAny();
                if(dynamic.isPresent()){
                    Map focus = dynamic.get();
                    String focusRedirectUrl = linkTo(methodOn(MobileIndexController.class).focusRedirect((String)focus.get("uuid"))).toUri().toString();
                    Map ret = new HashMap<String, String>();
                    ret.put("status", false);
                    Map content = new HashMap();

                    content.put("type", "redirect");
                    content.put("content",focusRedirectUrl);
                    simpMessagingTemplate.convertAndSend(topic__COALPIT_DELIVERY_welcome, JSON.toJSON(content));

                }


            }catch (Exception e){
                e.printStackTrace();
            }

        }






        if(notificationDataEvent.getKey().equals(ReactorEventConfig.notificationConsumer_syncImmediately_event)){

            Runnable myRunnable = new Runnable(){

                public void run(){


                }
            };
            Thread thread = new Thread(myRunnable);
            thread.start();
            differentialSyncService.syncImmediately();


        }




    }









    @Transactional
    public void get(List<SyncProperties> syncPropertieses) {



        for(SyncProperties syncProperties:syncPropertieses){

            QuotationPlan quotationPlan_remote = JSON.parseObject(syncProperties.getContent(),QuotationPlan.class);
            QuotationPlan quotationPlan_local = quotationPlanRepository.findByUuid(quotationPlan_remote.getUuid());


            quotationPlan_local.setStatus(quotationPlan_remote.getStatus());
            quotationPlanRepository.save(quotationPlan_local);


        }

    }



    public void syncResult(List<SyncProperties> syncProperties_sync) {
        List<SyncProperties> syncProperties_empty_content = new ArrayList<>();
        for(SyncProperties syncProperties : syncProperties_sync){
            syncProperties.setContent(null);
            syncProperties_empty_content.add(syncProperties);
        }
        if(syncProperties_sync.size()>0){
            Map result = new HashMap();

            result.put("type",DataSynchronizationTypeEnum.Sync.getText());
            //contentMap.put("uuid",uuid);
            result.put("content",JSON.toJSONString(syncProperties_empty_content));

            String client_request        = "request/"+ mqttPublishSample.getChannal_topic();
            try {
                mqttClient.publish(client_request, JSON.toJSONString(result).getBytes(),2,false);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }

    }

}