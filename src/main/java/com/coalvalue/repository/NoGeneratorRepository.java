package com.coalvalue.repository;

import com.coalvalue.domain.entity.NoGenerator;
import com.coalvalue.repository.base.BaseJpaRepository;

/**
 * Created by zohu on 3/21/2015.
 */
public interface NoGeneratorRepository extends BaseJpaRepository<NoGenerator, Integer> {





    NoGenerator findByCompanyId(Integer id);

    NoGenerator findByCompanyIdAndTypeName(Integer companyId, String text);
}
