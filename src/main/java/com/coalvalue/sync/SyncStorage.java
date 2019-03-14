package com.coalvalue.sync;

import com.alibaba.fastjson.JSON;
import com.coalvalue.domain.entity.Employee;
import com.coalvalue.domain.entity.Inventory;
import com.coalvalue.domain.entity.StorageSpace;
import com.coalvalue.domain.entity.SyncProperties;
import com.coalvalue.enumType.QualityIndicatorEnum;
import com.coalvalue.enumType.ResourceType;
import com.coalvalue.enumType.SynchronizeEnum;
import com.coalvalue.repository.InventoryRepository;
import com.coalvalue.repository.StorageSpaceRepository;
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
public class SyncStorage {
    public String supportType = ResourceType.STORAGE.getText();

    @Autowired
    private StorageSpaceRepository storageSpaceRepository;



    @Transactional
    public void diffSync_from_server(List<SyncProperties> syncPropertiesesMap, LocalDateTime lastSync) {

        List<SyncProperties> syncPropertieses_storage = syncPropertiesesMap.stream().filter(e->e.getItemType().equals(supportType)).collect(Collectors.toList());
        if(syncPropertieses_storage.size() == 0){
            return ;
        }



        List<String> uuids = syncPropertieses_storage.stream().map(e->e.getObjectUuid()).collect(Collectors.toList());
        Map<String,StorageSpace> map_item = storageSpaceRepository.findByUuidIn(uuids).
                stream().collect( Collectors .toMap(vo -> vo.getUuid(), Function.identity()));
        for(SyncProperties syncProperties:syncPropertieses_storage){

            Map quotationPlan_remote = JSON.parseObject(syncProperties.getContent(),Map.class);
            StorageSpace storageSpace = map_item.get(syncProperties.getObjectUuid());

            createFromMap(quotationPlan_remote,storageSpace);




        }

    }
/*

    @Transactional
    public void sync(List<SyncProperties> syncPropertieses_quotationPlan) {


        for(SyncProperties syncProperties:syncPropertieses_quotationPlan){

            Map quotationPlan_remote = JSON.parseObject(syncProperties.getContent(),Map.class);
            createFromMap(quotationPlan_remote);




        }

    }
*/



    @Transactional
    public void createFromMap(Map inventory_map, StorageSpace storageSpace) {


        String no = (String)inventory_map.get("no");

        String objectUuid = (String)inventory_map.get("uuid");
        String name = (String)inventory_map.get("name");
        if(storageSpace == null){

            storageSpace = new StorageSpace();
            storageSpace.setNo(no);

            storageSpace.setUuid(objectUuid);
            storageSpaceRepository.save(storageSpace);

        }
        storageSpace.setName(name);
        storageSpaceRepository.save(storageSpace);

    }
}
