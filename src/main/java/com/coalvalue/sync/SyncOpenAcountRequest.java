package com.coalvalue.sync;

import com.coalvalue.configuration.MqttPublishSample;
import com.coalvalue.domain.entity.OpeningAccountRequest;
import com.coalvalue.domain.entity.SyncProperties;
import com.coalvalue.enumType.ResourceType;
import com.coalvalue.repository.ConfigurationRepository;
import com.coalvalue.repository.OpeningAccountRequestRepository;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class SyncOpenAcountRequest {


    @Autowired
    private ConfigurationRepository configurationRepository;

    @Autowired
    private OpeningAccountRequestRepository openingAccountRequestRepository;


    @Autowired
    private MqttPublishSample mqttPublishSample;

    @Autowired
    private MqttClient mqttClient;
    @Transactional
    public List<SyncProperties> sync(List<SyncProperties> syncPropertieses_quotationPlan) {
        List<SyncProperties> syncProperties_sync = new ArrayList<>();
        for(SyncProperties syncProperties: syncPropertieses_quotationPlan){
            if(syncProperties.getItemType().equals(ResourceType.Opening_Account_Request.getText())){

                System.out.println("syncProperties Advanced_Payment_Transfer "+syncProperties.toString());
                OpeningAccountRequest advancedPaymentTransfer = openingAccountRequestRepository.findByUuid(syncProperties.getObjectUuid());
                if(advancedPaymentTransfer == null) {
                    advancedPaymentTransfer = new OpeningAccountRequest();

                    advancedPaymentTransfer.setUuid(syncProperties.getObjectUuid());
                }

                openingAccountRequestRepository.save(advancedPaymentTransfer);
                syncProperties_sync.add(syncProperties);
            }



        }

        return syncProperties_sync;




    }



}
