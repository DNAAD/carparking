package com.coalvalue.sync;

import com.alibaba.fastjson.JSON;
import com.coalvalue.configuration.ReactorEventConfig;
import com.coalvalue.domain.entity.Inventory;
import com.coalvalue.domain.entity.InventoryTransfer;
import com.coalvalue.domain.entity.QuotationPlan;
import com.coalvalue.domain.entity.SyncProperties;
import com.coalvalue.enumType.*;
import com.coalvalue.notification.NotificationConsumer;
import com.coalvalue.notification.NotificationData;
import com.coalvalue.repository.InventoryRepository;
import com.coalvalue.repository.QuotationPlanRepository;
import com.util.MD5Util;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import reactor.bus.Event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import static com.coalvalue.configuration.WebSocketConfig.*;

@Service
public class SyncInventory extends Sync {
    public String supportType = ResourceType.INVENTORY.getText();

    private static final Logger logger = LoggerFactory.getLogger(SyncInventory.class);


    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;



    @Transactional
    public void diffSync_from_server(List<SyncProperties> syncPropertiesesMap, String echo_session, LocalDateTime lastSync) {

        List<Inventory> notify_inventory = new ArrayList<>();

        List<SyncProperties> syncPropertieses_inventory = syncPropertiesesMap.stream().filter(e->e.getItemType().equals(supportType)).collect(Collectors.toList());
        if(syncPropertieses_inventory.size() == 0){
            return ;
        }


        List<String> uuids = syncPropertieses_inventory.stream().map(e->e.getObjectUuid()).collect(Collectors.toList());
        Map<String,Inventory> map_inventory = inventoryRepository.findByUuidIn(uuids).
                stream().collect( Collectors .toMap(vo -> vo.getUuid(), Function.identity()));



        if(syncPropertieses_inventory.size()!= 0){
            logger.info("收到了需要更新的 Inventory");

        }
        for(SyncProperties syncProperties:syncPropertieses_inventory){


            Map inventory_remote = JSON.parseObject(syncProperties.getContent(),Map.class);
            logger.debug("更新 item 详情： {}",inventory_remote.toString());

            logger.debug("以下是本地找到了 {}  ||");
            map_inventory.values().forEach(e->{
                System.out.println(e.toString());
            });
            logger.debug("以上是本地找到了 {}  ||");



            if(!version(syncProperties,1)){
                continue;
            }
            Inventory inventory = map_inventory.get(syncProperties.getObjectUuid());
            Inventory local = createFromMap(inventory_remote,inventory);
            notify_inventory.add(local);
        }






        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter(){
            public void afterCommit(){
                System.out.println("commit!!!");

                notify_inventory.forEach(e->e.toString());

                if(echo_session!= null && echo_session.equals(EchoSessionTypeEnum.Whole.getText())){

                }else{
                    System.out.println("&&&&&&&&&&&&&&&&&&&发送通知信息 &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                    Map content = new HashMap();

                    content.put("type", DataSynchronizationTypeEnum.Inventory.getText());
                    content.put("distributor","");
                    content.put("plateNumber","");
                    content.put("productName","");

                    simpMessagingTemplate.convertAndSend(topic__COALPIT_DELIVERY_report, JSON.toJSON(content));
                    System.out.println("&&&&&&&&&&&&&&&&&&&&&发送通知信息&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");

                }
            }
        });


    }



