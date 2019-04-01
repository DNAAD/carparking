package com.coalvalue.configuration;



import com.coalvalue.domain.pojo.IMEIconfig;
import com.coalvalue.domain.pojo.StatusInfo;
import com.coalvalue.service.ConfigurationService;
import com.coalvalue.service.assistant.ModuleMqttClientConfig;
import com.coalvalue.configuration.state.RegEventEnum;
import com.coalvalue.configuration.state.RegStatusEnum;
import com.coalvalue.statemachine.Events;
import com.coalvalue.task.SystemStatusBroadcast;
import com.coalvalue.util.EncryUtil;
import com.coalvalue.web.HardWareCommandService;
import com.github.dockerjava.api.DockerClient;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent>, Ordered {
    private Logger logger = LoggerFactory.getLogger(ApplicationReadyEventListener.class);
    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);


    @Autowired
    SystemStatusBroadcast systemStatusBroadcast;





    public static String alreadyConfigured ="";


    @Autowired
    HardWareCommandService hardWareCommandService;

    @Autowired
    ModuleMqttClientConfig moduleMqttClientConfig
            ;

    @Autowired
    ConfigurationService configurationService
            ;
    static public Boolean isBind =false;
    static public Boolean isStart =false;


    @Value("${own.configuration.up_local_mqtt}")
    private Boolean up_local_mqtt;
    @Value("${IMEI}")
    private String IMEI;
    @Autowired
    MqttPublishSample mqttPublishSample;


    @Autowired
    private StateMachine<RegStatusEnum, RegEventEnum> stateMachine;

    @Autowired
    Docker docker;


/*
    @Value("${serviceUrl}")
    private String serviceUrl;
*/


/*
    @Value("${sendMessageUrl}")
    private String sendMessageUrl;
*/


    //@Value("${Key}")
   // private String Key;//读取config文件中的key值
    //解密



  //    docker.isValid(dockerClient);

    private Boolean desDecode(String str) {
        String t = "";
        //System.out.println("加密后：" + EncryUtil.encrypt(t));
        t = EncryUtil.decrypt(str);
        //System.out.println("解密后：" + t);
        if(t.equals("perpetual license")) {
            return true;
        }else {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String nowDate = format.format(date);
            Integer result = EncryUtil.compareDate(t,nowDate);
            if(result == -1) {
                return false;
            }
        }

        return true;
    }
    //加密
    private String desCode(String str) {
        //str为加密的截止日期
        String t = EncryUtil.encrypt(str);
        //System.out.println("加密后：" + t);
        return t;
    }


    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        logger.info("系统启动完成，准备建立通道");

        System.out.println("Ip: " + GetNetworkAddress.GetAddress("ip"));
        System.out.println("Mac: " + GetNetworkAddress.GetAddress("mac"));


        logger.info("测试docker start");

        IMEIconfig imeIconfig = new IMEIconfig(IMEI,alreadyConfigured);
        imeIconfig.setAlreadyDocker(docker.isValid());
        imeIconfig.setAlreadyProperty(isValid());
        imeIconfig.setAlreadyMqtt(isValidMqtt());
        imeIconfig.setAlreadyLocalMqtt(isValidLocalMqtt());

        mqttPublishSample.setImei(imeIconfig);

        logger.info("测试docker end");





        executorService.execute(new Runnable() {
            @Override
            public void run() {

                logger.info("开始连接");
                stateMachine.start();



                System.out.println("发送开始连接时间======================================IDENTITY");






                Message<RegEventEnum> message = MessageBuilder
                        .withPayload(RegEventEnum.CONNECT)
                        .setHeader("ORDER_ENTITY_KEY", "order")
                        .build();

                stateMachine.sendEvent(message);


                try {
                    mqttPublishSample.reconnect();
                } catch (MqttException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("已经发送了 连接 事件 ===================================IDENTITY"+stateMachine.getState().getId().name());




                /*try {
                    JSONObject result = TulingApiProcess.getTulingResult(serviceUrl,"");

                    String apiUrl = result.getString("register_url");
                    TulingApiProcess.getTulingResultPostStateEvent(apiUrl, null,jsonObject);
                }catch (Exception e){
                    e.printStackTrace();
                }*/




            }
        });



        logger.debug("导入 配置信息到 内存 MqttClient");
        configurationService.init();

        try {
            logger.debug(" 更新 配置信息");
            configurationService.givenUsingTimer_whenStoppingThread_thenTimerTaskIsCancelled();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }



    public StatusInfo isValid(){
        StatusInfo statusInfo = new StatusInfo();
        logger.info("查找配置文件");

        String property = (String)getParamChanges().get("imei");
        if(StringUtils.isBlank(property)){

            //if(StringUtils.isBlank(mqttPublishSample.imei)){

            logger.debug("未找到配置文件");
            logger.info("查找给环境变量");

            if(IMEI!= null){
                alreadyConfigured="配置设备,来自于 环境变量";
                logger.info("找到了 环境配置 imei",alreadyConfigured);

                isBind = true;

            }else{
                logger.info("没有找到环境 配置imei，");
                isBind = false;
            }

        }else{

            alreadyConfigured="找到配置文件";
            isBind = true;


        }
        statusInfo.setStatus(isBind);
        statusInfo.setMessage(alreadyConfigured);
        return statusInfo;

    }

    public StatusInfo isValidMqtt(){
        StatusInfo statusInfo = new StatusInfo();



        statusInfo.setStatus(null);
        statusInfo.setMessage(stateMachine.getState().getId().name());
        return statusInfo;

    }

    public StatusInfo isValidLocalMqtt(){

        if(up_local_mqtt){
            logger.debug("===============开始连接 本地 MqttClient");
            try {
                moduleMqttClientConfig.retryWhenException();
            } catch (MqttException e) {
                System.out.println("-建立 或 连retryWhenException接 异常个");
                e.printStackTrace();
            }catch (Exception e) {
                System.out.println("-qException");
                e.printStackTrace();
            }

            moduleMqttClientConfig.chrome();
        }else{
            logger.debug("不开启本地mqtt连接");
        }

        StatusInfo statusInfo = new StatusInfo();



        statusInfo.setStatus(null);
       // statusInfo.setMessage(networkStateMachine.getState().getId().name());
        return statusInfo;

    }

    public Properties getParamChanges() {

        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream(new File(System.getProperty("user.home"),"app-properties.properties"));
            prop.load(input);
            // gMapReportUrl = prop.getProperty("gMapReportUrl");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {

        }

        return prop;
    }
    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }


}
