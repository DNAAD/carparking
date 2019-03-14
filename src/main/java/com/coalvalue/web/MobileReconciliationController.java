package com.coalvalue.web;

import com.coalvalue.domain.OperationResult;
import com.coalvalue.domain.entity.Inventory;
import com.coalvalue.domain.entity.InventoryTransfer;
import com.coalvalue.domain.entity.Reconciliation;
import com.coalvalue.dto.InventoryTransferDto;
import com.coalvalue.enumType.ReconcileStatusEnum;
import com.coalvalue.repository.ReconciliationRepository;
import com.coalvalue.service.*;
import com.coalvalue.service.other.GeneralServiceImpl;
import com.coalvalue.web.valid.TripCreateForm;
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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
@RequestMapping(value= {"/usercenter/reconciliation"})
public class MobileReconciliationController {
    private static final Logger logger = LoggerFactory.getLogger(MobileReconciliationController.class);

    @Autowired
    private InventoryService inventoryService;


    @Autowired
    private ReconciliationRepository reconciliationRepository;


    @Autowired
    private ReconciliationService reconciliationService;

    @Autowired
    private ReportService reportService;


    @Autowired
    private DistributorService distributorService;

    @Autowired
    private GeneralServiceImpl generalService;
    @Autowired
    private AsynchronousDataSynchronizationService asynchronousDataSynchronizationService;

    @Autowired
    private MqttService mqttService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(@RequestParam(value = "q", required = false) String searchTerm,Authentication authentication)  {


        ModelAndView modelAndView = new ModelAndView("/reconciliation_index");
        modelAndView.addObject("q",searchTerm);

        String companiesUrl = linkTo(methodOn(MobileInstanceTransprotController.class).transports("", null)).withSelfRel().getHref();
        modelAndView.addObject("transportUrl",companiesUrl);
        try {
            generalService.setGeneral(modelAndView, "", authentication);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String createTransferUrl = linkTo(methodOn(MobileReconciliationController.class).createReconciliation(null, null)).toUri().toASCIIString();

        modelAndView.addObject("createTransferUrl",createTransferUrl);


        String transferUrl = linkTo(methodOn(MobileReconciliationController.class).transfer("", null)).withSelfRel().getHref();
        modelAndView.addObject("transferUrl",transferUrl);


        String reconciliationUrl = linkTo(methodOn(MobileReconciliationController.class).reconciliation("", null)).withSelfRel().getHref();
        modelAndView.addObject("reconciliationUrl",reconciliationUrl);
        String reconciliation_confirmed = linkTo(methodOn(MobileReconciliationController.class).reconciliation_confirmed("", null)).withSelfRel().getHref();
        modelAndView.addObject("reconciliation_confirmedUrl",reconciliation_confirmed);

        List<Map> distributors = distributorService.getEnumAll();

        modelAndView.addObject("distributors",distributors);


        modelAndView.addObject("eeconcileStatusEnums", ReconcileStatusEnum.retriveTypese());

        return modelAndView;


    }

    @RequestMapping(value = "/transport_list", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> transfer(@RequestParam(value = "q", required = false) String searchTerm, @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable)  {

        InventoryTransferDto deliveryOrderDto = new InventoryTransferDto();
        deliveryOrderDto.setSearchText(searchTerm);
        deliveryOrderDto.setReconcileStatus(ReconcileStatusEnum.NoReconciliation.getText());


        return inventoryService.queryTransfer(deliveryOrderDto, pageable);


    }

    @RequestMapping(value = "/reconciliation_list", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> reconciliation(@RequestParam(value = "q", required = false) String searchTerm, @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable)  {

        return reconciliationService.findAll(null, pageable);


    }

    @RequestMapping(value = "/reconciliation_confirmed", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> reconciliation_confirmed(@RequestParam(value = "q", required = false) String searchTerm, @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable)  {

        return reconciliationService.findAllReconciliation_confirmed(null, pageable);


    }

    @RequestMapping(value = "inventories", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> stations(@RequestParam(value = "q", required = false) String searchTerm, @PageableDefault Pageable pageable)  {



        return inventoryService.query(null,pageable);
    }





    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody

    public Map createReconciliation(@Valid TripCreateForm locationCreateForm,
                                    Authentication authentication) {

        logger.debug("----- param is  id : {},  price:{}, notificationToIds:{}, returnTo:{}，sendMessageToFollower is:{}", locationCreateForm.toString());

        Map ret = new HashMap<String, String>();
        ret.put("status", false);

        LocalDateTime today = LocalDateTime.now();
        LocalDateTime endDay = today.minusDays(1000);
        OperationResult location = reportService.createReconciliation(endDay,today);
        if(location.isSuccess()){
            ret.put("status", true);
        }



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

        ModelAndView modelAndView = new ModelAndView("/reconciliation_detail");

        Reconciliation reconciliation = reconciliationService.getById(index);


        modelAndView.addObject("deliveryOrderMap",reconciliation);

        modelAndView.addObject("deliveryOrder",reconciliation);


        String reconciliationItemsUrl = linkTo(methodOn(MobileReconciliationController.class).reconciliationItems(reconciliation.getId(), null)).withSelfRel().getHref();
        modelAndView.addObject("reconciliationItemsUrl", reconciliationItemsUrl);

        String priceTransfer = linkTo(methodOn(MobileReconciliationController.class).priceTransfer(reconciliation.getId(), null)).withSelfRel().getHref();
        modelAndView.addObject("priceTransferUrl",priceTransfer);
        String statisticUrl = linkTo(methodOn(MobileReconciliationController.class).statistic(reconciliation.getId(), null)).withSelfRel().getHref();
        modelAndView.addObject("statisticUrl", statisticUrl);

        String addInventoryUrl = linkTo(methodOn(MobileReconciliationController.class).addInventory(reconciliation.getId(), null,null)).toUri().toASCIIString();
        modelAndView.addObject("addInventoryUrl",addInventoryUrl);



        String getRequestTradeCountTrend =   linkTo(methodOn(MobileReconciliationController.class).getRequestTradeCountTrend(reconciliation.getId())).withSelfRel().getHref();
        modelAndView.addObject("requestTradeCountTrendUrl", getRequestTradeCountTrend);






        return modelAndView;

    }



    @RequestMapping(value = "/InventoryTransfer_list", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> reconciliationItems(@RequestParam(value = "distributorId", required = false) Integer distributorId, @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable)  {

        Reconciliation distributor = reconciliationService.getById(distributorId);
        Page<Map>  pages = reconciliationService.queryreconciliationItems(distributor, pageable);


        return pages;
    }
    @RequestMapping(value = "/statistic", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> statistic(@RequestParam(value = "distributorId", required = false) Integer distributorId, @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable)  {
        Reconciliation distributor = reconciliationService.getById(distributorId);
        Page<Map>  pages = reconciliationService.queryStatistic(distributor, pageable);
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
            element.put("x",timeStatisticRecord.getCreateDate().toInstant(ZoneOffset.of("+8")).toEpochMilli()  );

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
}
