package com.coalvalue.notification;


import com.alibaba.fastjson.JSON;
import com.coalvalue.configuration.MqttPublishSample;
import com.coalvalue.configuration.ReactorEventConfig;
import com.coalvalue.domain.entity.Distributor;
import com.coalvalue.domain.OperationResult;
import com.coalvalue.domain.entity.Reconciliation;
import com.coalvalue.domain.entity.ReconciliationItem;
import com.coalvalue.enumType.DataSynchronizationTypeEnum;
import com.coalvalue.repository.ReconciliationItemRepository;
import com.coalvalue.repository.ReconciliationRepository;
import com.coalvalue.service.ConfigurationService;
import com.coalvalue.service.DistributorService;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.fn.Consumer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotificationConsumer_reconciliation implements Consumer<Event<NotificationData_reconciliation>> {
 


    @Autowired
    private ReconciliationRepository reconciliationRepository;

    @Autowired
    private ReconciliationItemRepository reconciliationItemRepository;

    @Autowired
    private DistributorService distributorService;

    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private MqttPublishSample mqttPublishSample;

    @Autowired
    private MqttClient mqttClient;

    @Override
    public void accept(Event<NotificationData_reconciliation> notificationDataEvent) {


        NotificationData_reconciliation notificationData = notificationDataEvent.getData();


        System.out.println("notificationConsumer_reconciliation_event "+ ReactorEventConfig.notificationConsumer_reconciliation_event);

        if(notificationDataEvent.getKey().equals(ReactorEventConfig.notificationConsumer_reconciliation_event)){

            List<Reconciliation> deliveryOrder_from =(List<Reconciliation>) notificationData.getObject();
            String producerNo = notificationData.getProducerNo();

            synchronize(producerNo,deliveryOrder_from);


        }


    }

    private final static String formatter_iso8601_pattern     = "yyyy-MM-dd'T'HH:mm:ss";
    private final static DateTimeFormatter formatter_iso8601  = DateTimeFormatter.ofPattern(formatter_iso8601_pattern);

    public OperationResult synchronize(String producerNo, List<Reconciliation> reconciliations) {


        Map map = new HashMap<>();



        List<Map> distributors = new ArrayList<>();

        for(Reconciliation reconciliation: reconciliations){
            List<ReconciliationItem> reconciliationItems = reconciliationItemRepository.findByReconciliationId(reconciliation.getId());
            Map reconciliationMap = new HashMap<>();

            Distributor distributor = distributorService.getById(reconciliation.getDistributorId());

            reconciliationMap.put("no", reconciliation.getNo());
            reconciliationMap.put("companyNo", producerNo);

            JSON.toJSONStringWithDateFormat(LocalDateTime.now(), "yyyy-MM-dd'T'HH:mm:ss.SSS");

            Long timestamp = LocalDateTime.now().atOffset(ZoneOffset.of("+8")).toEpochSecond();
            LocalDateTime time2 =LocalDateTime.ofEpochSecond(timestamp,0,ZoneOffset.ofHours(8));



            reconciliationMap.put("periodBeginDate", reconciliation.getPeriodBeginDate().atOffset(ZoneOffset.of("+8")).toEpochSecond());
            reconciliationMap.put("periedEndDate", reconciliation.getPeriedEndDate().atOffset(ZoneOffset.of("+8")).toEpochSecond());
            reconciliationMap.put("status", reconciliation.getStatus());
            reconciliationMap.put("amount", reconciliation.getTotalAmount().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            reconciliationMap.put("quantity", reconciliation.getTotalQuantity().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            reconciliationMap.put("count", reconciliation.getTotalCount());
            reconciliationMap.put("reconciliationItems", reconciliationItems);
            reconciliationMap.put("distributorNo", distributor.getNo());






            distributors.add(reconciliationMap);
        }
        map.put("reconciliations",distributors);

        map.put("type", DataSynchronizationTypeEnum.Reconciliation.getText());







        System.out.println("======发送 Reconciliation 信息");
        String client_request        = "request/"+ mqttPublishSample.getChannal_topic();
        try {
            mqttClient.publish(client_request, JSON.toJSONString(map).getBytes(),2,false);
        } catch (MqttException e) {
            e.printStackTrace();
        }


        return null;





    }
}