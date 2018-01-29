package com.coalvalue.service;


import com.coalvalue.domain.entity.InstanceTransport;
import com.coalvalue.domain.entity.ReportDeliveryOrder;
import com.coalvalue.domain.entity.TransportOperation;
import com.coalvalue.domain.entity.WxTemporaryQrcode;
import com.coalvalue.enumType.ResourceType;
import com.domain.entity.User;
import com.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by silence yuan on 2015/7/25.
 */
public interface DeliveryOrderService extends BaseService {


    Map get(ReportDeliveryOrder deliveryOrder);

    ReportDeliveryOrder getDeliveryOrderByTicket(String index);


    ReportDeliveryOrder createDeliveryOrder_(TransportOperation transportOperation);

    ReportDeliveryOrder createDeliveryOrder(ResourceType canvassing, Integer id);



    void generateQrcodeAccessCode(WxTemporaryQrcode wxeneral, ReportDeliveryOrder deliveryOrder);


    ReportDeliveryOrder getValid(ResourceType canvassing, String content);

    Page<ReportDeliveryOrder> getValidPage(ResourceType transportOperation, String verificationCode, User user, Pageable pageable);

    Page<ReportDeliveryOrder> getValidPageByQrcode(ResourceType transportOperation, String verificationCode, User user, Pageable pageable);

    Page<Map> query(String o, Pageable pageable);

    Page<Map> query_synthesized(String o, Pageable pageable);

    ReportDeliveryOrder findByPlateNumber(String license);

    ReportDeliveryOrder findByIdNumber(String idNumber);


    ReportDeliveryOrder findById(Integer deliveryOrderId);


    List<ReportDeliveryOrder> findByValidQrcode(String text);

    ReportDeliveryOrder findByIdAndStatus(Integer id, String text);

    ReportDeliveryOrder getById(Integer index);

    Map getMapById(Integer index);
}
