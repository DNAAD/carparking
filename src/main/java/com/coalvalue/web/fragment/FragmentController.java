package com.coalvalue.web.fragment;

import com.coalvalue.domain.entity.*;
import com.coalvalue.enumType.FeesTypeEnum;
import com.coalvalue.enumType.PriceCategoryStatusEnum;
import com.coalvalue.enumType.PriceCategoryTypeEnum;
import com.coalvalue.repository.*;
import com.coalvalue.service.*;
import com.coalvalue.task.LiveBroadcast;
import com.coalvalue.web.MobileRegisterController;
import com.coalvalue.web.SynthesizedController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

//import org.springframework.security.core.Authentication;

//import com.coalvalue.repositorySecondary.ProductRepository;

/**
 * Created by silence yuan on 2015/7/12.
 */

@Controller
@RequestMapping(value= {"/fragment"})
public class FragmentController {
    private static final Logger logger = LoggerFactory.getLogger(FragmentController.class);


    @Autowired
    private ReportDeliveryOrderRepository reportDeliveryOrderRepository;

    @Autowired
    private InstanceTransportRepository instanceTransportRepository;

    @Autowired
    private FeeRepository feeRepository;
    @Autowired
    private InventoryTransferRepository inventoryTransferRepository;


    @Autowired
    private LiveBroadcast liveBroadcast;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StorageSpaceRepository storageSpaceRepository;
    @Autowired
    private DistributorRepository distributorRepository;


    @Autowired
    private PriceCategoryRepository priceCategoryRepository;


    @RequestMapping(value = "/fragment", method = RequestMethod.GET)
    public ModelAndView fragment() {//,Authentication authentication)  {
        //  mqttService.sendNetworkStatus("OK");

        ModelAndView modelAndView = new ModelAndView("/fragment/login");
        String instanceTransport_loaded_Url = linkTo(methodOn(MobileRegisterController.class).login(null)).toUri().toASCIIString();
        modelAndView.addObject("loginUrl", instanceTransport_loaded_Url);
        return modelAndView;
    }

    @RequestMapping(value = "/fragmentDeliveryReportInfo", method = RequestMethod.GET)
    public ModelAndView fragmentDeliveryReportInfo(@RequestParam(value = "uuid", required = false) String uuid ) {//,Authentication authentication)  {
        //  mqttService.sendNetworkStatus("OK");

        ReportDeliveryOrder reportDeliveryOrder = reportDeliveryOrderRepository.findByUuid(uuid);
        ModelAndView modelAndView = new ModelAndView("/fragment/delivery_report_info");

        Inventory inventory = inventoryRepository.findByNo(reportDeliveryOrder.getInventoryNo());

        Product product = productRepository.findByNo(reportDeliveryOrder.getProductNo());
      LiveInforInventory liveInforInventory = liveBroadcast.getLiveBroadcast(inventory);

        Distributor distributor = distributorRepository.findByNo(reportDeliveryOrder.getDistributorNo());

        StorageSpace storageSpace = storageSpaceRepository.findByNo(reportDeliveryOrder.getStorageNo());
        modelAndView.addObject("inventory", inventory);
        modelAndView.addObject("liveInforInventory", new LiveInforInventory());//liveInforInventory);
        modelAndView.addObject("product", product);
        modelAndView.addObject("storageSpace", storageSpace);
        modelAndView.addObject("distributor", distributor);

        List<Fee> feees = feeRepository.findByDistributorNo(reportDeliveryOrder.getDistributorNo());


        List<PriceCategory> priceCategories = priceCategoryRepository.findByProductNoOrderBySeqAsc(reportDeliveryOrder.getProductNo());

        String s = priceCategories.stream().map(e->PriceCategoryTypeEnum.fromString(e.getName()).getDisplayText() + "/"+ e.getValue()+" //").collect(Collectors.toList()).toString();
        modelAndView.addObject("priceCategory", s);
        modelAndView.addObject("fees", feees);

        String createTransferUrl = linkTo(methodOn(SynthesizedController.class).createInstanceTransport(null, null)).toUri().toASCIIString();
        modelAndView.addObject("createInstanceUrl", createTransferUrl);



        modelAndView.addObject("feesTypeEnumes", FeesTypeEnum.retriveTypese());
        modelAndView.addObject("reportDeliveryOrder", reportDeliveryOrder);

        return modelAndView;
    }


