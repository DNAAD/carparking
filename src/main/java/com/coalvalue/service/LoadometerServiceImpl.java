package com.coalvalue.service;


import com.coalvalue.domain.OperationResult;
import com.coalvalue.domain.entity.*;
import com.coalvalue.notification.NotificationData;
import com.coalvalue.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by silence yuan on 2015/7/25.
 */

@Service("loadometerService")
public class LoadometerServiceImpl extends BaseServiceImpl implements LoadometerService {

    @Autowired
    private BehaviouralRepository behaviouralRepository;

    @Autowired
    private DeliveryOrderService deliveryOrderService;

    @Autowired
    private IDIdentificationRepository idIdentificationRepository;

    @Autowired
    private PlateRecognitionRepository plateRecognitionRepository;

    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private DistributorService distributorService;
    @Autowired
    private MqttService mqttService;




    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private QrcodeService qrcodeService;

    @Autowired
    private LoadometerPlateRecongiseCemeraRepository loadometerPlateRecongiseCemeraRepository;
    @Autowired
    private LoadometerRepository loadometerRepository;








    @Override
    public Page<Loadometer> findAll(Pageable pageable) {


        return loadometerRepository.findAll(pageable);

    }

    @Override
    public Loadometer findByCamera(String name) {

        List<LoadometerPlateRecongiseCemera> loadometerPlateRecongiseCemeras =  loadometerPlateRecongiseCemeraRepository.findByCemaraNo(name);
        for(LoadometerPlateRecongiseCemera loadometerPlateRecongiseCemera: loadometerPlateRecongiseCemeras){
            Loadometer loadometer = loadometerRepository.findByNo(loadometerPlateRecongiseCemera.getLoadometerNo());
            if(loadometer!= null){
                return loadometer;
            }
        }
        return null;
    }

    @Override
    @Transactional
    public OperationResult create(Object o) {

        OperationResult operationResult = new OperationResult();

        Loadometer iPythonService = new Loadometer();

        iPythonService.setNo("11223344");
        iPythonService =  loadometerRepository.save(iPythonService);
        operationResult.setSuccess(true);
        operationResult.setResultObject(iPythonService);

        return operationResult;

    }


}
