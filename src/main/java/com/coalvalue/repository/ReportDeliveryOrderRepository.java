package com.coalvalue.repository;

import com.coalvalue.domain.entity.ReportDeliveryOrder;
import com.coalvalue.repository.base.BaseJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by zhao yuan on 01/10/2015.
 */
public interface ReportDeliveryOrderRepository extends BaseJpaRepository<ReportDeliveryOrder, Integer> {


    ReportDeliveryOrder findById(Integer index);

    ReportDeliveryOrder findByTicket(String index);

    Page<ReportDeliveryOrder> findByTransportOperationId(Integer itemId, Pageable pageable);

    List<ReportDeliveryOrder> findByTransportOperationIdAndStatus(Integer id, String text);

    List<ReportDeliveryOrder> findByItemIdAndItemTypeAndStatus(Integer id, String text, String text1);

    Page<ReportDeliveryOrder> findByItemIdAndItemType(Integer itemId, String itemType, Pageable pageable);

    ReportDeliveryOrder findByItemTypeAndAccessCode(String text, String content);

    Page<ReportDeliveryOrder> findByItemTypeAndAccessCode(String text, String verificationCode, Pageable pageable);

    Page<ReportDeliveryOrder> findByItemTypeAndQrcodeUrl(String text, String verificationCode, Pageable pageable);

    ReportDeliveryOrder findByProductName(String license);

    ReportDeliveryOrder findByPlateNumber(String idNumber);

    ReportDeliveryOrder findByQrcode(String text);

    List<ReportDeliveryOrder> findByQrcodeAndStatus(String id, String text);

}
