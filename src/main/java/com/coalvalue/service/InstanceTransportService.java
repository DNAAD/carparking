package com.coalvalue.service;

import com.coalvalue.domain.entity.InstanceTransport;
import com.coalvalue.domain.entity.InventoryTransfer;
import com.coalvalue.domain.entity.ReportDeliveryOrder;
import com.coalvalue.dto.InstanceTransportDto;
import com.coalvalue.notification.NotificationData;
import com.coalvalue.web.InventoryTransferCreateForm;
import com.coalvalue.web.valid.InstanceTransportCreateForm;
import com.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * Created by silence yuan on 2015/7/25.
 */
public interface InstanceTransportService extends BaseService {



    void create(NotificationData data);

    Page<Map> query(Object om, Pageable pageable);

    InstanceTransport createFromDeliveryOrder(ReportDeliveryOrder reportDeliveryOrder, InstanceTransportCreateForm locationCreateForm);


    InstanceTransport getById(Integer id);

    InventoryTransfer createTransfer(InstanceTransport instanceTransport, InventoryTransferCreateForm locationCreateForm);

    InstanceTransport getBy_InstanceTransport_Id(Integer id);

    Page<Map> query_synthesized(InstanceTransportDto instanceTransportDto, String searchText, Pageable pageable);

    Map get(InstanceTransport deliveryOrder);
}
