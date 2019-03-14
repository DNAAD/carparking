package com.coalvalue.repository;


import com.coalvalue.domain.entity.AdvancedPaymentTransfer;
import com.coalvalue.domain.entity.Behavioural;
import com.coalvalue.domain.entity.StorageSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by zhao yuan on 01/10/2015.
 */
public interface AdvancedPaymentTransferRepository extends JpaRepository<AdvancedPaymentTransfer, Integer> {






    Page<AdvancedPaymentTransfer> findByDistributorNo(String companyNo, Pageable pageable);

    List<AdvancedPaymentTransfer> findByDistributorNo(String companyNo);

    AdvancedPaymentTransfer findTop1ByDistributorNoAndSyncStatusOrderByCreateDateDesc(String distributorNo, String text);

    AdvancedPaymentTransfer findByUuid(String objectUuid);

}
