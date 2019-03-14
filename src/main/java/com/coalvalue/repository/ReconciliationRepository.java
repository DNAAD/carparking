package com.coalvalue.repository;


import com.coalvalue.domain.entity.AdvancedPaymentTransfer;
import com.coalvalue.domain.entity.PriceCategory;
import com.coalvalue.domain.entity.Reconciliation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Created by zhao yuan on 01/10/2015.
 */
public interface ReconciliationRepository extends JpaRepository<Reconciliation, Integer> {


    Optional<Reconciliation> findById(Integer index);

    List<Reconciliation> findByStatus(String text);

    Page<Reconciliation> findByDistributorId(Integer id, Pageable pageable);

    Page<Reconciliation> findByStatus(String text, Pageable pageable);

}
