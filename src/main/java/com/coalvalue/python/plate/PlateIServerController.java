package com.coalvalue.python.plate;


import com.alibaba.fastjson.JSONObject;
import com.coalvalue.domain.OperationResult;
import com.coalvalue.python.LicensePlateDetectionIJavaService;
import com.coalvalue.service.EquipmentService;
import com.coalvalue.service.PlateRecognitionService;
import com.coalvalue.web.BaseController;
import com.coalvalue.web.PlateController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
/**
 * Created by yunpxu on 1/7/2015.
 * Step 1: Login flow
 * Step 2: Register flow
 * Step 3: Reset password flow
 */
@Controller
@RequestMapping(LicensePlateDetectionIJavaService.namespace+"/plate")
public class PlateIServerController extends BaseController {

    @Autowired
    private EquipmentService equipmentService;




    @Autowired
    private PlateRecognitionService plateRecognitionService;



    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView index(@RequestParam(value = "q", required = false) String searchTerm){//,Authentication authentication)  {
        //mqttService.sendNetworkStatus("OK");


        ModelAndView modelAndView = new ModelAndView("/iserver/plate_index");
        modelAndView.addObject("q",searchTerm);

       String lprUrl = linkTo(methodOn(PlateIServerController.class).camera("", null)).withSelfRel().getHref();

        modelAndView.addObject("lprUrl",lprUrl);
 /*       String command_create_url = linkTo(methodOn(MobilePlateController.class).create(null, null)).withSelfRel().getHref();
        modelAndView.addObject("command_create_url",command_create_url);
*/


       // String command_edit_url = linkTo(methodOn(PlateController.class).edit(null, null)).withSelfRel().getHref();
      //  modelAndView.addObject("command_edit_url",command_edit_url);


        //generalService.setGeneral(modelAndView);


        return modelAndView;
    }



    @RequestMapping(value = "/transport_list", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> camera(@RequestParam(value = "q", required = false) String searchTerm, @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable)  {
        return equipmentService.queryCamera(null, pageable);
    }


}
