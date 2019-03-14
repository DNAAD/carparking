package com.coalvalue.repository;

import com.coalvalue.domain.entity.IDIentification;
import com.coalvalue.domain.entity.InstanceTransport;
import com.coalvalue.domain.entity.Inventory;
import com.coalvalue.domain.entity.InventoryTransfer;
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
public interface InventoryRepository extends JpaRepository<Inventory, Integer>, JpaSpecificationExecutor<Inventory> {



    Optional<Inventory> findById(Integer inventoryId);

    Inventory findByNo(String productCoalType);


    List<Inventory> findByProductNo(String no);

    List<Inventory> findByStorageNoAndStatus(String no, String text);

    Page<Inventory> findByStatus(String text, Pageable pageable);



    Inventory findByUuid(String objectUuid);



    List<Inventory> findByModifyDateAfter(LocalDateTime lastSync);

    List<Inventory> findByUuidIn(List<String> uuids);

    List<Inventory> findByProductNoAndStatus(String no, String text);

    Inventory findByStorageNoAndProductNo(String no, String no1);

    List<Inventory> findByStorageNo(String no);
}
