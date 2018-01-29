package com.coalvalue.service;

import com.LPR;
import com.coalvalue.domain.OperationResult;

import com.coalvalue.domain.entity.Equipment;
import com.coalvalue.domain.entity.PlateRecognition;
import com.coalvalue.enumType.PlateDirectionEnum;
import com.coalvalue.notification.NotificationData;

import com.coalvalue.repository.PlateRecognitionRepository;

import com.coalvalue.web.valid.PlateRecognitionCreateForm;
import com.service.BaseServiceImpl;
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
    private MqttService mqttService;


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

    @Override
    @Transactional
    public PlateRecognition createPlateRecognition(LPR.TH_PlateResult_Pointer.ByReference pResult,String path) {
        PlateRecognition plateRecognition = new PlateRecognition();
        plateRecognition.setDirection(PlateDirectionEnum.fromInt(pResult.nDirection).getText());


        plateRecognition.setLicense(deCode_new_GB2312(pResult.license));
        plateRecognition.setColourCode(pResult.nColor);
        plateRecognition.setPath(path);
        System.out.println("存储车牌号："+plateRecognition.toString());

        mqttService.publishToHost(plateRecognition.getLicense());


        return plateRecognitionRepository.save(plateRecognition);


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
