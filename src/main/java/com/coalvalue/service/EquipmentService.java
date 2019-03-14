package com.coalvalue.service;

import com.coalvalue.domain.OperationResult;
import com.coalvalue.domain.entity.Company;
import com.coalvalue.domain.entity.Equipment;
import com.coalvalue.domain.entity.StorageSpace;
import com.coalvalue.domain.entity.User;
import com.coalvalue.domain.pojo.PlateRecogniseCameraPOJO;
import com.coalvalue.enumType.EquipmentTypeEnum;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by silence yuan on 2015/7/25.
 */
public interface EquipmentService extends BaseService {


    @Transactional
    Equipment create(String deviceId, EquipmentTypeEnum typeEnum, User user);

    Page<Map> queryCamera(Object o, Pageable pageable);


    Page<Map> queryCamera(EquipmentTypeEnum equipmentTypeEnum, Pageable pageable);

    Page<Map> queryEquipment(EquipmentTypeEnum equipmentTypeEnum, Pageable pageable);


    void updateEquipmentLiveInfo(Equipment equipment, PlateRecogniseCameraPOJO device_name);

    @Transactional
    Equipment createEquipment(String deviceId, EquipmentTypeEnum typeEnum);

    Object getPlateRecongniseCamera();


}
