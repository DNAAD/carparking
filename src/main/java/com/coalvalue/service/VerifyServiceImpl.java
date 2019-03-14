package com.coalvalue.service;

import com.coalvalue.domain.OperationResult;
import com.coalvalue.domain.entity.Location;
import com.coalvalue.domain.entity.ReportDeliveryOrder;
import com.coalvalue.repository.LocationRepository;
import com.coalvalue.repository.ReportDeliveryOrderRepository;
import com.coalvalue.web.valid.LocationCreateForm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//import org.springframework.security.core.Authentication;

/**
 * Created by silence yuan on 2015/6/28.
 */
@Service("verifyService")
public class VerifyServiceImpl extends BaseServiceImpl implements VerifyService {

    @Autowired
    private LocationRepository locationRepository;


    @Autowired
    private BehaviouralService behaviouralService;

    @Autowired
    private ReportDeliveryOrderRepository reportDeliveryOrderRepository;

    private static final Logger logger = LoggerFactory.getLogger(VerifyServiceImpl.class);


    @Override
    public Page<Location> queryStations(Integer userId, Pageable pageable) {
        return locationRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public OperationResult create(String lineCreateForm) {


        ReportDeliveryOrder deliveryOrder = reportDeliveryOrderRepository.findByNo(lineCreateForm);
        if(deliveryOrder!= null){
            behaviouralService.add_verified(deliveryOrder);
        }


        OperationResult operationResult = new OperationResult();
        operationResult.setSuccess(true);
        operationResult.setResultObject(deliveryOrder);
        return operationResult;
    }

    @Override
    @Transactional
    public Location edit(LocationCreateForm locationCreateForm) {


        Location line = locationRepository.findById(locationCreateForm.getId()).get();
        if(line != null){
            line.setName(locationCreateForm.getName());
            line.setDescription(locationCreateForm.getDescription());
            line.setCountryId(locationCreateForm.getCountryId());
            line.setPostalCode(locationCreateForm.getPostalCode());

            return locationRepository.save(line);
        }else{
            return null;
        }

    }

    @Override
    public List<Location> getLocations() {
        return locationRepository.findAll();
    }


}
