package com.coalvalue.repository;


import com.coalvalue.domain.Distributor;
import com.coalvalue.domain.entity.Behavioural;
import com.coalvalue.repository.base.BaseJpaRepository;

/**
 * Created by zhao yuan on 01/10/2015.
 */
public interface DistributorRepository extends BaseJpaRepository<Distributor, Integer> {


    Distributor getByCompanyNo(String companyNo);

    Distributor findById(Integer index);

    Distributor findByCompanyNo(String index);

    Distributor getByName(String name);

}
