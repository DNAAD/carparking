package com.coalvalue.notification;

import com.coalvalue.configuration.Constants;
import com.coalvalue.configuration.MqttPublishSample;
import com.coalvalue.configuration.ReactorEventConfig;
import com.coalvalue.domain.entity.Configuration;
import com.coalvalue.enumType.DataSynchronizationTypeEnum;
import com.coalvalue.service.ConfigurationService;
import com.coalvalue.service.ConfigurationServiceImpl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.fn.Consumer;
import java.lang.Override;import java.lang.String;
import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationConsumer_qrcode implements Consumer<Event<NotificationData_qrcode>> {

    protected transient Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MqttPublishSample mqttPublishSample;

    @Autowired
    private ConfigurationService configurationService;


/*
    @Autowired
    private JMSSenderService jmsSenderService;
*/


    @Override
    public void accept(Event<NotificationData_qrcode> notificationDataEvent) {


        NotificationData_qrcode notificationData = notificationDataEvent.getData();

        if(notificationDataEvent.getKey().equals(ReactorEventConfig.notificationConsumer_qrcode_create_event)){



            String storageNo = notificationData.getStorageNo();
            String type = notificationData.getType();




         if(Constants.WX_QRCODE_TYPE_STORAGESPACE.equals(type)){


             Map map = new HashMap<>();


             Configuration configuration =  configurationService.getConfiguration(ConfigurationServiceImpl.PRODUCER_CONFIGURATION_KEY_companyNo);

             map.put("type", DataSynchronizationTypeEnum.Configuration_qrcode.getText());

             map.put("storageNo", storageNo);





            // mqttPublishSample.publishTo_core_server_request(JSON.toJSONString(map));



            }


        }


    }
}