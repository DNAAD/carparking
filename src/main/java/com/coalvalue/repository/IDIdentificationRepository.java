package com.coalvalue.repository;


import com.coalvalue.domain.entity.Behavioural;
import com.coalvalue.domain.entity.IDIentification;
import com.coalvalue.repository.base.BaseJpaRepository;

/**
 * Created by zhao yuan on 01/10/2015.
 */
public interface IDIdentificationRepository extends BaseJpaRepository<IDIentification, Integer> {


    IDIentification findById(Integer id);

    IDIentification findByIdNumber(String idNumber);

}
