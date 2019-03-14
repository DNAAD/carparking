package com.coalvalue.service;

import com.coalvalue.domain.entity.Company;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

//import org.springframework.security.core.Authentication;

/**
 * Created by silence yuan on 2015/6/28.
 */
public interface CompanyService extends BaseService {





    Company getById(Integer warehouseId);



    Company getCompanyById(Integer companyId);





}
