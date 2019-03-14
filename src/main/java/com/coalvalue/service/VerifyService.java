package com.coalvalue.service;

import com.coalvalue.domain.OperationResult;
import com.coalvalue.domain.entity.Location;
import com.coalvalue.web.valid.LocationCreateForm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

//import org.springframework.security.core.Authentication;

/**
 * Created by silence yuan on 2015/6/28.
 */
public interface VerifyService extends BaseService {



    Page<Location> queryStations(Integer userId, Pageable pageable);


    OperationResult create(String lineCreateForm);

    Location edit(LocationCreateForm locationCreateForm);


    List<Location> getLocations();

}
