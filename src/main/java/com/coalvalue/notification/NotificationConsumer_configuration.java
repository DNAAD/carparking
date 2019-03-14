package com.coalvalue.notification;
//A Client server data sync algorithm - using objects diff and patch.
//https://www.linkedin.com/pulse/client-server-sync-algorithm-using-objects-diff-patch-kumar-krishna

import com.coalvalue.configuration.ReactorEventConfig;
import com.coalvalue.domain.entity.*;
import com.coalvalue.repository.EmployeeRepository;
import com.coalvalue.repository.ProductRepository;
import com.coalvalue.repository.StorageSpaceRepository;
import com.coalvalue.repository.UserRepository;
import com.coalvalue.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.bus.Event;
import reactor.fn.Consumer;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class NotificationConsumer_configuration implements Consumer<Event<NotificationData_configuration>> {


    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private StorageSpaceRepository storageSpaceRepository;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private StorageService storageService;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    public static String topic__COALPIT_DELIVERY_workbench = "/topic/COALPIT_DELIVERY_workbench/1";
    public static String topic__COALPIT_DELIVERY_report = "/topic/COALPIT_DELIVERY_report/1";

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

/*
    @Autowired
    private PasswordEncoder passwordEncoder;
*/


    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void accept(Event<NotificationData_configuration> notificationDataEvent) {


        NotificationData_configuration notificationData = notificationDataEvent.getData();



        if(notificationDataEvent.getKey().equals(ReactorEventConfig.notificationConsumer_configuration_event)){

            System.out.println("==========================notificationConsumer_configuration_event={}");
            Map map =(Map) notificationData.getObject();






/*

            com.coalvalue.domain.entity.Configuration configuration =  configurationService.getConfiguration(ConfigurationServiceImpl.PRODUCER_CONFIGURATION_KEY_companyNo);
            if(configuration!= null){
                map.put("companyNo", configuration.getStringValue());
            }
            configuration =  configurationService.getConfiguration(ConfigurationServiceImpl.PRODUCER_CONFIGURATION_KEY_companyName);
            if(configuration!= null){
                map.put("companyName", configuration.getStringValue());
            }
*/



            List<Map> storageSpaces =(List) map.get("storageSpaces");
            System.out.println("====storageSpaces======================={}"+ storageSpaces.toString());


            storageService.createFromMap(storageSpaces);


            List<Map> inventories =(List) map.get("inventories");
            System.out.println("====inventories======================={}"+ inventories.toString());

            inventoryService.createFromMap(inventories);
            List<Map> products =(List) map.get("products");
            System.out.println("==========================={}"+ products.toString());
            for(Map inventory_map: products){
                createProductsFromMap(inventory_map);
            }


            List<Map> employees =(List) map.get("employees");
            createEmployeeFromMap(employees);
        }


        if(notificationDataEvent.getKey().equals(ReactorEventConfig.notificationConsumer_sync_compare_than_sync_event)){

            System.out.println("==========================notificationConsumer_configuration_event={}");
            Map map =(Map) notificationData.getObject();




            com.coalvalue.domain.entity.Configuration configuration =  configurationService.getConfiguration(ConfigurationServiceImpl.PRODUCER_CONFIGURATION_KEY_companyNo);
            if(configuration!= null){
                map.put("companyNo", configuration.getStringValue());
            }
            configuration =  configurationService.getConfiguration(ConfigurationServiceImpl.PRODUCER_CONFIGURATION_KEY_companyName);
            if(configuration!= null){
                map.put("companyName", configuration.getStringValue());
            }



            List<Map> storageSpaces =(List) map.get("storageSpaces");
            System.out.println("====storageSpaces======================={}"+ storageSpaces.toString());


            storageService.createFromMap(storageSpaces);


            List<Map> inventories =(List) map.get("inventories");
            System.out.println("====inventories======================={}"+ inventories.toString());

            inventoryService.createFromMap(inventories);
            List<Map> products =(List) map.get("products");
            System.out.println("==========================={}"+ products.toString());
            for(Map inventory_map: products){
                createProductsFromMap(inventory_map);
            }


            List<Map> employees =(List) map.get("employees");
            createEmployeeFromMap(employees);
        }

    }



    @Transactional
    public void createProductsFromMap(Map inventory_map) {
        //  Date date = (Date)inventory_new.get("date");
        String no = (String)inventory_map.get("no");
        if(no!= null){
            Product inventory = productRepository.findByNo(no);

            String productCoalType = (String)inventory_map.get("coalType");

            String productGranularity = (String)inventory_map.get("granularity");
            BigDecimal quotation = (BigDecimal)inventory_map.get("quotation");
            String status = (String)inventory_map.get("status");
            BigDecimal inventory_on_hand = (BigDecimal)inventory_map.get("inventory");
            if(inventory== null){
                inventory = new Product();
                inventory.setNo(no);
                //inventory.setQuote(quotation);

            }
            inventory.setGranularity(productCoalType);
            inventory.setCoalType(productGranularity);
            inventory.setStatus(status);
            inventory = productRepository.save(inventory);
        }

    }


    @Transactional
    public void createEmployeeFromMap(List<Map> inventory_map) {
        for(Map  employeeMap : inventory_map){
            //  Date date = (Date)inventory_new.get("date");
            String no = (String)employeeMap.get("no");

            if(no!= null){
                Employee employee = employeeRepository.findByNo(no);

                String productCoalType = (String)employeeMap.get("name");

                String password = (String)employeeMap.get("password");
                String givenName = (String)employeeMap.get("givenName");
                String familyName = (String)employeeMap.get("familyName");
                String mobile = (String)employeeMap.get("mobile");


                User user = userRepository.findByUserId(no);
                if(employee== null){
                    employee = new Employee();
                    employee.setNo(no);



                }
                if(user == null){
                    user = new User();
                }

                user.setUserName(no);
               // user.setNickName(familyName+givenName);
                //user.setPassword(passwordEncoder.encode(password));
                user.setPassword(password);
                user.setMobile(mobile);
                user.setUserId(no);
                userRepository.save(user);


                employee.setMobile(mobile);
                employee.setGivenName(givenName);
                employee.setFamilyName(familyName);
                employee = employeeRepository.save(employee);
            }

        }

    }
}