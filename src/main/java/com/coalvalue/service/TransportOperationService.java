package com.coalvalue.service;


import com.coalvalue.domain.Distributor;
import com.coalvalue.domain.OperationResult;

import com.coalvalue.domain.entity.*;
import com.coalvalue.enumType.EventEnum;
import com.coalvalue.enumType.TransportOperationStatusEnum;

import com.domain.entity.User;
import com.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by silence yuan on 2015/7/25.
 */
public interface TransportOperationService extends BaseService {


    TransportOperation createOperation(TransportOperation transportOperation);


    TransportOperation getOperationById(Integer id);

    InstanceTransport getInstanceOperationById(Integer id);


    List<TransportOperation> getOperationsByUserAndStorageSpaceAndStatus(User user, StorageSpace storageSpace, TransportOperationStatusEnum createPendingCanvassing);

    OperationResult rejectOperation(TransportOperation transportOperation, User user);

    OperationResult agreetOperation(TransportOperation transportOperation, BigDecimal tareWeight, String ladingBileNo, User user, Boolean sendToCollaborator);


    TransportOperation update(TransportOperation transportOperation);

    OperationResult exitOperation(TransportOperation transportOperation, BigDecimal netWeight, User user);


    OperationResult printDeliveryOrder(TransportOperation transportOperation, User user);

    Page<TransportOperation> getOperationsByStorageSpaceAndStatusPage(StorageSpace storageSpace, Integer granularity, List<TransportOperationStatusEnum> createPending, Pageable pageable);


    @Transactional
    OperationResult createDeliveryOrder_coal_pit(StorageSpace storageSpace, Object o, User user);

    Map<String, Object> queryListByVerificationCodeCoalpit(String verificationCode, User user, Pageable pageable);



    ReportDeliveryOrder createDeliveryOrder_(TransportOperation transportOperation);


    ReportDeliveryOrder createDeliveryOrder(Distributor distributor, Map map);

}
