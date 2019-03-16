package com.coalvalue.configuration;



import com.coalvalue.service.ConfigurationService;
import com.coalvalue.service.assistant.ModuleMqttClientConfig;
import com.coalvalue.task.SystemStatusBroadcast;
import com.coalvalue.util.EncryUtil;
import com.coalvalue.web.HardWareCommandService;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
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
    MqttPublishSample mqttPublishSample;


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
        logger.debug("系统启动完成，准备建立通道");

        logger.debug("begin ------------------------------------"+mqttPublishSample.imei);
    //    logger.debug("begin ------------------------------------"+desDecode(Key));

/*
        Map map = new HashMap<>();
        map.put("id", ServiceTypeEnum.INDEX_STATISTIC.getText());
        map.put("Body","I like pie");
        Map date = new HashMap<>();
        date.put("name", ServiceTypeEnum.INDEX_STATISTIC.getDisplayText());

        map.put("data",date);


        String jsonObject = JSON.toJSONString(map);
*/

        //InitTasks.init_configuration();
        logger.debug("查找配置文件");
        String property = (String)getParamChanges().get("imei");
        if(StringUtils.isBlank(property)){

        //if(StringUtils.isBlank(mqttPublishSample.imei)){






            logger.debug("未找到配置文件");

            alreadyConfigured="未配置设备";

        }else{

            alreadyConfigured="找到配置文件";
            isBind = true;
            mqttPublishSample.imei = property;// "868784021789953";

            //stateMachine.start();
            executorService.execute(new Runnable() {
                @Override
                public void run() {

                    logger.debug("开始连接");
               //   mqttPublishSample.start();
                    try {
                        mqttPublishSample.reconnect();
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                /*try {
                    JSONObject result = TulingApiProcess.getTulingResult(serviceUrl,"");

                    String apiUrl = result.getString("register_url");
                    TulingApiProcess.getTulingResultPostStateEvent(apiUrl, null,jsonObject);
                }catch (Exception e){
                    e.printStackTrace();
                }*/


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
                }else{
                    logger.debug("不开启本地mqtt连接");
                }








                }
            });

        }

        logger.debug("导入 配置信息到 内存 MqttClient");
        configurationService.init();

        try {
            logger.debug(" 更新 配置信息");
            configurationService.givenUsingTimer_whenStoppingThread_thenTimerTaskIsCancelled();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
