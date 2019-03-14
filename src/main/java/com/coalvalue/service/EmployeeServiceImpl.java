package com.coalvalue.service;


import com.coalvalue.domain.entity.*;
import com.coalvalue.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by silence yuan on 2015/7/25.
 */

@Service("employeeService")
public class EmployeeServiceImpl extends BaseServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;


    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public void create(Map map) {
        String no = (String) map.get("no");
        User employee = userRepository.findByUserId(no);
        if(employee== null){
            User user = new User();
            user.setUserId(no);
            user.setUserName(no);
            user.setPassword("123");
            user = userRepository.save(user);
        }

    }

    @Override
    public Employee getEmployee(User user) {


        return employeeRepository.findByUserNo(user.getUserId());
    }

   /* @Override

    public Employee getCreate(String distributorNo, String operatorNo, String operatorName, String operatorPhone) {
        Employee employee = employeeRepository.findByNo(operatorNo);
        if(employee== null){
            employee = new Employee();
            employee.setNo(operatorNo);
            employee.setCompanyNo(distributorNo);
            employee.setName(operatorName);
            employee.setMobile(operatorPhone);

            employee = employeeRepository.save(employee);
        }

        return employee;
    }*/
}
