package com.coalvalue.service;


import com.alibaba.fastjson.JSON;
import com.coalvalue.configuration.CommonConstant;
import com.coalvalue.domain.entity.*;
import com.coalvalue.notification.NotificationData;
import com.coalvalue.repository.*;
import com.coalvalue.web.MobileDeliveryOrderController;
import com.coalvalue.web.MobileInstanceTransprotController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by silence yuan on 2015/7/25.
 */

@Service("behaviouralService")
public class BehaviouralServiceImpl extends BaseServiceImpl implements BehaviouralService {

    @Autowired
    private BehaviouralRepository behaviouralRepository;

    @Autowired
    private DeliveryOrderService deliveryOrderService;

    @Autowired
    private IDIdentificationRepository idIdentificationRepository;

    @Autowired
    private PlateRecognitionRepository plateRecognitionRepository;


    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private InstanceTransportService instanceTransportService;
    @Autowired
    private ReportDeliveryOrderRepository reportDeliveryOrderRepository;
    @Autowired
    private InstanceTransportRepository instanceTransportRepository;


    @Override
    @Transactional
    public void create(NotificationData data) {

        Behavioural behavioural = new Behavioural();
        behavioural = behaviouralRepository.save(behavioural);

    }

    @Override
    public void analyis(PlateRecognition plateRecognition) {
        ReportDeliveryOrder deliveryOrder = deliveryOrderService.findByPlateNumber(plateRecognition.getLicense());


    }

    @Override
    public Page<Map> querySynthesized(Object o, Pageable pageable) {

        return new PageImpl<Map>(beingWeighed_Entrance_maps, pageable, beingWeighed_Entrance_maps.size());
    }


    @Override
    public Page<Map> queryVerified(Object o, Pageable pageable) {

        return new PageImpl<Map>(beingVerified_maps, pageable, beingVerified_maps.size());
    }

    List<Map> maps = new ArrayList<>();

    List<Map> beingWeighed_Entrance_maps = new ArrayList<>();
    Map<String, Map> beingWeighed_Entrance_lists = new HashMap<>();

    List<Map> beingWeighed_Exit_maps = new ArrayList<>();
    Map<String, Map> beingWeighed_Exit_lists = new HashMap<>();

    List<Map> beingVerified_maps = new ArrayList<>();
    Map<String, Map> beingVerified_lists = new HashMap<>();


    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void addPlate(PlateRecognition plateRecognition) {


        Map map = new HashMap<>();
        Integer count = 1;
        map.put("license", plateRecognition.getLicense());

        ReportDeliveryOrder deliveryOrder = deliveryOrderService.findByPlateNumber(plateRecognition.getLicense());
        IDIentification idIentification = null;

        if (deliveryOrder != null) {
            count++;
            map.put("deliveryOrder_CompanyName", deliveryOrder.getCompanyName());

            map.put("product", deliveryOrder.getProductName());
            idIentification = idIdentificationRepository.findByIdNumber(deliveryOrder.getIdNumber());

        } else {
            map.put("deliveryOrder_CompanyName", "--");
        }


        if (idIentification != null) {
            count++;
            map.put("idNumber", idIentification.getId());
        } else {
            map.put("idNumber", "--");
        }


        map.put("conditions", count);
        maps.add(map);

    }

    @Override
    public void addPlate_IDIdentification(Integer idIentificationId) {


        Map map = new HashMap<>();
        Integer count = 1;


        IDIentification idIentification = idIdentificationRepository.findById(idIentificationId).get();
        map.put("idNumber", idIentification.getId());


        ReportDeliveryOrder deliveryOrder = deliveryOrderService.findByIdNumber(idIentification.getIdNumber());


        PlateRecognition plateRecognition = null;

        if (deliveryOrder != null) {
            count++;
            map.put("deliveryOrder_CompanyName", deliveryOrder.getCompanyName());
            map.put("product", deliveryOrder.getProductName());
            plateRecognition = plateRecognitionRepository.findByLicense(deliveryOrder.getLicense());
        } else {
            map.put("deliveryOrder_CompanyName", "--");
        }


        if (plateRecognition != null) {
            count++;
            map.put("license", plateRecognition.getLicense());
        } else {
            map.put("license", "--");
        }

        map.put("conditions", count);
        maps.add(map);

    }


