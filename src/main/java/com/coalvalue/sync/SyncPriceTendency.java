package com.coalvalue.sync;

import com.alibaba.fastjson.JSON;
import com.coalvalue.domain.entity.AdvancedPaymentTransfer;
import com.coalvalue.domain.entity.DevelopmentTrend;
import com.coalvalue.domain.entity.InventoryTransfer;
import com.coalvalue.domain.entity.SyncProperties;
import com.coalvalue.enumType.ResourceType;
import com.coalvalue.repository.AdvancedPaymentTransferRepository;
import com.coalvalue.repository.DevelopmentTrendRepository;
import com.coalvalue.repository.InventoryTransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class SyncPriceTendency {
    public String supportType = ResourceType.PriceTendency.getText();
    @Autowired
    private AdvancedPaymentTransferRepository advancedPaymentTransferRepository;


    @Autowired
    private DevelopmentTrendRepository developmentTrendRepository;

    @Transactional
    public void diffSync(List<SyncProperties> syncPropertiesesMap, String echo_session, LocalDateTime lastSync) {

        List<SyncProperties> syncPropertieses_storage = syncPropertiesesMap.stream().filter(e->e.getItemType().equals(supportType)).collect(Collectors.toList());
        if(syncPropertieses_storage.size() == 0){
            return ;
        }


        for(SyncProperties syncProperties:syncPropertieses_storage){

            Map quotationPlan_remote = JSON.parseObject(syncProperties.getContent(),Map.class);
            createFromMap(quotationPlan_remote);

        }

    }

    @Transactional
    public void sync(List<SyncProperties> syncPropertieses_quotationPlan) {


        for(SyncProperties syncProperties:syncPropertieses_quotationPlan){

            Map quotationPlan_remote = JSON.parseObject(syncProperties.getContent(),Map.class);
            createFromMap(quotationPlan_remote);




        }

    }



    @Transactional
    public void createFromMap( Map inventory_map) {


        String objectUuid = (String)inventory_map.get("uuid");
        String priceCategoryUuid = (String)inventory_map.get("priceCategoryUuid");
        BigDecimal value =  new BigDecimal(inventory_map.get("value").toString());
        System.out.println(inventory_map.get("createDate")+"tendency tendency tendency tendency tendency");
        System.out.println(inventory_map.get("createDate")+objectUuid);

        DevelopmentTrend storageSpace = developmentTrendRepository.findByUuid(objectUuid);
        if(storageSpace == null){
            storageSpace = new DevelopmentTrend();
            storageSpace.setUuid(objectUuid);
            storageSpace.setPriceCategoryUuid(priceCategoryUuid);
            storageSpace.setChangeDate(LocalDateTime.parse(inventory_map.get("createDate").toString()));
            storageSpace.setPrice(value);
            developmentTrendRepository.save(storageSpace);

        }



    }


/*
    @Transactional
    public List<SyncProperties> differentialSync(List<SyncProperties> syncPropertieses_quotationPlan, LocalDateTime _lastSync) {
        List<SyncProperties> syncProperties_fee = new ArrayList<>();

        List<InventoryTransfer> reportDeliveryOrders = inventoryTransferRepository.findByModifyDateAfter(_lastSync);

        for(InventoryTransfer reportDeliveryOrder: reportDeliveryOrders){
            SyncProperties syncProperties = new SyncProperties();
            syncProperties.setContent(JSON.toJSONString(reportDeliveryOrder));
            syncProperties.setObjectUuid(reportDeliveryOrder.getUuid());
            syncProperties.setItemType(supportType);
            syncProperties_fee.add(syncProperties);

        }
        System.out.println("发现 要同步的数据"+ syncProperties_fee.toString());

        return syncProperties_fee;
    }*/

}
