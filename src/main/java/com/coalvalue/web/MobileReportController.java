package com.coalvalue.web;

import com.coalvalue.domain.OperationResult;
//import com.coalvalue.domain.Trip;
import com.coalvalue.domain.entity.*;
import com.coalvalue.dto.InventoryTransferDto;
import com.coalvalue.enumType.*;
import com.coalvalue.domain.entity.Event;
import com.coalvalue.repository.*;
import com.coalvalue.service.*;
import com.coalvalue.service.other.GeneralServiceImpl;
import com.coalvalue.web.valid.TripCreateForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;
import java.util.function.Function;

import static java.time.ZoneOffset.UTC;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

//import org.springframework.security.core.Authentication;

//import com.coalvalue.repositorySecondary.ProductRepository;

/**
 * Created by silence yuan on 2015/7/12.
 */

@Controller
@RequestMapping(value= {"/report"})
public class MobileReportController {
    private static final Logger logger = LoggerFactory.getLogger(MobileReportController.class);

    @Autowired
    private InventoryService inventoryService;


    @Autowired
    private StorageSpaceRepository storageSpaceRepository;
    @Autowired
    private InventoryTransferRepository inventoryTransferRepository;



    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private FollowerRepository followerRepository;
    @Autowired
    private BehaviouralService behaviouralService;

    @Autowired
    private ReportService reportService;
    @Autowired
    private DistributorRepository distributorRepository;
    @Autowired
    private DevelopmentTrendRepository developmentTrendRepository;


    @Autowired
    private QrcodeService qrcodeService;

    @Autowired
    private GeneralServiceImpl generalService;
    @Autowired
    private AsynchronousDataSynchronizationService asynchronousDataSynchronizationService;

    @Autowired
    private InventoryRepository inventoryRepository;


    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private QuotationPlanRepository quotationPlanRepository;

    @Autowired
    private PriceCategoryRepository priceCategoryRepository;


    @Autowired
    private StorageService storageService;