    @Override
    public void add_delivery_order(NotificationData notificationData) {


        Map map = new HashMap<>();
        Integer count = 1;

        ReportDeliveryOrder deliveryOrder_from = (ReportDeliveryOrder) notificationData.getObject();

        ReportDeliveryOrder deliveryOrder = deliveryOrderService.findById(deliveryOrder_from.getId());


        IDIentification idIentification = idIdentificationRepository.findByIdNumber(deliveryOrder.getIdNumber());


        PlateRecognition plateRecognition = plateRecognitionRepository.findByLicense(deliveryOrder.getLicense());


        if (deliveryOrder != null) {
            count++;
            map.put("deliveryOrder_CompanyName", deliveryOrder.getCompanyName());
            map.put("product", deliveryOrder.getProductName());
        } else {
            map.put("deliveryOrder_CompanyName", "--");
        }

        if (idIentification != null) {
            count++;
            map.put("idNumber", idIentification.getIdNumber());
        } else {
            map.put("idNumber", "--");
        }

        if (plateRecognition != null) {
            count++;
            map.put("license", plateRecognition.getLicense());
        } else {
            map.put("license", "--");
        }

        map.put("conditions", count);
        maps.add(map);

    }

    @Override
    public void analyisQrcode(String text) {
        Map map = new HashMap<>();
        Integer count = 1;


        System.out.println("======text===text===text====== {}" + text);
        List<ReportDeliveryOrder> deliveryOrders = deliveryOrderService.findByValidQrcode(text);
        ReportDeliveryOrder deliveryOrder = deliveryOrders.get(deliveryOrders.size() - 1);


        IDIentification idIentification = null;


        PlateRecognition plateRecognition = null;// plateRecognitionRepository.findByLicense(deliveryOrder.getPlateNumber());


        if (deliveryOrder != null) {
            idIentification = idIdentificationRepository.findByIdNumber(deliveryOrder.getIdNumber());
            plateRecognition = plateRecognitionRepository.findByLicense(deliveryOrder.getLicense());
            count++;
            map.put("deliveryOrder_CompanyName", deliveryOrder.getCompanyName());
            map.put("idNumber", deliveryOrder.getIdNumber());
            map.put("license", deliveryOrder.getLicense());
            map.put("product", deliveryOrder.getProductName());
        } else {
            map.put("deliveryOrder_CompanyName", "--");
        }

        if (idIentification != null) {
            count++;
            if (idIentification.getIdNumber().equals(deliveryOrder.getIdNumber())) {
                map.put("idNumber_verified", true);
            } else {
                map.put("idNumber_verified", false);
            }

        } else {
            map.put("idNumber_verified", null);
        }

        if (plateRecognition != null) {
            count++;
            if (plateRecognition.getLicense().equals(deliveryOrder.getLicense())) {
                map.put("license_verified", true);
            } else {
                map.put("license_verified", false);
            }

        } else {
            map.put("license_verified", null);
        }

        map.put("conditions", count);
        maps.add(map);
    }

/*
    @Override
    public Page<Map> queryBeingWeighed(Object o, Pageable pageable) {
        return new PageImpl<Map>(beingWeighed_Entrance_maps, pageable, beingWeighed_Entrance_maps.size());
    }
*/


    @Override
    public Page<Map> queryBeingVerified(Object o, Pageable pageable) {
        return new PageImpl<Map>(beingVerified_maps, pageable, beingVerified_maps.size());
    }

