package com.coalvalue.web;

import com.coalvalue.domain.OperationResult;
import com.coalvalue.domain.entity.InstanceTransport;
import com.coalvalue.domain.entity.InventoryTransfer;
import com.coalvalue.domain.entity.ReportDeliveryOrder;
import com.coalvalue.domain.entity.User;
import com.coalvalue.dto.InventoryTransferDto;
import com.coalvalue.repository.InstanceTransportRepository;
import com.coalvalue.repository.ReportDeliveryOrderRepository;
import com.coalvalue.service.*;
import com.coalvalue.service.other.GeneralServiceImpl;
import com.coalvalue.web.valid.LineCreateForm;
import org.eclipse.paho.client.mqttv3.MqttException;
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
@RequestMapping(value= {"/transfer"})
public class MobileTransferController {
    private static final Logger logger = LoggerFactory.getLogger(MobileTransferController.class);





    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private InventoryTransferService inventoryTransferService;
    @Autowired
    private GeneralServiceImpl generalService;

    @Autowired
    private ReportDeliveryOrderRepository reportDeliveryOrderRepository;
    @Autowired
    private InstanceTransportRepository instanceTransportRepository;



    @Autowired
    private InstanceTransportService instanceTransportService;


    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView index(@RequestParam(value = "q", required = false) String searchTerm, Authentication authentication)  {

        ModelAndView modelAndView = new ModelAndView("/transfer_index");
        modelAndView.addObject("q",searchTerm);



        String transferUrl = linkTo(methodOn(MobileTransferController.class).transfer("", null)).withSelfRel().getHref();
        modelAndView.addObject("transportUrl",transferUrl);
        try {
            generalService.setGeneral(modelAndView, "", authentication);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String createTransferUrl = linkTo(methodOn(MobileTransferController.class).create(null,null, null)).toUri().toASCIIString();

        modelAndView.addObject("createTransferUrl",createTransferUrl);
        return modelAndView;
    }



    @RequestMapping(value = "/transport_list", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> transfer(@RequestParam(value = "q", required = false) String searchTerm, @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable)  {

        InventoryTransferDto deliveryOrderDto = new InventoryTransferDto();
        deliveryOrderDto.setSearchText(searchTerm);

        return inventoryService.queryTransfer(deliveryOrderDto, pageable);


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




        return null;
    }





    @RequestMapping(value = "createTransfer", method = RequestMethod.POST)
    @ResponseBody

    public Map create(@RequestParam(value = "instanceId",required = false) Integer id,@Valid InventoryTransferCreateForm locationCreateForm,
                      Authentication authentication) {

        logger.debug("----- param is  id : {},  price:{}, notificationToIds:{}, returnTo:{}，sendMessageToFollower is:{}",id, locationCreateForm.toString());

        User user = (User)authentication.getPrincipal();
        Map ret = new HashMap<String, String>();
        ret.put("status", false);


        InstanceTransport instanceTransport = instanceTransportService.getById(id);


        OperationResult operationResult = null;
        try {
            operationResult = instanceTransportService.createTransfer(instanceTransport, locationCreateForm, user);
            if(operationResult.isSuccess()){
                ret.put("status", true);
            }else{

                ret.put("message", operationResult.getErrorMessage());
            }


        } catch (MqttException e) {
            e.printStackTrace();
            ret.put("status", true);
            ret.put("message", e.getMessage());
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




        return ret;

    }




    @RequestMapping( value = "/{no}", method = RequestMethod.GET)
    public ModelAndView detail(@PathVariable(value = "no", required = false) String index, Authentication authentication, @PageableDefault Pageable pageable) {





        ModelAndView modelAndView = new ModelAndView("/transfer_detail");


        InventoryTransfer inventoryTransfer = inventoryTransferService.findByNo(index);

        String createUrl = linkTo(methodOn(MobileTransferController.class).create(inventoryTransfer.getId(), null,null)).withSelfRel().getHref();
        modelAndView.addObject("createUrl",createUrl);



        modelAndView.addObject("transferMap",inventoryTransferService.get(inventoryTransfer));

        modelAndView.addObject("inventoryTransfer",inventoryTransfer);



        ReportDeliveryOrder reportDeliveryOrder = reportDeliveryOrderRepository.findByNo(inventoryTransfer.getDeliveryOrderNo());

        modelAndView.addObject("reportDeliveryOrder",reportDeliveryOrder);



        InstanceTransport instanceTransport1 = instanceTransportRepository.findByUuid(inventoryTransfer.getInstanceUuid());

        modelAndView.addObject("instanceTransport",instanceTransport1);
   /*         WxTemporaryQrcode wxeneral = wxService.getByTransportation_Deliver_order(deliveryOrder, Constants.WX_QRCODE_TYPE_transportOperation_DELIVER_ORDER);

            if(wxeneral !=null){
                deliveryOrderService.generateQrcodeAccessCode(wxeneral,deliveryOrder);
            }*/
        //    modelAndView.addObject("qrcodeUrl",wxeneral.getQrCode());



        return modelAndView;

    }





}
