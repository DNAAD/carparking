package com.coalvalue.service;


import com.coalvalue.domain.OperationResult;
import com.coalvalue.domain.entity.*;
import com.coalvalue.enumType.FeesTypeEnum;
import com.coalvalue.enumType.PricingStrategyEnum;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by silence yuan on 2015/7/25.
 */
public interface FeeService extends BaseService {


    void createFeesForOrder(Distributor coalOrder, PricingStrategyEnum pricingStrategyEnum);

    Page<Map> queryFess(Distributor coalOrder, Pageable pageable);
    Page<Map> queryFess(String coalOrder, Pageable pageable);

    OperationResult complete(Fee fee, BigDecimal amount, String tax);

    boolean isEffective(String orderNo);

    OperationResult addFee(Distributor coalOrder, FeesTypeEnum feesTypeEnum);

    Fee syncAddFee(Fee fee_remote);



    void addFee(InventoryTransfer inventoryTransfer, Distributor distributor, InstanceTransport instanceTransport, Inventory inventory);

    PriceCategory getPriceCategory(Fee fee, String productNo);
}
