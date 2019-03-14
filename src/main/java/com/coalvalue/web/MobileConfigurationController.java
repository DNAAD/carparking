package com.coalvalue.web;

import com.coalvalue.domain.entity.Camera;
import com.coalvalue.domain.OperationResult;

import com.coalvalue.domain.entity.Loadometer;
import com.coalvalue.domain.entity.LoadometerPlateRecongiseCemera;
import com.coalvalue.python.IPythonService;
//import com.coalvalue.python.server.ModuleConfig;
import com.coalvalue.repository.CameraRepository;
import com.coalvalue.repository.LoadometerPlateRecongiseCemeraRepository;
import com.coalvalue.service.ConfigurationService;
import com.coalvalue.service.EquipmentService;
import com.coalvalue.service.other.GeneralServiceImpl;

import com.coalvalue.service.LoadometerService;
import com.coalvalue.task.SystemStatusBroadcast;
import com.coalvalue.web.valid.TripCreateForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
@RequestMapping(value= {"/configuration"})
public class MobileConfigurationController {
    private static final Logger logger = LoggerFactory.getLogger(MobileConfigurationController.class);


    @Autowired
    private ConfigurationService configurationService;



    @Autowired
    private CameraRepository cameraRepository;

    @Autowired
    private GeneralServiceImpl generalService;


    @Autowired
    private LoadometerService loadometerService;
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private LoadometerPlateRecongiseCemeraRepository loadometerPlateRecongiseCemeraRepository;

    @Autowired
    private SystemStatusBroadcast systemStatusBroadcast;


