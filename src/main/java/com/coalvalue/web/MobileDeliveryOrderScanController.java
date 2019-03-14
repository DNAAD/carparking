package com.coalvalue.web;

import com.coalvalue.domain.OperationResult;
import com.coalvalue.domain.entity.*;
import com.coalvalue.domain.pojo.DistributedTimeSlicePassword;
import com.coalvalue.dto.InventoryDto;
import com.coalvalue.repository.*;
import com.coalvalue.repository.specific.InventorySpec;
import com.coalvalue.service.*;
import com.coalvalue.service.other.GeneralServiceImpl;
import com.coalvalue.task.LiveBroadcast;
import com.coalvalue.task.TimeSilcePasswordService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;



/**
 * Created by silence yuan on 2015/7/12.
 */

@Controller
@RequestMapping(value= {"/front/deliveryOrderScan"})
public class MobileDeliveryOrderScanController {
    private static final Logger logger = LoggerFactory.getLogger(MobileDeliveryOrderScanController.class);

    @Autowired
    private InventoryService inventoryService;


    @Autowired
    private StorageSpaceRepository storageSpaceRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private QrcodeService qrcodeService;

    @Autowired
    private GeneralServiceImpl generalService;


    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private VerifyService verifyService;

    @Autowired
    private LiveBroadcast liveBroadcast;
    @Autowired
    private TimeSilcePasswordService timeSilcePasswordService;



