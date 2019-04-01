package com.coalvalue.task;

import com.coalvalue.configuration.MqttPublishSample;
import com.coalvalue.configuration.state.RegEventEnum;
import com.coalvalue.configuration.state.RegStatusEnum;
import com.coalvalue.domain.entity.Configuration;

import com.coalvalue.domain.pojo.IMEIconfig;
import com.coalvalue.domain.pojo.TopicQos;
import com.coalvalue.enumType.EchoSessionTypeEnum;
import com.coalvalue.enumType.ProjectStatusEnum;
import com.coalvalue.protobuf.Hub;
import com.coalvalue.publicCommand.CommonProcess;
import com.coalvalue.publicCommand.PrivateNotify;
import com.coalvalue.repository.ConfigurationRepository;
import com.coalvalue.service.*;

import com.coalvalue.util.HardwareUtil;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class InitTasks  {


    protected transient Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RegisterTasks registerTasks;

    @Autowired
    private CommonProcess commonProcess;


    public String activationCode;

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
    TimeSilcePasswordService timeSilcePasswordService;





    @Autowired
    LiveBroadcast liveBroadcast;


    static  String identity = null;



    public void identity(String echo_session) throws MqttException {










        logger.info("开始识别身份---- " + ":");

        logger.info("获取硬件信息---- " + ":");
    //    systemStatusBroadcast.reportFualtInfo(count++);
        HardwareUtil.getOs();
        if(HardwareUtil.isPiUnix){
            identity = HardwareUtil.getCPUSerial__()[0];
        }else{
            identity ="---";

        }

        if(identity == null){
            return;
        }else{
            logger.info("成功获取了硬件信息---- " + ":"+identity);
        }



        String UUID_TOPIC_default = mqttPublishSample.getUUID_TOPIC_default();
        IMEIconfig imei = mqttPublishSample.getImei();



        Hub.RequestIdentity requestIdentity = Hub.RequestIdentity.newBuilder()
                .setIdentity(identity)
                .setImei(imei.getImei())
                .setTimestamp(Timestamp.valueOf(LocalDateTime.now()).getTime())
                .build();

        Hub.Message message = Hub.Message.newBuilder()
                .setSeq(echo_session)
                .setRequestIdentity(requestIdentity)
                .build();
        mqttClient.publish(UUID_TOPIC_default, message.toByteArray(),2,false);
        logger.info("成功发送注册信息----identity{}, imei{} ",identity,imei);







    }
    public void completeIdentity(String echo_session, String channelId, String activationCode_, String status, String objectUuid, String companyNo, List<Hub.MqttTopic> topicsList) {
     //   systemStatusBroadcast.completeEastanblishChanel = true;
        logger.info("身份识别成功，初始化开始");
        activationCode = activationCode_;






        // TODO 以上建立了 新的 通道， 开始


        if(EchoSessionTypeEnum.Identity_bootup.getText().equals(echo_session)){








            logger.info("activationCode，初始化开始"+activationCode);
            List<TopicQos> topicQos = new ArrayList<>();
            for(Hub.MqttTopic topic : topicsList){
                if(topic.getName().equals("channelId")){
                    mqttPublishSample.setChannal_topic(topic.getTopic());

                    topicQos.add(TopicQos.of(topic.getTopic(), MqttPublishSample.qos_2));
                }
                if(topic.getName().equals("public")){
                    mqttPublishSample.setPublic_topic(topic.getTopic());

                    topicQos.add(TopicQos.of(topic.getTopic(), MqttPublishSample.qos_2));
                }
                if(topic.getName().equals("region")){
                    mqttPublishSample.setRegion_topic(topic.getTopic());

                    topicQos.add(TopicQos.of(topic.getTopic(), MqttPublishSample.qos_2));
                }
            }
            mqttPublishSample.subscribe(topicQos);



/*            Message<RegEventEnum> messageState = MessageBuilder
                    .withPayload(RegEventEnum.IDENTITY_SUCCESS)
                    .setHeader("ORDER_ENTITY_KEY", "order")
                    .build();
            stateMachine.sendEvent(messageState);*/



            if(status.equals(ProjectStatusEnum.Unbinded.getText())){
                commonProcess.deleteAll();
                System.out.println("删除了数据库啊啊啊，");

                // TODO 发送删除数据 通知




   /*         register();
            syncService.syncCompare();*/

            }


            if(status.equals(ProjectStatusEnum.Binded.getText())){


                logger.info("该设备 服务器端 已经注册");
                Configuration configuration =  configurationService.getConfiguration(ConfigurationServiceImpl.PRODUCER_CONFIGURATION_KEY_companyNo);

                if(configuration!= null) {
                    logger.info("该设备 本地已经注册 "+configuration);
                    if (!configuration.getStringValue().equals(companyNo)) {

                        logger.info("本地绑定 公司  与 与远程 绑定公司 NO NO NO NO  一致");

                        // TODO 发送删除数据 通知  privateNotify.deleteAll(); System.out.println("删除了数据库啊啊啊，");


                    }

                    logger.info("识别发现 本地绑定 公司  与 与远程 绑定公司 一致");
                }


                logger.info("识别发现 本地 没有注册");




/*                Message<RegEventEnum> messageState_ = MessageBuilder
                        .withPayload(RegEventEnum.REGISTER)
                        .setHeader("ORDER_ENTITY_KEY", "order")
                        .build();
                stateMachine.sendEvent(messageState_);*/
                registerTasks.register(EchoSessionTypeEnum.Register_bootup.getText());






            }




        }




/*      liveBroadcast.postConstruct();
        timeSilcePasswordService.executeTaskT_getTimeSilce();*/
    }









}

