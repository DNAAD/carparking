package com.coalvalue.task;


import com.alibaba.fastjson.JSON;
import com.coalvalue.configuration.SessionUtils;
import com.coalvalue.configuration.MqttPublishSample;
import com.coalvalue.domain.entity.*;
import com.coalvalue.protobuf.Hub;
import com.coalvalue.repository.*;
import com.coalvalue.service.*;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;


@Service
public class LiveBroadcast  {

/*
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
*/
@Autowired
LiveBroadcast liveBroadcast;
    @Autowired
    InventoryRepository inventoryRepository;


    @Autowired
    MqttClient mqttClient;


    @Autowired
    ConfigurationService configurationService;

    @Autowired
    LiveInformationService liveInformationService;

    @Autowired
    ConfigurationRepository configurationRepository;


    @Autowired
    StorageService storageService;

    @Autowired
    InventoryService inventoryService;

    @Autowired
    DistributorService distributorService;

    @Autowired
    MqttPublishSample mqttPublishSample;

    @Autowired
    SessionUtils sessionUtils;
    @Autowired
    EmployeeRepository employeeRepository;


    @Autowired
    StorageSpaceRepository storageSpaceRepository;

    @Autowired
    InstanceTransportRepository instanceTransportRepository;

    Map<String, LiveInforInventory> map_all_inventory = new HashMap();

/*
    Boolean ready = false;


    @PostConstruct
    public void postConstruct() {
        List<Inventory> inventories1 = inventoryRepository.findAll();
        for(Inventory inventory: inventories1){
            map_average_wait_time.put(inventory.getNo(),new BigDecimal(0.01));
            map_average_loading_time.put(inventory.getNo(),new BigDecimal(0.01));
        }
        ready = true;

    }*/



    //TODO 下提煤单，notificationConsumer_create_delivery_order_event
    // TODO，入站报送， notificationConsumer_input_tareweight_event
    // TODO, 出战 notificationConsumer_input_netweight_event

    // TODO, 登录 notificationConsumer_input_netweight_event

    // TODO, 服务器主动请求 notificationConsumer_input_netweight_event


    //TODO 第一次同步后，






    Map<String ,BigDecimal> map_average_wait_time = new HashMap();


    Map<String ,BigDecimal> map_average_loading_time = new HashMap();
/*    public BigDecimal reportAverageLoadingTime(String inventoryNo,BigDecimal time) {
        map_average_loading_time.put(inventoryNo,time);
        return map_average_loading_time.get(inventoryNo);
    }*/




/*    public BigDecimal reportAverageLoadingTime(String inventoryNo) {

        List<TraficIndex> traficIndices = traficIndexMap.get(inventoryNo);

        if(traficIndices != null){
            LocalDateTime localDateTimeEnd = LocalDateTime.now();
            LocalDateTime localDateTimeBegin = localDateTimeEnd.minusMinutes(5);
            Integer count = 0;
            Long total = 0L;
            for(TraficIndex traficIndexElement : traficIndices){

                if(traficIndexElement.getOccurTime().isAfter(localDateTimeBegin)){
                    total = traficIndexElement.getDuration().getSeconds()+total;
                    System.out.println("=============================== {}"+ total +"  "+ count);
                    count++;
                }
            }
            if(count!= 0 ){
                Double average = total*1.0/count;
                System.out.println("========================average======= {}"+ average);
                return new BigDecimal(average).setScale(2,BigDecimal.ROUND_HALF_UP);
            }else{
                return map_average_loading_time.get(inventoryNo).setScale(2);

            }
        }
        return map_average_loading_time.get(inventoryNo);



    }*/



















