package com.coalvalue.service;


import com.coalvalue.domain.entity.AdvancedPaymentTransfer;

import com.coalvalue.domain.entity.SyncProperties;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Map;

/**
 * Created by silence yuan on 2015/7/25.
 */
public interface SyncService extends BaseService {


    SyncProperties createPrepayments(AdvancedPaymentTransfer posting);

    void sync(List<SyncProperties> syncProperties_remote, Map<String, Object> contentMap);

    @Transactional
  // @Scheduled(fixedDelay = 60*1000,   initialDelay=3000)
    void syncCompare();

    void syncImmediately();
}
