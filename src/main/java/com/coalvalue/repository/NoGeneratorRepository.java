package com.coalvalue.repository;

import com.coalvalue.domain.entity.NoGenerator;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by zohu on 3/21/2015.
 */
public interface NoGeneratorRepository extends JpaRepository<NoGenerator, Integer> {





    NoGenerator findByCompanyId(Integer id);

    NoGenerator findByCompanyIdAndTypeName(Integer companyId, String text);
}