    public LiveInforInventory getLiveBroadcast(Inventory inventory) {

        LiveInforInventory map_all_inventory =  new LiveInforInventory();

        map_all_inventory.setInventoryNo(inventory.getNo());
        map_all_inventory.setCompanyNo(inventory.getCoalType());
        map_all_inventory.setQuantityOnHand(inventory.getQuantityOnHand());
        map_all_inventory.setStorageNo(inventory.getStorageNo());
        return map_all_inventory;
    }






/*
    Map<String, List<TraficIndex>> traficIndexMap = new HashMap<>();

    public void calculate(InstanceTransport instanceTransport,InventoryTransfer inventoryTransfer){

        List<TraficIndex> traficIndices = traficIndexMap.get(inventoryTransfer.getInventoryNo());

        if(traficIndices== null){
            traficIndices = new ArrayList<>();
            traficIndexMap.put(inventoryTransfer.getInventoryNo(),traficIndices);
        }

        Duration p = Duration.between( instanceTransport.getCreateDate(),inventoryTransfer.getCreateDate());

        TraficIndex traficIndex = new TraficIndex();
        traficIndex.setDuration(p);
        traficIndex.setOccurTime(LocalDateTime.now());

        traficIndices.add(traficIndex);

        LocalDateTime localDateTimeEnd = LocalDateTime.now();
        LocalDateTime localDateTimeBegin = localDateTimeEnd.minusMinutes(5);
        Integer count = 0;
        Long total = 0L;
        for(TraficIndex traficIndexElement : traficIndices){

            if(traficIndexElement.getOccurTime().isAfter(localDateTimeBegin)){
                total = traficIndexElement.getDuration().getSeconds()+total;
                System.out.println("=============================== {}"+ total +"  "+ count);
                count++;
            }
        }
        if(count!= 0 ){
            Long average = total/count;

            System.out.println("========================average======= {}"+ average);
            reportAverageLoadingTime(inventoryTransfer.getInventoryNo(), new BigDecimal(average));

        }else{
            reportAverageLoadingTime(inventoryTransfer.getInventoryNo(), new BigDecimal(-1));
        }




    }
*/
public void reportPrincipalEvent(User user) throws MqttException {


    Employee employee = employeeRepository.findByUserNo(user.getUserId());

    String client_request        = "principal/"+ mqttPublishSample.getChannal_topic();
/*        Map map = new HashMap();
        map.put("user_no",user.getUserId());
        map.put("employee_no",employee.getNo());
        map.put("employee_uuid",employee.getUuid());
        map.put("company_no",employee.getCompanyNo());
        map.put("storage_uuid",user.getPreference().getStorageUuid());*/

    Hub.Principal.Builder principal = Hub.Principal.newBuilder();


    if(user.getPreference().getStorageUuid()!= null){
        principal.setStorageId(user.getPreference().getStorageUuid());
    }

    principal.setEmployeeId(employee.getUuid())
            .setUserId(user.getUuid()).build();


    Hub.Message message = Hub.Message.newBuilder()
            .setPrincipal(principal)
            .setSeq("dd").build();


    mqttClient.publish(client_request, message.toByteArray(),2,false);


}


    public void reportQueueEvent_TEST(InstanceTransport instanceTransport, String inOrOut) throws MqttException {

        String client_request        = "queue/"+ mqttPublishSample.getChannal_topic();


        Hub.Queue queue = Hub.Queue.newBuilder()
                .setDeliveryOrderNo(instanceTransport.getDeliveryOrderNo())
                .setInventoryNo(instanceTransport.getInventoryNo())
                .setLicense(instanceTransport.getLicense())
                .setTransportUuid(instanceTransport.getUuid())
                .setStorageNo(instanceTransport.getStorageNo())
                .setInOut(inOrOut)
                .build();

        Hub.Message message = Hub.Message.newBuilder()
                .setSeq(UUID.randomUUID().toString())
                .setQueue(queue).build();
            mqttClient.publish(client_request, message.toByteArray(),2,false);
            System.out.println("000000+ 发布了："+ queue.toString());

 /*           map__.setAverageWaitingTime(map_average_wait_time.get(inventory.getNo()));
            map__.setAverageLaytime(reportAverageLoadingTime(inventory.getNo()));*/



    }

    public void reportQueueEvent_TEST(ReportDeliveryOrder reportDeliveryOrder, String inOrOut) throws MqttException {

        String client_request        = "queue/"+ mqttPublishSample.getChannal_topic();

        Map map = new HashMap();
        map.put("deliveryOrderNo",reportDeliveryOrder.getNo());
        map.put("inventoryNo",reportDeliveryOrder.getInventoryNo());
        map.put("license",reportDeliveryOrder.getLicense());
        map.put("storageNo",reportDeliveryOrder.getStorageNo());
        map.put("instanceTransportUuid",reportDeliveryOrder.getUuid());
        map.put("inOut",inOrOut);


            mqttClient.publish(client_request, JSON.toJSONString(map).getBytes(),2,false);
            System.out.println("000000+ 发布了："+ map.toString());



    }
}

