package com.coalvalue.repository;

import com.coalvalue.domain.entity.Configuration;
import com.coalvalue.repository.base.BaseJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by zhao yuan on 01/10/2015.
 */

public interface ConfigurationRepository extends BaseJpaRepository<Configuration, Integer> {


    Configuration findByCompanyId(Integer id);

    Page<Configuration> findByCompanyId(Integer companyId, Pageable pageable);

    Configuration findByCompanyIdAndKey(Integer id, String companyScanDisplayPrice);

    Configuration findById(Integer id);

    Configuration findByKey(String companyScanDisplayInventory);


    Page<Configuration> findByCompanyIdIsNullAndUserIdIsNull(Pageable pageRequest);

    List<Configuration> findByCompanyIdIsNullAndUserIdIsNull();

    Configuration findByKeyAndCompanyIdIsNull(String key);

    Configuration findByUserIdAndKey(Integer id, String companyScanDisplayPrice);


    Page<Configuration> findByUserId(Integer id, Pageable pageable);

}
