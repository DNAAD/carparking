package com.coalvalue.service;

import com.coalvalue.domain.entity.Employee;
import com.coalvalue.domain.entity.User;


import java.util.Map;

/**
 * Created by silence yuan on 2015/7/25.
 */
public interface EmployeeService extends BaseService {


    void create(Map map);


    Employee getEmployee(User user);

}
