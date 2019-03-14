package com.coalvalue.service.other;

import com.coalvalue.domain.entity.Configuration;
import com.coalvalue.domain.entity.Employee;
import com.coalvalue.domain.entity.User;
import com.coalvalue.notification.NotificationConsumer;
import com.coalvalue.repository.EmployeeRepository;
import com.coalvalue.service.*;
import com.coalvalue.task.FaceRemoteControl;
import com.coalvalue.task.SystemStatusBroadcast;
import com.coalvalue.web.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static com.coalvalue.configuration.WebSocketConfig.*;
//import org.springframework.security.core.Authentication;

/**
 * Created by silence yuan on 2015/6/28.
 */
@Service("generalService")
public class GeneralServiceImpl extends BaseServiceImpl {

    @Autowired
    private InventoryService inventoryService;


    @Autowired
    private DistributorService distributorService;

    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private FaceRemoteControl faceRemoteControl;
    @Autowired
    private EmployeeRepository employeeRepository;


    @Autowired
    private SystemStatusBroadcast systemStatusBroadcast;

    private static final Logger logger = LoggerFactory.getLogger(GeneralServiceImpl.class);





    public void setGeneral(ModelAndView modelAndView, String facePage, Authentication authentication) throws Exception{
        String welcomeUrl = linkTo(methodOn(MobileIndexController.class).welcome("")).withSelfRel().getHref();
        modelAndView.addObject("welcomeUrl",welcomeUrl);

        faceRemoteControl.deal(modelAndView,facePage);
        List<Map> mainLinks= new ArrayList<Map>();


        List<Map> links = new ArrayList<Map>();

        String locationsIndexUrl = linkTo(methodOn(MobilePlateController.class).index("",null)).withSelfRel().getHref();

        Map map = new HashMap();
        map.put("name", "智能识别");
        map.put("url", locationsIndexUrl);

        links.add(map);

        String tripsIndexUrl = linkTo(methodOn(MobileDeliveryOrderController.class).index("",null)).withSelfRel().getHref();


        map = new HashMap();
        map.put("name", "提煤出库");
        map.put("url", tripsIndexUrl);

        links.add(map);
        String transportUrl = linkTo(methodOn(MobileInstanceTransprotController.class).index("",null)).withSelfRel().getHref();


        map = new HashMap();
        map.put("name", "库存作业");
        map.put("url", transportUrl);

        links.add(map);


        String transferUrl = linkTo(methodOn(MobileTransferController.class).index("",null)).withSelfRel().getHref();
        map = new HashMap();
        map.put("name", "库存出库");
        map.put("url", transferUrl);
        links.add(map);



        String synthesizedUrl = linkTo(methodOn(SynthesizedController.class).index(null,null)).withSelfRel().getHref();


        modelAndView.addObject("synthesizedUrl",synthesizedUrl);

        String distributorUrl = linkTo(methodOn(MobileDistributorController.class).index("",null)).withSelfRel().getHref();


        map = new HashMap();
        map.put("name", "开户商");
        map.put("url", distributorUrl);

        links.add(map);




        String productUrl = linkTo(methodOn(MobileInventoryController.class).index("",null)).withSelfRel().getHref();


        map = new HashMap();
        map.put("name", "库存");
        map.put("url", productUrl);

        links.add(map);

        String storageUrl = linkTo(methodOn(MobileStorageController.class).index("",null)).withSelfRel().getHref();
        map = new HashMap();
        map.put("name", "堆场");
        map.put("url", storageUrl);
        links.add(map);

        String reconciliationUrl = linkTo(methodOn(MobileReconciliationController.class).index("",null)).withSelfRel().getHref();
        map = new HashMap();
        map.put("name", "对账");
        map.put("url", reconciliationUrl);

        links.add(map);

        String quipmentUrl = linkTo(methodOn(MobileCameraController.class).index("",null)).withSelfRel().getHref();


        map = new HashMap();
        map.put("name", "设备");
        map.put("url", quipmentUrl);

        links.add(map);

        String configurationUrl = linkTo(methodOn(MobileIndexController.class).searchCompanies("",null)).withSelfRel().getHref();


        map = new HashMap();
        map.put("name", "配置");
        map.put("url", configurationUrl);
        links.add(map);


        String employeeUrl = linkTo(methodOn(MobileEmployeeController.class).index("",null)).withSelfRel().getHref();


        map = new HashMap();
        map.put("name", "职工");
        map.put("url", employeeUrl);
        links.add(map);


        String reportUrl = linkTo(methodOn(MobileReportController.class).statistic(null)).withSelfRel().getHref();


        map = new HashMap();
        map.put("name", "今日汇总");
        map.put("url", reportUrl);
        mainLinks.add(map);


        Configuration configuration = configurationService.getConfiguration(ConfigurationServiceImpl.PRODUCER_CONFIGURATION_KEY_companyName);
        if(configuration!= null){
            modelAndView.addObject("companyName", configuration.getStringValue());
        }

        modelAndView.addObject("links",links);

        modelAndView.addObject("mainLinks",mainLinks);

        if(authentication!= null && authentication.isAuthenticated()){
            User user = (User)authentication.getPrincipal();
            Employee employee = employeeRepository.findByUserNo(user.getUserId());
            String userUrl = linkTo(methodOn(MobileEmployeeController.class).detail(employee.getUuid(),null)).withSelfRel().getHref();
            modelAndView.addObject("userUrl",userUrl);
        }


    }


