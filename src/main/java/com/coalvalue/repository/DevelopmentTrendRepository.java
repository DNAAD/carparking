package com.coalvalue.repository;



import com.coalvalue.domain.entity.DevelopmentTrend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Created by zhao yuan on 01/10/2015.
 */
public interface DevelopmentTrendRepository extends JpaRepository<DevelopmentTrend, Integer> {


    DevelopmentTrend findByUuid(String objectUuid);

    List<DevelopmentTrend> findByPriceCategoryUuid(String uuid);
}
