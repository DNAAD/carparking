package com.coalvalue.service.sync;


import com.alibaba.fastjson.JSON;
import com.coalvalue.configuration.MqttPublishSample;
import com.coalvalue.domain.entity.SyncProperties;
import com.coalvalue.enumType.DataSynchronizationTypeEnum;
import com.coalvalue.enumType.EchoSessionTypeEnum;
import com.coalvalue.enumType.SynchronizeEnum;

import com.coalvalue.protobuf.Hub;
import com.coalvalue.service.*;
import com.coalvalue.sync.*;
import com.coalvalue.task.LiveBroadcast;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.TimeUnit;
import static com.coalvalue.configuration.WebSocketConfig.*;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by silence yuan on 2015/7/25.
 */

@Service("differentialSyncService")
public class DifferentialSyncService extends BaseServiceImpl {


    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public String status = SynchronizeEnum.SyncPending.getText();

    public Boolean waitingForSync = false;
    public final static Cache<String,Boolean> cache_seq_sync = CacheBuilder.newBuilder()
            //设置cache的初始大小为10，要合理设置该值  
            .initialCapacity(10)
            //设置并发数为5，即同一时间最多只能有5个线程往cache执行写入操作  
            .concurrencyLevel(5)
            //设置cache中的数据在写入之后的存活时间为10秒  
            .expireAfterWrite(10,TimeUnit.SECONDS)
            .recordStats()
            // 设置缓存的移除通知
            .removalListener(new RemovalListener<Object, Object>() {
                public void onRemoval(RemovalNotification<Object, Object> notification) {
                    System.out.println(
                            notification.getKey() + " was removed, cause is " + notification.getCause());
                }
            })
            //构建cache实例  
            .build();



    public Cache<String,Map> cache_notification = CacheBuilder.newBuilder()
            //设置cache的初始大小为10，要合理设置该值  
            .initialCapacity(10)
            //设置并发数为5，即同一时间最多只能有5个线程往cache执行写入操作  
            .concurrencyLevel(5)
            //设置cache中的数据在写入之后的存活时间为10秒  
            .expireAfterWrite(50,TimeUnit.SECONDS)
            .recordStats()
            // 设置缓存的移除通知
            .removalListener(new RemovalListener<String, Map>() {
                public void onRemoval(RemovalNotification<String, Map> notification) {

                    System.out.println(
                            notification.getKey() + " was removed, cause is " + notification.getCause());
                    System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");


                    simpMessagingTemplate.convertAndSend(topic__COALPIT_DELIVERY_report, JSON.toJSON(notification.getValue()));
                    System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");


                }
            })
            //构建cache实例  
            .build();


    // I keep a record of the last sync time.
    public static LocalDateTime _lastSync = LocalDateTime.now().minusYears(1);




    @Autowired
    private SyncReportDeliveryOrder syncReportDeliveryOrder;
    @Autowired
    private SyncInventory syncInventory;
    @Autowired
    private SyncStorage syncStorage;
    @Autowired
    private SyncProduct syncProduct;

    @Autowired
    private SyncPriceCategory syncPriceCategory;

    @Autowired
    private SyncPriceTendency syncPriceTendency;
    @Autowired
    private SyncFee syncFee;
    @Autowired
    private SyncInventoryTransfer syncInventoryTransfer;
    @Autowired
    private SyncAdvancePayment syncAdvancePayment;

    @Autowired
    private SyncDistributor syncDistributor;

    @Autowired
    private SyncEmployee syncEmployee;
    @Autowired
    private MqttPublishSample mqttPublishSample;

    @Autowired
    private SyncQuotationPlan syncQuotationPlan;

    @Autowired
    private MqttClient mqttClient;
    @Autowired
    private SyncConfigurationResponse syncConfigurationResponse;


    @Autowired
    private SyncEquipment syncEquipment;

/*    public void notifyImmediately(Map content) {
        System.out.println("添加了通知列表");
        cache_notification.put(UUID.randomUUID().toString(), content);
    }

    @Scheduled(fixedDelay = 20000,   initialDelay=3000)
    public void notifyImmediately() {
        System.out.println("添加了通知列表");
        ConcurrentMap<String, Map> cacheMap = cache_notification.asMap();

        cacheMap.keySet().forEach(e->

        {if(cache_notification.getIfPresent(e) != null){

            cache_notification.invalidate(e);
        }
        });
    }*/
    //@Transactional(isolation = Isolation.READ_UNCOMMITTED,propagation=Propagation.REQUIRED)
    @Transactional(isolation = Isolation.SERIALIZABLE)



    public void syncImmediately() {
        differentialSync(EchoSessionTypeEnum.Sync_portion.getText(), "group");
    }





    // TODO 收到服务器变化信息， 记录 服务器 要同步标记。  这边发起同步，
    // TODO 本地UPDATE,   如果 网络连接，立即本地发起同步， 如果网络不连接， 记录 UPDATE 时间
    // TODO 网络连接， 立即接茬 update 标志， 如果有 立即同步。


    // TODO  如果 同步 超时， 那么， 再次 本地发送 同步信息。




