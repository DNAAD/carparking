package com.coalvalue.service;

import com.coalvalue.domain.entity.InstanceTransport;
import com.coalvalue.domain.entity.InventoryTransfer;
import com.coalvalue.domain.entity.ReportDeliveryOrder;
import com.coalvalue.notification.NotificationData;
import com.coalvalue.web.valid.InstanceTransportCreateForm;

import com.coalvalue.web.valid.InventoryTranferFormCorrect;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * Created by silence yuan on 2015/7/25.
 */
public interface InventoryTransferService extends BaseService {



    void create(NotificationData data);

    Page<Map> query(Object om, String searchText, Pageable pageable);

    InstanceTransport createFromDeliveryOrderInputTareWeight(ReportDeliveryOrder reportDeliveryOrder, InstanceTransportCreateForm locationCreateForm) throws MqttException;


/*
    OperationResult createTransfer(InstanceTransport instanceTransport, InventoryTransferCreateForm locationCreateForm);
*/


    Map get(InventoryTransfer deliveryOrder);

    InventoryTransfer getById(Integer index);

    InventoryTransfer correct(InventoryTransfer inventoryTransfer, InventoryTranferFormCorrect locationCreateForm);

    InventoryTransfer findByNo(String index);
}
