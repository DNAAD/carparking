package com.coalvalue.service.assistant;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;



@Service
public class ModuleConfig {


    String BROKER_HOST =""; //ModuleMqttBrokerConfig.BROKER_HOST;
    Integer BROKER_PORT = 1;//ModuleMqttBrokerConfig.BROKER_PORT;
    String TOPIC_COMMAND = ModuleMqttClientConfig.TOPIC_COMMAND;
    String TOPIC = ModuleMqttClientConfig.TOPIC;



    public Page<Map> queryModules(Object o, Pageable pageable) {





        List<Map> maps  = new ArrayList<>();


        Map map = new HashMap();
        map.put("name", "BROKER_HOST");
        map.put("value", BROKER_HOST);
        maps.add(map);


        map = new HashMap();
        map.put("name", "BROKER_PORT");
        map.put("value", BROKER_PORT);
        maps.add(map);

        map = new HashMap();
        map.put("name", "TOPIC_COMMAND");
        map.put("value", TOPIC_COMMAND);
        maps.add(map);

        map = new HashMap();
        map.put("name", "TOPIC");
        map.put("value", TOPIC);
        maps.add(map);
        Page<Map> pages = new PageImpl<Map>(maps,pageable,maps.size());
        return pages;
    }

}