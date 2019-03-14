package com.coalvalue.service;

import com.coalvalue.domain.OperationResult;
import com.coalvalue.domain.entity.Configuration;
import com.coalvalue.notification.NotificationData;
import com.coalvalue.protobuf.Hub;
import com.coalvalue.python.IPythonService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Created by silence yuan on 2015/7/25.
 */
public interface ConfigurationService extends BaseService {



    void create(NotificationData data);

    Page<Map> query(Object o, Pageable pageable);


    OperationResult openPlateRecognition();

    OperationResult runPython();


    void init();

    OperationResult getConfigurationFromService_mqtt(String appId, String appIdSecret);

    Configuration getConfiguration(String configuration_key_companyNo);



    Page<Map> queryModules(Object o, Pageable pageable);

    OperationResult startModule(IPythonService iPythonService);

    IPythonService getModule(String appId);

    OperationResult stopModule(IPythonService iPythonService);

    void createImageStrategy(Hub.ImageStrategy imageStrategy);


    void givenUsingTimer_whenStoppingThread_thenTimerTaskIsCancelled()
            throws InterruptedException;
}
