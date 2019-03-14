package com.coalvalue.repository;


import com.coalvalue.domain.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Created by Peter Xu on 01/10/2015.
 */
public interface CompanyRepository extends JpaRepository<Company, Integer> {



    Optional<Company> findById(Integer companyId);




}
