package com.coalvalue.service;


//import com.LPRMain;
import com.alibaba.fastjson.JSON;
import com.coalvalue.configuration.Constants;
import com.coalvalue.configuration.MqttReceiver;
import com.coalvalue.domain.entity.Camera;
import com.coalvalue.domain.OperationResult;
import com.coalvalue.domain.entity.Behavioural;
import com.coalvalue.domain.entity.Configuration;
import com.coalvalue.enumType.CameraTypeEnum;
import com.coalvalue.enumType.DataSynchronizationTypeEnum;
import com.coalvalue.notification.NotificationData;
import com.coalvalue.protobuf.Hub;
import com.coalvalue.python.*;
import com.coalvalue.repository.BehaviouralRepository;
import com.coalvalue.repository.CameraRepository;
import com.coalvalue.repository.ConfigurationRepository;
import com.coalvalue.service.strategy.StrategyService;
import com.coalvalue.web.MobileConfigurationController;

import com.google.protobuf.InvalidProtocolBufferException;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Blob;
import java.util.*;
import java.util.function.Function;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by silence yuan on 2015/7/25.
 */

@Service("configurationService")
public class ConfigurationServiceImpl extends BaseServiceImpl implements ConfigurationService {


   // @Value("${deploy.resource.path}")
    private String resource_path;



   // @Value("${deploy.python.path}")
    private String deployDir_path;

  //  @Value("${own.configuration.url}")
    private String ownConfigurationUrl;


    @Autowired
    private BehaviouralRepository behaviouralRepository;

    @Autowired
    private ConfigurationRepository configurationRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LicensePlateDetectionIJavaService licensePlateDetectionIJavaService;


    @Autowired
    private MqttService mqttService;

    @Autowired
    private CameraRepository cameraRepository;

    public static String PRODUCER_CONFIGURATION_KEY_appId = "appId";
    public static String PRODUCER_CONFIGURATION_KEY_appSecret = "appSecret";
    public static String PRODUCER_CONFIGURATION_KEY_companyNo = "companyNo";
    public static String PRODUCER_CONFIGURATION_KEY_companyName = "companyName";
    public static String PRODUCER_CONFIGURATION_KEY_qrcodeContent = "qrcodeContent";
    public static String PRODUCER_CONFIGURATION_KEY_camera_monitor_opened = "cameraMonitorOpened";
    public static String PRODUCER_CONFIGURATION_KEY_camera_license_plate_recognition_opened = "cameraLicensePlateRecognitionOpened";
    public static String PRODUCER_CONFIGURATION_KEY_company_storage_qrcode = "companyStorageQrcode";
    public static String PRODUCER_CONFIGURATION_KEY_activation_code = "activation_code";

    public static String PRODUCER_CONFIGURATION_KEY_imageStrategy = "imageStrategy";


/*    @PostConstruct
    public void defualtInit() {

        Configuration configuration = getConfiguration(PRODUCER_CONFIGURATION_KEY_camera_license_plate_recognition_opened);
        if(configuration!= null && configuration.getBooleanValue()){
            openPlateRecognition();
        }
    }*/

    public Hub.ImageStrategy imageStrategy;
    @Autowired
    MqttReceiver mqttReceiver;


    @Autowired
    StrategyService strategyService;
    Timer timer = new Timer("Timer");
    @Autowired
    MqttClient mqttClient;
    @Override
    @Transactional
    public void create(NotificationData data) {

        boolean isWindows = System.getProperty("os.name")
                .toLowerCase().startsWith("windows");

        Behavioural behavioural = new Behavioural();
        behavioural = behaviouralRepository.save(behavioural);

    }

    @Override
    public Page<Map> query(Object o, Pageable pageable) {

      //  init();
        Page<Configuration> configurations = configurationRepository.findAll(pageable);


        Page<Map> pages = configurations.map(new Function<Configuration, Map>() {
            public Map apply(Configuration objectEntity) {

                return  getContent(objectEntity.getId());
            }
        });

        //Page<Map> pages = new PageImpl<Map>(list,pageable,list.size());

        return pages;
    }


    @Override
    public OperationResult openPlateRecognition() {
        String ip = "192.168.30.214";
        String admin = "admin";
        String password = "admin";

        List<Camera> caremas = cameraRepository.findByType(CameraTypeEnum.license_plate_recognition.getText());
        //List<Camera> caremas = new ArrayList<>();
        for(Camera camera :caremas){
           // LPRMain lprtest = new LPRMain(plateRecognitionService,camera.getIp(),camera.getUsername(),camera.getPassword());
        }

        OperationResult operationResult = new OperationResult();

        operationResult.setSuccess(true);
        return operationResult;
    }

