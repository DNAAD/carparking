package com.coalvalue.sync;

import com.alibaba.fastjson.JSON;
import com.coalvalue.domain.entity.Equipment;
import com.coalvalue.domain.entity.Fee;
import com.coalvalue.domain.entity.SyncProperties;
import com.coalvalue.enumType.ResourceType;
import com.coalvalue.repository.EquipmentRepository;
import com.coalvalue.repository.FeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class SyncEquipment {
    public String supportType = ResourceType.Equipment.getText();


    @Autowired
    private EquipmentRepository equipmentRepository;


    @Transactional
    public void diffSync_from_server(List<SyncProperties> syncPropertiesesMap, LocalDateTime lastSync) {

        List<SyncProperties> syncPropertieses_storage = syncPropertiesesMap.stream().filter(e->e.getItemType().equals(supportType)).collect(Collectors.toList());
        System.out.println("受到了  设备 数据的 "+syncPropertieses_storage.size());
        if(syncPropertieses_storage.size() == 0){
            return ;
        }


        List<String> uuids = syncPropertieses_storage.stream().map(e->e.getObjectUuid()).collect(Collectors.toList());
        Map<String,Equipment> map_item = equipmentRepository.findByUuidIn(uuids).
                stream().collect( Collectors .toMap(vo -> vo.getUuid(), Function.identity()));
        for(SyncProperties syncProperties:syncPropertieses_storage){
            System.out.println("受到了  设备 数据的 "+syncProperties.toString());
            Map equipmentRemoteMap = JSON.parseObject(syncProperties.getContent(),Map.class);
            Equipment equipmentLocalEntity = map_item.get(syncProperties.getObjectUuid());
            createFromMap(equipmentRemoteMap,equipmentLocalEntity);




        }

    }






    @Transactional
    public void createFromMap(Map inventory_map, Equipment equipment) {


        String objectUuid = (String)inventory_map.get("uuid");

        if(equipment == null){
            String name = (String)inventory_map.get("name");
            String type = (String)inventory_map.get("type");
            String status = (String)inventory_map.get("status");
            String hubId = (String)inventory_map.get("hubId");
            String deviceId = (String)inventory_map.get("deviceId");
            equipment = new Equipment();
            equipment.setHubId(hubId);
            equipment.setName(name);
            equipment.setType(type);
            equipment.setStatus(status);
            equipment.setDeviceId(deviceId);

            equipment.setType(type);
            equipment.setUuid(objectUuid);



            equipmentRepository.save(equipment);

        }



    }
}
