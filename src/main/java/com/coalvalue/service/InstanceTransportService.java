package com.coalvalue.service;

import com.coalvalue.domain.OperationResult;
import com.coalvalue.domain.entity.Fee;
import com.coalvalue.domain.entity.InstanceTransport;
import com.coalvalue.domain.entity.ReportDeliveryOrder;
import com.coalvalue.domain.entity.User;
import com.coalvalue.dto.InstanceTransportDto;
import com.coalvalue.notification.NotificationData;
import com.coalvalue.web.InventoryTransferCreateForm;
import com.coalvalue.web.valid.InstanceTransportCreateForm;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Created by silence yuan on 2015/7/25.
 */
public interface InstanceTransportService extends BaseService {



    void create(NotificationData data);

    Page<Map> query(Object om, Pageable pageable);

    public InstanceTransport createFromDeliveryOrderInputTareWeight(ReportDeliveryOrder reportDeliveryOrder, Fee fee, InstanceTransportCreateForm locationCreateForm) throws MqttException;


    InstanceTransport getById(Integer id);

    OperationResult createTransfer(InstanceTransport instanceTransport, InventoryTransferCreateForm locationCreateForm, User user) throws MqttException;



    Page<Map> query_synthesized(InstanceTransportDto instanceTransportDto, String searchText, Pageable pageable);

    Map get(InstanceTransport deliveryOrder);

    Page<Map> query_BeingLoaded(Pageable pageable);

    InstanceTransport getLoadingByLicense(String license);
}
