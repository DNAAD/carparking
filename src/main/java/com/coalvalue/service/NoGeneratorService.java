package com.coalvalue.service;

import com.coalvalue.domain.entity.NoGenerator;
import com.domain.entity.User;
import com.service.BaseService;

/**
 * Created by Peter Xu on 01/10/2015.
 */
public interface NoGeneratorService extends BaseService {




    NoGenerator getOrderNo(Integer id);


}
