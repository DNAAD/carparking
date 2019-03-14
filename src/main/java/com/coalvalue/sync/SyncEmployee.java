package com.coalvalue.sync;

import com.alibaba.fastjson.JSON;
import com.coalvalue.configuration.ReactorEventConfig;
import com.coalvalue.domain.entity.*;
import com.coalvalue.domain.pojo.ReportDeliveryOrder_remote;
import com.coalvalue.enumType.ResourceType;
import com.coalvalue.notification.NotificationData;
import com.coalvalue.repository.ConfigurationRepository;
import com.coalvalue.repository.EmployeeRepository;
import com.coalvalue.repository.UserRepository;
import com.coalvalue.service.DistributorService;
import com.coalvalue.service.InventoryService;
import com.coalvalue.service.TransportOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.bus.Event;
import reactor.bus.EventBus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class SyncEmployee {

    public String supportType = ResourceType.Employee.getText();
    @Autowired
    private EmployeeRepository employeeRepository;



    @Autowired
    private UserRepository userRepository;



    @Transactional
    public List<SyncProperties> diffSync_from_server(List<SyncProperties> syncPropertiesesMap, LocalDateTime lastSync) {

        List<SyncProperties> syncPropertieses_Employee = syncPropertiesesMap.stream().filter(e->e.getItemType().equals(supportType)).collect(Collectors.toList());

        List<String> uuids = syncPropertieses_Employee.stream().map(e->e.getObjectUuid()).collect(Collectors.toList());
        Map<String,Employee> map_item = employeeRepository.findByUuidIn(uuids).
                stream().collect( Collectors .toMap(vo -> vo.getUuid(), Function.identity()));



        List<SyncProperties> syncProperties_sync_response = new ArrayList<>();
        for(SyncProperties syncProperties:syncPropertieses_Employee){

            Map quotationPlan_remote = JSON.parseObject(syncProperties.getContent(),Map.class);

            Employee quotationPlan_local = map_item.get(syncProperties.getObjectUuid());

            createEmployeeFromMap(quotationPlan_remote,quotationPlan_local);


        }





        return syncProperties_sync_response;












    }

/*
    @Transactional
    public List<SyncProperties> sync(List<SyncProperties> syncPropertieses_quotationPlan) {


        List<SyncProperties> syncProperties_sync_response = new ArrayList<>();
        for(SyncProperties syncProperties:syncPropertieses_quotationPlan){

            Map quotationPlan_remote = JSON.parseObject(syncProperties.getContent(),Map.class);

            Employee quotationPlan_local = employeeRepository.findByUuid(syncProperties.getObjectUuid());


                createEmployeeFromMap(quotationPlan_remote);


        }





        return syncProperties_sync_response;












    }
*/

    @Transactional
    public void createEmployeeFromMap(Map employeeMap, Employee employee) {


        System.out.println("--------------------"+employeeMap.toString());
        String userObjectUuid = (String)employeeMap.get("userObjectUuid");
        String uuid = (String)employeeMap.get("uuid");
        String employeeNo = (String)employeeMap.get("no");
        String userNo = (String)employeeMap.get("userNo");


        String password = (String)employeeMap.get("password");
        String givenName = (String)employeeMap.get("givenName");
        String familyName = (String)employeeMap.get("familyName");
        String mobile = (String)employeeMap.get("mobile");

        String companyNo = (String)employeeMap.get("companyNo");

        User user = userRepository.findByUserId(userNo);

        if(employee== null){

            if(user == null){
                user = new User();
                user.setUserId(userNo);
                user.setUuid(userObjectUuid);
            }

            user.setUserName(userNo);

            user.setPassword(password);
            user.setMobile(mobile);
            user = userRepository.save(user);








            if(employee== null){
                employee = new Employee();
                employee.setNo(employeeNo);
                employee.setCompanyNo(companyNo);
                employee.setUserNo(user.getUserId());
                employee.setUuid(uuid);

                employee.setGivenName((String)employeeMap.get("givenName"));
                employee.setFamilyName((String) employeeMap.get("familyName"));
                employee.setNickName( (String)employeeMap.get("nickName"));
                employee.setRealName( (String)employeeMap.get("realName"));
                employee.setGender((String) employeeMap.get("realName"));
            }

        }


        if(mobile!=null){
            employee.setMobile(mobile);

        }else{
            employee.setMobile("??");

        }
        employee.setGivenName(givenName);
        employee.setFamilyName(familyName);



        employee = employeeRepository.save(employee);

    }

}
