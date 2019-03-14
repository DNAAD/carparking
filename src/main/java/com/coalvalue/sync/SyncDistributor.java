package com.coalvalue.sync;

import com.alibaba.fastjson.JSON;
import com.coalvalue.domain.entity.Distributor;
import com.coalvalue.domain.entity.Inventory;
import com.coalvalue.domain.entity.Product;
import com.coalvalue.domain.entity.SyncProperties;
import com.coalvalue.enumType.QualityIndicatorEnum;
import com.coalvalue.enumType.ResourceType;
import com.coalvalue.repository.DistributorRepository;
import com.coalvalue.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class SyncDistributor {
    public String supportType = ResourceType.Distributor.getText();

    @Autowired
    private DistributorRepository distributorRepository;




    @Transactional
    public void diffSync_from_server(List<SyncProperties> syncPropertiesesMap, LocalDateTime lastSync) {

        List<SyncProperties> syncPropertieses_inventory = syncPropertiesesMap.stream().filter(e->e.getItemType().equals(supportType)).collect(Collectors.toList());
        if(syncPropertieses_inventory.size() == 0){
            return ;
        }


        List<String> uuids = syncPropertieses_inventory.stream().map(e->e.getObjectUuid()).collect(Collectors.toList());
        Map<String,Distributor> map_item = distributorRepository.findByUuidIn(uuids).
                stream().collect( Collectors .toMap(vo -> vo.getUuid(), Function.identity()));
        for(SyncProperties syncProperties:syncPropertieses_inventory){

            Distributor inventory_remote = JSON.parseObject(syncProperties.getContent(),Distributor.class);
            Distributor distributor = map_item.get(syncProperties.getObjectUuid());
            createFromMap(inventory_remote,distributor);




        }

    }



    public void createFromMap(Distributor inventory_map, Distributor quotationPlan_local) {


        String uuid = inventory_map.getUuid();

        if(quotationPlan_local== null){
            String name = inventory_map.getName();
            String no = inventory_map.getNo();

            Distributor inventory = new Distributor();

            inventory.setUuid(uuid);
            inventory.setName(name);


            inventory.setNo(no);

            inventory = distributorRepository.save(inventory);

        }







    }


    @Transactional
    public List<SyncProperties> differentialSync(List<SyncProperties> syncPropertieses_quotationPlan, LocalDateTime _lastSync) {
        List<SyncProperties> syncProperties_fee = new ArrayList<>();

        List<Distributor> reportDeliveryOrders = distributorRepository.findByModifyDateAfter(_lastSync);

        for(Distributor reportDeliveryOrder: reportDeliveryOrders){
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