    @Override
    public OperationResult runPython() {

        OperationResult operationResult = new OperationResult();

        operationResult.setSuccess(false);


        List<Camera> cameras = cameraRepository.findByType(CameraTypeEnum.monitor.getText());

        List<Map<String,String>> maps = new ArrayList<>();
        for(Camera camera:cameras){

            Map<String,String> map = new HashMap<String,String>();
            map.put("\"rstpUrl\"","\""+camera.getRstpUrl()+"\"");
            map.put("\"path\"","\""+resource_path+"/"+camera.getPath()+"\"");
            maps.add(map);
        }

        String command_edit_url = linkTo(methodOn(MobileConfigurationController.class).cameras(null, null)).withSelfRel().getHref();


        String a = "\'"+JSON.toJSONString(maps)+"\'", b = command_edit_url, c = "ccc", d = "ddd";
        //String[] args = new String[] { "cmd","/k","python "+deployDir_path+"/detect.py " +b};
//上面要求outfile.py是在java工程的根目录下的。

        //String[] args = new String[] { "cmd","/k","python "+deployDir_path+"/detect.py " +b};
        String args = "python "+ deployDir_path+"/detect.py " + b;
        System.out.println(System.getProperty("java.class.path"));
        Process proc = null;
        try {
            proc = Runtime.getRuntime().exec(args);
           // proc = Runtime.getRuntime().exec("python  /root/Desktop/demo.py");
            StreamHandler errorStreamHandler = new StreamHandler(proc.getErrorStream(), "ERROR");
            errorStreamHandler.start();
            StreamHandler outputStreamHandler = new StreamHandler(proc.getInputStream(), "STDOUT");
            outputStreamHandler.start();
           // proc.waitFor();
            operationResult.setSuccess(true);
        } catch (IOException e) {
            e.printStackTrace();

        }
        return operationResult;
    }

/*

*StreamHandler class

*/

    public class StreamHandler extends Thread
    {
        InputStream m_inputStream;
        String m_type;

        public StreamHandler(InputStream is, String type)
        {
            this.m_inputStream = is;
            this.m_type = type;
        }

        @Override
        public void run()

