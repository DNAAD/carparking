package com.coalvalue.repository;


import com.coalvalue.domain.entity.InstanceTransport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

/**
 * Created by zhao yuan on 01/10/2015.
 */
public interface InstanceTransportRepository extends JpaRepository<InstanceTransport, Integer>,JpaSpecificationExecutor<InstanceTransport> {





    Optional<InstanceTransport> findById(Integer id);







    Page<InstanceTransport> findByStatus(String text, Pageable pageable);

    InstanceTransport findByLicenseAndStatus(String license, String text);



    List<InstanceTransport> findByInventoryNoAndStatus(String no, String text);

    InstanceTransport findByUuid(String uuid);



    InstanceTransport findByDeliveryOrderNo(String no);


}