    @Autowired
    private BehaviouralService behaviouralService;
    @RequestMapping(value = "/index/{no}", method = RequestMethod.GET)
    public ModelAndView index(@PathVariable(value = "no", required = false) String no, Authentication authentication){


        ModelAndView modelAndView = new ModelAndView("/report_scan");
        modelAndView.addObject("q",no);


        try {
            generalService.setGeneral(modelAndView, "", authentication);
        } catch (Exception e) {
            e.printStackTrace();
        }


        String verifiedUrl = linkTo(methodOn(MobileDeliveryOrderScanController.class).verified("", null)).withSelfRel().getHref();
        modelAndView.addObject("verifiedUrl",verifiedUrl);

        String beingLoadedUrl = linkTo(methodOn(SynthesizedController.class).beingLoaded("", null)).withSelfRel().getHref();
        modelAndView.addObject("beingLoadedUrl",beingLoadedUrl);


        String beingWeighedUrl = linkTo(methodOn(SynthesizedController.class).beingWeighed("", null)).withSelfRel().getHref();
        modelAndView.addObject("beingWeighedUrl",beingWeighedUrl);



        StorageSpace storageSpace = storageSpaceRepository.findByNo(no);
        generalService.setWebSocketUrl("scan",null,modelAndView,storageSpace.getNo());




        DistributedTimeSlicePassword distributedTimeSlicePassword = timeSilcePasswordService.getTimeSlice(storageSpace.getNo());
        if(distributedTimeSlicePassword != null){
            modelAndView.addObject("permanentQrcode", distributedTimeSlicePassword.getQrcodeContent());

        }else{


        }

        String inventoryUrl = linkTo(methodOn(MobileDeliveryOrderScanController.class).inventory(storageSpace.getNo(), null)).withSelfRel().getHref();
        modelAndView.addObject("inventoryUrl",inventoryUrl);
        modelAndView.addObject("storageSpace", storageSpace);

        String verifyUrl = linkTo(methodOn(MobileDeliveryOrderScanController.class).synchronize(null,null)).toUri().toString();
        modelAndView.addObject("verifyUrl",verifyUrl);

        String url = linkTo(methodOn(MobileDeliveryOrderScanController.class).index(no,null)).withSelfRel().getHref();
        modelAndView.addObject("url", url);




        String scanResultUrl = linkTo(methodOn(MobileDeliveryOrderScanController.class).scanResult("", null)).withSelfRel().getHref();
        modelAndView.addObject("scanResultUrl",scanResultUrl);


        generalService.setWebSocketUrl("status",null,modelAndView,null);

        return modelAndView;




    }
    @RequestMapping(value = "/verified", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> verified(@RequestParam(value = "q", required = false) String searchTerm, @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable)  {
        return behaviouralService.queryBeingVerified(null,pageable);
    }

    @RequestMapping(value = "/getInventory___PRODUCT", method = RequestMethod.GET)
    @ResponseBody
    public Map getInventory___PRODUCT() {
        Random r2 = new Random();


        Map map = new HashMap<String, String>();

        List<Product> products =  productRepository.findAll();

        List<List<Object>> averages = new ArrayList<List< Object>>();



        for (Product product : products) {


            List element = new ArrayList<>();



            element.add(product.getCoalType() + "/" + product.getGranularity());


            element.add(1);

            List<Inventory> inventories = inventoryRepository.findByProductNo(product.getNo());



            element.add(inventories.stream().mapToDouble(e->e.getQuantityOnHand().doubleValue()).sum());



            averages.add(element);



        }
        //    map.put("ranges", averages);
        map.put("data", averages);
        return map;
    }





    @RequestMapping(value = "inventories", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> inventory(@RequestParam(value = "q", required = false) String searchTerm, @PageableDefault Pageable pageable)  {


        StorageSpace storageSpace = storageSpaceRepository.findByNo(searchTerm);
        InventoryDto deliveryOrderDto = new InventoryDto();
        if(storageSpace!= null){
            deliveryOrderDto.setStorageNo(storageSpace.getNo());
        }

        //  deliveryOrderDto.setStatus(DeliveryOrderStatusEnum.Valid.getText());

        InventorySpec deliveryOrderSpec =  new InventorySpec(deliveryOrderDto);
        Specification<Inventory> spec = deliveryOrderSpec.querySynthesizedSpec();

        Page<Inventory>  pages = inventoryRepository.findAll(spec,pageable);


        //    Page<Inventory> pages =  inventoryRepository.findByStorageNoAndStatus(storageSpace.getNo(), SynchronizeEnum.Been_synchronized.getText(),pageable);


        Page<Map> page = pages.map(new Function<Inventory, Map>() {
            public Map apply(Inventory objectEntity) {

                ObjectMapper m = new ObjectMapper();
                Map<String,Object> mappedObject = m.convertValue(objectEntity,Map.class);


                String companiesUrl = linkTo(methodOn(MobileInventoryController.class).detail(objectEntity.getNo(), null)).withSelfRel().getHref();

                mappedObject.put("url",companiesUrl);

                LiveInforInventory liveInforInventory = liveBroadcast.getLiveBroadcast(objectEntity);
                if(liveInforInventory!= null){
                    mappedObject.put("loadingCount",liveInforInventory.getLoadingCount());
                    mappedObject.put("waitingCount",liveInforInventory.getWaitingCount());
                    mappedObject.put("averageLaytime",liveInforInventory.getAverageLaytime().divide(new BigDecimal(60)).setScale(0));
                }


/*                Distributor distributor = distributorService.getByNo(objectEntity.getCompanyNo());
                String distributorUrl = linkTo(methodOn(MobileDistributorController.class).detail(distributor.getId(), null, null)).withSelfRel().getHref();

                mappedObject.put("distributorUrl",distributorUrl);*/
                return mappedObject;
            }
        });
        return page;
        //return inventoryService.query(storageSpace,pageable);
    }


    @RequestMapping(value = "products", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> products(@RequestParam(value = "q", required = false) String searchTerm, @PageableDefault Pageable pageable)  {


        Page<Product> pages = productRepository.findAll(pageable);

        Page<Map> page = pages.map(new Function<Product, Map>() {
            public Map apply(Product objectEntity) {

                ObjectMapper m = new ObjectMapper();
                Map<String,Object> mappedObject = m.convertValue(objectEntity,Map.class);

                mappedObject.put("createDate",objectEntity.getCreateDate());

                List<Inventory> inventories = inventoryRepository.findByProductNo(objectEntity.getNo());


                mappedObject.put("inventory",inventories.stream().mapToDouble(e->e.getQuantityOnHand().doubleValue()).sum());
                return mappedObject;
            }
        });
        return page;


    }



    @RequestMapping(value = "/InventoryTransfer_list", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> InventoryTransfer(@RequestParam(value = "distributorId", required = false) Integer distributorId, @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC)  Pageable pageable)  {


        Inventory distributor = inventoryService.getById(distributorId);


        Page<Map>  pages = inventoryService.queryInventoryTransfer(distributor, pageable);


        return pages;
    }






    @RequestMapping(value = "/synchronize", method = RequestMethod.POST)
    @ResponseBody

    public Map synchronize(@RequestParam(value = "no", required = false) String no,Authentication authentication) {

        logger.debug("----- param is  id : {},  price:{}, notificationToIds:{}, returnTo:{}，sendMessageToFollower is:{}", no.toString());

        Map ret = new HashMap<String, String>();
        ret.put("status", false);

/*        if(bindingResult.hasErrors()){
            ret.put("message", bindingResult.getAllErrors().toString());
        }*/
        OperationResult location = verifyService.create(no);
        if(location != null){
            ret.put("status", true);
        }


        return ret;

    }

    @RequestMapping(value = "scanResult", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> scanResult(@RequestParam(value = "q", required = false) String searchTerm, @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable)  {



        return new PageImpl<Map>(beingWeighed_Entrance_maps, pageable, beingWeighed_Entrance_maps.size());



    }
    List<Map> beingWeighed_Entrance_maps = new ArrayList<>();

    public void add_beingWeighed_Entrance(List<ReportDeliveryOrder> reportDeliveryOrders) {

        beingWeighed_Entrance_maps.clear();
        for(ReportDeliveryOrder reportDeliveryOrder: reportDeliveryOrders){

            if(reportDeliveryOrder.getNo()!=null){
                Map map = new HashMap<>();
                map.put("no", reportDeliveryOrder.getNo());
                map.put("license", reportDeliveryOrder.getLicense());
                map.put("distributor", reportDeliveryOrder.getCompanyName());
                map.put("product", reportDeliveryOrder.getProductName());
                Inventory inventory = inventoryRepository.findByNo(reportDeliveryOrder.getInventoryNo());

                map.put("quote", inventory.getQuote());


                String url = linkTo(methodOn(MobileDeliveryOrderController.class).detail(reportDeliveryOrder.getNo(), null,null)).withSelfRel().getHref();
                map.put("url", url);

                map.put("status", reportDeliveryOrder.getStatus());

                beingWeighed_Entrance_maps.add(map);
            }

        }



    }

}
