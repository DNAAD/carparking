package com.coalvalue.web;

import com.coalvalue.domain.OperationResult;

import com.coalvalue.domain.entity.Inventory;
import com.coalvalue.domain.entity.InventoryTransfer;
import com.coalvalue.domain.entity.PriceCategory;
import com.coalvalue.domain.entity.Product;
import com.coalvalue.repository.InventoryRepository;
import com.coalvalue.repository.PriceCategoryRepository;
import com.coalvalue.repository.ProductRepository;
import com.coalvalue.service.*;
import com.coalvalue.service.other.GeneralServiceImpl;
import com.coalvalue.web.valid.ProductCreateForm;
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
import java.time.ZoneOffset;
import java.util.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

//import org.springframework.security.core.Authentication;

//import com.coalvalue.repositorySecondary.ProductRepository;

/**
 * Created by silence yuan on 2015/7/12.
 */

@Controller
@RequestMapping(value= {"/usercenter/inventory"})
public class MobileInventoryController {
    private static final Logger logger = LoggerFactory.getLogger(MobileInventoryController.class);

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PriceCategoryRepository priceCategoryRepository;


    @Autowired
    private StorageService storageService;

    @Autowired
    private GeneralServiceImpl generalService;
    @Autowired
    private AsynchronousDataSynchronizationService asynchronousDataSynchronizationService;



    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView index
            (@RequestParam(value = "q", required = false) String searchTerm,Authentication authentication)  {


        ModelAndView modelAndView = new ModelAndView("/inventory_index");
        modelAndView.addObject("q",searchTerm);

        String companiesUrl = linkTo(methodOn(MobileInventoryController.class).stations("", null)).withSelfRel().getHref();
        modelAndView.addObject("stationsUrl",companiesUrl);

        String productUrl = linkTo(methodOn(MobileInventoryController.class).product("", null)).withSelfRel().getHref();
        modelAndView.addObject("productUrl",productUrl);


        String priceCategoryUrl = linkTo(methodOn(MobileInventoryController.class).priceCategory("", null)).withSelfRel().getHref();
        modelAndView.addObject("priceCategoryUrl",priceCategoryUrl);
        try {
            generalService.setGeneral(modelAndView, "", authentication);
        } catch (Exception e) {
            e.printStackTrace();
        }


        String command_create_url = linkTo(methodOn(MobileInventoryController.class).create(null, null)).withSelfRel().getHref();
        modelAndView.addObject("command_create_url",command_create_url);


        String command_edit_url = linkTo(methodOn(MobileInventoryController.class).edit(null, null,null)).withSelfRel().getHref();
        modelAndView.addObject("command_edit_url",command_edit_url);
        String commandProductEditUrl = linkTo(methodOn(MobileInventoryController.class).edit(null, null,null)).withSelfRel().getHref();
        modelAndView.addObject("commandProductEditUrl",commandProductEditUrl);


        String synchronize_url = linkTo(methodOn(MobileInventoryController.class).synchronize(null, null,null)).withSelfRel().getHref();
        modelAndView.addObject("synchronize_url",synchronize_url);
        modelAndView.addObject("storages", storageService.getAll());
        return modelAndView;
    }




    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> stations(@RequestParam(value = "q", required = false) String searchTerm, @PageableDefault Pageable pageable)  {



        return inventoryService.query(null,pageable);
    }


    @RequestMapping(value = "/product", method = RequestMethod.GET)
    @ResponseBody
    public Page<Product> product(@RequestParam(value = "q", required = false) String searchTerm, @PageableDefault Pageable pageable)  {


        Page<Product> products = productRepository.findAll(pageable);

        return products;
    }
    @RequestMapping(value = "/priceCategory", method = RequestMethod.GET)
    @ResponseBody
    public Page<PriceCategory> priceCategory(@RequestParam(value = "q", required = false) String searchTerm, @PageableDefault Pageable pageable)  {


        Page<PriceCategory> products = priceCategoryRepository.findAll(pageable);

        return products;
    }


    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody

    public Map create(@Valid TripCreateForm locationCreateForm,
                      Authentication authentication) {

        logger.debug("----- param is  id : {},  price:{}, notificationToIds:{}, returnTo:{}，sendMessageToFollower is:{}", locationCreateForm.toString());

        Map ret = new HashMap<String, String>();
        ret.put("status", false);

/*
        Trip location = tripService.create(locationCreateForm);
        if(location != null){


            ret.put("status", true);
        }
*/


        return ret;

    }



    @RequestMapping(value = "/commandProductEdit", method = RequestMethod.PUT)
    @ResponseBody

    public Map commandProductEdit(@Valid ProductCreateForm locationCreateForm, BindingResult bindingResult,
                                  Authentication authentication) {

        logger.debug("----- param is  id : {},  price:{}, notificationToIds:{}, returnTo:{}，sendMessageToFollower is:{}", locationCreateForm.toString());

        Map ret = new HashMap<String, String>();
        ret.put("status", false);

        if(bindingResult.hasErrors()){
            ret.put("message", bindingResult.getAllErrors().toString());
        }
        Product location = inventoryService.commandProductEdit(locationCreateForm);
        if(location != null){


            ret.put("status", true);
        }


        return ret;

    }

    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
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
    public ModelAndView detail(@PathVariable(value = "id", required = false) String index, Authentication authentication) {

        ModelAndView modelAndView = new ModelAndView("/inventory_detail");

        Inventory deliveryOrder = inventoryRepository.findByNo(index);


        modelAndView.addObject("deliveryOrderMap",deliveryOrder);

        modelAndView.addObject("deliveryOrder",deliveryOrder);

   /*         WxTemporaryQrcode wxeneral = wxService.getByTransportation_Deliver_order(deliveryOrder, Constants.WX_QRCODE_TYPE_transportOperation_DELIVER_ORDER);

            if(wxeneral !=null){
                deliveryOrderService.generateQrcodeAccessCode(wxeneral,deliveryOrder);
            }*/
        //    modelAndView.addObject("qrcodeUrl",wxeneral.getQrCode());

        String companiesUrl = linkTo(methodOn(MobileInventoryController.class).InventoryTransfer(deliveryOrder.getId(), null)).withSelfRel().getHref();
        modelAndView.addObject("transferUrl",companiesUrl);

        String priceTransfer = linkTo(methodOn(MobileInventoryController.class).priceTransfer(deliveryOrder.getId(), null)).withSelfRel().getHref();
        modelAndView.addObject("priceTransferUrl",priceTransfer);


        String addInventoryUrl = linkTo(methodOn(MobileInventoryController.class).addInventory(deliveryOrder.getId(), null,null)).toUri().toASCIIString();
        modelAndView.addObject("addInventoryUrl",addInventoryUrl);



        String getRequestTradeCountTrend =   linkTo(methodOn(MobileInventoryController.class).getRequestTradeCountTrend(deliveryOrder.getId())).withSelfRel().getHref();
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
}