    @Override
    public void add_beingWeighed_Entrance(String direction, PlateRecognition plateRecognition, ReportDeliveryOrder reportDeliveryOrder) {

        Map map = new HashMap<>();

        if (reportDeliveryOrder != null) {
            Integer count = 1;

            String license = reportDeliveryOrder.getLicense();
            //    InstanceTransport instanceTransport = instanceTransportRepository.findByPlateNumberAndStatus(reportDeliveryOrder.getPlateNumber(), InstanceTransportStatusEnum.LOADING.getText());


            //   List<ReportDeliveryOrder> reportDeliveryOrders = reportDeliveryOrderRepository.findByPlateNumberAndStatus(reportDeliveryOrder.getPlateNumber(), DeliveryOrderStatusEnum.Valid.getText());

            //  System.out.println("==============================={}"+ reportDeliveryOrder.getPlateNumber()+"|"+ license);

            if (!beingWeighed_Entrance_lists.containsKey(reportDeliveryOrder.getLicense())) {
                //    ReportDeliveryOrder reportDeliveryOrder = reportDeliveryOrders.get(0);
                Map content = new HashMap<>();//beingWeighed_lists.get(license);
                content.put("distributor", reportDeliveryOrder.getProducerNo());
                content.put("plateNumber", license);
                content.put("productName", reportDeliveryOrder.getInventoryNo());

                simpMessagingTemplate.convertAndSend(NotificiationService.topic__COALPIT_DELIVERY_workbench, JSON.toJSON(content));



                count++;
                map.put("license", reportDeliveryOrder.getLicense());


                map.put("distributor", reportDeliveryOrder.getCompanyName());
                map.put("product", reportDeliveryOrder.getProductName());
                logger.error("-----------------reportDeliveryOrder{} ", reportDeliveryOrder.toString());
                Inventory inventory = inventoryRepository.findByNo(reportDeliveryOrder.getInventoryNo());
                logger.error("-----------------inventory{} ", inventory.toString());
                map.put("quote", inventory.getQuote());

                map.put("message", "找到了对应的提煤单");

                String url = linkTo(methodOn(MobileDeliveryOrderController.class).detail(reportDeliveryOrder.getNo(), null,null)).withSelfRel().getHref();
                map.put("url", url);

                map.put("conditions", count);
                map.put("status", CommonConstant.TareWeigh_being_weighed);
                beingWeighed_Entrance_lists.put(license, map);
                beingWeighed_Entrance_maps.add(map);
            } else {

            }
        } else {
            Integer count = 1;

            String license = plateRecognition.getLicense();
            Map content = new HashMap<>();//beingWeighed_lists.get(license);
            content.put("distributor", "找不到公司");
            content.put("plateNumber", license);
            content.put("productName", "找不产品");

            simpMessagingTemplate.convertAndSend(NotificiationService.topic__COALPIT_DELIVERY_workbench, JSON.toJSON(content));
            if (!beingWeighed_Entrance_lists.containsKey(license)) {

                System.out.print("----添加 99999999999    beingWeighed_lists -------------reportDeliveryOrder{} ");
                //    ReportDeliveryOrder reportDeliveryOrder = reportDeliveryOrders.get(0);

                count++;
                map.put("license", license);


                map.put("distributor", "找不到提煤公司");
                map.put("product", "找不到产品信息");
                map.put("quote", 11);

                map.put("message", "没有找到_找到了对应的提煤单");

            } else {
                map.put("license", license);
                map.put("message", "没有找到_找到了对应的提煤单");
            }

            map.put("conditions", count);
            map.put("status", CommonConstant.TareWeigh_being_weighed);
            beingWeighed_Entrance_lists.put(license, map);
            beingWeighed_Entrance_maps.add(map);


            List<Map> beingWeighed_Exit_maps = new ArrayList<>();
            Map<String, Map> beingWeighed_Exit_lists = new HashMap<>();

        }

    }

    @Override
    public void add_beingWeighed_Exit(String direction, PlateRecognition plateRecognition, InstanceTransport instanceTransport) {

        Map map = new HashMap<>();

        if (instanceTransport != null) {
            Integer count = 1;

            String license = instanceTransport.getLicense();
            //    InstanceTransport instanceTransport = instanceTransportRepository.findByPlateNumberAndStatus(instanceTransport.getPlateNumber(), InstanceTransportStatusEnum.LOADING.getText());


            //   List<ReportDeliveryOrder> reportDeliveryOrders = reportDeliveryOrderRepository.findByPlateNumberAndStatus(instanceTransport.getPlateNumber(), DeliveryOrderStatusEnum.Valid.getText());

            //  System.out.println("==============================={}"+ instanceTransport.getPlateNumber()+"|"+ license);

            if (!beingWeighed_Exit_lists.containsKey(instanceTransport.getLicense())) {
                //    ReportDeliveryOrder instanceTransport = reportDeliveryOrders.get(0);
                Map content = new HashMap<>();//beingWeighed_lists.get(license);
                content.put("distributor", instanceTransport.getDistributorNo());
                content.put("plateNumber", license);
                content.put("productName", instanceTransport.getInventoryNo());

                simpMessagingTemplate.convertAndSend(NotificiationService.topic__COALPIT_DELIVERY_workbench, JSON.toJSON(content));



                count++;
                map.put("license", instanceTransport.getLicense());


                map.put("distributor", instanceTransport.getDistributorNo());
                map.put("product", instanceTransport.getInventoryNo());
                logger.error("-----------------instanceTransport{} ", instanceTransport.toString());
                Inventory inventory = inventoryRepository.findByNo(instanceTransport.getInventoryNo());
                logger.error("-----------------inventory{} ", inventory.toString());
                map.put("quote", inventory.getQuote());

                map.put("message", "找到了装车信息");
                String url = linkTo(methodOn(MobileInstanceTransprotController.class).detail(instanceTransport.getId(), null,null)).withSelfRel().getHref();
                map.put("url", url);


                map.put("conditions", count);
                map.put("status", CommonConstant.TareWeigh_being_weighed);
                beingWeighed_Exit_lists.put(license, map);
                beingWeighed_Exit_maps.add(map);
            } else {

            }
        } else {
            Integer count = 1;

            String license = plateRecognition.getLicense();
            Map content = new HashMap<>();//beingWeighed_lists.get(license);
            content.put("distributor", "找不到公司");
            content.put("plateNumber", license);
            content.put("productName", "找不产品");

            simpMessagingTemplate.convertAndSend(NotificiationService.topic__COALPIT_DELIVERY_workbench, JSON.toJSON(content));
            if (!beingWeighed_Exit_lists.containsKey(license)) {

                System.out.print("----添加 99999999999    beingWeighed_lists -------------instanceTransport{} ");
                //    ReportDeliveryOrder instanceTransport = reportDeliveryOrders.get(0);

                count++;
                map.put("license", license);


                map.put("distributor", "找不到提煤公司");
                map.put("product", "找不到产品信息");
                map.put("quote", 11);

                map.put("message", "没有找到_找到了对应的提煤单");

            } else {
                map.put("license", license);
                map.put("message", "没有找到_找到了对应的提煤单");
            }

            map.put("conditions", count);
            map.put("status", CommonConstant.TareWeigh_being_weighed);
            beingWeighed_Exit_lists.put(license, map);
            beingWeighed_Exit_maps.add(map);

        }

    }


