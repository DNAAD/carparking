package com.coalvalue.web;

import com.LPRMain;
import com.coalvalue.domain.OperationResult;
import com.coalvalue.domain.entity.InstanceTransport;
import com.coalvalue.domain.entity.InventoryTransfer;
import com.coalvalue.domain.entity.Line;
import com.coalvalue.domain.entity.ReportDeliveryOrder;
import com.coalvalue.service.GeneralServiceImpl;
import com.coalvalue.service.InstanceTransportService;
import com.coalvalue.service.LineService;
import com.coalvalue.service.PlateRecognitionService;
import com.coalvalue.web.valid.InstanceTransportCreateForm;
import com.coalvalue.web.valid.LineCreateForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
@RequestMapping(value= {"/instance"})
public class MobileInstanceTransprotController {
    private static final Logger logger = LoggerFactory.getLogger(MobileInstanceTransprotController.class);





    @Autowired
    private PlateRecognitionService plateRecognitionService;
    @Autowired
    private LineService lineService;
    @Autowired
    private GeneralServiceImpl generalService;



    @Autowired
    private InstanceTransportService instanceTransportService;


    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView index(@RequestParam(value = "q", required = false) String searchTerm){//,Authentication authentication)  {

        ModelAndView modelAndView = new ModelAndView("/templates/transport_index");
        modelAndView.addObject("q",searchTerm);

        String companiesUrl = linkTo(methodOn(MobileInstanceTransprotController.class).stations("", null)).withSelfRel().getHref();
        modelAndView.addObject("transportUrl",companiesUrl);
        generalService.setGeneral(modelAndView);

        String createTransferUrl = linkTo(methodOn(MobileInstanceTransprotController.class).create(null,null, null)).toUri().toASCIIString();

        modelAndView.addObject("createTransferUrl",createTransferUrl);
        return modelAndView;
    }






    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> stations(@RequestParam(value = "q", required = false) String searchTerm, @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC)  Pageable pageable)  {



        return instanceTransportService.query(null, pageable);
    }



    @RequestMapping(value = "/command=create", method = RequestMethod.GET)
    @ResponseBody
    public Object command_get_product_starage(@RequestParam(value = "id",required = true) Integer id,@RequestBody LineCreateForm lineCreateForm,Authentication authentication) {
        // jsmMessageService.sendStatisticMessage("editPrice");

        logger.debug("get_product_price_categories === param is : productId is :{} ", id);



   //     User user = (User) authentication.getPrincipal();







        Map<String, Object> map = new HashMap<String, Object>();

        map.put("code","0");
        map.put("desc","成功");


        List<Object> companyMap = new ArrayList<Object>();



        Line line = lineService.create(lineCreateForm);

        return line;
    }





    @RequestMapping(value = "createTransfer", method = RequestMethod.POST)
    @ResponseBody

    public Map create(@RequestParam(value = "instanceId",required = false) Integer id,@Valid InventoryTransferCreateForm locationCreateForm,
                      Authentication authentication) {

        logger.debug("----- param is  id : {},  price:{}, notificationToIds:{}, returnTo:{}，sendMessageToFollower is:{}", locationCreateForm.toString());

        Map ret = new HashMap<String, String>();
        ret.put("status", false);


        InstanceTransport instanceTransport = instanceTransportService.getById(id);

/*
        OperationResult operationResult = lineService.valid(locationCreateForm);
        if(!operationResult.isSuccess()){
            ret.put("message", operationResult.getErrorMessage());
        }
*/



        InventoryTransfer location = instanceTransportService.createTransfer(instanceTransport, locationCreateForm);
        if(location != null){


            ret.put("status", true);
        }


        return ret;

    }


    @RequestMapping(value = "edit", method = RequestMethod.PUT)
    @ResponseBody

    public Map edit(@Valid LineCreateForm locationCreateForm, BindingResult bindingResult,
                    Authentication authentication) {

        logger.debug("----- param is  id : {},  price:{}, notificationToIds:{}, returnTo:{}，sendMessageToFollower is:{}", locationCreateForm.toString());

        Map ret = new HashMap<String, String>();
        ret.put("status", false);
        if(bindingResult.hasErrors()){
            ret.put("message", bindingResult.getAllErrors().toString());
        }


        Line location = lineService.edit(locationCreateForm);
        if(location != null){


            ret.put("status", true);
        }


        return ret;

    }




    @RequestMapping( value = "/{id}", method = RequestMethod.GET)
    public ModelAndView detail(@PathVariable(value = "id", required = false) Integer index, Authentication authentication, @PageableDefault Pageable pageable) {





        ModelAndView modelAndView = new ModelAndView("/templates/instance_transport_detail");


        InstanceTransport deliveryOrder = instanceTransportService.getById(index);

        String createUrl = linkTo(methodOn(MobileInstanceTransprotController.class).create(deliveryOrder.getId(), null,null)).withSelfRel().getHref();
        modelAndView.addObject("createUrl",createUrl);



        modelAndView.addObject("deliveryOrderMap",instanceTransportService.get(deliveryOrder));

        modelAndView.addObject("deliveryOrder",deliveryOrder);

   /*         WxTemporaryQrcode wxeneral = wxService.getByTransportation_Deliver_order(deliveryOrder, Constants.WX_QRCODE_TYPE_transportOperation_DELIVER_ORDER);

            if(wxeneral !=null){
                deliveryOrderService.generateQrcodeAccessCode(wxeneral,deliveryOrder);
            }*/
        //    modelAndView.addObject("qrcodeUrl",wxeneral.getQrCode());



        return modelAndView;

    }





}
