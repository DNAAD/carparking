package com.coalvalue.repository;

import com.coalvalue.domain.entity.StorageSpace;
import com.coalvalue.domain.entity.TransportOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by zhao yuan on 01/10/2015.
 */
public interface TransportOperationRepository extends JpaRepository<TransportOperation, Integer>, JpaSpecificationExecutor<TransportOperation> {

    List<TransportOperation> findByDriverIdAndSpaceIdAndStatus(Integer id, Integer id1, String text);


    Optional<TransportOperation> findById(Integer fromTransport);


    Page<TransportOperation> findBySpaceIdAndStatusInAndProductIdAndProductType(Integer id, List<String> statues, Integer granularity, String text, Pageable pageable);


    TransportOperation findByUuid(String transportOperationUuid);


}
