package com.coalvalue.service;

import com.coalvalue.domain.Distributor;
import com.coalvalue.domain.entity.*;
import com.coalvalue.enumType.InventoryStatusEnum;
import com.coalvalue.web.valid.DistributorCreateForm;
import com.coalvalue.web.valid.LineCreateForm;
import com.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by silence yuan on 2015/7/25.
 */
public interface DistributorService extends BaseService {


    Distributor getDistributor(String companyNo, String traderName);


    Page<Map> query(Object o, Pageable pageable);

    Distributor getById(Integer index);

    Map get(Distributor deliveryOrder);

    Distributor getByNo(String index);

    Page<Map> queryInventoryTransfer(Distributor o, Pageable pageable);

    List<InventoryTransfer> getTransfers(Distributor distributor);

    @Transactional
    AdvancedPaymentTransfer createAdvancedPayment(InstanceTransport instanceTransport, BigDecimal amount);


    @Transactional
    AdvancedPaymentTransfer addAdvancedPayment(Distributor inventory, BigDecimal amount);

    Page<Map> queryAdvancedPaymentTransferr(Distributor distributor, Pageable pageable);

    List<AdvancedPaymentTransfer> getAdvancedPaymentTransfers(Distributor distributor);

    long getDistributorNumber();


    Distributor create(DistributorCreateForm distributorCreateForm);

    List<Distributor> get();


    Distributor update(Distributor inventory);

    Distributor createDistributorFromMap(Map inventory_);

}
