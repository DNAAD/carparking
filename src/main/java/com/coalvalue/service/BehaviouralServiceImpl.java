package com.coalvalue.service;


import com.coalvalue.domain.entity.Behavioural;
import com.coalvalue.domain.entity.IDIentification;
import com.coalvalue.domain.entity.PlateRecognition;
import com.coalvalue.domain.entity.ReportDeliveryOrder;
import com.coalvalue.enumType.DeliveryOrderStatusEnum;
import com.coalvalue.notification.NotificationData;
import com.coalvalue.repository.BehaviouralRepository;
import com.coalvalue.repository.IDIdentificationRepository;
import com.coalvalue.repository.PlateRecognitionRepository;
import com.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private DistributorService distributorService;

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


        return new PageImpl<Map>(maps,pageable,maps.size());
    }

    List<Map> maps = new ArrayList<>();



    @Override
    public void addPlate(PlateRecognition plateRecognition) {


        Map map = new HashMap<>();
        Integer count = 1;
        map.put("license",plateRecognition.getLicense());

        ReportDeliveryOrder deliveryOrder = deliveryOrderService.findByPlateNumber(plateRecognition.getLicense());
        IDIentification idIentification = null;

        if(deliveryOrder != null){
            count++;
            map.put("deliveryOrder_CompanyName",deliveryOrder.getCompanyName());

            map.put("product",deliveryOrder.getProductName());
            idIentification = idIdentificationRepository.findByIdNumber(deliveryOrder.getIdNumber());

        }else{
            map.put("deliveryOrder_CompanyName","--");
        }


        if(idIentification != null){
            count++;
            map.put("idNumber",idIentification.getId());
        }else{
            map.put("idNumber","--");
        }



        map.put("conditions",count);
        maps.add(map);

    }

    @Override
    public void addPlate_IDIdentification(Integer idIentificationId) {


        Map map = new HashMap<>();
        Integer count = 1;


        IDIentification idIentification = idIdentificationRepository.findById(idIentificationId);
        map.put("idNumber",idIentification.getId());


        ReportDeliveryOrder deliveryOrder = deliveryOrderService.findByIdNumber(idIentification.getIdNumber());


        PlateRecognition plateRecognition = null;

        if(deliveryOrder != null){
            count++;
            map.put("deliveryOrder_CompanyName",deliveryOrder.getCompanyName());
            map.put("product",deliveryOrder.getProductName());
            plateRecognition = plateRecognitionRepository.findByLicense(deliveryOrder.getPlateNumber());
        }else{
            map.put("deliveryOrder_CompanyName","--");
        }


        if(plateRecognition!= null){
            count++;
            map.put("license",plateRecognition.getLicense());
        }else{
            map.put("license","--");
        }

        map.put("conditions",count);
        maps.add(map);

    }




    @Override
    public void add_delivery_order(NotificationData notificationData) {


        Map map = new HashMap<>();
        Integer count = 1;

        ReportDeliveryOrder  deliveryOrder_from = notificationData.getObject();

        logger.debug("================={} ",deliveryOrder_from.toString());
        System.out.println("================={} "+deliveryOrder_from.toString());


        ReportDeliveryOrder deliveryOrder = deliveryOrderService.findById(deliveryOrder_from.getId());




        IDIentification idIentification = idIdentificationRepository.findByIdNumber(deliveryOrder.getIdNumber());





        PlateRecognition plateRecognition = plateRecognitionRepository.findByLicense(deliveryOrder.getPlateNumber());




        if(deliveryOrder != null){
            count++;
            map.put("deliveryOrder_CompanyName",deliveryOrder.getCompanyName());
            map.put("product",deliveryOrder.getProductName());
        }else{
            map.put("deliveryOrder_CompanyName","--");
        }

        if(idIentification!= null){
            count++;
            map.put("idNumber",idIentification.getIdNumber());
        }else{
            map.put("idNumber","--");
        }

        if(plateRecognition!= null){
            count++;
            map.put("license",plateRecognition.getLicense());
        }else{
            map.put("license","--");
        }

        map.put("conditions",count);
        maps.add(map);

    }

    @Override
    public void analyisQrcode(String text) {
        Map map = new HashMap<>();
        Integer count = 1;


        System.out.println("======text===text===text====== {}"+text );
        List<ReportDeliveryOrder> deliveryOrders = deliveryOrderService.findByValidQrcode(text);
        ReportDeliveryOrder deliveryOrder = deliveryOrders.get(deliveryOrders.size()-1);


        IDIentification idIentification = null;


        PlateRecognition plateRecognition =null;// plateRecognitionRepository.findByLicense(deliveryOrder.getPlateNumber());




        if(deliveryOrder != null){
            idIentification = idIdentificationRepository.findByIdNumber(deliveryOrder.getIdNumber());
            plateRecognition = plateRecognitionRepository.findByLicense(deliveryOrder.getPlateNumber());
            count++;
            map.put("deliveryOrder_CompanyName",deliveryOrder.getCompanyName());
            map.put("idNumber",deliveryOrder.getIdNumber());
            map.put("license",deliveryOrder.getPlateNumber());
            map.put("product",deliveryOrder.getProductName());
        }else{
            map.put("deliveryOrder_CompanyName","--");
        }

        if(idIentification!= null){
            count++;
            if(idIentification.getIdNumber().equals(deliveryOrder.getIdNumber())){
                map.put("idNumber_verified",true);
            }else{
                map.put("idNumber_verified",false);
            }

        }else{
            map.put("idNumber_verified",null);
        }

        if(plateRecognition!= null){
            count++;
            if(plateRecognition.getLicense().equals(deliveryOrder.getPlateNumber())){
                map.put("license_verified",true);
            }else{
                map.put("license_verified",false);
            }

        }else{
            map.put("license_verified",null);
        }

        map.put("conditions",count);
        maps.add(map);
    }
}