    private String websocket_server_url ="http://localhost:6080/ws";// UriComponentsBuilder.newInstance().scheme("http").host("localhost:6080").path("hello").();




    public void setWebSocketUrl(String synthesized, Authentication authentication, ModelAndView modelAndView, String no) {



        if(synthesized.equals("synthesized")){
            modelAndView.addObject("websocket_url", websocket_server_url);
            UriComponentsBuilder end_point = UriComponentsBuilder.newInstance();
            //UriComponents uriComponents = end_point.path(NotificationConsumer.topic__COALPIT_DELIVERY_workbench"/topic/COALPIT_DELIVERY_workbench/{storageId}").buildAndExpand(id);
            UriComponents uriComponents = end_point.path(topic__COALPIT_DELIVERY_workbench).buildAndExpand();
            logger.debug("---COALPIT_DELIVERY_workbench--------------- :{}", uriComponents.toString());
            modelAndView.addObject("websocket_topic", uriComponents.toUriString());

        }

        if(synthesized.equals("scan")){
            modelAndView.addObject("websocket_url", websocket_server_url);
            UriComponentsBuilder end_point = UriComponentsBuilder.newInstance();
            UriComponents uriComponents = end_point.path(topic__COALPIT_DELIVERY_scan+no).buildAndExpand();
            logger.debug("---COALPIT_DELIVERY_workbench--------------- :{}", uriComponents.toString());
            modelAndView.addObject("websocket_topic", uriComponents.toUriString());

        }

        if(synthesized.equals("report")){

            List<String> websocket_topics = new ArrayList<>();

            modelAndView.addObject("websocket_url", websocket_server_url);
            UriComponentsBuilder end_point = UriComponentsBuilder.newInstance();
            UriComponents uriComponents = end_point.path(topic__COALPIT_DELIVERY_report).buildAndExpand();
            logger.debug("---COALPIT_DELIVERY_workbench--------------- :{}", uriComponents.toString());
            modelAndView.addObject("websocket_topic", uriComponents.toUriString());

            websocket_topics.add(uriComponents.toUriString());




            end_point = UriComponentsBuilder.newInstance();
            uriComponents = end_point.path(topic__COALPIT_DELIVERY_welcome).buildAndExpand();
            logger.debug("---COALPIT_DELIVERY_workbench--------------- :{}", uriComponents.toString());
            websocket_topics.add(uriComponents.toUriString());


            modelAndView.addObject("websocket_topic_welcome", uriComponents.toUriString());
        }
        if(synthesized.equals("status")){
            modelAndView.addObject("websocket_url", websocket_server_url);
            UriComponentsBuilder end_point = UriComponentsBuilder.newInstance();
            UriComponents uriComponents = end_point.path(topic__COALPIT_DELIVERY_status).buildAndExpand();
            logger.debug("---COALPIT_DELIVERY_workbench--------------- :{}", uriComponents.toString());
            modelAndView.addObject("websocket_topic_status", uriComponents.toUriString());
            modelAndView.addObject("status", systemStatusBroadcast.getPageShownReportFualtInfo());

        }
        if(synthesized.equals("auth_bind")){
            modelAndView.addObject("websocket_url", websocket_server_url);
            UriComponentsBuilder end_point = UriComponentsBuilder.newInstance();
            UriComponents uriComponents = end_point.path(topic__COALPIT_DELIVERY_auth_bind).buildAndExpand();
            logger.debug("---COALPIT_DELIVERY_workbench--------------- :{}", uriComponents.toString());
            modelAndView.addObject("websocket_topic", uriComponents.toUriString());

           // modelAndView.addObject("status", systemStatusBroadcast.getPageShownReportFualtInfo());

        }
        if(synthesized.equals("welcome")){
            modelAndView.addObject("websocket_url", websocket_server_url);
            UriComponentsBuilder end_point = UriComponentsBuilder.newInstance();
            UriComponents uriComponents = end_point.path(topic__COALPIT_DELIVERY_welcome).buildAndExpand();
            logger.debug("---COALPIT_DELIVERY_workbench--------------- :{}", uriComponents.toString());
            modelAndView.addObject("websocket_topic", uriComponents.toUriString());


        }


    }


    public void getStatistic(Authentication authentication, ModelAndView modelAndView, Integer id) {



        Map map = new HashMap<>();


        map.put("inventoryCount", inventoryService.getInventoryCount());

        map.put("distributorCount", distributorService.getDistributorNumber());


        List<Map> distributors_map = distributorService.query(null,new PageRequest(0,100)).getContent();

        String distributors_management_url = null;
        try {
            distributors_management_url = linkTo(methodOn(MobileDistributorController.class).index(null,null)).toUri().toASCIIString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("distributors_management_url", distributors_management_url);

        map.put("distributors", distributors_map);
        modelAndView.addObject("statistic", map);


        List<Map> inventories_map = inventoryService.queryAll(new PageRequest(0,100)).getContent();


        String inventories_management_url = linkTo(methodOn(MobileInventoryController.class).index(null,null)).toUri().toASCIIString();
        map.put("inventories_management_url", inventories_management_url);

        map.put("inventories", inventories_map);




    }


}
