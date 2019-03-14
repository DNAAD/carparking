package com.coalvalue.service;

import com.coalvalue.domain.OperationResult;
import com.coalvalue.domain.entity.Loadometer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by silence yuan on 2015/7/25.
 */
public interface LoadometerService extends BaseService {


    Page<Loadometer> findAll(Pageable pageable);

    Loadometer findByCamera(String name);

    OperationResult create(Object o);






/*    void onPerformSync();
    void syncImmediately();*/
}
