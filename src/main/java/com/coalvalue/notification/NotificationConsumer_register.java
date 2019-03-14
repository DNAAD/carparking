package com.coalvalue.notification;


import com.coalvalue.configuration.ReactorEventConfig;
import com.coalvalue.configuration.state.RegEventEnum;
import com.coalvalue.configuration.state.RegStatusEnum;
import com.coalvalue.domain.entity.Configuration;
import com.coalvalue.repository.ConfigurationRepository;
import com.coalvalue.service.ConfigurationServiceImpl;

import com.coalvalue.task.InitTasks;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.fn.Consumer;

import javax.transaction.Transactional;
import java.util.Map;


@Service
public class NotificationConsumer_register implements Consumer<Event<NotificationData_register>> {
    protected transient Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private StateMachine<RegStatusEnum, RegEventEnum> stateMachine;



    @Autowired
    private InitTasks scheduledTasksMqtt;

    @Autowired
    private ConfigurationRepository configurationRepository;

    @Override
    public void accept(Event<NotificationData_register> notificationDataEvent) {


        NotificationData_register notificationData = notificationDataEvent.getData();

        if(notificationDataEvent.getKey().equals(ReactorEventConfig.notificationConsumer_register_event)){










        }


    }

    @Transactional
    private void configuration(Map map) {
        logger.debug("================= content :{}", map.toString());
        String value = null;



        if(null != map.get(ConfigurationServiceImpl.PRODUCER_CONFIGURATION_KEY_companyNo)){
            value = (String)map.get(ConfigurationServiceImpl.PRODUCER_CONFIGURATION_KEY_companyNo);
            createConfiguration_String(ConfigurationServiceImpl.PRODUCER_CONFIGURATION_KEY_companyNo,value);
        }

        if(StringUtils.isNotBlank( (String)map.get(ConfigurationServiceImpl.PRODUCER_CONFIGURATION_KEY_companyName))){
            value = (String)map.get(ConfigurationServiceImpl.PRODUCER_CONFIGURATION_KEY_companyName);
            logger.debug("================= value :{}", value);

            createConfiguration_String(ConfigurationServiceImpl.PRODUCER_CONFIGURATION_KEY_companyName,value);
        }
        if(StringUtils.isNotBlank( (String)map.get(ConfigurationServiceImpl.PRODUCER_CONFIGURATION_KEY_qrcodeContent))){
            value = (String)map.get(ConfigurationServiceImpl.PRODUCER_CONFIGURATION_KEY_qrcodeContent);
            logger.debug("================= value :{}", value);

            createConfiguration_String(ConfigurationServiceImpl.PRODUCER_CONFIGURATION_KEY_qrcodeContent,value);
        }

    }



    public Configuration createConfiguration_String(String key, String value){

        Configuration configuration = configurationRepository.findByKey(key);
        if(configuration == null){
            configuration = new Configuration();

        }
        configuration.setKey(key);
        configuration.setStringValue(value);
        configuration = configurationRepository.save(configuration);
        return configuration;

    }
}