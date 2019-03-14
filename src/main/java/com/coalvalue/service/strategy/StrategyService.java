package com.coalvalue.service.strategy;


import com.coalvalue.configuration.MqttPublishSample;
import com.coalvalue.domain.OperationResult;
import com.coalvalue.domain.entity.Equipment;
import com.coalvalue.domain.pojo.PlateRecogniseCameraPOJO;
import com.coalvalue.enumType.EquipmentTypeEnum;
import com.coalvalue.enumType.ResourceType;
import com.coalvalue.protobuf.Hub;
import com.coalvalue.repository.EquipmentRepository;
import com.coalvalue.service.BaseServiceImpl;
import com.coalvalue.service.EquipmentService;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.protobuf.ByteString;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * Created by silence yuan on 2015/6/28.
 */
@Service("strategyService")

public class StrategyService extends BaseServiceImpl  {


    public enum Strategy_COMMAND {
        Strategy_COMMAND_极少探测,
        Strategy_COMMAND_过磅,

        Strategy_COMMAND_极少探测_openvpn;
    }

    class Condition {
        private LocalDateTime time;
        private String command;
        private Boolean openvpn;
        private LocalDateTime receiveTime;
        private String objectUuid;

        public LocalDateTime getTime() {
            return time;
        }

        public void setTime(LocalDateTime time) {
            this.time = time;
        }

        public String getCommand() {
            return command;
        }

        public void setCommand(String command) {
            this.command = command;
        }

        public Boolean getOpenvpn() {
            return openvpn;
        }

        public void setOpenvpn(Boolean openvpn) {
            this.openvpn = openvpn;
        }

        public void setReceiveTime(LocalDateTime receiveTime) {

            this.receiveTime = receiveTime;
        }

        public LocalDateTime getReceiveTime() {
            return receiveTime;
        }

        public void setObjectUuid(String objectUuid) {
            this.objectUuid = objectUuid;
        }

        public String getObjectUuid() {
            return objectUuid;
        }
    }
    public final static Cache<String,Condition> cache_storage__= CacheBuilder.newBuilder()
            //设置cache的初始大小为10，要合理设置该值  
            .initialCapacity(10)
            //设置并发数为5，即同一时间最多只能有5个线程往cache执行写入操作  
            .concurrencyLevel(5)
            //设置cache中的数据在写入之后的存活时间为10秒  
            .expireAfterWrite(15,TimeUnit.SECONDS)
            .recordStats()
            // 设置缓存的移除通知
            .removalListener(new RemovalListener<Object, Object>() {
                public void onRemoval(RemovalNotification<Object, Object> notification) {
                    System.out.println(
                            notification.getKey() + " was removed, cause is " + notification.getCause());
                }
            })
            //构建cache实例  
            .build();


   @Autowired
    @Qualifier("mqttClientLocal")
    MqttClient mqttClientLocal;

    private static final Logger logger = LoggerFactory.getLogger(StrategyService.class);
    @Autowired

    EquipmentRepository equipmentRepository;

    @Autowired

    MqttPublishSample mqttPublishSample;
    @Autowired

    MqttClient mqttClient;
    @Autowired

    EquipmentService equipmentService;



    public static final String TOPIC = "camera";

    Map map = new HashMap();
    public void health(String s) {

        map.put(s,"online");

    }


    public void command(Strategy_COMMAND com, String uuid) {
        Condition condition = new Condition();
        condition.setObjectUuid(uuid);
        condition.setTime(LocalDateTime.now());
        if(com.name().equals(Strategy_COMMAND.Strategy_COMMAND_极少探测.name())){
            String sessionId= UUID.randomUUID().toString();

            condition.setCommand(com.name());
            condition.setOpenvpn(false);
            condition.setObjectUuid(uuid);

            cache_storage__.put(sessionId,condition);
            System.out.println("------发送"+ mqttClientLocal.getServerURI() + " "+ mqttClientLocal.getClientId()+ " "+ mqttClientLocal.getCurrentServerURI());
            try {
                mqttClientLocal.publish(TOPIC+"/"+"1",sessionId.getBytes(),1,false);
                System.out.println("------发送"+sessionId);

            } catch (MqttException e) {
                e.printStackTrace();
            }


        }

        if(com.equals(Strategy_COMMAND.Strategy_COMMAND_极少探测_openvpn)){
            String sessionId= UUID.randomUUID().toString();

            condition.setCommand(com.name());
            condition.setOpenvpn(true);

            cache_storage__.put(sessionId,condition);
            System.out.println("------发送"+ mqttClientLocal.getServerURI() + " "+ mqttClientLocal.getClientId()+ " "+ mqttClientLocal.getCurrentServerURI());
            try {
                mqttClientLocal.publish(TOPIC+"/"+"1",sessionId.getBytes(),1,false);

            } catch (MqttException e) {
                e.printStackTrace();
            }


            System.out.println("------发送");
        }


        if(com.equals(Strategy_COMMAND.Strategy_COMMAND_过磅)){

            String sessionId= UUID.randomUUID().toString();

            condition.setCommand(com.name());
            condition.setOpenvpn(true);

            cache_storage__.put(sessionId,condition);
            System.out.println("------发送"+ mqttClientLocal.getServerURI() + " "+ mqttClientLocal.getClientId()+ " "+ mqttClientLocal.getCurrentServerURI());
            try {
                mqttClientLocal.publish(TOPIC+"/"+"1",sessionId.getBytes(),1,false);

            } catch (MqttException e) {
                e.printStackTrace();
            }


            System.out.println("------发送");

        }


    }




