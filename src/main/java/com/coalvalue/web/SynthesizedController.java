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
@RequestMapping(value= {"/home"})
public class SynthesizedController {
    private static final Logger logger = LoggerFactory.getLogger(SynthesizedController.class);


/*
    @Autowired
    private SessionRegistry sessionRegistry;//这个类会自动注入 不用我们自己去手动注入
*/


    @Autowired
    private BehaviouralService behaviouralService;


    @Autowired
    private VerifyService locationService;
    @Autowired
    private InventoryTransferRepository inventoryTransferRepository;


    @Autowired
    private DeliveryOrderService deliveryOrderService;

    @Autowired
    private GeneralServiceImpl generalService;

    @Autowired
    private DistributorService distributorService;
    @Autowired
    private InstanceTransportService instanceTransportService;
    @Autowired
    private PreferenceService preferenceService;


    @Autowired
    private DifferentialSyncService differentialSyncService;


    @Autowired
    private InventoryTransferService inventoryTransferService;

    @Autowired
    private ReportDeliveryOrderRepository reportDeliveryOrderRepository;
    @Autowired
    private InstanceTransportRepository instanceTransportRepository;
    @Autowired
    private StorageSpaceRepository storageSpaceRepository;


    @Autowired
    private FeeService feeService;

    @Autowired
    private FeeRepository feeRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView index(@RequestParam(value = "q", required = false) String searchTerm,Authentication authentication)  {
        //  mqttService.sendNetworkStatus("OK");


        ModelAndView modelAndView = new ModelAndView("/synthesized_index");


        String exitIntelligent = linkTo(methodOn(SynthesizedController.class).exitIntelligent("", null)).withSelfRel().getHref();
        modelAndView.addObject("exitIntelligentUrl", exitIntelligent);

        String entranceIntelligent = linkTo(methodOn(SynthesizedController.class).entranceIntelligent("", null)).withSelfRel().getHref();
        modelAndView.addObject("entranceIntelligentUrl", entranceIntelligent);


/*      String command_create_url = linkTo(methodOn(SynthesizedController.class).create(null, null)).withSelfRel().getHref();
        modelAndView.addObject("command_create_url",command_create_url);*/


        String command_edit_url = linkTo(methodOn(SynthesizedController.class).edit(null, null)).withSelfRel().getHref();
        modelAndView.addObject("command_edit_url", command_edit_url);




        String instanceTransport_loaded_Url = linkTo(methodOn(SynthesizedController.class).transfer(null, null)).toUri().toASCIIString();
        modelAndView.addObject("transfer_no_reconciliation_Url", instanceTransport_loaded_Url);


        List<Distributor> producers = distributorService.getAll();

        modelAndView.addObject("collaborators", producers);
        //mobileService.setWebSocketUrl(authentication,modelAndView,WebSocketServerUrlTypeEnum.STORAGE_DETAIL,storageSpace.getId());

        generalService.getStatistic(null, modelAndView, 1);
        generalService.setWebSocketUrl("synthesized", null, modelAndView, "1");

        try {
            generalService.setGeneral(modelAndView, FaceRemoteControl.FACE_PAGE_workbench,authentication);
        } catch (Exception e) {
            e.printStackTrace();
        }


        generalService.setWebSocketUrl("status", null, modelAndView, null);


        String feeUrl = linkTo(methodOn(SynthesizedController.class).fee(null, null)).toUri().toASCIIString();
        modelAndView.addObject("feeUrl", feeUrl);








/*
        System.out.println("u begin");
        List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
        for (int i = 0,len=allPrincipals.size(); i < len; i++)
        {
            System.out.println(allPrincipals.get(i));
        }

*/


        String welcomeUrl = null;
        try {
            welcomeUrl = linkTo(methodOn(MobileIndexController.class).welcome("")).withSelfRel().getHref();
        } catch (Exception e) {
            e.printStackTrace();
        }
        modelAndView.addObject("welcomeUrl", welcomeUrl);

        String fragmentUrl = linkTo(methodOn(SynthesizedController.class).fragment()).withSelfRel().getHref();
        modelAndView.addObject("fragmentUrl", fragmentUrl);

        String fragmentDeliveryReportInfoUrl = linkTo(methodOn(FragmentController.class).fragmentDeliveryReportInfo(null)).toUri().toString();
        modelAndView.addObject("fragmentDeliveryReportInfoUrl", fragmentDeliveryReportInfoUrl);

        String fragmentDeliveryReportInfoNetweightUrl = linkTo(methodOn(FragmentController.class).delivery_report_info_netweight(null)).toUri().toString();
        modelAndView.addObject("fragmentDeliveryReportInfoNetweightUrl", fragmentDeliveryReportInfoNetweightUrl);


        String inventory_transfer_correctUrl = linkTo(methodOn(FragmentController.class).inventory_transfer_correct(null)).toUri().toString();
        modelAndView.addObject("inventory_transfer_correctUrl", inventory_transfer_correctUrl);

/*      String command_correct_url = linkTo(methodOn(SynthesizedController.class).correctEdit(inventoryTransfer.getUuid(), null,null)).withSelfRel().getHref();
        modelAndView.addObject("command_correct_url", command_correct_url);*/




        String selectStorageUrl =   linkTo(methodOn(SecurityController.class).storage_select(null,null)).toUri().toString();
        modelAndView.addObject("selectStorageUrl",selectStorageUrl);


        if(authentication!= null && authentication.isAuthenticated()){
            Map map = preferenceService.getPreference((User)authentication.getPrincipal());
            modelAndView.addObject("preference", map);

            String storageNo = (String)map.get("storageNo");
            String deliveryOrderUrl = linkTo(methodOn(SynthesizedController.class).deliveryOrderStorage(storageNo, null,null)).toUri().toASCIIString();
            modelAndView.addObject("deliveryOrderUrl", deliveryOrderUrl);

            String instanceTransportUrl = linkTo(methodOn(SynthesizedController.class).instanceTransport(storageNo, null,null)).toUri().toASCIIString();
            modelAndView.addObject("instanceTransportUrl", instanceTransportUrl);
        }else{

            List<StorageSpace> storageSpaces = storageSpaceRepository.findAll();


            if(storageSpaces.size() != 0){
                Map map = preferenceService.getStorage(storageSpaces.get(0));
                modelAndView.addObject("preference", map);


                String deliveryOrderUrl = linkTo(methodOn(SynthesizedController.class).deliveryOrderStorage(storageSpaces.get(0).getNo(), null,null)).toUri().toASCIIString();
                modelAndView.addObject("deliveryOrderUrl", deliveryOrderUrl);
            }else{
                modelAndView.addObject("deliveryOrderUrl", "");
            }

        }

        return modelAndView;
    }

/*
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(
            Exception ex, WebRequest request) {
        return new ResponseEntity<Object>(
                "Access denied message here", new HttpHeaders(), HttpStatus.FORBIDDEN);
    }*/


