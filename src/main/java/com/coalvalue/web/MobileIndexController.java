package com.coalvalue.web;

import static com.coalvalue.configuration.WebSocketConfig.*;

import com.alibaba.fastjson.JSON;
import com.coalvalue.configuration.ApplicationReadyEventListener;
import com.coalvalue.configuration.MqttPublishSample;
import com.coalvalue.domain.entity.Configuration;
import com.coalvalue.domain.entity.StorageSpace;
import com.coalvalue.domain.pojo.IMEIconfig;
import com.coalvalue.repository.StorageSpaceRepository;
import com.coalvalue.service.*;
import com.coalvalue.service.assistant.BonjourService;
import com.coalvalue.service.assistant.ChromeService;
import com.coalvalue.service.assistant.OpenvpnService;
import com.coalvalue.service.other.GeneralServiceImpl;
import com.coalvalue.task.AuthQrcodeTasks;
import com.coalvalue.task.SystemStatusBroadcast;

import com.coalvalue.util.HardwareUtil;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

//import org.springframework.security.core.Authentication;

//import com.coalvalue.repositorySecondary.ProductRepository;

/**
 * Created by silence yuan on 2015/7/12.
 */

@Controller
@RequestMapping/*(value= {"/"})*/
public class MobileIndexController {
    private static final Logger logger = LoggerFactory.getLogger(MobileIndexController.class);


    @Autowired
    private OpenvpnService openvpnService;
    @Autowired
    private ChromeService chromeService;


    @Value("${own.configuration.mqtt.uuid_topic_default}")
    public String UUID_TOPIC_default;
    String topic = RandomStringUtils.randomAlphanumeric(20).toUpperCase();

    public String listen_uuid = "listen"+topic;
    public static String topic_delivery_web_server        = "delivery_web_server";

    public static final String mqtt_image_registry = "mqtt.image_registry";

    public static int qos             = 2;
    public static int qos_2             = 2;
    @Value("${own.configuration.mqtt.broker.url}")
    private String broker;




    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    @Autowired
    private StorageSpaceRepository storageSpaceRepository;


    @Autowired
    private GeneralServiceImpl generalService;

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private AuthQrcodeTasks authQrcodeTasks;
    @Autowired
    private SystemStatusBroadcast systemStatusBroadcast;
    @Autowired
    private BonjourService bonjourService;

    @Autowired
    private HardWareCommandService hardWareCommandService;

    @Autowired
    private MqttPublishSample mqttPublishSample;


/*    @Autowired
    private UpdateFX updater;

    */

