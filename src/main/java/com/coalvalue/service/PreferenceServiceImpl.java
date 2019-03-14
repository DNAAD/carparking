package com.coalvalue.service;

import com.coalvalue.domain.OperationResult;
import com.coalvalue.domain.entity.*;
import com.coalvalue.repository.CompanyRepository;
import com.coalvalue.repository.EmployeeRepository;
import com.coalvalue.repository.PreferenceRepository;
import com.coalvalue.repository.StorageSpaceRepository;
import com.coalvalue.web.MobileReportController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


/**
 * Created by silence yuan on 2015/6/28.
 */
@Service("preferenceService")
public class PreferenceServiceImpl extends BaseServiceImpl implements PreferenceService {

    @Autowired
    private PreferenceRepository preferenceRepository;
    @Autowired
    private StorageSpaceRepository storageSpaceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    private static final Logger logger = LoggerFactory.getLogger(PreferenceServiceImpl.class);

    @Transactional
    private Preference create(Employee employee){

        Preference preference  = new Preference();
            preference.setEmployeeUuid(employee.getUuid());

        return preference;
    }
    @Transactional
    private Preference get(User user){
        Employee employee = employeeRepository.findByUserNo(user.getUserId());
        Preference preference = preferenceRepository.findByEmployeeUuid(employee.getUuid());
        if(preference== null){
            preference = new Preference();
            preference.setEmployeeUuid(employee.getUuid());
            preference = preferenceRepository.save(preference);
        }
        return preference;
    }
    @Override
    @Transactional
    public OperationResult changeDefaultStorage(StorageSpace storage, User user) {
        Preference preference = get(user);
        preference.setStorageUuid(storage.getUuid());
        OperationResult operationResult = new OperationResult();
        operationResult.setSuccess(true);
        operationResult.setResultObject(preference);
        return operationResult;
    }

    @Override
    public Map getPreference(User principal) {
        Employee employee = employeeRepository.findByUserNo(principal.getUserId());
        Preference preference = preferenceRepository.findByEmployeeUuid(employee.getUuid());
        if(preference == null){
            preference = create(employee);
        }

        Map map = new HashMap();
        map.put("",principal.getUsername());
        if(preference.getStorageUuid()!= null){
            StorageSpace storageSpace = storageSpaceRepository.findByUuid(preference.getStorageUuid());

            map.put("storageSpaceName",storageSpace.getName());
            map.put("storageSpaceNo",storageSpace.getNo());
            map.put("storageNo",storageSpace.getNo());

            String reportUrl = linkTo(methodOn(MobileReportController.class).index(storageSpace.getNo(),null)).toUri().toString();

            map.put("reportUrl",reportUrl);
        }

        return map;
    }

    @Override
    public Preference getPreference(Employee employee) {

        Preference preference = preferenceRepository.findByEmployeeUuid(employee.getUuid());
        if(preference == null){
            preference = create(employee);
        }

        return preference;
    }



    @Override
    public Map getStorage(StorageSpace storageSpace) {

        Map map = new HashMap();
        map.put("storageSpaceName",storageSpace.getName());
        map.put("storageSpaceNo",storageSpace.getNo());
        map.put("storageNo",storageSpace.getNo());

        String reportUrl = linkTo(methodOn(MobileReportController.class).index(storageSpace.getNo(),null)).toUri().toString();

        map.put("reportUrl",reportUrl);

        return map;
    }
}
