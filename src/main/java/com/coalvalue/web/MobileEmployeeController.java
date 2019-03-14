package com.coalvalue.web;


import com.coalvalue.domain.entity.Distributor;
import com.coalvalue.domain.entity.Employee;
import com.coalvalue.domain.entity.User;
import com.coalvalue.repository.EmployeeRepository;
import com.coalvalue.service.EmployeeService;
import com.coalvalue.web.valid.EmpoyeeCreateForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

//import com.coalvalue.service.EmployeeService;

/**
 * Created by silence yuan on 2015/7/12.
 */

@Controller
@RequestMapping(value= "/usercenter/employee")
public class MobileEmployeeController {
    private static final Logger logger = LoggerFactory.getLogger(MobileEmployeeController.class);




   @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public ModelAndView index(@RequestParam(value = "searchTerm", required = false) String searchTerm,
                              Authentication authentication) {

        ModelAndView modelAndView = new ModelAndView("/employee_index");


        User user = (User)authentication.getPrincipal();


        String employeeUrl =   linkTo(methodOn(MobileEmployeeController.class).employee(null,null)).withSelfRel().getHref();
        modelAndView.addObject("employeeUrl", employeeUrl);


        String command_create_url = linkTo(methodOn(MobileEmployeeController.class).create(null, null)).withSelfRel().getHref();
        modelAndView.addObject("command_create_url",command_create_url);


        String command_edit_url = linkTo(methodOn(MobileEmployeeController.class).edit(null, null,null)).withSelfRel().getHref();
        modelAndView.addObject("command_edit_url",command_edit_url);


        return modelAndView;

    }







    @RequestMapping(value = "/items",method = RequestMethod.GET)
    @ResponseBody
    public Page<Employee> employee(Authentication authentication, @PageableDefault Pageable pageable) {


        User user = (User)authentication.getPrincipal();
        System.out.println("==============="+user.toString());
        Page<Employee> page = employeeRepository.findAll(pageable);


        return page;
    }



    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody

    public Map create(@Valid EmpoyeeCreateForm locationCreateForm, Authentication authentication) {


        User user = (User)authentication.getPrincipal();
        logger.debug("----- param is  id : {},  price:{}, notificationToIds:{}, returnTo:{}，sendMessageToFollower is:{}", user.toString());

        Map ret = new HashMap<String, String>();
        ret.put("status", false);

        Employee location =null;// employeeService.create(locationCreateForm,user);
        if(location != null){


            ret.put("status", true);
        }


        return ret;

    }


    @RequestMapping(value = "edit", method = RequestMethod.PUT)
    @ResponseBody

    public Map edit(@Valid EmpoyeeCreateForm locationCreateForm, BindingResult bindingResult,
                    Authentication authentication) {

        logger.debug("----- param is  id : {},  price:{}, notificationToIds:{}, returnTo:{}，sendMessageToFollower is:{}", locationCreateForm.toString());

        Map ret = new HashMap<String, String>();
        ret.put("status", false);

        if(bindingResult.hasErrors()){
            ret.put("message", bindingResult.getAllErrors().toString());
        }
        Employee location = null;//employeeService.edit(locationCreateForm);
        if(location != null){


            ret.put("status", true);
        }


        return ret;

    }




    @RequestMapping( value = "/{uuid}", method = RequestMethod.GET)
    public ModelAndView detail(@PathVariable(value = "uuid", required = false) String uuid, Authentication authentication) {

        ModelAndView modelAndView = new ModelAndView("/employee_detail");

        Employee employee = employeeRepository.findByUuid(uuid);

        modelAndView.addObject("employee",employee);

        return modelAndView;

    }

}
