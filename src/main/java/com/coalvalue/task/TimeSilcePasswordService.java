package com.coalvalue.task;

import com.alibaba.fastjson.JSON;
import com.coalvalue.configuration.MqttPublishSample;
import com.coalvalue.domain.pojo.DistributedTimeSlicePassword;
import com.coalvalue.enumType.DataSynchronizationTypeEnum;
import com.coalvalue.service.BaseServiceImpl;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.coalvalue.configuration.WebSocketConfig.*;
/**
 * Created by silence yuan on 2015/7/25.
 */
@Service
public class TimeSilcePasswordService extends BaseServiceImpl {

    public static boolean tiemSliceReady = false;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    MqttPublishSample mqttPublishSample;

    @Autowired
    MqttClient mqttClient;
    Map<String,TimeSilceElement> timeSlicePasswords = new HashMap<>();


    public void syncImmediately(String plateNumber){

        List<Map> maps = JSON.parseArray(plateNumber, Map.class);

        Multimap<String, DistributedTimeSlicePassword> inventoryTransfers_groups = ArrayListMultimap.create();
        for(Map slice_map : maps){


            Integer key = (Integer)slice_map.get("key");
            String qrcodeContent = (String)slice_map.get("qrcodeContent");
            String storageNo = (String)slice_map.get("storageNo");
            DistributedTimeSlicePassword distributedTimeSlicePassword = new DistributedTimeSlicePassword();
            distributedTimeSlicePassword.setKey(key);
            distributedTimeSlicePassword.setQrcodeContent(qrcodeContent);
            distributedTimeSlicePassword.setStorageNo(storageNo);


            inventoryTransfers_groups.put(distributedTimeSlicePassword.getStorageNo(),distributedTimeSlicePassword);
        }

        for(String no: inventoryTransfers_groups.keys()){
            TimeSilceElement timeMaps = new TimeSilceElement();
            timeMaps.setIndex(0);

            Collection<DistributedTimeSlicePassword> distributedTimeSlicePasswordCollections = inventoryTransfers_groups.get(no);

            timeMaps.setCollection(distributedTimeSlicePasswordCollections);
            timeSlicePasswords.put(no, timeMaps);
        }




/*
        result3 = timeSlicePasswords.values().stream()
                .collect(Collectors.toList());*/
        //lists.add(distributedTimeSlicePassword);

    }


    public DistributedTimeSlicePassword getTimeSlice(String no) {


        TimeSilceElement result3 = timeSlicePasswords.get(no);

        if(result3!= null){
            List<DistributedTimeSlicePassword> distributedTimeSlicePasswords = (List)result3.getCollection();
            Integer count = result3.getIndex();
            DistributedTimeSlicePassword distributedTimeSlicePassword = distributedTimeSlicePasswords.get(count%(distributedTimeSlicePasswords.size()));
            count++;
            result3.setIndex(count);


            logger.debug("index is "+ count);
            logger.debug("sile is "+ distributedTimeSlicePasswords.size());


            return distributedTimeSlicePassword;
        }else{
            return null;
        }



    }

    public List<String> getAllStorages() {


        Set<String> nos =  timeSlicePasswords.keySet();


        List listN = new ArrayList();

        for(String no : nos){
            if(no!= null){
                listN.add(no);
            }
        }
        return listN;


    }


    @Scheduled(fixedDelay = 9000,   initialDelay=3000)
    public void qrcode_scan() {

        if(tiemSliceReady){
            System.out.println("============================= send qrcode update");

            Map content = new HashMap();

            content.put("id", 34);

            content.put("type", "canvassing_add");

            content.put("distributor","333");
            content.put("plateNumber", "4444");


            List<String> storageSpaces = getAllStorages();

            for(String storageSpace: storageSpaces){
                DistributedTimeSlicePassword distributedTimeSlicePassword = getTimeSlice(storageSpace);
                if(distributedTimeSlicePassword!= null){
                   // System.out.println("========================distributedTimeSlicePassword is NOT NULL"+distributedTimeSlicePassword.getQrcodeContent());
                    content.put("content",distributedTimeSlicePassword.getQrcodeContent());
                    simpMessagingTemplate.convertAndSend(topic__COALPIT_DELIVERY_scan+distributedTimeSlicePassword.getStorageNo(), JSON.toJSON(content));
                }else{
                   // System.out.println("========================distributedTimeSlicePassword is NULL");
                  //  content.put("content",UUID.randomUUID().toString());
                   // simpMessagingTemplate.convertAndSend(NotificationConsumer.topic__COALPIT_DELIVERY_scan, JSON.toJSON(content));
                }

            }


        }

    }



    private TaskScheduler scheduler;

  //  @PostConstruct
    public void executeTaskT_getTimeSilce() {
        System.out.println("=========TimeSilcePasswordService=======TimeSilcePasswordService============= TRIGER get silence");

        Map content = new HashMap();

        content.put("id", 34);
        content.put("type", DataSynchronizationTypeEnum.TimeSlice.getText());
        String client_request        = "request/"+ mqttPublishSample.getChannal_topic();
        //    simpMessagingTemplate.convertAndSend(NotificationConsumer.topic__COALPIT_DELIVERY_scan, JSON.toJSON(content));
        try {
            mqttClient.publish(client_request, JSON.toJSONString(content).getBytes(),2,false);
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }



    private class TimeSilceElement{
        Integer index = 0;
        Collection<DistributedTimeSlicePassword> collection;

        public Integer getIndex() {
            return index;
        }

        public void setIndex(Integer index) {
            this.index = index;
        }

        public Collection<DistributedTimeSlicePassword> getCollection() {
            return collection;
        }

        public void setCollection(Collection<DistributedTimeSlicePassword> collection) {
            this.collection = collection;
        }
    }

}
