package com.coalvalue.repository;


import com.coalvalue.domain.entity.AdvancedPaymentTransfer;
import com.coalvalue.domain.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

/**
 * Created by zhao yuan on 01/10/2015.
 */
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {




    Page<Employee> findByCompanyNo(String id, Pageable pageable);

    Employee findByNo(String no);

    List<String> findUuidBy();

    Employee findByUuid(String objectUuid);

    Employee findByUserNo(String userId);

    List<Employee> findByUuidIn(List<String> uuids);
}
