package com.coalvalue.task;

import com.alibaba.fastjson.JSON;
import com.coalvalue.configuration.MqttPublishSample;
import com.coalvalue.domain.entity.Configuration;
import com.coalvalue.enumType.DataSynchronizationTypeEnum;
import com.coalvalue.enumType.EchoSessionTypeEnum;
import com.coalvalue.protobuf.Hub;
import com.coalvalue.repository.ConfigurationRepository;
import com.coalvalue.service.*;
import com.coalvalue.service.sync.DifferentialSyncService;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RegisterTasks {

    //Value("${imei}")
   // public String imei = System.getenv("imei");


//https://www.baeldung.com/spring-state-machine

    protected transient Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private DifferentialSyncService differentialSyncService;


    @Autowired
    MqttClient mqttClient;


    @Autowired
    ConfigurationService configurationService;
    @Autowired
    LiveInformationService liveInformationService;

    @Autowired
    ConfigurationRepository configurationRepository;


    @Autowired
    StorageService storageService;

    @Autowired
    InventoryService inventoryService;

    @Autowired
    DistributorService distributorService;
    @Autowired
    MqttPublishSample mqttPublishSample;
    @Autowired
    SystemStatusBroadcast systemStatusBroadcast;



    @Autowired
    TimeSilcePasswordService timeSilcePasswordService;




    Map request_sync = new HashMap();
    public void register(String echo_session) {

        System.out.println("进行注册 操作 开始");
        String seq = UUID.randomUUID().toString();
            request_sync.put("seq",seq );

        String client_request        = "request/"+ mqttPublishSample.getChannal_topic();




        List<Configuration> configuration = configurationRepository.findAll();
        if(configuration.size() == 0){
            logger.info("缺少配置信息，发送注册信息 "+ mqttPublishSample.getChannal_topic());
            Map map = new HashMap<>();
            map.put("type", DataSynchronizationTypeEnum.Rgister.getText());
            map.put("uuid", mqttPublishSample.getChannal_topic());
            map.put("echo_session", echo_session);
            map.put("seq", seq);

            try {
                mqttClient.publish(client_request, JSON.toJSONString(map).getBytes(),2,false);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }else{
            logger.info("有配置信息，不再发送注册信息 "+ mqttPublishSample.getChannal_topic());
            //firstCheck_configuratoin();
        }




    }

    public void process(Hub.Register register) {

        String seq = register.getSeq();
        String echo_session =register.getEchoSession();
        completeRegister(seq,echo_session);
    }




        public void completeRegister(String seq, String echo_session) {

        System.out.println("@@成功完成了了注册");

        if(EchoSessionTypeEnum.Register_bootup.getText().equals(echo_session)){
            System.out.println("@@Register_bootup 开始 同步 信息 ");
            if(seq!= null && request_sync.containsKey(seq)){

            }else{
                systemStatusBroadcast.completeRegister = true;
                differentialSyncService.differentialSync( EchoSessionTypeEnum.Whole.getText(),"");
            }
        }


            try {
                mqttPublishSample.reportStatus(systemStatusBroadcast.info());
            } catch (MqttException e) {
                e.printStackTrace();
            }

    }


    public boolean support(Hub.Message message) {

        return DataSynchronizationTypeEnum.Rgister.getText().equals(message.getType());

    }

    public boolean surportSyncRequest(Hub.Message message) {
        return DataSynchronizationTypeEnum.Sync.getText().equals(message.getType());


    }

    public void decesionSyncRequest(Hub.Register register) {

        System.out.println("@@Register_bootup 开始 同步 信息 ");
/*        if(seq!= null && request_sync.containsKey(seq)){

        }else{

        }*/

        differentialSyncService.differentialSync( EchoSessionTypeEnum.Whole.getText(),"");
    }
}

