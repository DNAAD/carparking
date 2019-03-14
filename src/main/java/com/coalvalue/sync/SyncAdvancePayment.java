package com.coalvalue.sync;

import com.alibaba.fastjson.JSON;
import com.coalvalue.domain.entity.AdvancedPaymentTransfer;
import com.coalvalue.domain.entity.InventoryTransfer;
import com.coalvalue.domain.entity.StorageSpace;
import com.coalvalue.domain.entity.SyncProperties;
import com.coalvalue.enumType.ResourceType;
import com.coalvalue.repository.AdvancedPaymentTransferRepository;
import com.coalvalue.repository.InventoryTransferRepository;
import com.coalvalue.repository.StorageSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class SyncAdvancePayment {
    public String supportType = ResourceType.Advanced_Payment_Transfer.getText();

    @Autowired
    private AdvancedPaymentTransferRepository advancedPaymentTransferRepository;


    @Autowired
    private InventoryTransferRepository inventoryTransferRepository;

    @Transactional
    public void diffSync(List<SyncProperties> syncPropertiesesMap, LocalDateTime lastSync) {

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




/*        List<StorageSpace> storageSpaces_ = storageSpaceRepository.findAll();
        Map<String,StorageSpace> storages = storageSpaces_.stream().collect(Collectors.toMap(StorageSpace::getNo, e->e));
        List<String> list = new ArrayList<>(storages.keySet());



        List<StorageSpace> new_storage = new ArrayList<>();
        for(Map inventory_map: inventory_mapS){*/
            String no = (String)inventory_map.get("no");

        String objectUuid = (String)inventory_map.get("uuid");
        AdvancedPaymentTransfer storageSpace = advancedPaymentTransferRepository.findByUuid(objectUuid);
        if(storageSpace == null){
            String name = (String)inventory_map.get("name");
            storageSpace = new AdvancedPaymentTransfer();
/*            storageSpace.setNo(no);
            storageSpace.setName(name);*/
            storageSpace.setUuid(objectUuid);
            advancedPaymentTransferRepository.save(storageSpace);

        }



    }



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
    }

}