    @RequestMapping(value = "/delivery_report_info_netweight", method = RequestMethod.GET)
    public ModelAndView delivery_report_info_netweight(@RequestParam(value = "uuid", required = false) String uuid ) {//,Authentication authentication)  {
        //  mqttService.sendNetworkStatus("OK");

        InstanceTransport instanceTransport = instanceTransportRepository.findByUuid(uuid);
        ModelAndView modelAndView = new ModelAndView("/fragment/delivery_report_info_netweight");

        ReportDeliveryOrder reportDeliveryOrder = reportDeliveryOrderRepository.findByNo(instanceTransport.getDeliveryOrderNo());

        Inventory inventory = inventoryRepository.findByNo(reportDeliveryOrder.getInventoryNo());

        Product product = productRepository.findByNo(reportDeliveryOrder.getProductNo());
        LiveInforInventory liveInforInventory = liveBroadcast.getLiveBroadcast(inventory);

        Distributor distributor = distributorRepository.findByNo(reportDeliveryOrder.getDistributorNo());

        StorageSpace storageSpace = storageSpaceRepository.findByNo(reportDeliveryOrder.getStorageNo());
        modelAndView.addObject("inventory", inventory);
        modelAndView.addObject("liveInforInventory", liveInforInventory);
        modelAndView.addObject("product", product);
        modelAndView.addObject("storageSpace", storageSpace);
        modelAndView.addObject("distributor", distributor);


        List<Fee> feees = feeRepository.findByDistributorNo(reportDeliveryOrder.getDistributorNo());


        List<PriceCategory> priceCategories = priceCategoryRepository.findByProductNoOrderBySeqAsc(reportDeliveryOrder.getProductNo());



        modelAndView.addObject("priceCategories", priceCategories);
        modelAndView.addObject("fees", feees);

        String createTransferUrl = linkTo(methodOn(SynthesizedController.class).createTransfer(instanceTransport.getUuid(), null, null)).toUri().toASCIIString();
        modelAndView.addObject("createTransferUrl", createTransferUrl);

        modelAndView.addObject("feesTypeEnumes", FeesTypeEnum.retriveTypese());
        modelAndView.addObject("instanceTransport", instanceTransport);
        modelAndView.addObject("reportDeliveryOrder", reportDeliveryOrder);
        return modelAndView;
    }






    @GetMapping("/inventory_transfer_correct")
    public ModelAndView inventory_transfer_correct(@RequestParam(value = "uuid", required = false) String uuid ) {//,Authentication authentication)  {

        InventoryTransfer inventoryTransfer = inventoryTransferRepository.findByUuid(uuid);
        ModelAndView modelAndView = new ModelAndView("/fragment/inventory_transfer_correct");

        ReportDeliveryOrder reportDeliveryOrder = reportDeliveryOrderRepository.findByNo(inventoryTransfer.getDeliveryOrderNo());

        List<Fee> feees = feeRepository.findByDistributorNo(reportDeliveryOrder.getDistributorNo());


        List<PriceCategory> priceCategories = priceCategoryRepository.findByProductNoOrderBySeqAsc(reportDeliveryOrder.getProductNo());

        modelAndView.addObject("priceCategories", priceCategories);
        modelAndView.addObject("fees", feees);

        String createTransferUrl = linkTo(methodOn(SynthesizedController.class).createTransfer(inventoryTransfer.getUuid(), null, null)).toUri().toASCIIString();
        modelAndView.addObject("createTransferUrl", createTransferUrl);

        modelAndView.addObject("feesTypeEnumes", FeesTypeEnum.retriveTypese());
        modelAndView.addObject("inventoryTransfer", inventoryTransfer);
        modelAndView.addObject("reportDeliveryOrder", reportDeliveryOrder);





        String command_correct_url = linkTo(methodOn(SynthesizedController.class).correctEdit(inventoryTransfer.getUuid(), null,null)).withSelfRel().getHref();
        modelAndView.addObject("command_correct_url", command_correct_url);

        return modelAndView;
    }

}
