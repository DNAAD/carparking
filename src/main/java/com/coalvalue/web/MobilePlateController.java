package com.coalvalue.web;

import com.coalvalue.domain.entity.Location;
import com.coalvalue.domain.entity.PlateRecognition;
import com.coalvalue.service.other.GeneralServiceImpl;
import com.coalvalue.service.VerifyService;
import com.coalvalue.service.MqttService;
import com.coalvalue.service.PlateRecognitionService;
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
@RequestMapping(value= {"/plate_KK"})
public class MobilePlateController {
    private static final Logger logger = LoggerFactory.getLogger(MobilePlateController.class);




    @Autowired
    private VerifyService locationService;

    @Autowired
    private PlateRecognitionService plateRecognitionService;

    @Autowired
    private GeneralServiceImpl generalService;


    @Autowired
    private MqttService mqttService;


    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView index(@RequestParam(value = "q", required = false) String searchTerm,Authentication authentication)  {
        //mqttService.sendNetworkStatus("OK");


        ModelAndView modelAndView = new ModelAndView("/plate_index");
        modelAndView.addObject("q",searchTerm);

        String plateUrl = linkTo(methodOn(MobilePlateController.class).plate("", null)).withSelfRel().getHref();

        modelAndView.addObject("plateUrl",plateUrl);
 /*       String command_create_url = linkTo(methodOn(MobilePlateController.class).create(null, null)).withSelfRel().getHref();
        modelAndView.addObject("command_create_url",command_create_url);
*/


        String command_edit_url = linkTo(methodOn(MobilePlateController.class).edit(null, null)).withSelfRel().getHref();
        modelAndView.addObject("command_edit_url",command_edit_url);


        try {
            generalService.setGeneral(modelAndView, "", authentication);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return modelAndView;
    }




    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public Page<PlateRecognition> plate(@RequestParam(value = "q", required = false) String searchTerm, @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable)  {



        return plateRecognitionService.query(null,pageable);
    }







    @RequestMapping(value = "", method = RequestMethod.PUT)
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
