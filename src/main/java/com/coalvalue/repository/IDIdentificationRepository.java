package com.coalvalue.repository;


import com.coalvalue.domain.entity.Behavioural;
import com.coalvalue.domain.entity.Equipment;
import com.coalvalue.domain.entity.IDIentification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by zhao yuan on 01/10/2015.
 */
public interface IDIdentificationRepository extends JpaRepository<IDIentification, Integer> {


    Optional<IDIentification> findById(Integer id);

    IDIentification findByIdNumber(String idNumber);

}
