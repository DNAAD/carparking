package com.coalvalue.sync;

import com.alibaba.fastjson.JSON;
import com.coalvalue.domain.entity.InventoryTransfer;
import com.coalvalue.domain.entity.ReportDeliveryOrder;
import com.coalvalue.domain.entity.StorageSpace;
import com.coalvalue.domain.entity.SyncProperties;
import com.coalvalue.enumType.ResourceType;
import com.coalvalue.repository.InventoryTransferRepository;
import com.coalvalue.repository.StorageSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class SyncInventoryTransfer {
    public String supportType = ResourceType.INVENTORY_TRANSFER.getText();

    @Autowired
    private StorageSpaceRepository storageSpaceRepository;


    @Autowired
    private InventoryTransferRepository inventoryTransferRepository;

    @Transactional
    public void diffSync(List<SyncProperties> syncPropertiesesMap) {

        List<SyncProperties> syncPropertieses_storage = syncPropertiesesMap.stream().filter(e->e.getItemType().equals(supportType)).collect(Collectors.toList());

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
        StorageSpace storageSpace = storageSpaceRepository.findByUuid(objectUuid);
        if(storageSpace == null){
            String name = (String)inventory_map.get("name");
            storageSpace = new StorageSpace();
            storageSpace.setNo(no);
            storageSpace.setName(name);
            storageSpace.setUuid(objectUuid);
            storageSpaceRepository.save(storageSpace);

        }



    }



    @Transactional
    public List<SyncProperties> differentialSync(List<SyncProperties> syncPropertieses_quotationPlan, LocalDateTime _lastSync) {
        System.out.println("======同步的 数据类型"+ supportType);
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