    @Override
    public void add_beingWeighed_tinput_tareWeight(InstanceTransport instanceTransport) {
        Map map = beingWeighed_Entrance_lists.get(instanceTransport.getDeliveryOrderNo());
        map.put("tareWeight",instanceTransport.getTareWeight());
    }





    @Override
    public void add_beingWeighed_tinput_netWeight(InstanceTransport instanceTransport) {
        Map map = beingWeighed_Entrance_lists.get(instanceTransport.getDeliveryOrderNo());
        map.put("netWeight",instanceTransport.getNetWeight());

    }

    @Override
    public Page<Map> queryBeingLoaded(Object o, Pageable pageable) {

        return instanceTransportService.query_BeingLoaded(pageable);
    }

    @Override
    public void add_beingWeighed_Entrance(String direction, PlateRecognition deliveryOrder_from) {

    }

    @Override
    public void add_verified(ReportDeliveryOrder reportDeliveryOrder) {

        Map map = new HashMap<>();
        Integer count = 1;

        String license = reportDeliveryOrder.getLicense();

        if( !beingVerified_lists.containsKey(reportDeliveryOrder.getLicense())){
            //    ReportDeliveryOrder reportDeliveryOrder = reportDeliveryOrders.get(0);
            Map content = new HashMap<>();//beingWeighed_lists.get(license);
            content.put("distributor",reportDeliveryOrder.getProducerNo());
            content.put("plateNumber",license);
            content.put("productName",reportDeliveryOrder.getInventoryNo());

            simpMessagingTemplate.convertAndSend(NotificiationService.topic__COALPIT_DELIVERY_report, JSON.toJSON(content));


            if(reportDeliveryOrder!= null){
                count++;
                map.put("license",reportDeliveryOrder.getLicense());



                map.put("distributor",reportDeliveryOrder.getCompanyName());
                map.put("product",reportDeliveryOrder.getProductName());
                logger.error("-----------------reportDeliveryOrder{} ",reportDeliveryOrder.toString());
                Inventory inventory = inventoryRepository.findByNo(reportDeliveryOrder.getInventoryNo());
                logger.error("-----------------inventory{} ",inventory.toString());
                map.put("quote",inventory.getQuote());

                map.put("message","找到了对应的提煤单");

            }else{
                map.put("license",license);
                map.put("message","没有找到_找到了对应的提煤单");
            }

            map.put("conditions",count);
            map.put("status", CommonConstant.TareWeigh_being_weighed);
            beingVerified_lists.put(license,map);
            beingVerified_maps.add(map);
        }else{

        }

    }

    @Override
    public Page<Map> querySynthesizedExitIntelligent(Object o, Pageable pageable) {
        return new PageImpl<Map>(beingWeighed_Exit_maps, pageable, beingWeighed_Exit_maps.size());
    }

    @Override
    public Page<Map> querySynthesizedEntranceIntelligent(Object o, Pageable pageable) {
        return new PageImpl<Map>(beingWeighed_Entrance_maps, pageable, beingWeighed_Entrance_maps.size());
    }
}
