package com.coalvalue.notification;
//A Client server data sync algorithm - using objects diff and patch.
//https://www.linkedin.com/pulse/client-server-sync-algorithm-using-objects-diff-patch-kumar-krishna

import com.alibaba.fastjson.JSON;
import com.coalvalue.configuration.ReactorEventConfig;
import com.coalvalue.domain.entity.Configuration;
import com.coalvalue.domain.entity.Employee;
import com.coalvalue.domain.entity.Product;
import com.coalvalue.domain.entity.User;
import com.coalvalue.enumType.DataSynchronizationTypeEnum;
import com.coalvalue.repository.EmployeeRepository;
import com.coalvalue.repository.ProductRepository;
import com.coalvalue.repository.StorageSpaceRepository;
import com.coalvalue.repository.UserRepository;
import com.coalvalue.service.ConfigurationService;
import com.coalvalue.service.ConfigurationServiceImpl;
import com.coalvalue.service.InventoryService;
import com.coalvalue.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.bus.Event;
import reactor.fn.Consumer;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.coalvalue.configuration.WebSocketConfig.*;
@Service
public class NotificationConsumer_quotation implements Consumer<Event<NotificationData_quotation>> {




    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    @Override
    public void accept(Event<NotificationData_quotation> notificationDataEvent) {


        NotificationData_quotation notificationData = notificationDataEvent.getData();



        if(notificationDataEvent.getKey().equals(ReactorEventConfig.notificationConsumer_change_price_group_quotation_pre_event)){

            System.out.println("==========================notificationConsumer_change_price_group_quotation_pre_event={}");

            Map content = new HashMap();
            content.put("type", DataSynchronizationTypeEnum.Probationary_quotation.getText());

            simpMessagingTemplate.convertAndSend(topic__COALPIT_DELIVERY_report, JSON.toJSON(content));





        }


    }



}