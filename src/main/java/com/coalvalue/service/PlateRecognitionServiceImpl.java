package com.coalvalue.service;

//import com.LPR;
import com.coalvalue.configuration.ReactorEventConfig;
import com.coalvalue.domain.OperationResult;

import com.coalvalue.domain.entity.*;
import com.coalvalue.enumType.PlateDirectionEnum;


import com.coalvalue.model.TruckComming;
import com.coalvalue.notification.NotificationData_plateRecognition;
import com.coalvalue.repository.LoadometerRepository;
import com.coalvalue.repository.PlateRecognitionRepository;

import com.coalvalue.web.valid.PlateRecognitionCreateForm;

import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.bus.EventBus;

import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;

/**
 * Created by silence yuan on 2015/7/25.
 */
@Service("plateRecognitionService")
public class PlateRecognitionServiceImpl extends BaseServiceImpl implements PlateRecognitionService {


    @Autowired
    private PlateRecognitionRepository plateRecognitionRepository;

    @Autowired
    EventBus eventBus;


    @Autowired
    BehaviouralService behaviouralService;

    @Autowired
    InstanceTransportService instanceTransportService;

    @Autowired
    DeliveryOrderService deliveryOrderService;

    @Autowired
    LoadometerRepository loadometerRepository;



    @Override
    public OperationResult record(Equipment equipment_, PlateRecognitionCreateForm canvassingCreateForm_) {


        PlateRecognition plateRecognition = new PlateRecognition();



        plateRecognition = plateRecognitionRepository.save(plateRecognition);


/*
        NotificationData notificationData = new NotificationData();
  //      notificationData.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_EVENT);
        notificationData.setObject(plateRecognition);
        eventBus.notify("notificationConsumer", Event.wrap(notificationData));

*/


        OperationResult operationResult = new OperationResult();
        operationResult.setResultObject(plateRecognition);
        operationResult.setSuccess(true);
        return operationResult;
    }

   /* @Override
    @Transactional
    public PlateRecognition createPlateRecognition(LPR.TH_PlateResult_Pointer.ByReference pResult,String path) {
        PlateRecognition plateRecognition = new PlateRecognition();
        plateRecognition.setDirection(PlateDirectionEnum.fromInt(pResult.nDirection).getText());


        plateRecognition.setLicense(deCode_new_GB2312(pResult.license).trim());
        plateRecognition.setColourCode(pResult.nColor);
        plateRecognition.setPath(path);
        System.out.println("存储车牌号："+plateRecognition.toString());



        plateRecognition = plateRecognitionRepository.save(plateRecognition);





        NotificationData_plateRecognition notificationData = new NotificationData_plateRecognition();
        notificationData.setObject(plateRecognition);
        eventBus.notify(ReactorEventConfig.notificationConsumer_plate_recognition_event, Event.wrap(notificationData));
        return plateRecognition;




    }

    @Override
    @Transactional
    public void createPlateRecognition(LPR.TH_PlateResult_Pointer.ByReference pResult) {
        PlateRecognition plateRecognition = new PlateRecognition();
        plateRecognition.setDirection("333");


        plateRecognition.setLicense("33333333");
        plateRecognition.setColourCode(1);
        System.out.println("存储车牌号------------测试："+plateRecognition.toString());
        plateRecognitionRepository.save(plateRecognition);
    }*/


    @Transactional
    public PlateRecognition createPlateRecognition(TruckComming truckComming) {
        PlateRecognition plateRecognition = new PlateRecognition();
        plateRecognition.setDirection(PlateDirectionEnum.fromInt(truckComming.nDirection).getText());


        plateRecognition.setLicense(deCode_new_GB2312(truckComming.license).trim());
        plateRecognition.setColourCode(truckComming.nColor);
        plateRecognition.setPath(truckComming.path);

        System.out.println("存储车牌号："+plateRecognition.toString());



        plateRecognition = plateRecognitionRepository.save(plateRecognition);



        if(plateRecognition.getDirection().equals("In")){
            ReportDeliveryOrder reportDeliveryOrder = deliveryOrderService.getValidByLicense(plateRecognition.getLicense());
            NotificationData_plateRecognition notificationData = new NotificationData_plateRecognition();
            notificationData.setObject(plateRecognition);
            eventBus.notify(ReactorEventConfig.notificationConsumer_delivery_order_in_on_weight_event, Event.wrap(notificationData));
        }

        if(plateRecognition.getDirection().equals("Out")){
            InstanceTransport instanceTransport = instanceTransportService.getLoadingByLicense(plateRecognition.getLicense());
            NotificationData_plateRecognition notificationData = new NotificationData_plateRecognition();
            notificationData.setObject(plateRecognition);
            eventBus.notify(ReactorEventConfig.notificationConsumer_create_instance_transport_out_on_weight_event, Event.wrap(notificationData));
        }


        return plateRecognition;




    }

    @Override
    public Page<PlateRecognition> query(Object o, Pageable pageable) {



        return  plateRecognitionRepository.findAll(pageable);

    }

    @Override
    public void analyis(PlateRecognition plateRecognition) {
        behaviouralService.analyis(plateRecognition);
    }


    public static String deCode_new_GB2312(byte[] str) {
        try {
            String stirng = new String(str, "GB2312");
            return java.net.URLDecoder.decode(stirng, "GB2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }
}
