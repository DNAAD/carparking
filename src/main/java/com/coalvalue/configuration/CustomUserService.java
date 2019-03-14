package com.coalvalue.configuration;

import com.coalvalue.domain.entity.Employee;
import com.coalvalue.domain.entity.Preference;
import com.coalvalue.domain.entity.User;
import com.coalvalue.repository.DistributorRepository;

import com.coalvalue.repository.EmployeeRepository;
import com.coalvalue.repository.UserRepository;
import com.coalvalue.service.PreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PreferenceService preferenceService;

    @Autowired
    EmployeeRepository employeeRepository;

/*    @Autowired
    PreferenceRepository preferenceRepository;*/
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        System.out.println("ddddddddddddddddddd"+s);
        User user = userRepository.findByUserName(s);

        preferenceService.getPreference(user);
        if (user == null) {
            user = userRepository.findByMobile(s);

            if(user == null){
                throw new UsernameNotFoundException("用户名不存在");
            }
            System.out.println("ddddddddddddddddddd"+user.toString());
        }
        Employee employee = employeeRepository.findByUserNo(user.getUserId());
        user.setCompanyNo(employee.getCompanyNo());
        user.setEmployee(employee);
        Preference preference = preferenceService.getPreference(employee);
        user.setPreference(preference);



       // Distributor distributor = distributorRepository.findById(user.getCompanyId());
        //user.setDistributor(distributor);
/*        Preference preference = preferenceRepository.findByUserId(user.getId());
        user.setPreference(preference);*/

/*        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
        UserDetails userDetails = (UserDetails)new User(user.getUsername(),
                user.getPassword(), Arrays.asList(authority));*/
        System.out.println("s:"+s);
        System.out.println("username:"+user.getUsername()+";password:"+user.getPassword());
        return user;
    }
}