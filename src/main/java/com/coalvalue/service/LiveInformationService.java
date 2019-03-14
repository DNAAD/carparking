package com.coalvalue.service;


import com.coalvalue.domain.OperationResult;
import com.coalvalue.domain.entity.InstanceTransport;
import com.coalvalue.domain.entity.Inventory;
import com.coalvalue.domain.entity.LiveInforInventory;
import com.coalvalue.domain.entity.ReportDeliveryOrder;
import com.coalvalue.enumType.BrandEnterpriseFeatureEnum;
import com.coalvalue.enumType.BrandEnterpriseStatusEnum;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * Created by silence yuan on 2015/7/25.
 */
public interface LiveInformationService extends BaseService {


    List<LiveInforInventory> getInventory();


    void update(Inventory inventory, InstanceTransport instanceTransport);


    void update(Inventory inventory, ReportDeliveryOrder reportDeliveryOrder);

    LiveInforInventory getInventoryByNo(String no);
}
