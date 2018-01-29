package com.coalvalue.web;

import com.coalvalue.domain.entity.Location;
import com.coalvalue.dto.InstanceTransportDto;
import com.coalvalue.enumType.InstanceTransportStatusEnum;
import com.coalvalue.service.*;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

//import org.springframework.security.core.Authentication;

//import com.coalvalue.repositorySecondary.ProductRepository;

/**
 * Created by silence yuan on 2015/7/12.
 */

@Controller
@RequestMapping(value= {""})
public class SynthesizedController {
    private static final Logger logger = LoggerFactory.getLogger(SynthesizedController.class);


    @Autowired
    private BehaviouralService behaviouralService;


    @Autowired
    private LocationService locationService;

    @Autowired
    private DeliveryOrderService deliveryOrderService;

    @Autowired
    private GeneralServiceImpl generalService;

    @Autowired
    private InstanceTransportService instanceTransportService;

    @Autowired
    private MqttService mqttService;


    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView index(@RequestParam(value = "q", required = false) String searchTerm){//,Authentication authentication)  {
      //  mqttService.sendNetworkStatus("OK");
        ModelAndView modelAndView = new ModelAndView("/templates/synthesized_index");
        modelAndView.addObject("q",searchTerm);

        String companiesUrl = linkTo(methodOn(SynthesizedController.class).stations("", null)).withSelfRel().getHref();
        modelAndView.addObject("locationsUrl",companiesUrl);
        String command_create_url = linkTo(methodOn(SynthesizedController.class).create(null, null)).withSelfRel().getHref();
        modelAndView.addObject("command_create_url",command_create_url);


        String command_edit_url = linkTo(methodOn(SynthesizedController.class).edit(null, null)).withSelfRel().getHref();
        modelAndView.addObject("command_edit_url",command_edit_url);


        String deliveryOrderUrl = linkTo(methodOn(SynthesizedController.class).deliveryOrder(null, null)).toUri().toASCIIString();
        modelAndView.addObject("deliveryOrderUrl",deliveryOrderUrl);

        String instanceTransportUrl = linkTo(methodOn(SynthesizedController.class).instanceTransport(null, null)).toUri().toASCIIString();
        modelAndView.addObject("instanceTransportUrl",instanceTransportUrl);

        String instanceTransport_loaded_Url = linkTo(methodOn(SynthesizedController.class).instanceTransport_loaded(null, null)).toUri().toASCIIString();
        modelAndView.addObject("instanceTransport_loaded_Url",instanceTransport_loaded_Url);


        //mobileService.setWebSocketUrl(authentication,modelAndView,WebSocketServerUrlTypeEnum.STORAGE_DETAIL,storageSpace.getId());





        generalService.getStatistic(null,modelAndView,1);
        generalService.setWebSocketUrl(null,modelAndView,1);
        generalService.setGeneral(modelAndView);
        return modelAndView;
    }




    @RequestMapping(value = "stations", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> stations(@RequestParam(value = "q", required = false) String searchTerm, @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable)  {



        return behaviouralService.querySynthesized(null,pageable);
    }
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> deliveryOrder(@RequestParam(value = "searchText", required = false) String searchText, @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC)  Pageable pageable)  {

        System.out.print("========================= {}" + searchText);

        if(searchText != null)
            searchText = searchText.trim();


        return deliveryOrderService.query_synthesized(searchText, pageable);
    }

    @RequestMapping(value = "/instanceTransport_loaded", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> instanceTransport_loaded(@RequestParam(value = "searchText", required = false) String searchText, @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC)  Pageable pageable)  {

        System.out.print("========================= {}" + searchText);

        if(searchText != null)
            searchText = searchText.trim();



        InstanceTransportDto instanceTransportDto = new InstanceTransportDto();
        instanceTransportDto.setStatus(InstanceTransportStatusEnum.LEAVE.getText());

        return instanceTransportService.query_synthesized(instanceTransportDto,searchText, pageable);
    }

    @RequestMapping(value = "/instanceTransport", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> instanceTransport(@RequestParam(value = "searchText", required = false) String searchText, @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC)  Pageable pageable)  {

        System.out.print("========================= {}" + searchText);

        if(searchText != null)
            searchText = searchText.trim();
        InstanceTransportDto instanceTransportDto = new InstanceTransportDto();
        instanceTransportDto.setStatus(InstanceTransportStatusEnum.LOADING.getText());
        return instanceTransportService.query_synthesized(instanceTransportDto, searchText, pageable);
    }



    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ResponseBody

    public Map create(@Valid LocationCreateForm locationCreateForm,
                                      Authentication authentication) {

        logger.debug("----- param is  id : {},  price:{}, notificationToIds:{}, returnTo:{}，sendMessageToFollower is:{}", locationCreateForm.toString());

        Map ret = new HashMap<String, String>();
        ret.put("status", false);

            Location location = locationService.create(locationCreateForm);
            if(location != null){
                ret.put("status", true);
            }


        return ret;

    }


    @RequestMapping(value = "edit", method = RequestMethod.PUT)
    @ResponseBody

    public Map edit(@Valid LocationCreateForm locationCreateForm,
                      Authentication authentication) {

        logger.debug("----- param is  id : {},  price:{}, notificationToIds:{}, returnTo:{}，sendMessageToFollower is:{}", locationCreateForm.toString());

        Map ret = new HashMap<String, String>();
        ret.put("status", false);

        Location location = locationService.edit(locationCreateForm);
        if(location != null){


            ret.put("status", true);
        }


        return ret;

    }




}
