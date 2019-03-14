package com.coalvalue.service;

import com.coalvalue.configuration.CommonConstant;
import com.coalvalue.domain.entity.Company;
import com.coalvalue.repository.CompanyRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by silence yuan on 2015/6/28.
 */
@Service("companyService")
//@Transactional(readOnly = true)
public class CompanyServiceImpl extends BaseServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;


    private static final Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);



    @Override
    public Company getById(Integer warehouseId) {
        return companyRepository.findById(warehouseId).get();
    }



    @Override
    public Company getCompanyById(Integer companyId) {

        return companyRepository.findById(companyId).get();
    }




}