    // @Autowired
    //private ModuleConfig moduleConfig;
    @Value("${deploy.resource.path}")
    private String resource_path;


    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(@RequestParam(value = "q", required = false) String searchTerm,Authentication authentication)  {


        ModelAndView modelAndView = new ModelAndView("/configuration_index");
        modelAndView.addObject("q",searchTerm);

        String companiesUrl = linkTo(methodOn(MobileConfigurationController.class).stations("", null)).withSelfRel().getHref();
        modelAndView.addObject("stationsUrl",companiesUrl);


        String queryModulesUrl = linkTo(methodOn(MobileConfigurationController.class).queryModules("", null)).withSelfRel().getHref();
        modelAndView.addObject("queryModulesUrl",queryModulesUrl);


        String queryModuleConfigUrl = linkTo(methodOn(MobileConfigurationController.class).queryModuleConfig("", null)).withSelfRel().getHref();
        modelAndView.addObject("queryModuleConfigUrl",queryModuleConfigUrl);

        try {
            generalService.setGeneral(modelAndView, "", authentication);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map map = new HashMap<>();



        modelAndView.addObject("configuration",map);

        String command_create_url = linkTo(methodOn(MobileConfigurationController.class).create(null, null)).withSelfRel().getHref();
        modelAndView.addObject("command_create_url",command_create_url);


        String command_edit_url = linkTo(methodOn(MobileConfigurationController.class).edit(null, null,null)).withSelfRel().getHref();
        modelAndView.addObject("command_edit_url",command_edit_url);

        String command_reflesh_url = linkTo(methodOn(MobileConfigurationController.class).reflesh(null,null, null)).toUri().toASCIIString();
        modelAndView.addObject("command_reflesh_url",command_reflesh_url);



        String startModuleUrl = linkTo(methodOn(MobileConfigurationController.class).startModule(null,null)).toUri().toASCIIString();
        modelAndView.addObject("startModuleUrl",startModuleUrl);


        String stopModuleUrl = linkTo(methodOn(MobileConfigurationController.class).stopModule(null,null)).toUri().toASCIIString();
        modelAndView.addObject("stopModuleUrl",stopModuleUrl);



        String loadometerUrl = linkTo(methodOn(MobileConfigurationController.class).queryLoadometer(null,null)).toUri().toASCIIString();
        modelAndView.addObject("loadometerUrl",loadometerUrl);

        String loadometerCemeraUrl = linkTo(methodOn(MobileConfigurationController.class).queryLoadometer(null,null)).toUri().toASCIIString();
        modelAndView.addObject("loadometerCemeraUrl",loadometerCemeraUrl);

        modelAndView.addObject("cameras",equipmentService.getPlateRecongniseCamera());



        String createLoadometerUrl = linkTo(methodOn(MobileConfigurationController.class).createLoadometerUrl(null,null)).toUri().toASCIIString();
        modelAndView.addObject("createLoadometerUrl",createLoadometerUrl);
        String infoUrl = linkTo(methodOn(MobileIndexController.class).info("",null)).withSelfRel().getHref();
        modelAndView.addObject("infoUrl",infoUrl);



        String statusinfo = linkTo(methodOn(MobileConfigurationController.class).statusinfo("",null)).withSelfRel().getHref();
        modelAndView.addObject("statusinfoUrl",statusinfo);




        return modelAndView;
    }




    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> stations(@RequestParam(value = "q", required = false) String searchTerm, @PageableDefault Pageable pageable)  {



        return configurationService.query(null, pageable);
    }





    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody

    public Map create(@Valid TripCreateForm locationCreateForm,
                      Authentication authentication) {

        logger.debug("----- param is  id : {},  price:{}, notificationToIds:{}, returnTo:{}，sendMessageToFollower is:{}", locationCreateForm.toString());

        Map ret = new HashMap<String, String>();
        ret.put("status", false);



        return ret;

    }


    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody

    public Map edit(@Valid TripCreateForm locationCreateForm, BindingResult bindingResult,
                    Authentication authentication) {

        logger.debug("----- param is  id : {},  price:{}, notificationToIds:{}, returnTo:{}，sendMessageToFollower is:{}", locationCreateForm.toString());

        Map ret = new HashMap<String, String>();
        ret.put("status", false);

        if(bindingResult.hasErrors()){
            ret.put("message", bindingResult.getAllErrors().toString());
        }


        return ret;

    }



    @RequestMapping(value = "/reflesh", method = RequestMethod.POST)
    @ResponseBody

    public Map reflesh(@RequestParam(value = "appId", required = false) String appId,@RequestParam(value = "appSecret", required = false) String appIdSecret,
                    Authentication authentication) {

        logger.debug("----- param is  id : {},  price:{}, notificationToIds:{}, returnTo:{}，sendMessageToFollower is:{}", appIdSecret.toString());
        Map ret = new HashMap<String, String>();
        ret.put("status", false);


        OperationResult location = configurationService.getConfigurationFromService_mqtt(appId,appIdSecret);


        if(location.isSuccess()){
            ret.put("status", true);
        }
        return ret;

    }

    @RequestMapping(value = "/runPython", method = RequestMethod.GET)
    @ResponseBody

    public Map runPython(@RequestParam(value = "appId", required = false) String appId,@RequestParam(value = "appSecret", required = false) String appIdSecret,
                       Authentication authentication) {

        logger.debug("----- param is  id : {},  price:{}, notificationToIds:{}, returnTo:{}，sendMessageToFollower is:{}", appIdSecret);
        Map ret = new HashMap<String, String>();
        ret.put("status", false);

        OperationResult location = configurationService.runPython();
        if(location.isSuccess()){
            ret.put("status", true);
        }
        return ret;

    }

    @RequestMapping(value = "/cameras", method = RequestMethod.GET)
    @ResponseBody

    public Object cameras(@Valid TripCreateForm locationCreateForm,
                      Authentication authentication) {

        logger.debug("----- param is  id : {},  price:{}, notificationToIds:{}, returnTo:{}，sendMessageToFollower is:{}", locationCreateForm.toString());

        Map ret = new HashMap<String, String>();
        ret.put("status", false);

        List<Camera> location = cameraRepository.findAll();


        List<Map> maps = new ArrayList<Map>();
        for(Camera camera:location){
            Map map = new HashMap<>();
            map.put("rstpUrl",camera.getRstpUrl());
            map.put("path",resource_path+"/"+camera.getPath());
            maps.add(map);
        }
        if(location != null){


            ret.put("status", true);
        }

        return maps;

    }











    @RequestMapping(value = "queryModules", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> queryModules(@RequestParam(value = "q", required = false) String searchTerm, @PageableDefault Pageable pageable)  {



        return configurationService.queryModules(null, pageable);
    }
    @RequestMapping(value = "queryModuleConfig", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> queryModuleConfig(@RequestParam(value = "q", required = false) String searchTerm, @PageableDefault Pageable pageable)  {



        return null;//moduleConfig.queryModules(null, pageable);
    }
    @RequestMapping(value = "queryLoadometer", method = RequestMethod.GET)
    @ResponseBody
    public Page<Loadometer> queryLoadometer(@RequestParam(value = "q", required = false) String searchTerm, @PageableDefault Pageable pageable)  {
        //return loadometerRepository.findAll(pageable);

        return loadometerService.findAll(pageable);
    }
    @RequestMapping(value = "queryLoadometerCemeraUrl", method = RequestMethod.GET)
    @ResponseBody
    public Page<LoadometerPlateRecongiseCemera> queryLoadometerCemeraUrl(@RequestParam(value = "q", required = false) String searchTerm, @PageableDefault Pageable pageable)  {
        return loadometerPlateRecongiseCemeraRepository.findAll(pageable);
    }



    @RequestMapping(value = "/startModule", method = RequestMethod.POST)
    @ResponseBody

    public Map startModule(@RequestParam(value = "modualId", required = false) String modualId, Authentication authentication) {

        logger.debug("----- param is  id : {},  price:{}, notificationToIds:{}, returnTo:{}，sendMessageToFollower is:{}");
        Map ret = new HashMap<String, String>();
        ret.put("status", false);

        IPythonService iPythonService = configurationService.getModule(modualId);

        OperationResult location = configurationService.startModule(iPythonService);
        if(location.isSuccess()){
            ret.put("status", true);
        }
        ret.put("message", location.getErrorMessage());

        return ret;

    }
    @RequestMapping(value = "/createLoadometerUrl", method = RequestMethod.POST)
    @ResponseBody

    public Map createLoadometerUrl(@RequestParam(value = "modualId", required = false) String modualId, Authentication authentication) {

        logger.debug("----- param is  id : {},  price:{}, notificationToIds:{}, returnTo:{}，sendMessageToFollower is:{}");
        Map ret = new HashMap<String, String>();
        ret.put("status", false);


        OperationResult location = loadometerService.create(null);
        if(location.isSuccess()){
            ret.put("status", true);
        }
        ret.put("message", location.getErrorMessage());

        return ret;

    }


    @RequestMapping(value = "/stopModule", method = RequestMethod.POST)
    @ResponseBody

    public Map stopModule(@RequestParam(value = "modualId", required = false) String modualId, Authentication authentication) {

        logger.debug("----- param is  id : {},  price:{}, notificationToIds:{}, returnTo:{}，sendMessageToFollower is:{}");
        Map ret = new HashMap<String, String>();
        ret.put("status", false);

        IPythonService iPythonService = configurationService.getModule(modualId);

        OperationResult location = configurationService.stopModule(iPythonService);
        if(location.isSuccess()){
            ret.put("status", true);
        }
        ret.put("message", location.getErrorMessage());

        return ret;

    }


    @RequestMapping(value = "/statusinfo", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> statusinfo(@RequestParam(value = "q", required = false) String searchTerm, @PageableDefault Pageable pageable)  {
        List<Map> maps = systemStatusBroadcast.info();
        return new PageImpl<Map>(maps, pageable, maps.size());


    }



}
