package com.coalvalue.repository;


import com.coalvalue.domain.entity.AdvancedPaymentTransfer;
import com.coalvalue.domain.entity.Behavioural;
import com.coalvalue.repository.base.BaseJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by zhao yuan on 01/10/2015.
 */
public interface AdvancedPaymentTransferRepository extends BaseJpaRepository<AdvancedPaymentTransfer, Integer> {


    Page<AdvancedPaymentTransfer> findByDistributorId(Integer id, Pageable pageable);

    List<AdvancedPaymentTransfer> findByDistributorId(Integer id);
}