    public void differentialSync(String echo_session, String group){

        List<SyncProperties> syncProperties = new ArrayList<>();

        List<SyncProperties> syncPropertieses = null;
        syncProperties.addAll(syncReportDeliveryOrder.differentialSync(syncPropertieses,_lastSync));
        syncProperties.addAll(syncInventoryTransfer.differentialSync(syncPropertieses,_lastSync));
        syncProperties.addAll(syncInventory.differentialSync(syncPropertieses,_lastSync));


/*
        for(SyncProperties syncProperty: syncProperties){
            System.out.println("++"+syncProperty.getItemType()+ syncProperty.getObjectUuid());
            System.out.println("++"+syncProperty.getContent());
        }
*/

        System.out.println("================开始 本地需要同步的信息 开始发送到 服务器");
        differentialSync(syncProperties,echo_session); // 向服务器 同步 等待
        System.out.println("================完成 本地需要同步的信息 开始发送到 服务器");


    }



    void differentialSync(List<SyncProperties> list, String echo_session) {
        String seq = UUID.randomUUID().toString();
        cache_seq_sync.put(seq,true);


        Map map = new HashMap();
        if(echo_session!= null){
            map.put("echo_session",echo_session);
        }

        map.put("seq",seq);
        map.put("type",DataSynchronizationTypeEnum.Differential_sync.getText());
        map.put("sync", JSON.toJSONString(list));
        map.put("lastSync", _lastSync.toInstant(ZoneOffset.UTC).toEpochMilli());
        String client_request        = "request/"+ mqttPublishSample.getChannal_topic();


        try {
            mqttClient.publish(client_request,JSON.toJSONString(map).getBytes(),0,false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }



    public boolean surport(Hub.Message message) {

        if (DataSynchronizationTypeEnum.Differential_sync.getText().equals(message.getType())) { // 服务器单独发来的同步信息

            return true;
        }else {
            return false;
        }

    }

    public void process(Hub.SyncDifferential differentialSync) {
        Long _lastSync = differentialSync.getLastSync();
        LocalDateTime date = Instant.ofEpochMilli(_lastSync).atZone(ZoneOffset.UTC).toLocalDateTime();

        System.out.println(differentialSync.toString()+"***********************收到服务器的 更新**************************" + date);

        String seq = differentialSync.getSeq();
        String sync = differentialSync.getSync();

        String echo_session = differentialSync.getEchoSession();
        System.out.println("seq***" + seq);
        System.out.println("sync***" + sync);
        System.out.println("echo_session***" + echo_session);

        differentialSyncComplete(seq, sync, date, echo_session);


    }

    public void differentialSyncComplete(String seq_sync, String sync, LocalDateTime lastSync, String echo_session) {

        Boolean seq_done = cache_seq_sync.getIfPresent(seq_sync);


        if (seq_done!= null) {
            cache_seq_sync.invalidate(seq_done);



            List<SyncProperties> syncPropertiesesMap = JSON.parseArray(sync,SyncProperties.class);

            System.out.println("收到 差异同步的 回复信息， 来自服务器的 需要更新的数据---- " );
            for(SyncProperties syncProperties: syncPropertiesesMap){
                System.out.println(syncProperties.getItemType()+ "------"+syncProperties.getObjectUuid() );
                System.out.println("---- "+ syncProperties.getContent());
            }



            syncEquipment.diffSync_from_server(syncPropertiesesMap,lastSync);
            syncConfigurationResponse.diffSync_from_server(syncPropertiesesMap,lastSync);
            syncPriceTendency.diffSync(syncPropertiesesMap,echo_session,lastSync);
            syncPriceCategory.diffSync_from_server(syncPropertiesesMap,echo_session,lastSync);
            syncQuotationPlan.diffSync_from_server(syncPropertiesesMap,echo_session,lastSync);


            syncReportDeliveryOrder.diffSync_from_server(syncPropertiesesMap,echo_session,lastSync);
            syncEmployee.diffSync_from_server(syncPropertiesesMap,lastSync);
            syncInventory.diffSync_from_server(syncPropertiesesMap,echo_session,lastSync);
            syncStorage.diffSync_from_server(syncPropertiesesMap,lastSync);
            syncProduct.diffSync_from_server(syncPropertiesesMap);

            syncFee.diffSync_from_server(syncPropertiesesMap,lastSync);
            syncAdvancePayment.diffSync(syncPropertiesesMap,lastSync);

            syncDistributor.diffSync_from_server(syncPropertiesesMap,lastSync);




            System.out.println("更新了， 同步时间---- "+lastSync );
            System.out.println("更新了， 同步时间---- "+echo_session );
            //记录最后一次同步时间
            _lastSync = lastSync;

            status = SynchronizeEnum.SyncPending.getText();



        }



        if(EchoSessionTypeEnum.Whole.getText().equals(echo_session)){
            //liveBroadcast.reportEvent(true);
        }


        try {
            mqttPublishSample.sendSyncComplete(lastSync);
        } catch (MqttException e) {
            e.printStackTrace();
        }


    }

}
