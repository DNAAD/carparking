package com.coalvalue.service;

import com.coalvalue.notification.NotificationData;
import com.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * Created by silence yuan on 2015/7/25.
 */
public interface ConfigurationService extends BaseService {



    void create(NotificationData data);

    Page<Map> query(Object o, Pageable pageable);


    String getAppId();


    String getAppSecret();
}
