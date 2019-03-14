package com.coalvalue.repository;

import com.coalvalue.domain.entity.Inventory;
import com.coalvalue.domain.entity.InventoryTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by zhao yuan on 01/10/2015.
 */
public interface InventoryTransferRepository extends JpaRepository<InventoryTransfer, Integer>,JpaSpecificationExecutor<InventoryTransfer> {



    Page<InventoryTransfer> findByInventoryNo(String id, Pageable pageable);





    long countBy();


    List<InventoryTransfer> findByCreateDateBetween(LocalDateTime startDate, LocalDateTime endDate);



    Page<InventoryTransfer> findByReconcileStatus(String text, Pageable pageable);

    List<InventoryTransfer> findByReconcileStatusAndCreateDateBetween(String text, LocalDateTime startDate, LocalDateTime endDate);

    List<InventoryTransfer> findByInventoryNo(String no);

    List<InventoryTransfer> findByDistributorNo(String companyNo);

    Page<InventoryTransfer> findByDistributorNo(String companyNo, Pageable pageable);


    InventoryTransfer findByNo(String no);

    List<String> findUuidBy();

    Page<InventoryTransfer> findByCreateDateBetween(LocalDateTime localDateBegin, LocalDateTime localDateEnd, Pageable pageable);


    InventoryTransfer findByUuid(String objectUuid);

    List<InventoryTransfer> findByModifyDateAfter(LocalDateTime lastSync);

    InventoryTransfer findByInstanceUuid(String uuid);
}