    public OperationResult receive(Map data, MultipartFile multiPartFileList) {
        OperationResult operationResult = new OperationResult();
        Long time = (Long)data.get("time");
        String deviceId = (String)data.get("deviceId");
        String sessionId = (String)data.get("sessionId");


        Equipment equipment = equipmentRepository.findByTypeAndDeviceId(EquipmentTypeEnum.Camera.getText(),deviceId);


        if(equipment != null){

            Condition kind = cache_storage__.getIfPresent(sessionId);

            System.out.println("---------- upload file"+kind.getTime());
            System.out.println("---------- upload file"+kind.getCommand());
            System.out.println("---------- upload file"+kind.getOpenvpn());
            System.out.println("---------- upload file"+cache_storage__.asMap());

            kind.setReceiveTime(LocalDateTime.now());


            if(kind.getCommand().equals(Strategy_COMMAND.Strategy_COMMAND_过磅.name())){
                Hub.Image course2 = Hub.Image.newBuilder()
                        .setId(Integer.valueOf(equipment.getDeviceId()))
                        .setImageBytes(ByteString.copyFrom(toByteArray(multiPartFileList)))
                        .setType(ResourceType.INVENTORY_TRANSFER.getText())
                        .setObjectUuid(kind.getObjectUuid())
                        .build();
                shedule(multiPartFileList,course2,equipment);

                //FileUtil.

                operationResult.setSuccess(true);

                return operationResult;

            }
            if(kind.getCommand().equals(Strategy_COMMAND.Strategy_COMMAND_极少探测.name())){
                Hub.Image course2 = Hub.Image.newBuilder()
                        .setId(Integer.valueOf(equipment.getDeviceId()))
                        .setImageBytes(ByteString.copyFrom(toByteArray(multiPartFileList)))
                        .setType(ResourceType.STORAGE.getText())
                        .setObjectUuid(equipment.getUuid())
                        .build();
                shedule(multiPartFileList,course2,equipment);

                operationResult.setSuccess(true);
                return operationResult;
            }

            if(kind.getCommand().equals(Strategy_COMMAND.Strategy_COMMAND_极少探测_openvpn.name())){

                Hub.Image course2 = Hub.Image.newBuilder()
                        .setId(Integer.valueOf(equipment.getDeviceId()))
                        .setImageBytes(ByteString.copyFrom(toByteArray(multiPartFileList)))
                        .setType(ResourceType.STORAGE.getText())
                        .setObjectUuid(equipment.getUuid())
                        .build();
                shedule(multiPartFileList,course2,equipment);

                operationResult.setSuccess(true);
                return operationResult;
            }
        }else{
            logger.error("不支持的设备 发来 图片数据"+deviceId);
        }

        operationResult.setSuccess(false);

        return operationResult;

    }
    public  byte[] toByteArray(MultipartFile multiPartFileList) {


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(multiPartFileList.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ImageIO.write(bufferedImage, "jpg", baos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                baos.close();
            } catch(Exception inner) {
            }
            //  bufferedImage.dispose();
        }

        byte[] res=baos.toByteArray();





        return res;

    }

    public void shedule(MultipartFile multiPartFileList,Hub.Image image, Equipment equipment) {






        ;

        //  public static String image="image/+";
        String client_request        = "image/"+ mqttPublishSample.getChannal_topic();

        try {
            //  mqttClient.publish(client_request, JSON.toJSONString(imageMap).getBytes(),2,false);
            mqttClient.publish(client_request,image.toByteArray(),2,false); // ,
        } catch (MqttException e) {
            e.printStackTrace();
        }


    }











}
