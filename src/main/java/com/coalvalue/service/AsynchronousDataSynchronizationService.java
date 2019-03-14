package com.coalvalue.service;

import com.coalvalue.domain.OperationResult;
import com.coalvalue.domain.entity.PlateRecognition;
import com.coalvalue.domain.entity.SyncProperties;
import com.coalvalue.notification.NotificationData;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * Created by silence yuan on 2015/7/25.
 */
public interface AsynchronousDataSynchronizationService extends BaseService {



    void create(NotificationData data);

    void analyis(PlateRecognition plateRecognition);


    Page<Map> querySynthesized(Object o, Pageable pageable);

    void addPlate(PlateRecognition plateRecognition);

    void addPlate_IDIdentification(Integer idIentificationId);

    void add_delivery_order(NotificationData deliveryOrderId);

    void analyisQrcode(String text);

    void synchroniz(String type, Map map);

    OperationResult synchronize() ;

    void syncImmediately(SyncProperties synchronized_, Map map);





/*    void onPerformSync();
    void syncImmediately();*/
}