    @GetMapping("/")
    public ModelAndView searchCompanies(@RequestParam(value = "q", required = false) String searchTerm,Authentication authentication) throws Exception  {


        ModelAndView modelAndView = new ModelAndView("/index");
        modelAndView.addObject("q",searchTerm);
        //mobileService.setAuthenticationInfo(authentication,modelAndView);

        Configuration configuration = configurationService.getConfiguration(ConfigurationServiceImpl.PRODUCER_CONFIGURATION_KEY_activation_code);

        if(configuration!= null){
            String workbenchUrl = linkTo(methodOn(SynthesizedController.class).index(null,null)).withSelfRel().getHref();
            modelAndView = new ModelAndView("redirect:"+workbenchUrl);
            return modelAndView;
        }


        modelAndView.addObject("navIndex", "search");

        String infoUrl = linkTo(methodOn(MobileIndexController.class).info("",null)).withSelfRel().getHref();
        modelAndView.addObject("infoUrl",infoUrl);
        String gateWayUrl = linkTo(methodOn(MobileIndexController.class).gateWays("",null)).withSelfRel().getHref();
        modelAndView.addObject("gateWayUrl",gateWayUrl);

        String serverUrl = linkTo(methodOn(MobileIndexController.class).service("",null)).withSelfRel().getHref();
        modelAndView.addObject("serverUrl",serverUrl);


        generalService.setGeneral(modelAndView, "", authentication);

        generalService.setWebSocketUrl("auth_bind",null,modelAndView,null);
        authQrcodeTasks.reportCloudDeliveryServer();

        String executeUrl = linkTo(methodOn(MobileIndexController.class).execute()).withSelfRel().getHref();
        modelAndView.addObject("executeUrl",executeUrl);
        String focusUrl = linkTo(methodOn(MobileIndexController.class).focus("",null)).withSelfRel().getHref();
        modelAndView.addObject("focusUrl",focusUrl);

        String focuseCommandUrl = linkTo(methodOn(MobileIndexController.class).focuseCommand(null)).toUri().toString();
        modelAndView.addObject("focuseCommandUrl",focuseCommandUrl);



        String storageUrl = linkTo(methodOn(MobileStorageController.class).stations(null,null)).toUri().toString();
        modelAndView.addObject("storageUrl",storageUrl);


        configuration =  configurationService.getConfiguration(ConfigurationServiceImpl.PRODUCER_CONFIGURATION_KEY_companyName);
        if(configuration!= null){
            modelAndView.addObject("companyName", configuration.getStringValue());

        }


        String configurationUrl = linkTo(methodOn(MobileConfigurationController.class).index("",null)).withSelfRel().getHref();
        modelAndView.addObject("configurationUrl",configurationUrl);


        String newQueryUrl = linkTo(methodOn(MobileIndexController.class).newQuery(null)).toUri().toString();
        modelAndView.addObject("authUrl",newQueryUrl);


        URL serviceStart = linkTo(methodOn(MobileIndexController.class).serviceStart(null)).toUri().toURL();
        modelAndView.addObject("serviceStartUrl",serviceStart);
        String stopStart = linkTo(methodOn(MobileIndexController.class).stopStart(null)).toUri().toString();
        modelAndView.addObject("serviceStopUrl",stopStart);
   //     modelAndView.addObject("updaterRlease",updater.getApplication());


        String requestPageUrl = linkTo(methodOn(MobileIndexController.class).jmdns("",null)).withSelfRel().getHref();
        modelAndView.addObject("jmdnsUrl",requestPageUrl);

        return modelAndView;
    }


