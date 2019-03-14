package com.coalvalue.sync;

import com.alibaba.fastjson.JSON;
import com.coalvalue.domain.entity.QuotationPlan;
import com.coalvalue.domain.entity.SyncProperties;
import com.coalvalue.enumType.DataSynchronizationTypeEnum;
import com.coalvalue.enumType.EchoSessionTypeEnum;
import com.coalvalue.enumType.ResourceType;
import com.coalvalue.notification.NotificationConsumer;
import com.coalvalue.repository.QuotationPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import static com.coalvalue.configuration.WebSocketConfig.*;

@Service
public class SyncQuotationPlan extends Sync{

    public String supportType = ResourceType.QUOTATION_PLAN.getText();
    @Autowired
    private QuotationPlanRepository quotationPlanRepository;



    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    @Transactional
    public void diffSync_from_server(List<SyncProperties> syncPropertiesesMap, String echo_session, LocalDateTime lastSync) {



        List<SyncProperties> syncPropertieses_QuotationPlan = syncPropertiesesMap.stream().filter(e->e.getItemType().equals(supportType)).collect(Collectors.toList());
        if(syncPropertieses_QuotationPlan.size() == 0){
            return;
        }
        diffSync_transaction(syncPropertieses_QuotationPlan,lastSync);



       if(echo_session!= null && echo_session.equals(EchoSessionTypeEnum.Whole.getText())){

        }else{

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
            Map content = new HashMap();

            content.put("type", DataSynchronizationTypeEnum.Probationary_quotation.getText());
            content.put("plateNumber","");

            simpMessagingTemplate.convertAndSend(topic__COALPIT_DELIVERY_report, JSON.toJSON(content));
            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");

        }

    }


    public void diffSync_transaction(List<SyncProperties> syncPropertieses_QuotationPlan,  LocalDateTime lastSync) {



        List<String> uuids = syncPropertieses_QuotationPlan.stream().map(e->e.getObjectUuid()).collect(Collectors.toList());
        Map<String,QuotationPlan> quotationPlanes = quotationPlanRepository.findByUuidIn(uuids).
                stream().collect( Collectors .toMap(vo -> vo.getUuid(), Function.identity()));

        for(SyncProperties syncProperties:syncPropertieses_QuotationPlan){

            if(version(syncProperties,1)){

            }
            QuotationPlan quotationPlan_remote = JSON.parseObject(syncProperties.getContent(),QuotationPlan.class);

            QuotationPlan quotationPlan_local = quotationPlanes.get(syncProperties.getObjectUuid());


            //TODO uuid, triggerDate, BeingAjustedTime, status

            if(quotationPlan_local == null){
                quotationPlan_local = new QuotationPlan();
                quotationPlan_local.setUuid(quotationPlan_remote.getUuid());
                quotationPlan_local.setTriggerDate(quotationPlan_remote.getTriggerDate());
                quotationPlan_local.setBeingAdjustedTime(quotationPlan_remote.getBeingAdjustedTime());


            }
            quotationPlan_local.setStatus(quotationPlan_remote.getStatus());
            quotationPlanRepository.save(quotationPlan_local);


        }


    }

}
