package com.coalvalue.service;

import com.alibaba.fastjson.JSON;
import com.coalvalue.domain.entity.Camera;
import com.coalvalue.domain.OperationResult;
import com.coalvalue.domain.entity.*;
import com.coalvalue.domain.pojo.PlateRecogniseCameraPOJO;
import com.coalvalue.enumType.CameraTypeEnum;
import com.coalvalue.enumType.EquipmentBulletinDisplayModeEnum;
import com.coalvalue.enumType.EquipmentStatusEnum;
import com.coalvalue.enumType.EquipmentTypeEnum;

import com.coalvalue.repository.CameraRepository;
import com.coalvalue.repository.EquipmentRepository;
//import com.coalvalue.util.SequenceGenerator;

import com.coalvalue.web.MobileCameraController;
import com.fasterxml.jackson.databind.ObjectMapper;

//import org.apache.activemq.command.ActiveMQQueue;
import freemarker.ext.beans.HashAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Function;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by silence yuan on 2015/7/25.
 */

@Service("equipmentService")
public class EquipmentServiceImpl extends BaseServiceImpl implements EquipmentService {

    public static final String myTopic_messages = "queue.led";

    // @Value("${server_live.url}")
    // private String serverLiveUrl;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private CompanyService companyService;


/*    @Autowired
    private JmsTemplate jmsTemplate;*/
    //  @Autowired
    // private SequenceGenerator sequenceGenerator;



    @Autowired
    private CameraRepository cameraRepository;



    @Override
    @Transactional
    public Equipment create(String deviceId, EquipmentTypeEnum typeEnum, User user) {
        OperationResult operationResult = new OperationResult();
        operationResult.setSuccess(true);

        Equipment equipment = equipmentRepository.findByTypeAndDeviceId( typeEnum.getText(), deviceId);
        if(equipment== null){

            equipment = new Equipment();
            equipment.setDeviceId(deviceId);
            equipment.setType(typeEnum.getText());
            equipment.setUuid(UUID.randomUUID().toString());

            equipmentRepository.save(equipment);
        }
        operationResult.setResultObject(equipment);
        return equipment;
    }
    @Override
    public Page<Map> queryCamera(Object o, Pageable pageable) {


        Page<Camera> cameras =  cameraRepository.findAll(pageable);

        Page<Map> pages = cameras.map(new Function<Camera, Map>() {
            public Map apply(Camera objectEntity) {




                HashMap<String,Object> map = new HashMap<>();
                map.put("rstpUrl",objectEntity.getRstpUrl());
                map.put("path",objectEntity.getPath());
                map.put("no",objectEntity.getNo());
                map.put("createDate",objectEntity.getCreateDate());
                map.put("username",objectEntity.getUsername());
                map.put("password",objectEntity.getPassword());
                map.put("ip",objectEntity.getIp());
                map.put("type", CameraTypeEnum.fromString(objectEntity.getType()).getDisplayText());
                String companiesUrl = linkTo(methodOn(MobileCameraController.class).detail(objectEntity.getId(), null)).withSelfRel().getHref();

                map.put("url",companiesUrl);



                return map;

            }
        });

        //Page<Map> pages = new PageImpl<Map>(list,pageable,list.size());

        return pages;

    }

    //@Scheduled(cron="0/3 * * * * ?")
    public void executeUploadTask() {

        // 间隔1分钟,执行工单上传任务
        Thread current = Thread.currentThread();
        System.out.println("equipments size " );

      /*  ActiveMQQueue destination = new ActiveMQQueue(myTopic_messages);

        jmsTemplate.send(destination, new MessageCreator() {
            public javax.jms.Message createMessage(Session session) throws JMSException {
                MapMessage mapMessage = session.createMapMessage();
                mapMessage.setString("event", "changePrice");
                mapMessage.setString("equipmentId", "changePrice");
                mapMessage.setString("content","00");

                return mapMessage;

            }});
*/

    }

    @Override
    public Page<Map> queryCamera(EquipmentTypeEnum equipmentTypeEnum, Pageable pageable) {

        Page<Camera> cameras = null;
        if(equipmentTypeEnum== null){
            cameras = cameraRepository.findAll(pageable);
        }else{
            cameras = cameraRepository.findByType(equipmentTypeEnum.getText(), pageable);
        }


        Page<Map> pages = cameras.map(new Function<Camera, Map>() {
            public Map apply(Camera objectEntity) {


                HashMap<String, Object> map = new HashMap<>();
                map.put("rstpUrl", objectEntity.getRstpUrl());
                map.put("path", objectEntity.getPath());
                map.put("no", objectEntity.getNo());
                map.put("createDate", objectEntity.getCreateDate());
                map.put("username", objectEntity.getUsername());
                map.put("password", objectEntity.getPassword());
                map.put("ip", objectEntity.getIp());
                map.put("type", CameraTypeEnum.fromString(objectEntity.getType()).getDisplayText());
                String companiesUrl = linkTo(methodOn(MobileCameraController.class).detail(objectEntity.getId(), null)).withSelfRel().getHref();

                map.put("url", companiesUrl);


                return map;

            }
        });


        return pages;

    }


    @Override
    public Page<Map> queryEquipment(EquipmentTypeEnum equipmentTypeEnum, Pageable pageable) {

        Page<Equipment> cameras = cameras = equipmentRepository.findAll( pageable);


        Page<Map> pages = cameras.map(new Function<Equipment, Map>() {
            public Map apply(Equipment objectEntity) {

                ObjectMapper m = new ObjectMapper();
                Map<String,Object> map = m.convertValue(objectEntity,Map.class);



                map.put("type", EquipmentTypeEnum.fromString(objectEntity.getType()).getDisplayText());
                String companiesUrl = linkTo(methodOn(MobileCameraController.class).detail(objectEntity.getId(), null)).withSelfRel().getHref();

                map.put("url", companiesUrl);


                PlateRecogniseCameraPOJO plateRecogniseCameraPOJO = deviceMap.get(objectEntity.getName());

                if(plateRecogniseCameraPOJO!= null){
                    map.put("deviceName", plateRecogniseCameraPOJO.getDevice_name());
                    map.put("ipaddr", plateRecogniseCameraPOJO.getIpaddr());
                    map.put("port", plateRecogniseCameraPOJO.getPort());
                    map.put("timeStamp", plateRecogniseCameraPOJO.getTimeStemp());
                }

                return map;

            }
        });


        return pages;

    }

    Map<String,PlateRecogniseCameraPOJO> deviceMap = new HashMap();




    @Override
    public void updateEquipmentLiveInfo(Equipment equipment, PlateRecogniseCameraPOJO device_name) {
        device_name.setTimeStemp(LocalDateTime.now());
        deviceMap.put(device_name.getSerialno(),device_name);

    }


    @Override
    @Transactional
    public Equipment createEquipment(String deviceId, EquipmentTypeEnum typeEnum) {
        OperationResult operationResult = new OperationResult();
        operationResult.setSuccess(true);

        Equipment equipment = equipmentRepository.findByTypeAndName(typeEnum.getText(), deviceId);
        if(equipment== null){

            equipment = new Equipment();
            equipment.setName(deviceId);

            //  equipment.setDeviceId(sequenceGenerator.nextUuidNO());
            equipment.setType(typeEnum.getText());
            equipment = equipmentRepository.save(equipment);
        }
        operationResult.setResultObject(equipment);
        return equipment;
    }

    @Override
    public Object getPlateRecongniseCamera() {
        return null;
    }
}
