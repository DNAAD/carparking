package com.coalvalue.web;

import com.coalvalue.domain.Distributor;
import com.coalvalue.domain.OperationResult;
import com.coalvalue.domain.TemporaryQrcode;
import com.coalvalue.domain.entity.*;
import com.coalvalue.enumType.ResourceType;
import com.coalvalue.service.*;
import com.coalvalue.web.valid.DistributorCreateForm;
import com.coalvalue.web.valid.LineCreateForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

//import org.springframework.security.core.Authentication;

//import com.coalvalue.repositorySecondary.ProductRepository;

/**
 * Created by silence yuan on 2015/7/12.
 */

@Controller
@RequestMapping(value= {"/distributor"})
public class MobileDistributorController {
    private static final Logger logger = LoggerFactory.getLogger(MobileDistributorController.class);





    @Autowired
    private QrcodeService qrcodeService;
    @Autowired
    private LineService lineService;
    @Autowired
    private GeneralServiceImpl generalService;



    @Autowired
    private DistributorService distributorService;
    @Autowired
    private DeliveryOrderService deliveryOrderService;


    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView index(@RequestParam(value = "q", required = false) String searchTerm){//,Authentication authentication)  {

        ModelAndView modelAndView = new ModelAndView("/templates/distributor_index");
        modelAndView.addObject("q",searchTerm);

        String companiesUrl = linkTo(methodOn(MobileDistributorController.class).stations("", null)).withSelfRel().getHref();
        modelAndView.addObject("transportUrl",companiesUrl);




        String command_create_url = linkTo(methodOn(MobileDistributorController.class).create(null, null)).withSelfRel().getHref();
        modelAndView.addObject("command_create_url",command_create_url);


        String command_edit_url = linkTo(methodOn(MobileInventoryController.class).edit(null, null,null)).withSelfRel().getHref();
        modelAndView.addObject("command_edit_url",command_edit_url);



        String synchronize_url = linkTo(methodOn(MobileInventoryController.class).synchronize(null, null,null)).withSelfRel().getHref();
        modelAndView.addObject("synchronize_url",synchronize_url);


        generalService.setGeneral(modelAndView);
        return modelAndView;
    }






    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> stations(@RequestParam(value = "q", required = false) String searchTerm, @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC)  Pageable pageable)  {



        return distributorService.query(null, pageable);
    }



    @RequestMapping(value = "/InventoryTransfer_list", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> InventoryTransfer(@RequestParam(value = "distributorId", required = false) Integer distributorId, @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC)  Pageable pageable)  {


        Distributor distributor = distributorService.getById(distributorId);


        Page<Map>  pages = distributorService.queryInventoryTransfer(distributor, pageable);


        return pages;
    }


    @RequestMapping( value = "/{id}", method = RequestMethod.GET)
    public ModelAndView detail(@PathVariable(value = "id", required = false) Integer index, Authentication authentication) {

        ModelAndView modelAndView = new ModelAndView("/templates/distributor_detail");

        Distributor distributor = distributorService.getById(index);

        modelAndView.addObject("deliveryOrderMap",distributorService.get(distributor));

        modelAndView.addObject("deliveryOrder",distributor);

   /*         WxTemporaryQrcode wxeneral = wxService.getByTransportation_Deliver_order(deliveryOrder, Constants.WX_QRCODE_TYPE_transportOperation_DELIVER_ORDER);

            if(wxeneral !=null){
                deliveryOrderService.generateQrcodeAccessCode(wxeneral,deliveryOrder);
            }*/
        //    modelAndView.addObject("qrcodeUrl",wxeneral.getQrCode());

        String companiesUrl = linkTo(methodOn(MobileDistributorController.class).InventoryTransfer(distributor.getId(), null)).withSelfRel().getHref();
        modelAndView.addObject("transferUrl", companiesUrl);



        String getRequestTradeCountTrend =   linkTo(methodOn(MobileDistributorController.class).getRequestTradeCountTrend(distributor.getId())).withSelfRel().getHref();
        modelAndView.addObject("requestTradeCountTrendUrl", getRequestTradeCountTrend);

        String getAdvancedPaymentTrend =   linkTo(methodOn(MobileDistributorController.class).getAdvancedPaymentTrend(distributor.getId())).withSelfRel().getHref();
        modelAndView.addObject("getAdvancedPaymentTrendUrl", getAdvancedPaymentTrend);




        String advancedPaymentTransferUrl = linkTo(methodOn(MobileDistributorController.class).advancedPaymentTransfer(distributor.getId(), null)).withSelfRel().getHref();
        modelAndView.addObject("advancedPaymentTransferUrl",advancedPaymentTransferUrl);


        String addInventoryUrl = linkTo(methodOn(MobileDistributorController.class).addInventory(distributor.getId(), null,null)).toUri().toASCIIString();
        modelAndView.addObject("addInventoryUrl",addInventoryUrl);


        String getBindQrcodeUrl = linkTo(methodOn(MobileDistributorController.class).getBindQrcode(distributor.getId(), null)).toUri().toASCIIString();
        modelAndView.addObject("getBindQrcodeUrl",getBindQrcodeUrl);
        return modelAndView;

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





    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ResponseBody

    public Map create(@Valid DistributorCreateForm distributorCreateForm,
                      Authentication authentication) {

        logger.debug("----- param is  id : {},  price:{}, notificationToIds:{}, returnTo:{}，sendMessageToFollower is:{}", distributorCreateForm.toString());

        Map ret = new HashMap<String, String>();
        ret.put("status", false);


 /*       OperationResult operationResult = distributorService.valid(distributorCreateForm);
        if(!operationResult.isSuccess()){
            ret.put("message", operationResult.getErrorMessage());
        }
*/


        Distributor location = distributorService.create(distributorCreateForm);

        if(location != null){
            ret.put("status", true);
        }


        return ret;

    }


    @RequestMapping(value = "", method = RequestMethod.PUT)
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



    @RequestMapping(value = "/request_trade_count_trend", method = RequestMethod.POST)
    @ResponseBody
    public List getRequestTradeCountTrend(@RequestParam(value = "id",required = true) Integer id) {

        List<Map> result = new ArrayList<Map>();



        Map map = new HashMap<String, String>();
        map.put("name", "平台请求量");


        Distributor distributor = distributorService.getById(id);
        List<InventoryTransfer> records = distributorService.getTransfers(distributor);



        List<Map<String,Object>> values = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> averages = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> ranges = new ArrayList<Map<String,Object>>();

        for(InventoryTransfer timeStatisticRecord :records){
            Map<String,Object> element = new HashMap<>();
            element.put("x",timeStatisticRecord.getCreateDate().getTime() );

            element.put("y",timeStatisticRecord.getAmount() );
            averages.add(element);
        }

        map.put("data", averages);
        result.add(map);
        return result;

    }

    @RequestMapping(value = "/advancedPaymentTransfer", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> advancedPaymentTransfer(@RequestParam(value = "distributorId", required = false) Integer distributorId, @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC)  Pageable pageable)  {

        Distributor distributor = distributorService.getById(distributorId);
        Page<Map>  pages = distributorService.queryAdvancedPaymentTransferr(distributor, pageable);
        return pages;
    }



    @RequestMapping(value = "addInventory", method = RequestMethod.POST)
    @ResponseBody

    public Map addInventory(@RequestParam(value = "id", required = false) Integer id,
                            @RequestParam(value = "amount", required = false) BigDecimal amount,
                            Authentication authentication) {

        logger.debug("----- param is  id : {},  price:{}, notificationToIds:{}, returnTo:{}，sendMessageToFollower is:{}", amount.toString());

        Map ret = new HashMap<String, String>();
        ret.put("status", false);


        Distributor inventory = distributorService.getById(id);
        AdvancedPaymentTransfer location = distributorService.addAdvancedPayment(inventory, amount);
        if(location != null){


            ret.put("status", true);
        }
        return ret;

    }




    @RequestMapping(value = "/advancedPayment_trend", method = RequestMethod.POST)
    @ResponseBody
    public List getAdvancedPaymentTrend(@RequestParam(value = "id",required = true) Integer id) {

        List<Map> result = new ArrayList<Map>();



        Map map = new HashMap<String, String>();
        map.put("name", "平台请求量");


        Distributor distributor = distributorService.getById(id);
        List<AdvancedPaymentTransfer> records = distributorService.getAdvancedPaymentTransfers(distributor);



        List<Map<String,Object>> values = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> averages = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> ranges = new ArrayList<Map<String,Object>>();

        for(AdvancedPaymentTransfer timeStatisticRecord :records){
            Map<String,Object> element = new HashMap<>();
            element.put("x",timeStatisticRecord.getCreateDate().getTime() );

            element.put("y",timeStatisticRecord.getAmount() );
            averages.add(element);
        }

        map.put("data", averages);
        result.add(map);
        return result;

    }

















    @RequestMapping(value = "/command=getBindQrcode", method = RequestMethod.POST)
    @ResponseBody
    public Object getBindQrcode(@RequestParam(value = "id",required = true) Integer id,Authentication authentication) {
        // jsmMessageService.sendStatisticMessage("editPrice");

        logger.debug("get_product_price_categories === param is : productId is :{} ", id);




        Map<String, Object> map = new HashMap<String, Object>();

        map.put("code","0");
        map.put("desc","成功");


        List<Object> companyMap = new ArrayList<Object>();

        Distributor distributor = distributorService.getById(id);

        TemporaryQrcode temporaryQrcode = qrcodeService.getBindQrcodeForDistributor(distributor);

      //  Line line = lineService.create(lineCreateForm);

        return temporaryQrcode;
    }












    @RequestMapping(value = "/confirmOrderSettle", method = RequestMethod.GET)
    public ModelAndView confirmOrderSettle(@RequestParam("orderId") Integer orderId,
                                           HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("/mobile/user_center/confirmSettleShippingPage");

        logger.debug("confirmSettle:  [orderId]" + orderId);
        Distributor coalOrder = distributorService.getById(orderId);
/*

        if (coalOrder != null){
            List<Shipping> shippings = null;

            shippings = shippingRepository.getShippingsByCoalOrder(coalOrder.getId());

            modelAndView.addObject("shippings", shippings);
            modelAndView.addObject("coalOrder", coalOrder);
        }
*/

        String shipmentShippingUri = linkTo(methodOn(MobileDistributorController.class).transfer(coalOrder.getId(),null,null,null)).withSelfRel().getHref();
        modelAndView.addObject("shipmentShippingUri", shipmentShippingUri);

        String settleUrl = linkTo(methodOn(MobileDistributorController.class).reqSettle(null,coalOrder.getId(),null)).withSelfRel().getHref();
        modelAndView.addObject("settleUrl", settleUrl);



/*        String settlementUrl =   linkTo(methodOn(MobileDistributorController.class).settlements(coalOrder.getId(), null,null)).withSelfRel().getHref();
        modelAndView.addObject("settlementUrl",settlementUrl );*/


        return modelAndView;



    }

    private Class<?> reqSettle(Object o, Integer id, Object o1) {
        return null;
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> transfer(@RequestParam("orderId") Integer coalOrderId,
                              @RequestParam(value = "status", required = false) String status,
                              @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable,Authentication authentication) {

        logger.debug("================= param is{}",status);


        Distributor coalOrder = distributorService.getById(coalOrderId);

        logger.debug("====================================param is {}", coalOrder.toString());
        return distributorService.queryInventoryTransfer(coalOrder, pageable);
    }





    // 这里构建一个 payments
    @RequestMapping(value = "/reqSettle", method = RequestMethod.POST)
    @ResponseBody
    public Map reqSettle(@RequestParam(value = "shippingIds",required = false) Integer[] shippingIds,
                         @RequestParam(value = "orderId",required = false) Integer orderId,
                         Authentication authentication) {


        Map ret = new HashMap<>();
        ret.put("status", true);


        logger.debug("confirmSettle: [signedShippingIds]" + Arrays.toString(shippingIds) + "and [orderId]" + orderId);

  /*      Distributor coalOrder = distributorService.getById(coalOrderId);



        List<InventoryTransfer> shippings = distributorService.queryTransfersByIds(shippingIds);


        distributorService.createPaymentForUncheckTransfer(shippings, coalOrder, user);*/

        return ret;
    }


    @RequestMapping(value = "/settlement/list", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> settlements(@RequestParam("id") Integer id,
                                 @PageableDefault(sort = "createDate",
                                         direction = Sort.Direction.DESC)Pageable pageable,Authentication authentication) {





/*        CapitalPaymentDto capitalPaymentDto = new CapitalPaymentDto();
        capitalPaymentDto.setOrderId(id);
        logger.debug("payment param:{}" , capitalPaymentDto.toString());
        return capitalPaymentService.queryPayments(capitalPaymentDto, pageable, user);*/

        return null;//
  /*
        if(user.getUserRoles().contains(UserRolesEnum.USER_ROLE_ADMIN.getText())) {
            capitalPaymentDto.setOrderId(id);
            logger.debug("payment param:{}" , capitalPaymentDto.toString());
            return capitalPaymentService.queryPayments(capitalPaymentDto, pageable, user);
        }

        if(user.getUserRoles().contains(UserRolesEnum.USER_ROLE_DRIVER.getText())){
            capitalPaymentDto.setOrderId(id);
            logger.debug("payment param:{}" , capitalPaymentDto.toString());
            return capitalPaymentService.queryPayments(capitalPaymentDto, pageable, user);
        }


        return null;*/
    }


}
