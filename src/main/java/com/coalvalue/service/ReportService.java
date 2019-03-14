package com.coalvalue.service;

import com.coalvalue.domain.OperationResult;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

//import org.springframework.security.core.Authentication;

/**
 * Created by silence yuan on 2015/6/28.
 */
public interface ReportService extends BaseService {




    OperationResult  createReconciliation(LocalDateTime startDate, LocalDateTime endDate);


    Page<Map> getReport(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