    @RequestMapping(value = "/createTransfer/{uuid}", method = RequestMethod.POST)
    @ResponseBody
    public Map createTransfer(@PathVariable(value = "uuid", required = false) String uuid, @Valid InventoryTransferCreateForm locationCreateForm,
                              Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        logger.debug("----- param is  id : {},  price:{}, notificationToIds:{}, returnTo:{}，sendMessageToFollower is:{}", uuid, locationCreateForm.toString());
        Map ret = new HashMap<String, String>();
        ret.put("status", false);

        InstanceTransport instanceTransport = instanceTransportRepository.findByUuid(uuid);
        OperationResult operationResult = null;
        try {
            operationResult = instanceTransportService.createTransfer(instanceTransport, locationCreateForm, user);
            if (operationResult.isSuccess()) {
                ret.put("status", true);
            } else {
                ret.put("message", operationResult.getErrorMessage());
            }

        } catch (MqttException e) {
            e.printStackTrace();
            ret.put("status", false);
            ret.put("message", e.getMessage());
        }

        return ret;
    }


    @RequestMapping(value = "createInstanceTransport", method = RequestMethod.POST)
    @ResponseBody
    public Map createInstanceTransport(@Valid InstanceTransportCreateForm locationCreateForm, Authentication authentication)  {
        logger.debug("----- param is  id : {},  price:{}, notificationToIds:{}, returnTo:{}，sendMessageToFollower is:{}", locationCreateForm.toString());
        Map ret = new HashMap<String, String>();
        ret.put("status", false);

        Fee fee = feeRepository.findByUuid(locationCreateForm.getFeeUuid());
        ReportDeliveryOrder reportDeliveryOrder = reportDeliveryOrderRepository.findByUuid(locationCreateForm.getUuid());
        InstanceTransport instanceTransport = null;
        try {
            instanceTransport = instanceTransportService.createFromDeliveryOrderInputTareWeight(reportDeliveryOrder,fee, locationCreateForm);
            if (instanceTransport != null) {
                differentialSyncService.syncImmediately();
                ret.put("status", true);
            }
        } catch (MqttException e) {
            e.printStackTrace();
            ret.put("status", false);
            ret.put("message",e.getMessage());
        }

        return ret;

    }


