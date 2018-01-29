package com.coalvalue.web;

import com.coalvalue.configuration.CommonConstant;
import com.coalvalue.configuration.Constants;
import com.coalvalue.domain.OperationResult;

import com.coalvalue.domain.entity.*;

import com.coalvalue.enumType.ResourceType;
import com.coalvalue.enumType.TransportOperationStatusEnum;

import com.coalvalue.repository.TransportOperationRepository;
import com.coalvalue.service.CompanyService;
import com.coalvalue.service.DeliveryOrderService;
import com.coalvalue.service.ProductService;
import com.coalvalue.service.WxService;

import com.domain.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by silence yuan on 2015/7/12.
 */

@Controller
@RequestMapping(value= {"/workbench"})
public class MobileBillWorkbenchController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(MobileBillWorkbenchController.class);




    @Autowired
    private CompanyService companyService;


    @Autowired
    private DeliveryOrderService deliveryOrderService;


    @Autowired
    private TransportOperationRepository transportOperationRepository;
    @Autowired
    private ProductService productService;


    @Autowired
    private WxService wxService;





    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView discovery(@PathVariable(value = "id") Integer index,@RequestParam(value = "ticket", required = false) String ticket, Authentication authentication, @PageableDefault Pageable pageable) {


        WxTemporaryQrcode wxTemporaryQrcode = null;//wxService.getByTicket(ticket);

        if(wxTemporaryQrcode != null){
            ModelAndView modelAndView = new ModelAndView("/mobile/bill_workbench");
            Company company = companyService.getCompanyById(index);
           // mobileService.setWebSocketUrl(authentication,modelAndView,WebSocketServerUrlTypeEnum.Bill_workbench,company.getId());
          //  mobileService.setAuthenticationInfo(authentication,modelAndView);
            //   mobileService.set


          //  List<StorageSpace> storageSpaces = storageSpaceService.getStorageSpaces(company);


          //  StorageSpace storageSpace = storageSpaces.get(0);
          //  mobileService.setWebSocketUrl(authentication,modelAndView,WebSocketServerUrlTypeEnum.Bill_workbench,storageSpace.getId());
            modelAndView.addObject("company", company);



            Sort sortby = new Sort(Sort.Direction.DESC, "createDate");

            Pageable pageable_ = new PageRequest(0, 15, sortby);


            String confirmArrivalCommandUri = linkTo(methodOn(MobileBillWorkbenchController.class).resultList(null, null, null)).withSelfRel().getHref();

            modelAndView.addObject("operationResultListUri",confirmArrivalCommandUri );






           // String storageSpaceCreateOperationsUrl =   linkTo(methodOn(MobileUserCenterStorageSpaceController.class).storageSpaceCreateOperations(storageSpace.getId(), null)).withSelfRel().getHref();
           // modelAndView.addObject("storageSpaceCreateOperationsUrl",storageSpaceCreateOperationsUrl);


            String storageSpaceLoadingOperationsUrl =   linkTo(methodOn(MobileInstanceTransprotController.class).stations(TransportOperationStatusEnum.LOADING.getText(),null)).withSelfRel().getHref();
            modelAndView.addObject("storageSpaceLoadingOperationsUrl",storageSpaceLoadingOperationsUrl);


            String storageSpaceLeaveOperationsUrl =   linkTo(methodOn(MobileInstanceTransprotController.class).stations(TransportOperationStatusEnum.LEAVE.getText(), null)).withSelfRel().getHref();
            modelAndView.addObject("storageSpaceLeaveOperationsUrl",storageSpaceLeaveOperationsUrl);

            String storageSpaceRejectOperationsUrl =   linkTo(methodOn(MobileInstanceTransprotController.class).stations(TransportOperationStatusEnum.REJECT.getText(), null)).withSelfRel().getHref();
            modelAndView.addObject("storageSpaceRejectOperationsUrl",storageSpaceRejectOperationsUrl);


            String agreeOperationsUrl =   linkTo(methodOn(MobileBillWorkbenchController.class).billArrivalAgreeOperation(null, null,null, null,null,null)).withSelfRel().getHref();
            modelAndView.addObject("agreeOperationsUrl",agreeOperationsUrl);


            return modelAndView;

        }

        return  null;

    }



    @RequestMapping(value = "/delivering/result-list", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> resultList(
            @RequestParam(value = "verificationCode", required = false) String verificationCode,
            @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable,
            Authentication authentication){

            User user = (User)authentication.getPrincipal();
        Page<ReportDeliveryOrder> reportDeliveryOrderMap;
        if(verificationCode.length()>30){
            reportDeliveryOrderMap = deliveryOrderService.getValidPageByQrcode(ResourceType.TRANSPORT_OPERATION,verificationCode, user, pageable);

        }else{
            reportDeliveryOrderMap = deliveryOrderService.getValidPage(ResourceType.TRANSPORT_OPERATION,verificationCode, user, pageable);
        }

        Page<Map> page = reportDeliveryOrderMap.map(new Converter<ReportDeliveryOrder, Map>() {
            public Map convert(ReportDeliveryOrder objectEntity) {

                Map map = new HashMap();



                TransportOperation operation = transportOperationRepository.findById(objectEntity.getItemId());
                Product product = productService.getById(operation.getProductId());
                Company productRcompany = null;
                if(product != null){
                    productRcompany = companyService.getCompanyById(product.getCompanyId());
                    map.put("productSource", productRcompany.getCompanyName());
                    map.put("productName", product.getCoalType() + product.getGranularity());
                }else {
                    map.put("productSource", "无关联产品");

                }


                StorageSpace storageSpace  = null;//storageSpaceService.getStorageSpace(operation.getSpaceId());

                Company distributorCompany = companyService.getCompanyById(storageSpace.getCompanyId());


                    map.put("plateNumber", operation.getPlateNumber());



                    map.put("driverName", operation.getDriverName());
                    map.put("driverPhone", operation.getDriverPhone());


                map.put("createDate", operation.getPlateNumber());
                map.put("id", operation.getId());
                map.put("no", operation.getNo());
                map.put("companyName", operation.getPlateNumber());


                map.put("grossWeight", operation.getPlateNumber());

                map.put("cargoType", operation.getPlateNumber());
                map.put("orderNo", operation.getPlateNumber());



                map.put("netWeight", operation.getNetWeight());


                if(operation.getStatus()!= null){
                    map.put("status",TransportOperationStatusEnum.fromString(operation.getStatus()).getDisplayText());

                }


                Company company = companyService.getCompanyById(operation.getCompanyId());
                if(company != null){
                    map.put("companyLogoUrl", company.getSmallImage());
                    map.put("partnerCompanyName", company.getCompanyName());
                }



                        map.put("productSourceUrl", "无关联产品");




                return map;
            }
        });

        return page;



    }
    @RequestMapping(value = "/delivering/result-list-coalpit", method = RequestMethod.GET)
    @ResponseBody
    public Map shippingResultListCoalPit(
            @RequestParam(value = "verificationCode",required = false) String verificationCode,
            @PageableDefault(sort="createDate",direction= Sort.Direction.DESC) Pageable pageable,
            Authentication authentication){
       logger.debug("verificationCode :{}",verificationCode);
        User user = (User)authentication.getPrincipal();



        return null;//transportOperationService.queryListByVerificationCodeCoalpit(verificationCode, user,pageable);


    }


    @RequestMapping(value = "/command=notify",method = RequestMethod.POST)
    @ResponseBody
    public Map billArrivalAgreeOperation(@RequestParam(value = "id", required = false) Integer id,
                                         @RequestParam(value = "tareWeight", required = false) BigDecimal tareWeight,
                                         @RequestParam(value = "sendToCollaborator", required = false) Boolean sendToCollaborator,
                                         @RequestParam(value = "checkNo", required = false) Boolean checkNo,

                                         @RequestParam(value = "ladingBileNo", required = false) String ladingBileNo, Authentication authentication){

        logger.debug("===param is : id:{}, tareWeight :{} sendToCollaborator:{}",id, tareWeight,sendToCollaborator);


        Map ret = new HashMap<String, String>();
        User user = (User)authentication.getPrincipal();
        ret.put("status", true);

        TransportOperation transportOperation = null;//transportOperationService.getOperationById(id);

        OperationResult result = null;//transportOperationService.notifyArrival(transportOperation, tareWeight, ladingBileNo, user, checkNo);
        if(result.isSuccess()) {
            transportOperation = (TransportOperation)result.getResultObject();
        }else {
            ret.put("status", false);
            ret.put("message",result.getErrorMessage());
        }
        return ret;

    }




    @RequestMapping( value = "/login",method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "id", required = false) Integer index, Authentication authentication, @PageableDefault Pageable pageable) {



        ModelAndView modelAndView = new ModelAndView("/mobile/bill_workbench_waiting");
        Company company = companyService.getCompanyById(index);
    //    mobileService.setWebSocketUrl(authentication,modelAndView,WebSocketServerUrlTypeEnum.CONFIGURATION,company.getId());
     //   mobileService.setAuthenticationInfo(authentication,modelAndView);




        String url =   linkTo(methodOn(MobileBillWorkbenchController.class).redirect(company.getId(),null,null)).withSelfRel().getHref();

        modelAndView.addObject("url",url);

        WxTemporaryQrcode wxeneral = null;//wxService.getVariableByConfiguration(company.getId(), Constants.WX_QRCODE_TYPE_WORKBENCH_CONFIGURATION);

        if(wxeneral != null ) {
            if (wxeneral.getStatus().equals(CommonConstant.QRCODE_STATUS_Valid)) {
                modelAndView.addObject("qrcodeUrl", wxeneral.getQrCode());
            }
        }


        return modelAndView;

    }


    @RequestMapping(value = "/redirect/{id}", method = RequestMethod.GET)
    public Object redirect(@PathVariable(value = "id") Integer id,@RequestParam(value = "ticket",required = false) String verificationCode,
            Authentication authentication){
        logger.debug("verificationCode :{}",verificationCode);
       // User user = (User)authentication.getPrincipal();

        WxTemporaryQrcode wxTemporaryQrcode =null;// wxService.getByTicket(verificationCode);


        String url =   linkTo(methodOn(MobileBillWorkbenchController.class).discovery(id, wxTemporaryQrcode.getTicket(),null, null)).withSelfRel().getHref();

        return "redirect:" +url;


    }

}