    @RequestMapping(value = "/index/{no}", method = RequestMethod.GET)
    public ModelAndView index(@PathVariable(value = "no", required = false) String no,Authentication authentication)  {


        ModelAndView modelAndView = new ModelAndView("/report");
        String url = linkTo(methodOn(MobileReportController.class).index(no,null)).withSelfRel().getHref();
        modelAndView.addObject("url", url);
        modelAndView.addObject("interval",60*60*12);

        modelAndView.addObject("q",no);


        try {
            generalService.setGeneral(modelAndView, "", authentication);
        } catch (Exception e) {
            e.printStackTrace();
        }


        String command_create_url = linkTo(methodOn(MobileReportController.class).create(null, null)).withSelfRel().getHref();
        modelAndView.addObject("command_create_url",command_create_url);


        String command_edit_url = linkTo(methodOn(MobileReportController.class).edit(null, null,null)).withSelfRel().getHref();
        modelAndView.addObject("command_edit_url",command_edit_url);


/*
        String synchronize_url = linkTo(methodOn(MobileReportController.class).synchronize(null, null,null)).withSelfRel().getHref();
        modelAndView.addObject("synchronize_url",synchronize_url);*/

       String intelligentUrl = linkTo(methodOn(SynthesizedController.class).entranceIntelligent("", null)).withSelfRel().getHref();
        modelAndView.addObject("intelligentUrl",intelligentUrl);

        String beingLoadedUrl = linkTo(methodOn(MobileReportController.class).beingLoaded("", null)).withSelfRel().getHref();
        modelAndView.addObject("beingLoadedUrl",beingLoadedUrl);


        String beingWeighedUrl = linkTo(methodOn(SynthesizedController.class).beingWeighed("", null)).withSelfRel().getHref();
        modelAndView.addObject("beingWeighedUrl",beingWeighedUrl);

        generalService.setWebSocketUrl("report",null,modelAndView,"1");
        generalService.setWebSocketUrl("status",null,modelAndView,null);


        StorageSpace storageSpace = storageSpaceRepository.findByNo(no);
        modelAndView.addObject("storageSpace", storageSpace);



        WxPermanentQrcode wxPermanentQrcode = qrcodeService.getCompany();
        modelAndView.addObject("permanentQrcode", wxPermanentQrcode);




        String inventoryUrl = linkTo(methodOn(MobileReportController.class).inventory(storageSpace.getNo(), null)).withSelfRel().getHref();
        modelAndView.addObject("inventoryUrl",inventoryUrl);


        QuotationPlan quotationPlan = quotationPlanRepository.findTop1ByOrderByCreateDateDesc();

        if(quotationPlan!= null && quotationPlan.getStatus().equals(QuotationPlanStatusEnum.ACTIVE.getText())){
            modelAndView.addObject("quotationPlan", quotationPlan);
        }else{
            modelAndView.addObject("quotationPlan", null);
        }



        String follower = linkTo(methodOn(MobileReportController.class).follower(no,null)).withSelfRel().getHref();
        modelAndView.addObject("followerUrl", follower);


        List<Follower> followers = followerRepository.findAll();
        modelAndView.addObject("followers", followers.size());

        String liveInfoUrl = linkTo(methodOn(MobileReportController.class).liveInfo()).withSelfRel().getHref();
        modelAndView.addObject("liveInfoUrl", liveInfoUrl);
        String pie_inventoryDataUrl = linkTo(methodOn(MobileReportController.class).pie_inventoryDataUrl(storageSpace.getNo())).withSelfRel().getHref();
        modelAndView.addObject("pie_inventoryDataUrl", pie_inventoryDataUrl);
        String getInventoryUrl = linkTo(methodOn(MobileReportController.class).getHighChartClumeInventory(storageSpace.getNo())).withSelfRel().getHref();
        modelAndView.addObject("getInventoryUrl", getInventoryUrl);


        String trendencyUrl = linkTo(methodOn(MobileReportController.class).trendency()).withSelfRel().getHref();
        modelAndView.addObject("trendencyUrl", trendencyUrl);

        String updownUrl = linkTo(methodOn(MobileReportController.class).updown()).withSelfRel().getHref();
        modelAndView.addObject("updownUrl", updownUrl);

        String updownUpdate = linkTo(methodOn(MobileReportController.class).updownUpdate()).withSelfRel().getHref();
        modelAndView.addObject("updownUpdateUrl", updownUpdate);


        return modelAndView;




    }
    @RequestMapping(value = "beingLoaded", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> beingLoaded(@RequestParam(value = "q", required = false) String searchTerm, @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable)  {



        return behaviouralService.queryBeingLoaded(null, pageable);
    }



    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(Authentication authentication)  {


        ModelAndView modelAndView = new ModelAndView("/report_all");


        String inventoryUrl = linkTo(methodOn(MobileReportController.class).products("", null)).withSelfRel().getHref();
        modelAndView.addObject("inventoryUrl",inventoryUrl);

        try {
            generalService.setGeneral(modelAndView, "", authentication);
        } catch (Exception e) {
            e.printStackTrace();
        }

/*


        String command_create_url = linkTo(methodOn(MobileReportController.class).create(null, null)).withSelfRel().getHref();
        modelAndView.addObject("command_create_url",command_create_url);


        String command_edit_url = linkTo(methodOn(MobileReportController.class).edit(null, null,null)).withSelfRel().getHref();
        modelAndView.addObject("command_edit_url",command_edit_url);*/


/*
        String synchronize_url = linkTo(methodOn(MobileReportController.class).synchronize(null, null,null)).withSelfRel().getHref();
        modelAndView.addObject("synchronize_url",synchronize_url);*/

       // String intelligentUrl = linkTo(methodOn(SynthesizedController.class).intelligent("", null)).withSelfRel().getHref();
        modelAndView.addObject("intelligentUrl","intelligentUrl");

        String beingLoadedUrl = linkTo(methodOn(SynthesizedController.class).beingLoaded("", null)).withSelfRel().getHref();
        modelAndView.addObject("beingLoadedUrl",beingLoadedUrl);


        String beingWeighedUrl = linkTo(methodOn(SynthesizedController.class).beingWeighed("", null)).withSelfRel().getHref();
        modelAndView.addObject("beingWeighedUrl",beingWeighedUrl);

        generalService.setWebSocketUrl("report",null,modelAndView,"1");



        Configuration configuration = configurationService.getConfiguration(ConfigurationServiceImpl.PRODUCER_CONFIGURATION_KEY_qrcodeContent);

        WxPermanentQrcode wxPermanentQrcode = new WxPermanentQrcode();
        if(configuration!= null){
            wxPermanentQrcode.setContent(configuration.getStringValue());
        }

        modelAndView.addObject("permanentQrcode", wxPermanentQrcode);




        String url = linkTo(methodOn(MobileReportController.class).index(null)).withSelfRel().getHref();
        modelAndView.addObject("url", url);


        String storageUrl = linkTo(methodOn(MobileReportController.class).storageSpace(null)).withSelfRel().getHref();
         modelAndView.addObject("storageUrl", storageUrl);

        String eventUrl = linkTo(methodOn(MobileReportController.class).event(null)).withSelfRel().getHref();
        modelAndView.addObject("eventUrl", eventUrl);

       String getInventoryUrl = linkTo(methodOn(MobileReportController.class).getInventory___PRODUCT()).withSelfRel().getHref();
        modelAndView.addObject("getInventoryUrl", getInventoryUrl);



        return modelAndView;


    }

    @RequestMapping(value = "/getInventory___/{no}", method = RequestMethod.GET)
    @ResponseBody
    public Map getHighChartClumeInventory(@PathVariable("no") String no) {
        return getYulinmeiIndexUrlByGranularityByDistrict(no);
    }

    @RequestMapping(value = "/getInventory___PRODUCT", method = RequestMethod.GET)
    @ResponseBody
    public Map getInventory___PRODUCT() {
        Random r2 = new Random();


        Map map = new HashMap<String, String>();

        List<Product> products =  productRepository.findAll();
        //    List<Inventory> inventories =  inventoryRepository.findByStorageId(storageId);

        //List<Inventory> inventories =  inventoryRepository.findByItemIdAndItemTypeAndStatus(storageId, ResourceType.COAL_PRODUCT.getText(), InventoryStatusEnum.OPEN.getText());

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




    @RequestMapping(value = "/liveInfo", method = RequestMethod.GET)
    @ResponseBody
    public Map liveInfo() {
        Map map = new HashMap<String, String>();

        List<Follower> followers = followerRepository.findAll();
        map.put("followers", followers.size());
        return map;
    }




    public Map getYulinmeiIndexUrlByGranularityByDistrict(String storageId) {


        Map map = new HashMap<String, String>();

        List<Inventory> inventories =  inventoryRepository.findByStorageNoAndStatus(storageId,InventoryStatusEnum.OPEN.getText());
        //    List<Inventory> inventories =  inventoryRepository.findByStorageId(storageId);

        //List<Inventory> inventories =  inventoryRepository.findByItemIdAndItemTypeAndStatus(storageId, ResourceType.COAL_PRODUCT.getText(), InventoryStatusEnum.OPEN.getText());

        List<List<Object>> averages = new ArrayList<List< Object>>();





        for (Inventory inventory : inventories) {

            Product product = productRepository.findByNo(inventory.getProductNo());
            List<PriceCategory> priceCategories = priceCategoryRepository.findByProductNo(product.getNo());
            if(priceCategories.size() ==0){
                continue;
            }

            List element = new ArrayList<>();
            element.add(inventory.getCoalType()+"/"+inventory.getGranularity());

            element.add(priceCategories.get(0).getValue());

            element.add(inventory.getQuantityOnHand());
            averages.add(element);

/*            if(inventory.getQuantityOnHand() != null && inventory.getQuote()!= null){

            }*/
        /*    if(inventory.getQuantityOnHand() == null && inventory.getQuote()!= null){
                List element = new ArrayList<>();
                element.add(inventory.getCoalType()+"/"+inventory.getGranularity());
                element.add(priceCategories.get(0).getValue());
                element.add(100);
                averages.add(element);
            }
            if(inventory.getQuantityOnHand() == null && inventory.getQuote()== null){

            }
            if(inventory.getQuantityOnHand() != null && inventory.getQuote()== null){
                List element = new ArrayList<>();
                element.add(inventory.getCoalType()+"/"+inventory.getGranularity());
                element.add(1);
                element.add(inventory.getQuantityOnHand());
                averages.add(element);
            }*/


        }
        //    map.put("ranges", averages);
        map.put("data", averages);
        return map;


    }



    @RequestMapping(value = "/inventories", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> inventory(@RequestParam(value = "q", required = false) String no, @PageableDefault Pageable pageable)  {


        StorageSpace storageSpace = storageSpaceRepository.findByNo(no);
        return inventoryService.queryForReport(storageSpace,pageable);
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



    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody

    public Map create(@Valid TripCreateForm locationCreateForm,
                      Authentication authentication) {

        logger.debug("----- param is  id : {},  price:{}, notificationToIds:{}, returnTo:{}，sendMessageToFollower is:{}", locationCreateForm.toString());

        Map ret = new HashMap<String, String>();
        ret.put("status", false);

     /*   Trip location = tripService.create(locationCreateForm);
        if(location != null){


            ret.put("status", true);
        }

*/
        return ret;

    }


    @RequestMapping(value = "edit", method = RequestMethod.PUT)
    @ResponseBody

    public Map edit(@Valid TripCreateForm locationCreateForm, BindingResult bindingResult,
                    Authentication authentication) {

        logger.debug("----- param is  id : {},  price:{}, notificationToIds:{}, returnTo:{}，sendMessageToFollower is:{}", locationCreateForm.toString());

        Map ret = new HashMap<String, String>();
        ret.put("status", false);

        if(bindingResult.hasErrors()){
            ret.put("message", bindingResult.getAllErrors().toString());
        }
        Inventory location = inventoryService.edit(locationCreateForm);
        if(location != null){


            ret.put("status", true);
        }


        return ret;

    }




    @RequestMapping( value = "/{id}", method = RequestMethod.GET)
    public ModelAndView detail(@PathVariable(value = "id", required = false) Integer index, Authentication authentication) {

        ModelAndView modelAndView = new ModelAndView("/inventory_detail");

        Inventory deliveryOrder = inventoryService.getById(index);


        modelAndView.addObject("deliveryOrderMap",deliveryOrder);

        modelAndView.addObject("deliveryOrder",deliveryOrder);

   /*         WxTemporaryQrcode wxeneral = wxService.getByTransportation_Deliver_order(deliveryOrder, Constants.WX_QRCODE_TYPE_transportOperation_DELIVER_ORDER);

            if(wxeneral !=null){
                deliveryOrderService.generateQrcodeAccessCode(wxeneral,deliveryOrder);
            }*/
        //    modelAndView.addObject("qrcodeUrl",wxeneral.getQrCode());

        String companiesUrl = linkTo(methodOn(MobileReportController.class).InventoryTransfer(deliveryOrder.getId(), null)).withSelfRel().getHref();
        modelAndView.addObject("transferUrl",companiesUrl);

        String priceTransfer = linkTo(methodOn(MobileReportController.class).priceTransfer(deliveryOrder.getId(), null)).withSelfRel().getHref();
        modelAndView.addObject("priceTransferUrl",priceTransfer);


        String addInventoryUrl = linkTo(methodOn(MobileReportController.class).addInventory(deliveryOrder.getId(), null,null)).toUri().toASCIIString();
        modelAndView.addObject("addInventoryUrl",addInventoryUrl);



        String getRequestTradeCountTrend =   linkTo(methodOn(MobileReportController.class).getRequestTradeCountTrend(deliveryOrder.getId())).withSelfRel().getHref();
        modelAndView.addObject("requestTradeCountTrendUrl", getRequestTradeCountTrend);




        return modelAndView;

    }



    @RequestMapping(value = "/InventoryTransfer_list", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> InventoryTransfer(@RequestParam(value = "distributorId", required = false) Integer distributorId, @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC)  Pageable pageable)  {


        Inventory distributor = inventoryService.getById(distributorId);


        Page<Map>  pages = inventoryService.queryInventoryTransfer(distributor, pageable);


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


        Inventory inventory = inventoryService.getInventoryById(id);
        Inventory location = inventoryService.addInventory(inventory, amount);
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


        Inventory distributor = inventoryService.getById(id);
        List<InventoryTransfer> records = inventoryService.getTransfers(distributor);

        List<Map<String,Object>> values = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> averages = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> ranges = new ArrayList<Map<String,Object>>();

        for(InventoryTransfer timeStatisticRecord :records){
            Map<String,Object> element = new HashMap<String,Object>();
            element.put("x",timeStatisticRecord.getCreateDate().toInstant(ZoneOffset.of("+8")).toEpochMilli() );

            element.put("y",timeStatisticRecord.getAmount() );
            averages.add(element);
        }

        map.put("data", averages);
        result.add(map);
        return result;

    }



    @RequestMapping(value = "/priceTransfer", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> priceTransfer(@RequestParam(value = "distributorId", required = false) Integer distributorId, @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC)  Pageable pageable)  {


        Inventory distributor = inventoryService.getById(distributorId);


        Page<Map>  pages = inventoryService.queryInventoryTransfer(distributor, pageable);


        return pages;
    }










    @RequestMapping(value = "synchronize", method = RequestMethod.POST)
    @ResponseBody

    public Map synchronize(@Valid TripCreateForm locationCreateForm, BindingResult bindingResult,Authentication authentication) {

        logger.debug("----- param is  id : {},  price:{}, notificationToIds:{}, returnTo:{}，sendMessageToFollower is:{}", locationCreateForm.toString());

        Map ret = new HashMap<String, String>();
        ret.put("status", false);

        if(bindingResult.hasErrors()){
            ret.put("message", bindingResult.getAllErrors().toString());
        }
        OperationResult location = asynchronousDataSynchronizationService.synchronize();
        if(location != null){
            ret.put("status", true);
        }


        return ret;

    }


    @RequestMapping(value = "storageSpace", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> storageSpace( @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable)  {



        Page<StorageSpace> pages = storageSpaceRepository.findAll(pageable);

        Page<Map> page = pages.map(new Function<StorageSpace, Map>() {
            public Map apply(StorageSpace objectEntity) {

                ObjectMapper m = new ObjectMapper();
                Map<String,Object> mappedObject = m.convertValue(objectEntity,Map.class);

                mappedObject.put("createDate",objectEntity.getCreateDate());

                String url = linkTo(methodOn(MobileStorageController.class).detail(objectEntity.getId(), null,null)).withSelfRel().getHref();

                mappedObject.put("url",url);

                String reportUrl = linkTo(methodOn(MobileReportController.class).index(objectEntity.getNo(),null)).withSelfRel().getHref();

                storageService.liveInfo(objectEntity,mappedObject);

                mappedObject.put("reportUrl",reportUrl);
                return mappedObject;
            }
        });
        return page;
    }



    @RequestMapping(value = "event", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> event( @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable)  {



        Page<Event> pages = eventRepository.findAll(pageable);

        Page<Map> page = pages.map(new Function<Event, Map>() {
            public Map apply(Event objectEntity) {

                ObjectMapper m = new ObjectMapper();
                Map<String,Object> mappedObject = m.convertValue(objectEntity,Map.class);

                mappedObject.put("createDate",objectEntity.getCreateDate());

/*                String url = linkTo(methodOn(MobileStorageController.class).detail(objectEntity.getId(), null,null)).withSelfRel().getHref();

                mappedObject.put("url",url);*/

                return mappedObject;
            }
        });
        return page;
    }










    @RequestMapping(value = "follower", method = RequestMethod.GET)
    @ResponseBody
    public Page<Follower> follower(@RequestParam(value = "q", required = false) String searchTerm, @PageableDefault(sort = "attendTime", direction = Sort.Direction.DESC) Pageable pageable)  {


        Page<Follower> pages = followerRepository.findAll(pageable);


        return pages;


    }









    @RequestMapping(value = "/getReport", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> getReport(@RequestParam(value = "q", required = false) String searchTerm, @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable)  {

        LocalDateTime today = LocalDateTime.now();
        LocalDateTime startDay = today.minusDays(1000);
        return reportService.getReport(startDay, today,pageable);
    }









    @RequestMapping(value = "/statistic", method = RequestMethod.GET)
    public ModelAndView statistic(Authentication authentication)  {


        ModelAndView modelAndView = new ModelAndView("/synthesized_statistic_index");

        modelAndView.addObject("interval",60*60*12);


        try {
            generalService.setGeneral(modelAndView, "", authentication);
        } catch (Exception e) {
            e.printStackTrace();
        }

        generalService.getStatistic(null,modelAndView,1);


        String distributorUrl = linkTo(methodOn(MobileReportController.class).getReport(null, null)).toUri().toASCIIString();
        modelAndView.addObject("distributorUrl",distributorUrl);


        String distributorReconciliationUrl = linkTo(methodOn(MobileReportController.class).shippings_sign_ROLE_Distributor_not_trader( null,null,null)).toUri().toString();
        modelAndView.addObject("inventoryTransferUrl",distributorReconciliationUrl);


/*        String shipmentByProducerUrl = linkTo(methodOn(MobileReportController.class).shippings_by_producer_for_Distributor_no_trader(distributor.getNo(), null,null,null)).withSelfRel().getHref();
        modelAndView.addObject("shipmentByProducerUrl",shipmentByProducerUrl);

        String shipmentByOperatorUrl = linkTo(methodOn(MobileReportController.class).shippings_by_operator_for_distributor_NO_TRADER(distributor.getNo(), null,null,null)).withSelfRel().getHref();
        modelAndView.addObject("shipmentByOperatorUrl",shipmentByOperatorUrl);*/


        String statistic_today_report_url = linkTo(methodOn(MobileReportController.class).statistic_today_report_ROLE_Distributor_not_trader(null,null,null,null)).toUri().toString();
        modelAndView.addObject("statistic_today_report_url",statistic_today_report_url);


        String statisticInventoryUrl = linkTo(methodOn(MobileReportController.class).statisticInventory(null,null,null,null)).toUri().toString();
        modelAndView.addObject("statisticInventoryUrl",statisticInventoryUrl);

        List<Map> dataTime = new ArrayList<>();

        List<LocalDate> localDateTimes = new ArrayList<>();
        LocalDate localDateTime_now = LocalDate.now().atStartOfDay(UTC).toLocalDate();
        for(int i = 0; i<10 ;i++){
            localDateTimes.add(localDateTime_now);
            LocalDate localDateTime = localDateTime_now.minusDays(1);
            localDateTime_now = localDateTime;

        }


        modelAndView.addObject("localDateTimes",localDateTimes);


        return modelAndView;




    }



    @RequestMapping(value = "/pie_inventoryDataUrl/{no}", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> pie_inventoryDataUrl(@PathVariable(value = "no", required = false) String no) {



        List<Inventory> producerDistributorOpenAccounts =  inventoryRepository.findByStorageNoAndStatus(no,InventoryStatusEnum.OPEN.getText());


        List<Map> maps = new ArrayList<>();
        for(Inventory producerDistributorOpenAccount : producerDistributorOpenAccounts){
            Map map = new HashMap();
            map.put("name", producerDistributorOpenAccount.getCoalType()+ producerDistributorOpenAccount.getGranularity());
            map.put("y", producerDistributorOpenAccount.getQuantityOnHand());
            maps.add(map);


        }


        return maps;




    }

























    @RequestMapping(value = "/shipping/shippings_sign_ROLE_Distributor_not_trader", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> shippings_sign_ROLE_Distributor_not_trader( InventoryTransferDto shippingDto, @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable,
                                                                Authentication authentication){
        User user = (User)authentication.getPrincipal();



        Page<InventoryTransfer> shippings = null;
        if(shippingDto.getPeriod()!= null){
            LocalDateTime localDateBegin= shippingDto.getPeriod().atStartOfDay();
            LocalDateTime localDateEnd = shippingDto.getPeriod().plusDays(1).atStartOfDay();
            shippings = inventoryTransferRepository.findByCreateDateBetween(localDateBegin,localDateEnd,pageable);
            System.out.println(" shippingDto.getPeriod()");
        }else{


            shippings = inventoryTransferRepository.findAll(pageable);
            System.out.println(" NO NO NO NO NO NO NO shippingDto.getPeriod()");
        }
        ObjectMapper m = new ObjectMapper();
        Page<Map> page = shippings.map(new Function<InventoryTransfer, Map>() {
            public Map apply(InventoryTransfer objectEntity) {
                Map<String,Object> mappedObject = m.convertValue(objectEntity,Map.class);
/*                String url        =   linkTo(methodOn(MobileInventoryTransferController.class).detail(objectEntity.getId(),null,null)).withSelfRel().getHref();
                mappedObject.put("url", url);*/


/*

                Distributor producerCompany = distributorRepository.findByCompanyNo(objectEntity.getDistributorNo());
                String producerCompanyUrl        =   linkTo(methodOn(MobileDistributorController.class).detail(producerCompany.getCompanyNo(),null)).withSelfRel().getHref();
                mappedObject.put("producerUrl", producerCompanyUrl);
                mappedObject.put("producer", producerCompany.getName());
                mappedObject.put("producerNo", producerCompany.getCompanyNo());

*/

                Distributor distributorCompany = distributorRepository.findByNo(objectEntity.getDistributorNo());
                String distributorCompanyUrl        =   linkTo(methodOn(MobileDistributorController.class).detail(distributorCompany.getNo(),null)).withSelfRel().getHref();
                mappedObject.put("distributorUrl", distributorCompanyUrl);
                mappedObject.put("distributorNo", distributorCompany.getNo());
                mappedObject.put("distributor", distributorCompany.getName());

                StorageSpace storageSpace = storageSpaceRepository.findByNo(objectEntity.getStorageNo());
/*
                String storageUrl        =   linkTo(methodOn(MobileStorageSpaceController.class).storageDetail(storageSpace.getNo(),null)).withSelfRel().getHref();

                mappedObject.put("storageUrl", storageUrl);
                mappedObject.put("storageNo", storageSpace.getNo());
                mappedObject.put("storage", storageSpace.getName());
*/


/*
                Product product = productRepository.findByNo(objectEntity.getProductNo());
                String productUrl  = linkTo(methodOn(MobileSearchController.class).trade_agent_product(product.getNo(),null)).withSelfRel().getHref();
                mappedObject.put("productUrl", productUrl);
                mappedObject.put("productCoalType", product.getCoalType());
                mappedObject.put("productGranularity", product.getGranularity());
                mappedObject.put("productNo", product.getNo());
*/


                mappedObject.put("reconcileStatus", ReconcileStatusEnum.fromString(objectEntity.getReconcileStatus()).getDisplayText());
                mappedObject.put("createDate", objectEntity.getCreateDate());


                return mappedObject;
            }
        });
        return page;
    }







    @RequestMapping(value = "/shipping/statistic_today_report_ROLE_Distributor_not_trader", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> statistic_today_report_ROLE_Distributor_not_trader(@RequestParam(value = "searchText", required = false) String searchText, InventoryTransferDto shippingDto, @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable,
                                                                        Authentication authentication){
        User user = (User)authentication.getPrincipal();
        List<Map> content = new ArrayList<>();

        System.out.println("=====================================++++++++++++++++++++++++++++"+shippingDto.getPeriod().toString());
        if(shippingDto.getPeriod()!= null){
            LocalDateTime localDateBegin= shippingDto.getPeriod().atStartOfDay();
            LocalDateTime localDateEnd = shippingDto.getPeriod().plusDays(1).atStartOfDay();

            List<InventoryTransfer> shippings = inventoryTransferRepository.findByCreateDateBetween(localDateBegin,localDateEnd);
            System.out.println("=====================================++++++++++++++++++++++++++++"+shippings.toString());



            Map statisticInfo = new HashMap<>();

            BigDecimal amount = new BigDecimal(0);
            BigDecimal quality = new BigDecimal(0);
            BigDecimal taxAmount = new BigDecimal(0);








            Integer count = 0;
            BigDecimal correctCheckedAmount = new BigDecimal(0);
            BigDecimal correctCheckedQuality = new BigDecimal(0);
            BigDecimal correctCheckedTaxAmount = new BigDecimal(0);



            Integer checkedCount = 0;
            BigDecimal checkedWeight = new BigDecimal(0);
            BigDecimal checkedAmount = new BigDecimal(0);
            BigDecimal checkedTaxAmount = new BigDecimal(0);

            Integer unsettledCount = 0;
            BigDecimal unsettledWeight = new BigDecimal(0);



            Integer correctCount = 0;
            BigDecimal correctWeight = new BigDecimal(0);
            BigDecimal correctAmount = new BigDecimal(0);
            BigDecimal correctTaxAmount = new BigDecimal(0);



            for(InventoryTransfer shipping: shippings){
                if(shipping.getStatus().equals(FinancialStatusEnum.checked.getText())){
                    checkedCount++;

                    checkedAmount = checkedAmount.add(shipping.getTaxAmount());
                    checkedTaxAmount = checkedTaxAmount.add(shipping.getTaxAmount());
                    checkedWeight = checkedWeight.add(shipping.getWeight());
                }
                if(shipping.getStatus().equals(FinancialStatusEnum.correct.getText())){
                    correctCount++;

                    correctTaxAmount = correctTaxAmount.add(shipping.getTaxAmount());
                    correctWeight = correctWeight.add(shipping.getWeight());
                    correctAmount = correctAmount.add(shipping.getAmount());


                }
                if(shipping.getStatus().equals(FinancialStatusEnum.unsettled.getText())){
                    count++;
                    unsettledCount++;
                    unsettledWeight = unsettledWeight.add(shipping.getWeight());
                }
    /*            amount = amount.add(shipping.getAmount());
                quality = quality.add(shipping.getWeight());
                taxAmount = taxAmount.add(shipping.getTaxAmount());*/

            }

            statisticInfo = new HashMap<>();
            statisticInfo.put("name", "发货车辆统计");
            statisticInfo.put("total", checkedCount+correctCount+unsettledCount);
            statisticInfo.put("checked", checkedCount);
            statisticInfo.put("correct", correctCount);
            statisticInfo.put("unsettled", unsettledCount);


            content.add(statisticInfo);


            statisticInfo = new HashMap<>();
            statisticInfo.put("name", "合计总金额amount");
            statisticInfo.put("total", checkedAmount.add(correctAmount));
            statisticInfo.put("checked", checkedAmount);
            statisticInfo.put("correct", correctAmount);
            statisticInfo.put("unsettled", "?");

            content.add(statisticInfo);


            statisticInfo = new HashMap<>();
            statisticInfo.put("name", "合计开票金额TaxAmount");
            statisticInfo.put("total", checkedTaxAmount.add(correctTaxAmount));
            statisticInfo.put("correct", correctTaxAmount);
            statisticInfo.put("checked", checkedTaxAmount);
            statisticInfo.put("unsettled", "?");

            content.add(statisticInfo);


            statisticInfo = new HashMap<>();
            statisticInfo.put("name", "全部数量quality");
            statisticInfo.put("total", checkedWeight.add(correctWeight).add(unsettledWeight));
            statisticInfo.put("correct", correctWeight);
            statisticInfo.put("checked", checkedWeight);
            statisticInfo.put("unsettled", unsettledWeight);
            content.add(statisticInfo);


        }

        Page<Map> pages = new PageImpl<Map>(content, pageable, content.size());
        return pages;

    }















    @RequestMapping(value = "/shipping/statisticInventory", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> statisticInventory(@RequestParam(value = "searchText", required = false) String searchText, InventoryTransferDto shippingDto, @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable,
                                                                     Authentication authentication){
        User user = (User)authentication.getPrincipal();
        Page<InventoryTransfer> shippings = null;
        if(shippingDto.getPeriod()!= null){
            LocalDateTime  localDateBegin= shippingDto.getPeriod().atStartOfDay();
            LocalDateTime localDateEnd = shippingDto.getPeriod().plusDays(1).atStartOfDay();

            shippings = inventoryTransferRepository.findByCreateDateBetween(localDateBegin,localDateEnd,pageable);

            System.out.println(" shippingDto.getPeriod()");
        }else{
            shippings = inventoryTransferRepository.findAll(pageable);
            System.out.println(" NO NO NO NO NO NO NO shippingDto.getPeriod()");
        }
        Multimap<String, InventoryTransfer> purchaser_groups = ArrayListMultimap.create();

        BigDecimal totalValue = new BigDecimal(0);
        //BigDecimal totalServiceCharge = new BigDecimal(0);
        //BigDecimal totalDiscount = new BigDecimal(0);
        BigDecimal totalQuantity = new BigDecimal(0);
        BigDecimal commodityPrices = new BigDecimal(0);
        BigDecimal totalTaxAmount = new BigDecimal(0);

        for(InventoryTransfer shipping:shippings){

            //totalServiceCharge = totalServiceCharge.add(shipping.getServiceCharge());
            //totalDiscount = totalDiscount.add(shipping.getDiscount());

            totalQuantity = totalQuantity.add(shipping.getWeight());
            if(shipping.getUnitPrice()!= null){
                commodityPrices = commodityPrices.add(shipping.getWeight().multiply(shipping.getUnitPrice()));
            }

            //totalTaxAmount = totalTaxAmount.add(shipping.getTaxAmount());
            purchaser_groups.put(shipping.getInventoryNo(),shipping);
        }

        List<Map> purchaser_statistic = new ArrayList<>();
        for(String operatorNo : purchaser_groups.keySet()) {

            Map distributor_map = new HashMap<>();
            Collection<InventoryTransfer>  inventoryTransfers_for_distributor = purchaser_groups.get(operatorNo);


            Inventory employee = inventoryRepository.findByNo(operatorNo);

            BigDecimal totalQuantity_ = inventoryTransfers_for_distributor.stream().map(e->e.getWeight()).reduce(new BigDecimal(0),(acc, item) -> {
                return acc.add(item);
            });


/*            BigDecimal commodityPrices_ = inventoryTransfers_for_distributor.stream().map(e->e.getWeight().multiply(e.getUnitPrice())).reduce(new BigDecimal(0),(acc, item) -> {
                return acc.add(item);
            });*/


            BigDecimal totalValue_ = new BigDecimal(0);
            //totalValue_ = totalValue_.add(totalServiceCharge_).add(commodityPrices_).subtract(totalDiscount);
            totalValue_ = totalValue_.add(commodityPrices);

            distributor_map.put("inventoryNo",employee.getNo());
            distributor_map.put("coalType",employee.getCoalType());
            distributor_map.put("granularity",employee.getGranularity());
            String producerUrl = linkTo(methodOn(MobileInventoryController.class).detail(employee.getNo(), null)).withSelfRel().getHref();
            distributor_map.put("purchaserUrl",producerUrl);


            distributor_map.put("totalAmount",totalValue_);
/*            distributor_map.put("totalServiceCharge",totalServiceCharge_);
            distributor_map.put("totalDiscount",totalDiscount_);*/
            distributor_map.put("totalQuantity",totalQuantity_);
            distributor_map.put("totalCount", inventoryTransfers_for_distributor.size());
            distributor_map.put("commodityPrices", commodityPrices);
            purchaser_statistic.add(distributor_map);
        }

        Page<Map> pages = new PageImpl<Map>(purchaser_statistic, pageable, purchaser_statistic.size());

        return pages;
    }





    @RequestMapping(value = "/trendency", method = RequestMethod.GET)
    @ResponseBody
    public Object trendency()  {




        List series = new ArrayList<>();


        List<PriceCategory> coalSupplies = priceCategoryRepository.findAll();

        for(PriceCategory priceCategory : coalSupplies){

            Product product = productRepository.findByUuid(priceCategory.getObjectUuid());
            logger.debug("产品：{}",product.getCoalType() + product.getGranularity());
            List<ArrayList<Object>> trends = new ArrayList<>();


            List<DevelopmentTrend> developmentTrends = developmentTrendRepository.findByPriceCategoryUuid(priceCategory.getUuid());

            logger.debug("存在价格 类型：");
            for(DevelopmentTrend developmentTrend :developmentTrends){
                logger.debug("price 变化{}",developmentTrend.getPrice());
                ArrayList<Object> element = new ArrayList<Object>();



                element.add(java.util.Date
                        .from(developmentTrend.getChangeDate().atZone(ZoneId.systemDefault())
                                .toInstant()).getTime());
               // element.add(developmentTrend.getChangeDate().atZone(ZoneId.of("UTC")).toEpochSecond());
                element.add(developmentTrend.getPrice());
                trends.add(element);
            }

            String p = PriceCategoryTypeEnum.fromString(priceCategory.getName()).getDisplayText();


            Map serie = new LinkedHashMap<>();

            serie.put("name", product.getCoalType() + product.getGranularity()+p);
          //  serie.put("dashStyle", "LongDashDot");
            serie.put("data", trends);
            series.add(serie);




        }

        return series;
    }

    Integer count = 0;
    @RequestMapping(value = "/updown", method = RequestMethod.GET)
    @ResponseBody
    public Object updown()  {




        List series = new ArrayList<>();

        {
            List<ArrayList> trends = new LinkedList<>();



            {
                ArrayList element = new ArrayList<Object>();

                element.add(java.util.Date
                        .from(LocalDateTime.now().minusHours(1).atZone(ZoneId.systemDefault())
                                .toInstant()).getTime());
                element.add(4);
                trends.add(element);
            }
            {
                ArrayList element = new ArrayList<Object>();

                element.add(java.util.Date
                        .from(LocalDateTime.now().minusHours(2).atZone(ZoneId.systemDefault())
                                .toInstant()).getTime());
                element.add(2);
                trends.add(element);
            }
            Map serie = new LinkedHashMap<>();
            serie.put("name", "涨价");
            serie.put("data", trends);
            series.add(serie);
        }



        {
            List<ArrayList> trends = new LinkedList<>();
            {
                ArrayList element = new ArrayList<Object>();
                element.add(java.util.Date
                        .from(LocalDateTime.now().minusHours(1).atZone(ZoneId.systemDefault())
                                .toInstant()).getTime());
                element.add(-5);
                trends.add(element);
            }
            {
                ArrayList<Object> element = new ArrayList<Object>();

                element.add(java.util.Date
                        .from(LocalDateTime.now().minusHours(2).atZone(ZoneId.systemDefault())
                                .toInstant()).getTime());
                element.add(-2);
                trends.add(element);
            }


            Map serie = new LinkedHashMap<>();
            serie.put("name", "跌价");
            serie.put("data", trends);
            series.add(serie);
        }


        return series;
    }




    ArrayList preElement = new ArrayList<Object>();

    @RequestMapping(value = "/updownUpdate", method = RequestMethod.GET)
    @ResponseBody
    public Object updownUpdate()  {




        List series = new ArrayList<>();

        {
            List<ArrayList> trends = new LinkedList<>();



            {


                ArrayList element = new ArrayList<Object>();

                element.add(java.util.Date
                        .from(LocalDateTime.now().plusHours(count).atZone(ZoneId.systemDefault())
                                .toInstant()).getTime());
                element.add(4+count);
                preElement = element;
                trends.add(element);
            }

            Map serie = new LinkedHashMap<>();
            serie.put("name", "涨价");
            serie.put("data", trends);
            serie.put("start", preElement);
            series.add(serie);
            count++;
        }



     /*   {
            List<ArrayList> trends = new LinkedList<>();
            {
                ArrayList element = new ArrayList<Object>();
                element.add(java.util.Date
                        .from(LocalDateTime.now().minusHours(1).atZone(ZoneId.systemDefault())
                                .toInstant()).getTime());
                element.add(-5);
                trends.add(element);
            }



            Map serie = new LinkedHashMap<>();
            serie.put("name", "跌价");
            serie.put("data", trends);
            series.add(serie);
        }
*/

        return series;
    }
}
