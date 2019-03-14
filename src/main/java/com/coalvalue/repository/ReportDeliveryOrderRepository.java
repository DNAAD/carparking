package com.coalvalue.repository;

import com.coalvalue.domain.entity.ReportDeliveryOrder;
import com.coalvalue.domain.entity.WxTemporaryQrcode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by zhao yuan on 01/10/2015.
 */
public interface ReportDeliveryOrderRepository extends JpaRepository<ReportDeliveryOrder, Integer>,JpaSpecificationExecutor<ReportDeliveryOrder> {


    Optional<ReportDeliveryOrder> findById(Integer index);

    ReportDeliveryOrder findByTicket(String index);






    ReportDeliveryOrder findByProductName(String license);

    ReportDeliveryOrder findByLicense(String idNumber);



    List<ReportDeliveryOrder> findByQrcodeAndStatus(String id, String text);



    List<ReportDeliveryOrder> findByLicenseAndStatus(String license, String text);

    Page<ReportDeliveryOrder> findByDistributorNo(String companyNo, Pageable amount);

    ReportDeliveryOrder findByNo(String lineCreateForm);

    ReportDeliveryOrder findByUuid(String objectUuid);

    List<ReportDeliveryOrder> findByModifyDateAfter(LocalDateTime lastSync);

    List<ReportDeliveryOrder> findByUuidIn(List<String> uuids);
}
