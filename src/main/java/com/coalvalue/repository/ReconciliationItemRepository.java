package com.coalvalue.repository;


import com.coalvalue.domain.entity.Reconciliation;
import com.coalvalue.domain.entity.ReconciliationItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by zhao yuan on 01/10/2015.
 */
public interface ReconciliationItemRepository extends JpaRepository<ReconciliationItem, Integer> {


    Page<ReconciliationItem> findByReconciliationId(Integer id, Pageable pageable);

    List<ReconciliationItem> findByReconciliationId(Integer id);
}
