package com.coalvalue.service;

import com.coalvalue.domain.entity.NoGenerator;



/**
 * Created by Peter Xu on 01/10/2015.
 */
public interface NoGeneratorService extends BaseService {




    NoGenerator getOrderNo(Integer id);


}
