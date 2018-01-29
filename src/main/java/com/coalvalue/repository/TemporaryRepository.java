package com.coalvalue.repository;


import com.coalvalue.domain.TemporaryQrcode;
import com.coalvalue.domain.entity.AdvancedPaymentTransfer;
import com.coalvalue.repository.base.BaseJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by zhao yuan on 01/10/2015.
 */
public interface TemporaryRepository extends BaseJpaRepository<TemporaryQrcode, Integer> {

}