        {
            InputStreamReader isr = null;
            BufferedReader br = null;

            try
            {

                //设置编码方式，否则输出中文时容易乱码
                isr = new InputStreamReader(m_inputStream, "GBK");
                br = new BufferedReader(isr);
                String line=null;
                while ( (line = br.readLine()) != null)
                {
                    System.out.println("PRINT > " + m_type + " : " + line);
                }
            }
            catch (IOException ioe)
            {
                ioe.printStackTrace();
            }
            finally
            {
                try
                {
                    br.close();
                    isr.close();
                }
                catch (IOException ex)
                {
                    //Logger.getLogger(StreamHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }



    @Override
    @Transactional
    public void init() {


        Configuration configuration = getConfiguration(PRODUCER_CONFIGURATION_KEY_camera_monitor_opened);
        if(configuration == null){
            configuration = new Configuration();
            configuration.setBooleanValue(false);
            configuration.setKey(PRODUCER_CONFIGURATION_KEY_camera_monitor_opened);
            configurationRepository.save(configuration);
        }


        configuration = getConfiguration(PRODUCER_CONFIGURATION_KEY_camera_license_plate_recognition_opened);
        if(configuration == null){
            configuration = new Configuration();
            configuration.setBooleanValue(false);
            configuration.setKey(PRODUCER_CONFIGURATION_KEY_camera_license_plate_recognition_opened);
            configurationRepository.save(configuration);
        }

        configuration = configurationRepository.findByKey(PRODUCER_CONFIGURATION_KEY_imageStrategy);
        if(configuration != null){

            try {
                imageStrategy = Hub.ImageStrategy.parseFrom(configuration.getBlob());
            } catch (InvalidProtocolBufferException e) {
                e.printStackTrace();
            }
        }else{
            imageStrategy = Hub.ImageStrategy.newBuilder()
                    .setPolicyType(Hub.ImageStrategy.PolicyType.MANUAL)
                    .build();
            configuration = new Configuration();
            configuration.setKey(PRODUCER_CONFIGURATION_KEY_imageStrategy);
            configuration.setBlob(imageStrategy.toByteArray());
            configurationRepository.save(configuration);

        }


        System.out.println("-------------------------------- 在配置 初始化中");
    }




    @Override
    public OperationResult getConfigurationFromService_mqtt(String appId, String appIdSecret) {

        Map map = new HashMap<>();
        map.put("appId",appId);
        map.put("appIdSecret",appIdSecret);
        map.put("type",DataSynchronizationTypeEnum.Rgister.getText());


        mqttService.subscribe(appId);
        mqttService.publishTo_core_web_server(JSON.toJSONString(map));

        OperationResult operationResult = new OperationResult();
        operationResult.setSuccess(true);
        return operationResult;

    }





    @Override
    public Configuration getConfiguration(String configuration_key_companyNo) {
        return configurationRepository.findByKey(configuration_key_companyNo);
    }


    public Map getContent(Integer canvassingId){


        Configuration canvassing  = configurationRepository.findById(canvassingId).get();


        Map map = new HashMap<>();
        map.put("stringValue",canvassing.getStringValue());
        map.put("booleanValue",canvassing.getBooleanValue());
        map.put("companyId",canvassing.getCompanyId());
        map.put("id",canvassing.getId());
        map.put("intValue",canvassing.getIntValue());
        map.put("key",canvassing.getKey());
        map.put("uri",canvassing.getType());
        map.put("value",canvassing.getValue());
        map.put("mode",canvassing.getMode());
        map.put("userId",canvassing.getUserId());
        map.put("modifyDate",canvassing.getModifyDate());
        map.put("modifyBy",canvassing.getModifyBy());



        return map;




    }

    public Map getFrontContent(Integer canvassingId){

        Configuration canvassing  = configurationRepository.findById(canvassingId).get();


        HashMap<String,Object> map = new HashMap<>();
        map.put("StringValue",canvassing.getStringValue());
        map.put("BooleanValue",canvassing.getBooleanValue());
        map.put("CompanyId",canvassing.getCompanyId());
        map.put("id",canvassing.getId());
        map.put("intValue",canvassing.getIntValue());
        map.put("key",canvassing.getKey());
        map.put("uri",canvassing.getType());
        map.put("value",canvassing.getValue());
        map.put("mode",canvassing.getMode());
        map.put("userId",canvassing.getUserId());
        map.put("modifyDate",canvassing.getModifyDate());
        map.put("modifyBy",canvassing.getModifyBy());



        return map;

    }
















    private Map<String,IPythonService> pythonServiceMap = new HashMap<>();


    @Autowired
    private QrcodeIPythonService qrcodeIPythonService;

    @Autowired
    private ObjectDetectionDeepLearningPythonService objectDetectionDeepLearningPythonService;

    @Autowired
    private ScreanOnOffIPythonService screanOnOffIPythonService;

    @Override
    public Page<Map> queryModules(Object o, Pageable pageable) {




        List<IPythonService> services = new ArrayList<>();
        services.add(qrcodeIPythonService);
        services.add(objectDetectionDeepLearningPythonService);
        services.add(licensePlateDetectionIJavaService);
        services.add(screanOnOffIPythonService);

        List<Map> maps = get(services);
        Page<Map> pages = new PageImpl<Map>(maps,pageable,maps.size());
        return pages;
    }

    List<Map> get(List<IPythonService> services){
        List<Map> maps  = new ArrayList<>();

        for(IPythonService service : services){
            maps.add(service.getInfo());
            pythonServiceMap.put(service.getId(),service);

        }
        return maps;
    }


    @Override
    public OperationResult startModule(IPythonService iPythonService) {
        OperationResult operationResult = new OperationResult();
        if(iPythonService.isLive()){
            operationResult.setSuccess(false);
            operationResult.setResultMessage("已经运行之中");
            return operationResult;
        }
        System.out.println("开始脚本服务模块");
        iPythonService.start();

        operationResult.setSuccess(true);

        return operationResult;
    }

    @Override
    public IPythonService getModule(String appId) {
        return pythonServiceMap.get(appId);
    }



    @Override
    public OperationResult stopModule(IPythonService iPythonService) {
        OperationResult operationResult = new OperationResult();
        if(iPythonService.isLive()){
            operationResult.setSuccess(false);
            operationResult.setResultMessage("已经运行之中");
            return operationResult;
        }
        System.out.println("开始脚本服务模块");
        iPythonService.start();

        operationResult.setSuccess(true);

        return operationResult;
    }



    @Override
    @Transactional
    public void createImageStrategy(Hub.ImageStrategy imageStrategy) {

        if(imageStrategy!= null){
            Configuration configuration = configurationRepository.findByKey(PRODUCER_CONFIGURATION_KEY_imageStrategy);
                configuration.setBlob(imageStrategy.toByteArray());
                configuration = configurationRepository.save(configuration);
                try {
                    this.imageStrategy = Hub.ImageStrategy.parseFrom(configuration.getBlob());
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
                this.imageStrategy = imageStrategy;


        }


    }




    @Override
    public void givenUsingTimer_whenStoppingThread_thenTimerTaskIsCancelled()
            throws InterruptedException {

        System.out.println(" 更新 imageStrategy");
        if(imageStrategy.getPolicyType().equals(Hub.ImageStrategy.PolicyType.AUTOMATIC)){
            TimerTask task = new TimerTask() {
                public void run() {
                    System.out.println("Task performed on " + new Date());
                    // TODO: stop the thread here
                    strategyService.command(StrategyService.Strategy_COMMAND.Strategy_COMMAND_极少探测_openvpn, "UUID");

                }
            };


            timer = new Timer("Timer");
            timer.scheduleAtFixedRate(task, 1000L, imageStrategy.getInterval()*1000);

            //Thread.sleep(1000L * 2);
        }
        if(imageStrategy.getPolicyType().equals(Hub.ImageStrategy.PolicyType.MANUAL)){

            timer.cancel();
        }

    }
}
