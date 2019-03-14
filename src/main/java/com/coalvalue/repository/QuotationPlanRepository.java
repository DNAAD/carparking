package com.coalvalue.repository;


import com.coalvalue.domain.entity.Behavioural;
import com.coalvalue.domain.entity.QuotationPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by zhao yuan on 01/10/2015.
 */
public interface QuotationPlanRepository extends JpaRepository<QuotationPlan, Integer> {


    QuotationPlan findTop1ByOrderByCreateDateDesc();


    QuotationPlan findTop1ByStatusOrderByCreateDateDesc(String text);

    QuotationPlan findByUuid(String uuid);

    List<QuotationPlan> findByUuidIn(List<String> uuids);


}
