package com.coalvalue.web;

import com.coalvalue.domain.entity.Camera;
import com.coalvalue.domain.entity.Distributor;
import com.coalvalue.domain.OperationResult;
import com.coalvalue.domain.entity.Inventory;
import com.coalvalue.domain.entity.InventoryTransfer;
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

import javax.servlet.http.HttpServletResponse;
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
@RequestMapping(value= {"/usercenter/camera"})
public class MobileCameraController {
    private static final Logger logger = LoggerFactory.getLogger(MobileCameraController.class);

    @Autowired
    private InventoryService inventoryService;


    @Autowired
    private ReconciliationRepository reconciliationRepository;


    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private ReportService reportService;


    @Autowired
    private DistributorService distributorService;

    @Autowired
    private GeneralServiceImpl generalService;
    @Autowired
    private AsynchronousDataSynchronizationService asynchronousDataSynchronizationService;

/*    @Autowired
    private CameraService cameraService;*/

    @Autowired
    private ConfigurationService configurationService;



    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(@RequestParam(value = "q", required = false) String searchTerm,Authentication authentication)  throws Exception {


        ModelAndView modelAndView = new ModelAndView("/camera_index");
        modelAndView.addObject("q",searchTerm);

        String cameraUrl = linkTo(methodOn(MobileCameraController.class).camera("", null)).withSelfRel().getHref();
        modelAndView.addObject("cameraUrl",cameraUrl);
        String equipmentUrl = linkTo(methodOn(MobileCameraController.class).equipment("", null)).withSelfRel().getHref();
        modelAndView.addObject("equipmentUrl",equipmentUrl);


        generalService.setGeneral(modelAndView,"",authentication);



        String transferUrl = linkTo(methodOn(MobileCameraController.class).camera("", null)).withSelfRel().getHref();
        modelAndView.addObject("transferUrl",transferUrl);



        String openPlateRecognitionUrl = linkTo(methodOn(MobileCameraController.class).openPlateRecognition(null, null)).withSelfRel().getHref();
        modelAndView.addObject("openPlateRecognitionUrl",openPlateRecognitionUrl);
        List<Distributor> distributors = distributorService.getAll();

        modelAndView.addObject("distributors",distributors);

        return modelAndView;


    }

    @RequestMapping(value = "/transport_list", method = RequestMethod.GET)
    @ResponseBody
    public  Page<Map> camera(@RequestParam(value = "q", required = false) String searchTerm, @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable)  {
        return equipmentService.queryCamera(null, pageable);
    }


    @RequestMapping(value = "/equipment", method = RequestMethod.GET)
    @ResponseBody
    public  Page<Map> equipment(@RequestParam(value = "q", required = false) String searchTerm, @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable)  {
        return equipmentService.queryEquipment(null, pageable);
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

        ModelAndView modelAndView = new ModelAndView("/camera_detail");

        Camera deliveryOrder = null;//cameraService.getById(index);



        modelAndView.addObject("camera",deliveryOrder);

       // String imageUrl = linkTo(methodOn(MobileCameraController.class).voiddoGet(deliveryOrder.getId(), null)).withSelfRel().getHref();
        modelAndView.addObject("imageUrl","/usercenter/camera/images/"+deliveryOrder.getId());


        return modelAndView;

    }

    @RequestMapping(value = "/images/{id}", method = RequestMethod.GET)
    public void voiddoGet(@PathVariable(value = "id", required = false) Integer index,HttpServletResponse response){

        Camera deliveryOrder = null;//cameraService.getById(index);

        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "No-cache");
        response.setDateHeader("Expires", 0);
/*
        try {


            CameraObject cameraObject =cameraService.getImageByCamera(deliveryOrder);
            if(cameraObject.status.equals(CameraStatusEnum.Running.getText())){
                BufferedImage bufferedImage = cameraObject.getBufferedImage();
                if(bufferedImage!= null){
                    System.out.println("=========bufferedImage :{}"+bufferedImage.toString());
                    response.setContentType("image/png");
                    //  ImageIO.write(bufferedImage, "png", response.getOutputStream());
                    ImageIO.write(bufferedImage, "JPEG", response.getOutputStream());
                }else {
                    System.out.println("=========bufferedImage :NULL NULL");
                }
            }



        } catch (Exception ex) {
            logger.debug("send the verify code and image failed********");
            ex.printStackTrace();
        }*/

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


    @RequestMapping(value = "openPlateRecognition", method = RequestMethod.POST)
    @ResponseBody

    public Map openPlateRecognition(@Valid TripCreateForm locationCreateForm,
                      Authentication authentication) {

        logger.debug("----- param is  id : {},  price:{}, notificationToIds:{}, returnTo:{}，sendMessageToFollower is:{}", locationCreateForm.toString());

        Map ret = new HashMap<String, String>();
        ret.put("status", false);

        LocalDateTime today = LocalDateTime.now();
        LocalDateTime endDay = today.minusDays(1000);
        OperationResult location = configurationService.openPlateRecognition();
        if(location.isSuccess()){


            ret.put("status", true);
        }



        return ret;

    }

}
