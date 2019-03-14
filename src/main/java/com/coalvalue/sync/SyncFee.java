package com.coalvalue.sync;

import com.alibaba.fastjson.JSON;
import com.coalvalue.domain.entity.Fee;
import com.coalvalue.domain.entity.Product;
import com.coalvalue.domain.entity.StorageSpace;
import com.coalvalue.domain.entity.SyncProperties;
import com.coalvalue.enumType.ResourceType;
import com.coalvalue.repository.FeeRepository;
import com.coalvalue.repository.StorageSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class SyncFee {
    public String supportType = ResourceType.FEE.getText();

    @Autowired
    private FeeRepository feeRepository;



    @Transactional
    public void diffSync_from_server(List<SyncProperties> syncPropertiesesMap, LocalDateTime lastSync) {

        List<SyncProperties> syncPropertieses_storage = syncPropertiesesMap.stream().filter(e->e.getItemType().equals(supportType)).collect(Collectors.toList());
        if(syncPropertieses_storage.size() == 0){
            return ;
        }


        List<String> uuids = syncPropertieses_storage.stream().map(e->e.getObjectUuid()).collect(Collectors.toList());
        Map<String,Fee> map_item = feeRepository.findByUuidIn(uuids).
                stream().collect( Collectors .toMap(vo -> vo.getUuid(), Function.identity()));
        for(SyncProperties syncProperties:syncPropertieses_storage){

            Map quotationPlan_remote = JSON.parseObject(syncProperties.getContent(),Map.class);
            Fee fee = map_item.get(syncProperties.getObjectUuid());
            createFromMap(quotationPlan_remote,fee);




        }

    }





    public void createFromMap(Map inventory_map, Fee fee) {


        String objectUuid = (String)inventory_map.get("uuid");

        if(fee == null){
            String no = (String)inventory_map.get("no");
            String distributorNo = (String)inventory_map.get("distributorNo");
            String pricingStrategy = (String)inventory_map.get("pricingStrategy");
            String status = (String)inventory_map.get("status");
            String tax = (String)inventory_map.get("tax");
            String type = (String)inventory_map.get("type");
            fee = new Fee();
            fee.setNo(no);
            fee.setDistributorNo(distributorNo);
            fee.setPricingStrategy(pricingStrategy);
            fee.setStatus(status);
            fee.setTax(tax);
            fee.setType(type);
            fee.setUuid(objectUuid);
            feeRepository.save(fee);

        }



    }
}