    @RequestMapping(value = "/info/gateWays", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> gateWays(@RequestParam(value = "q", required = false) String searchTerm, @PageableDefault Pageable pageable)  {


        List<Map> maps = systemStatusBroadcast.gateWayinfo();

        System.out.println("------------"+pageable.toString());
        return new PageImpl<Map>(maps, pageable, maps.size());


    }
    @RequestMapping(value = "/info/jmdns", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> jmdns(@RequestParam(value = "q", required = false) String searchTerm, @PageableDefault Pageable pageable)  {


        return bonjourService.requestPage(pageable);



    }


    @RequestMapping(value = "/configurationInfo", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> info(@RequestParam(value = "q", required = false) String searchTerm, @PageableDefault Pageable pageable)  {

        String[] strings = searchTerm.split(" ");

        searchTerm = searchTerm.replace(",","");
        logger.debug("----------------- param is :{}", searchTerm);
        if (searchTerm != null) {
            //     companies = searchService.searchCompany(searchTerm);
        }
        logger.debug("we are in search company");


        List<Map> maps = systemStatusBroadcast.info();

        return new PageImpl<Map>(maps, pageable, maps.size());


    }

    @RequestMapping(value = "/execute", method = RequestMethod.POST)
    @ResponseBody

    public Map execute() {

        logger.debug("----- param is  id : {},  price:{}, notificationToIds:{}, returnTo:{}，sendMessageToFollower is:{}");

        Map ret = new HashMap<String, String>();
        ret.put("status", false);


        hardWareCommandService.execute();

        return ret;

    }
    @RequestMapping(value = "/focus/command", method = RequestMethod.POST)
    @ResponseBody
    public Map focuseCommand(@RequestParam(value = "uuid", required = false) String uuid) {

        System.out.print(uuid+"----- param is  id : {},  price:{}, notificationToIds:{}, returnTo:{}，sendMessageToFollower is:{}");


        Map ret = new HashMap<String, String>();
        ret.put("status", false);
        Map content = new HashMap();

        content.put("type", "redirect");
        System.out.print(uuid+"----sendMessageToFollower is:{}"+dynamic.toString());

        Map map = dynamic.stream().filter(e->e.get("uuid").equals(uuid)).findAny().get();
        String focusRedirectUrl = linkTo(methodOn(MobileIndexController.class).focusRedirect((String)map.get("uuid"))).toUri().toString();


        content.put("content",focusRedirectUrl);


        simpMessagingTemplate.convertAndSend(topic__COALPIT_DELIVERY_welcome, JSON.toJSON(content));
        return ret;

    }

    @RequestMapping(value = "/welcome" ,method = RequestMethod.GET)
    public ModelAndView welcome(@RequestParam(value = "q", required = false) String searchTerm) throws Exception{//,Authentication authentication)  {
        readyStaticFocus();


        ModelAndView modelAndView = new ModelAndView("/welcome");




        modelAndView.addObject("isBind", ApplicationReadyEventListener.isBind);

        generalService.setWebSocketUrl("welcome",null,modelAndView,null);


        URI uri_local = linkTo(methodOn(MobileIndexController.class).searchCompanies(null,null)).toUri();

        try {
            URI uri = new URI("http",null,HardwareUtil.getServerIp(),80,uri_local.getPath(),uri_local.getQuery(),null);
            modelAndView.addObject("configurationUrl",uri.toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        Configuration configuration =  configurationService.getConfiguration(ConfigurationServiceImpl.PRODUCER_CONFIGURATION_KEY_companyName);
        if(configuration!= null){
            modelAndView.addObject("companyName", configuration.getStringValue());

        }


        String workbenchUrl = linkTo(methodOn(SynthesizedController.class).index(null,null)).toUri().toString();

        modelAndView.addObject("workbenchUrl", workbenchUrl);
        return modelAndView;
    }





    @RequestMapping(value = "/focus" ,method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> focus(@RequestParam(value = "q", required = false) String searchTerm, @PageableDefault Pageable pageable) throws Exception {


        System.out.println("----------------- param is :{}"+searchTerm);

        System.out.println("we are in search company");


        List<Map> maps = focus();

        return new PageImpl<Map>(maps, pageable, maps.size());


    }

    public static List<Map> staticMaps = null ;

    public static List<Map> dynamic = new ArrayList<>();
    public List<Map> focus()  throws Exception {
        readyStaticFocus();

        dynamic = new ArrayList<>();

        dynamic.addAll(staticMaps);


            List<StorageSpace> storageSpaces = storageSpaceRepository.findAll();


            for(StorageSpace storageSpace: storageSpaces){
                Map<String,Object> objectMap = new HashMap<>();
                objectMap.put("name",storageSpace.getName()+"/"+storageSpace.getNo());
                String infoUrl = linkTo(methodOn(MobileReportController.class).index(storageSpace.getNo(),null)).withSelfRel().getHref();
                objectMap.put("uuid", storageSpace.getUuid());
                objectMap.put("url",infoUrl);
                dynamic.add(objectMap);
            }



        return dynamic;//new PageImpl<Map>(maps, pageable, maps.size());


    }


    public void readyStaticFocus() throws Exception{
        if(staticMaps == null){
            staticMaps= new ArrayList<>();
            Map<String,Object> objectMap = new HashMap<>();
            objectMap.put("name","welcome");
            objectMap.put("uuid", UUID.randomUUID().toString());
            String infoUrl = linkTo(methodOn(MobileIndexController.class).welcome("")).withSelfRel().getHref();

            objectMap.put("url",infoUrl);
            staticMaps.add(objectMap);
        }
        dynamic.addAll(staticMaps);
    }



    @RequestMapping(value = "/focus/Redirect/{uuid}" ,method = RequestMethod.GET)
    public ModelAndView focusRedirect(@PathVariable(value = "uuid", required = false) String uuid){//,Authentication authentication)  {

        Map map = dynamic.stream().filter(e->e.get("uuid").equals(uuid)).findAny().get();

        return new ModelAndView("redirect:"+map.get("url"));


    }


    @PostMapping("/newQuery")
    @ResponseBody
    public Map newQuery(@RequestParam(value = "imei",required = false) String imei_)  {

        String fileName = "QueryMap.json";

        IMEIconfig imeIconfig = mqttPublishSample.setImei(new IMEIconfig(imei_,"aaaaa"));

        mqttPublishSample.end();



        ClassLoader classLoader = getClass().getClassLoader();

        System.getProperty("user.home");
        //File file = new File(classLoader.getResource(".").getFile() + "/test.xml");
        saveParamChanges(imeIconfig.getImei());

        String property = (String)getParamChanges().get("imei");

        System.out.println("------------------ : {}"+ property);
   /*     File file = new File(System.getProperty("user.home") + "/test.xml");

        System.setProperty("imei", mqttPublishSample.imei);

        if (file.createNewFile()) {


            System.out.println("File is created!");
        } else {
            System.out.println("File already exists.");
        }
      */
        return new HashMap();
    }


    public void saveParamChanges(String imei) {
        try {
            // create and set properties into properties object
            Properties props = new Properties();
            props.setProperty("imei", imei);
            props.setProperty("Prop2", "test");
            props.setProperty("Prop3", "tata");
            // get or create the file
            File f = new File(System.getProperty("user.home"),"app-properties.properties");
            OutputStream out = new FileOutputStream( f );
            // write into it
            DefaultPropertiesPersister p = new DefaultPropertiesPersister();
            p.store(props, out, "Header COmment");
        } catch (Exception e ) {
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








    @RequestMapping(value = "/info/server", method = RequestMethod.GET)
    @ResponseBody
    public Page<Map> service(@RequestParam(value = "q", required = false) String searchTerm, @PageableDefault Pageable pageable)  {


        List<Map> maps = new ArrayList<>();
        Map<String,Object> serviceObjectMap = new HashMap<>();
        serviceObjectMap.put("name",openvpnService.getClass().getName());
        serviceObjectMap.put("url",openvpnService.getClass());
        maps.add(serviceObjectMap);

        serviceObjectMap = new HashMap<>();
        serviceObjectMap.put("name",chromeService.getClass().getName());
        serviceObjectMap.put("url",chromeService.getClass());
        maps.add(serviceObjectMap);


/*        serviceObjectMap = new HashMap<>();
        serviceObjectMap.put("name",updater.getClass().getName());
        serviceObjectMap.put("url",updater.getClass());
        maps.add(serviceObjectMap);*/

      //  updater.checkUpdates();

        System.out.println("------------"+pageable.toString());
        return new PageImpl<Map>(maps, pageable, maps.size());


    }


    @RequestMapping(value = "/service/start", method = RequestMethod.POST)
    @ResponseBody
    public Map serviceStart(@RequestParam(value = "name", required = false) String name) {

        Map ret = new HashMap<String, String>();
        if(name.equals(openvpnService.getClass().getName())){
            System.out.println("---------------{}"+name);

            openvpnService.start();
            ret.put("status", false);
        }







        return ret;

    }
    @RequestMapping(value = "/service/stop", method = RequestMethod.POST)
    @ResponseBody
    public Map stopStart(@RequestParam(value = "name", required = false) String name) {

        Map ret = new HashMap<String, String>();
        if(name.equals(openvpnService.getClass())){
            openvpnService.stop();
            ret.put("status", false);

        }







        return ret;

    }

}
