package com.coalvalue.sync;

import com.alibaba.fastjson.JSON;
import com.coalvalue.domain.entity.Configuration;
import com.coalvalue.domain.entity.Distributor;
import com.coalvalue.domain.entity.QuotationPlan;
import com.coalvalue.domain.entity.SyncProperties;
import com.coalvalue.enumType.ResourceType;
import com.coalvalue.repository.ConfigurationRepository;
import com.coalvalue.repository.QuotationPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class SyncConfigurationResponse extends Sync{


    @Autowired
    private ConfigurationRepository configurationRepository;

    public String supportType = ResourceType.CONFIGURATION_RESPONSE.getText();





    @Transactional
    public void diffSync_from_server(List<SyncProperties> syncPropertiesesMap, LocalDateTime lastSync) {
        List<SyncProperties> syncPropertieses_ConfigurationResponse = syncPropertiesesMap.stream().filter(e->e.getItemType().equals(supportType)).collect(Collectors.toList());
        if(syncPropertieses_ConfigurationResponse.size() == 0){
            return ;
        }



        List<String> uuids = syncPropertieses_ConfigurationResponse.stream().map(e->e.getObjectUuid()).collect(Collectors.toList());
        Map<String,Configuration> map_item = configurationRepository.findByUuidIn(uuids).
                stream().collect( Collectors .toMap(vo -> vo.getUuid(), Function.identity()));
        for(SyncProperties syncProperties:syncPropertieses_ConfigurationResponse){

            Configuration quotationPlan_remote = JSON.parseObject(syncProperties.getContent(),Configuration.class);
            Configuration quotationPlan_local = map_item.get(quotationPlan_remote.getUuid());


            if(quotationPlan_local == null){
                quotationPlan_local= new Configuration();
                quotationPlan_local.setUuid(quotationPlan_remote.getUuid());
                quotationPlan_local.setKey(quotationPlan_remote.getKey());
                quotationPlan_local.setStringValue(quotationPlan_remote.getValue());
                quotationPlan_local.setModifyDate(quotationPlan_remote.getModifyDate());
                configurationRepository.save(quotationPlan_local);
            }else{

                quotationPlan_local.setUuid(quotationPlan_remote.getUuid());
                quotationPlan_local.setStringValue(quotationPlan_remote.getValue());
                quotationPlan_local.setKey(quotationPlan_remote.getKey());
                quotationPlan_local.setModifyDate(quotationPlan_remote.getModifyDate());
                configurationRepository.save(quotationPlan_local);
            }



        }

    }



    @Transactional
    public List<SyncProperties> differentialSync(List<SyncProperties> syncPropertieses_quotationPlan, LocalDateTime _lastSync) {


        List<SyncProperties> syncProperties_fee = syncPropertieses_quotationPlan.stream().filter(e->e.getItemType().equals(supportType)).collect(Collectors.toList());


        for(SyncProperties syncProperties:syncProperties_fee){


            Configuration syncProperties1 = configurationRepository.findByUuid(syncProperties.getObjectUuid());

            syncProperties.setContent(JSON.toJSONString(syncProperties1));

        }
        return syncPropertieses_quotationPlan;
    }

}
