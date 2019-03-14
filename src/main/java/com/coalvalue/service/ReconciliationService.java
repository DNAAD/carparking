package com.coalvalue.service;

import com.coalvalue.domain.entity.Distributor;
import com.coalvalue.domain.entity.PlateRecognition;
import com.coalvalue.domain.entity.Reconciliation;
import com.coalvalue.domain.entity.SyncProperties;
import com.coalvalue.notification.NotificationData;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * Created by silence yuan on 2015/7/25.
 */
public interface ReconciliationService extends BaseService {



    void create(NotificationData data);

    void analyis(PlateRecognition plateRecognition);


    Page<Map> querySynthesized(Object o, Pageable pageable);



    void syncImmediately(SyncProperties synchronized_, Map map);

    Page<Map> findAll(Distributor distributor, Pageable pageable);

    Reconciliation getById(Integer index);

    Page<Map> queryreconciliationItems(Reconciliation distributor, Pageable pageable);

    Page<Map> queryStatistic(Reconciliation reconciliation, Pageable pageable);

    Page<Map> findAllReconciliation_confirmed(Object o, Pageable pageable);









/*    void onPerformSync();
    void syncImmediately();*/
}
