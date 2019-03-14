package com.coalvalue.repository;


import com.coalvalue.domain.entity.QualityInspectionReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Created by zhao yuan on 01/10/2015.
 */
public interface QualityInspectionReportRepository extends JpaRepository<QualityInspectionReport, Integer> {



    Page<QualityInspectionReport> findByCompanyId(Integer companyId, Pageable pageRequest);


    Optional<QualityInspectionReport> findById(Integer id);

    List<QualityInspectionReport> findByCompanyId(Integer id);
}