    @Transactional
    public Inventory createFromMap(Map inventory_map, Inventory quotationPlan_local) {


        String uuid = (String)inventory_map.get("uuid");
        String status = (String)inventory_map.get("status");
        BigDecimal quotation = (BigDecimal)inventory_map.get("quotation");
        BigDecimal inventory_on_hand = (BigDecimal)inventory_map.get("inventory");



        if(quotationPlan_local== null){
            logger.info("没有找到 Inventory，所以新建一个");
            String no = (String)inventory_map.get("no");
            String storageSpaceNo = (String)inventory_map.get("storageSpaceNo");
            String productCoalType = (String)inventory_map.get("coalType");
            String productGranularity = (String)inventory_map.get("granularity");

            String productNo = (String)inventory_map.get("productNo");

            List<Map> indicators = (List)inventory_map.get("indicators");





            quotationPlan_local = new Inventory();
            quotationPlan_local.setGranularity(productCoalType);
            quotationPlan_local.setCoalType(productGranularity);


            quotationPlan_local.setProductNo(productNo);
            quotationPlan_local.setStorageNo(storageSpaceNo);
            quotationPlan_local.setNo(no);

            if(indicators!= null){

                updateIndicators(quotationPlan_local,indicators);
            }
            quotationPlan_local.setUuid(uuid);


        }

        quotationPlan_local.setQuantityOnHand(inventory_on_hand);
        quotationPlan_local.setStatus(status);
        quotationPlan_local.setQuote(quotation);
        quotationPlan_local = inventoryRepository.save(quotationPlan_local);
        logger.info("更新完成 了Inventory,{}",quotationPlan_local.getNo());


        return quotationPlan_local;



    }

    private void updateIndicators(Inventory inventory, List<Map> indicators) {
        Map<String,Map> indicator_index_map = new HashMap<>();
        for(Map map: indicators){
            String name = (String)map.get("symbol");
            indicator_index_map.put(name,map);


        }

        List<QualityIndicatorEnum> symbiles = new ArrayList<>();
        symbiles.add(QualityIndicatorEnum.Qnetad);
        symbiles.add(QualityIndicatorEnum.Mt);
        symbiles.add(QualityIndicatorEnum.St);
        symbiles.add(QualityIndicatorEnum.Aad);
        symbiles.add(QualityIndicatorEnum.Vad);


        for(QualityIndicatorEnum indicatorEnum: symbiles){

            Map map = (Map)indicator_index_map.get(indicatorEnum.getSymbol());
            if(map != null){
                BigDecimal bigDecima1l = new BigDecimal(map.get("value").toString());

                if(indicatorEnum.getSymbol().equals(QualityIndicatorEnum.Qnetad.getSymbol())){
                    inventory.setIndicator1(bigDecima1l);
                }
                if(indicatorEnum.getSymbol().equals(QualityIndicatorEnum.Mt.getSymbol())){
                    inventory.setIndicator2(bigDecima1l);
                }
                if(indicatorEnum.getSymbol().equals(QualityIndicatorEnum.St.getSymbol())){
                    inventory.setIndicator3(bigDecima1l);
                }
                if(indicatorEnum.getSymbol().equals(QualityIndicatorEnum.Aad.getSymbol())){
                    inventory.setIndicator4(bigDecima1l);
                }
                if(indicatorEnum.getSymbol().equals(QualityIndicatorEnum.Vad.getSymbol())){
                    inventory.setIndicator5(bigDecima1l);
                }
            }


        }



    }


    @Transactional
    public List<SyncProperties> differentialSync(List<SyncProperties> syncPropertieses_quotationPlan, LocalDateTime _lastSync) {
        System.out.println("======同步的 数据类型"+ supportType);
        List<SyncProperties> syncProperties_fee = new ArrayList<>();

        List<Inventory> reportDeliveryOrders = inventoryRepository.findByModifyDateAfter(_lastSync);

        for(Inventory reportDeliveryOrder: reportDeliveryOrders){
            SyncProperties syncProperties = new SyncProperties();
            syncProperties.setContent(JSON.toJSONString(reportDeliveryOrder));
            syncProperties.setObjectUuid(reportDeliveryOrder.getUuid());
            syncProperties.setItemType(supportType);
            syncProperties_fee.add(syncProperties);

        }
        System.out.println("发现 要同步的数据"+supportType+ syncProperties_fee.toString());

        return syncProperties_fee;
    }





}
