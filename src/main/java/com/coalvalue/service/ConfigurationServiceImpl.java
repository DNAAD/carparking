package com.coalvalue.service;


import com.coalvalue.configuration.Constants;
import com.coalvalue.domain.entity.Behavioural;
import com.coalvalue.enumType.ConfigurationAttributeEnum;
import com.coalvalue.notification.NotificationData;
import com.coalvalue.repository.BehaviouralRepository;
import com.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by silence yuan on 2015/7/25.
 */

@Service("configurationService")
public class ConfigurationServiceImpl extends BaseServiceImpl implements ConfigurationService {

    @Autowired
    private BehaviouralRepository behaviouralRepository;


    @Override
    @Transactional
    public void create(NotificationData data) {

        Behavioural behavioural = new Behavioural();
        behavioural = behaviouralRepository.save(behavioural);

    }

    @Override
    public Page<Map> query(Object o, Pageable pageable) {

        List<Map> list = new ArrayList<>();

        Map map = new HashMap<>();
        map.put("name", "APP_ID");
        map.put("value", Constants.APP_ID);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "APP_SECRET");
        map.put("value", Constants.APP_SECRET);
        list.add(map);

        for(ConfigurationAttributeEnum configurationAttributeEnum: ConfigurationAttributeEnum.values()){
            map = new HashMap<>();
            map.put("name", configurationAttributeEnum.getDisplayText());
            map.put("value", "");
            list.add(map);
        }


        Page<Map> pages = new PageImpl<Map>(list,pageable,list.size());

        return pages;
    }

    @Override
    public String getAppId() {

        return Constants.APP_ID;

    }
    @Override
    public String getAppSecret() {

        return Constants.APP_SECRET;

    }
}
