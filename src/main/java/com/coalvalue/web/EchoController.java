package com.coalvalue.web;

import com.coalvalue.domain.OperationResult;
import com.coalvalue.domain.entity.*;
import com.coalvalue.dto.DeliveryOrderDto;
import com.coalvalue.dto.InstanceTransportDto;
import com.coalvalue.enumType.DeliveryOrderStatusEnum;
import com.coalvalue.enumType.InstanceTransportStatusEnum;
import com.coalvalue.repository.*;
import com.coalvalue.service.*;
import com.coalvalue.service.other.GeneralServiceImpl;
import com.coalvalue.service.sync.DifferentialSyncService;
import com.coalvalue.task.FaceRemoteControl;
import com.coalvalue.web.fragment.FragmentController;
import com.coalvalue.web.valid.InstanceTransportCreateForm;
import com.coalvalue.web.valid.InventoryTranferFormCorrect;
import com.coalvalue.web.valid.LocationCreateForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

//import org.springframework.security.core.Authentication;
//import com.coalvalue.repositorySecondary.ProductRepository;

/**
 * Created by silence yuan on 2015/7/12.
 */

@Controller
@RequestMapping(value= {"/echo"})
public class EchoController {
    private static final Logger logger = LoggerFactory.getLogger(EchoController.class);



    @Autowired
    private BehaviouralService behaviouralService;



    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView index(@RequestParam(value = "q", required = false) String searchTerm,Authentication authentication)  {
        //  mqttService.sendNetworkStatus("OK");


        ModelAndView modelAndView = new ModelAndView("/camera");



        return modelAndView;
    }






}
