package com.coalvalue.repository;

import com.coalvalue.domain.entity.InventoryTransfer;
import com.coalvalue.repository.base.BaseJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by zhao yuan on 01/10/2015.
 */
public interface InventoryTransferRepository extends BaseJpaRepository<InventoryTransfer, Integer> {


    Page<InventoryTransfer> findByDistributor(Integer id, Pageable pageable);

    Page<InventoryTransfer> findByInventoryId(Integer id, Pageable pageable);


    List<InventoryTransfer> findByDistributor(Integer id);

    List<InventoryTransfer> findByInventoryId(Integer id);

    long countBy();


}