    @RequestMapping(value = "exitIntelligent", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> exitIntelligent(@RequestParam(value = "q", required = false) String searchTerm, @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable) {

        return behaviouralService.querySynthesizedExitIntelligent(null, pageable);
    }

    @RequestMapping(value = "entranceIntelligent", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> entranceIntelligent(@RequestParam(value = "q", required = false) String searchTerm, @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable) {

        return behaviouralService.querySynthesizedEntranceIntelligent(null, pageable);
    }

    @RequestMapping(value = "/delivery_order_list", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> deliveryOrder(@RequestParam(value = "searchText", required = false) String searchText, @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable) {

        System.out.print("========================= {}" + searchText);

        if (searchText != null)
            searchText = searchText.trim();

        DeliveryOrderDto deliveryOrderDto = new DeliveryOrderDto();
        deliveryOrderDto.setSearchText(searchText);
        deliveryOrderDto.setStatus(DeliveryOrderStatusEnum.Valid.getText());

        return deliveryOrderService.query_report_delivery(deliveryOrderDto, pageable);
    }
    @RequestMapping(value = "/deliveryOrderStorage/{no}", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> deliveryOrderStorage(@RequestParam(value = "no", required = false) String no,
            @RequestParam(value = "searchText", required = false) String searchText,
                                          @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable) {

        System.out.print("========================= {}" + searchText);

        if (searchText != null)
            searchText = searchText.trim();
        DeliveryOrderDto deliveryOrderDto = new DeliveryOrderDto();
        deliveryOrderDto.setSearchText(searchText);
        deliveryOrderDto.setStatus(DeliveryOrderStatusEnum.Valid.getText());

        deliveryOrderDto.setStorageNo(no);

        return deliveryOrderService.query_report_delivery(deliveryOrderDto, pageable);
    }
    @RequestMapping(value = "/instanceTransport_loaded", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> transfer(@RequestParam(value = "searchText", required = false) String searchText, @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable) {

        System.out.print("========================= {}" + searchText);

        if (searchText != null)
            searchText = searchText.trim();


        InstanceTransportDto instanceTransportDto = new InstanceTransportDto();
        instanceTransportDto.setStatus(InstanceTransportStatusEnum.LEAVE.getText());


        return inventoryTransferService.query(instanceTransportDto, searchText, pageable);

    }

    @RequestMapping(value = "/instanceTransport/{no}", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> instanceTransport(@PathVariable(value = "no", required = false) String no,@RequestParam(value = "searchText", required = false) String searchText, @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable) {

        System.out.print("========================= {}" + searchText);

        if (searchText != null)
            searchText = searchText.trim();
        InstanceTransportDto instanceTransportDto = new InstanceTransportDto();
        instanceTransportDto.setStatus(InstanceTransportStatusEnum.LOADING.getText());
        instanceTransportDto.setStorageNo(no);
        return instanceTransportService.query_synthesized(instanceTransportDto, searchText, pageable);
    }


    @RequestMapping(value = "edit", method = RequestMethod.PUT)
    @ResponseBody

    public Map edit(@Valid LocationCreateForm locationCreateForm,
                    Authentication authentication) {

        logger.debug("----- param is  id : {},  price:{}, notificationToIds:{}, returnTo:{}，sendMessageToFollower is:{}", locationCreateForm.toString());

        Map ret = new HashMap<String, String>();
        ret.put("status", false);

        Location location = locationService.edit(locationCreateForm);
        if (location != null) {


            ret.put("status", true);
        }


        return ret;

    }


    @RequestMapping(value = "beingWeighed", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> beingWeighed(@RequestParam(value = "q", required = false) String searchTerm, @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable) {


        return null;// behaviouralService.queryBeingWeighed(null, pageable);
    }


    @RequestMapping(value = "beingLoaded", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> beingLoaded(@RequestParam(value = "q", required = false) String searchTerm, @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable) {

        return behaviouralService.queryBeingLoaded(null, pageable);
    }


    @RequestMapping(value = "/fee", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> fee(@RequestParam(value = "no", required = false) String no, @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable) {


        ReportDeliveryOrder reportDeliveryOrder = reportDeliveryOrderRepository.findByNo(no);

        return feeService.queryFess(reportDeliveryOrder.getDistributorNo(), pageable).getContent();
    }



    @RequestMapping(value = "/correctEdit/{uuid}", method = RequestMethod.POST)
    @ResponseBody

    public Map correctEdit(@PathVariable(value = "uuid", required = false) String uuid, @Valid InventoryTranferFormCorrect locationCreateForm,
                              Authentication authentication) {
        User user = (User) authentication.getPrincipal();
/*    @PostMapping("/correctEdit/{uuid}")
    @ResponseBody*/
/*

    public Map correctEdit(@PathVariable(value = "uuid") String uuid,@RequestBody InventoryTranferFormCorrect locationCreateForm,
                           Authentication authentication) {
*/

        logger.debug("----- param is  id : {},  price:{}, notificationToIds:{}, returnTo:{}，sendMessageToFollower is:{}", locationCreateForm.toString());

        Map ret = new HashMap<String, String>();
        ret.put("status", false);

        InventoryTransfer inventoryTransfer = inventoryTransferRepository.findByUuid(uuid);
        InventoryTransfer location = inventoryTransferService.correct(inventoryTransfer, locationCreateForm);
        if (location != null) {


            ret.put("status", true);
        }


        return ret;

    }

    public List<Object> getUsersFromSessionRegistry() {
/*        return sessionRegistry.getAllPrincipals().stream()
                .filter(u -> !sessionRegistry.getAllSessions(u, false).isEmpty())
                .map(e->e)
                .collect(Collectors.toList());*/
        return new ArrayList<>();
    }


    @RequestMapping(value = "/fragment", method = RequestMethod.GET)
    public ModelAndView fragment() {//,Authentication authentication)  {
        //  mqttService.sendNetworkStatus("OK");

        ModelAndView modelAndView = new ModelAndView("/fragment/login");
        String instanceTransport_loaded_Url = linkTo(methodOn(MobileRegisterController.class).login(null)).toUri().toASCIIString();
        modelAndView.addObject("loginUrl", instanceTransport_loaded_Url);
        return modelAndView;
    }










}
