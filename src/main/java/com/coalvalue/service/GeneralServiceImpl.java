package com.coalvalue.service;

import com.coalvalue.web.*;
import com.service.BaseServiceImpl;
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

    private static final Logger logger = LoggerFactory.getLogger(GeneralServiceImpl.class);





    public void setGeneral(ModelAndView modelAndView) {
        List<Map> links = new ArrayList<Map>();

        String locationsIndexUrl = linkTo(methodOn(MobilePlateController.class).index("")).withSelfRel().getHref();

        Map map = new HashMap();
        map.put("name", "车牌识别");
        map.put("url", locationsIndexUrl);

        links.add(map);
        String configurationUrl = linkTo(methodOn(MobileConfigurationController.class).index("")).withSelfRel().getHref();


        map = new HashMap();
        map.put("name", "配置");
        map.put("url", configurationUrl);
        links.add(map);

        String tripsIndexUrl = linkTo(methodOn(MobileDeliveryOrderController.class).index("")).withSelfRel().getHref();


        map = new HashMap();
        map.put("name", "提煤出库");
        map.put("url", tripsIndexUrl);

        links.add(map);
        String transportUrl = linkTo(methodOn(MobileInstanceTransprotController.class).index("")).withSelfRel().getHref();


        map = new HashMap();
        map.put("name", "库存作业");
        map.put("url", transportUrl);

        links.add(map);



        String synthesizedUrl = linkTo(methodOn(SynthesizedController.class).index("")).withSelfRel().getHref();


 /*       map = new HashMap();
        map.put("name", "综合");
        map.put("url", synthesizedUrl);

        links.add(map);*/
        modelAndView.addObject("synthesizedUrl",synthesizedUrl);

        String distributorUrl = linkTo(methodOn(MobileDistributorController.class).index("")).withSelfRel().getHref();


        map = new HashMap();
        map.put("name", "开户商");
        map.put("url", distributorUrl);

        links.add(map);




        String productUrl = linkTo(methodOn(MobileInventoryController.class).index("")).withSelfRel().getHref();


        map = new HashMap();
        map.put("name", "库存");
        map.put("url", productUrl);

        links.add(map);



/*
        String stationsIndexUrl = linkTo(methodOn(MobileStationController.class).searchCompanies("")).withSelfRel().getHref();
        modelAndView.addObject("stationsIndexUrl",stationsIndexUrl);


        map = new HashMap();
        map.put("name", "stations");
        map.put("url", stationsIndexUrl);

        links.add(map);

        String linesIndexUrl = linkTo(methodOn(MobileInstanceTransprotController.class).index("")).withSelfRel().getHref();
        modelAndView.addObject("linesIndexUrl",linesIndexUrl);


        map = new HashMap();
        map.put("name", "lines");
        map.put("url", linesIndexUrl);

        links.add(map);

        String linesSchedulesUrl = linkTo(methodOn(MobileLinesSchedulesController.class).index("")).withSelfRel().getHref();
        modelAndView.addObject("linesSchedulesUrl",linesSchedulesUrl);

        map = new HashMap();
        map.put("name", "linesSchedules");
        map.put("url", linesSchedulesUrl);

        links.add(map);*/


        modelAndView.addObject("companyName","樊家河三号矿一号堆场");
        modelAndView.addObject("links",links);


    }


    private String websocket_server_url ="http://localhost:6080/hello";// UriComponentsBuilder.newInstance().scheme("http").host("localhost:6080").path("hello").();




    public void setWebSocketUrl(Authentication authentication, ModelAndView modelAndView, Integer id) {


            modelAndView.addObject("websocket_url", websocket_server_url);
            UriComponentsBuilder end_point = UriComponentsBuilder.newInstance();
            UriComponents uriComponents = end_point.path("/topic/COALPIT_DELIVERY_workbench/{storageId}").buildAndExpand(id);
            logger.debug("---COALPIT_DELIVERY_workbench--------------- :{}", uriComponents.toString());
            modelAndView.addObject("websocket_topic", uriComponents.toUriString());



    }


    public void getStatistic(Authentication authentication, ModelAndView modelAndView, Integer id) {



        Map map = new HashMap<>();


        map.put("inventoryCount", inventoryService.getInventoryCount());

        map.put("distributorCount", distributorService.getDistributorNumber());


        List<Map> distributors_map = distributorService.query(null,new PageRequest(0,100)).getContent();

        String distributors_management_url = linkTo(methodOn(MobileDistributorController.class).index(null)).toUri().toASCIIString();
        map.put("distributors_management_url", distributors_management_url);

        map.put("distributors", distributors_map);
        modelAndView.addObject("statistic", map);


        List<Map> inventories_map = inventoryService.query(null,new PageRequest(0,100)).getContent();


        String inventories_management_url = linkTo(methodOn(MobileInventoryController.class).index(null)).toUri().toASCIIString();
        map.put("inventories_management_url", inventories_management_url);

        map.put("inventories", inventories_map);





    }


}
