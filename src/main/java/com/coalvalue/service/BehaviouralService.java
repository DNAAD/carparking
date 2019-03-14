package com.coalvalue.service;

import com.coalvalue.domain.entity.InstanceTransport;
import com.coalvalue.domain.entity.PlateRecognition;
import com.coalvalue.domain.entity.ReportDeliveryOrder;
import com.coalvalue.notification.NotificationData;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * Created by silence yuan on 2015/7/25.
 */
public interface BehaviouralService extends BaseService {



    void create(NotificationData data);

    void analyis(PlateRecognition plateRecognition);


    Page<Map> querySynthesized(Object o, Pageable pageable);

    Page<Map> queryVerified(Object o, Pageable pageable);

    void addPlate(PlateRecognition plateRecognition);

    void addPlate_IDIdentification(Integer idIentificationId);

    void add_delivery_order(NotificationData deliveryOrderId);

    void analyisQrcode(String text);

/*
    Page<Map> queryBeingWeighed(Object o, Pageable pageable);
*/

    Page<Map> queryBeingVerified(Object o, Pageable pageable);

    public void add_beingWeighed_Entrance(String direction, PlateRecognition plateRecognition, ReportDeliveryOrder license) ;

    void add_beingWeighed_Exit(String direction, PlateRecognition plateRecognition, InstanceTransport reportDeliveryOrder);

    void add_beingWeighed_tinput_tareWeight(InstanceTransport instanceTransport);

    void add_beingWeighed_tinput_netWeight(InstanceTransport instanceTransport);

    Page<Map> queryBeingLoaded(Object o, Pageable pageable);

    void add_beingWeighed_Entrance(String direction, PlateRecognition deliveryOrder_from);

    void add_verified(ReportDeliveryOrder reportDeliveryOrder);

    Page<Map> querySynthesizedExitIntelligent(Object o, Pageable pageable);


    Page<Map> querySynthesizedEntranceIntelligent(Object o, Pageable pageable);
}
