package com.coalvalue.repository;


import com.coalvalue.domain.entity.Fee;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


/**
 * Created by zohu on 3/21/2015.
 */
public interface FeeRepository extends JpaRepository<Fee, Integer> {


    Optional<Fee> findById(Integer no);


    List<Fee> findByDistributorNoAndStatus(String orderNo, String text);

    Fee findByDistributorNoAndType(String no, String text);



    Page<Fee> findByDistributorNo(String companyNo, Pageable pageable);

    Fee findByNo(String no);

    List<Fee> findByDistributorNo(String distributorNo);

    Fee findByUuid(String objectUuid);

    List<Fee> findByUuidIn(List<String> uuids);
}
