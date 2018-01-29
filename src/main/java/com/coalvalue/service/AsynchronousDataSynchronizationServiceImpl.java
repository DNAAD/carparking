package com.coalvalue.service;


import com.alibaba.fastjson.JSON;
import com.coalvalue.configuration.CommonConstant;
import com.coalvalue.configuration.Constants;
import com.coalvalue.domain.Distributor;
import com.coalvalue.domain.OperationResult;
import com.coalvalue.domain.entity.*;
import com.coalvalue.enumType.DataSynchronizationTypeEnum;
import com.coalvalue.enumType.PlateDirectionEnum;
import com.coalvalue.notification.NotificationData;
import com.coalvalue.repository.BehaviouralRepository;
import com.coalvalue.repository.IDIdentificationRepository;
import com.coalvalue.repository.PlateRecognitionRepository;
import com.service.BaseServiceImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by silence yuan on 2015/7/25.
 */

@Service("asynchronousDataSynchronizationService")
public class AsynchronousDataSynchronizationServiceImpl extends BaseServiceImpl implements AsynchronousDataSynchronizationService {

    @Autowired
    private BehaviouralRepository behaviouralRepository;

    @Autowired
    private DeliveryOrderService deliveryOrderService;

    @Autowired
    private IDIdentificationRepository idIdentificationRepository;

    @Autowired
    private PlateRecognitionRepository plateRecognitionRepository;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private DistributorService distributorService;
    @Autowired
    private MqttService mqttService;


    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private QrcodeService qrcodeService;



    @Override
    @Transactional
    public void create(NotificationData data) {

        Behavioural behavioural = new Behavioural();
        behavioural = behaviouralRepository.save(behavioural);

    }

    @Override
    public void analyis(PlateRecognition plateRecognition) {
        ReportDeliveryOrder deliveryOrder = deliveryOrderService.findByPlateNumber(plateRecognition.getLicense());


    }

    @Override
    public Page<Map> querySynthesized(Object o, Pageable pageable) {


        return new PageImpl<Map>(maps,pageable,maps.size());
    }

    List<Map> maps = new ArrayList<>();



    @Override
    public void addPlate(PlateRecognition plateRecognition) {


        Map map = new HashMap<>();
        Integer count = 1;
        map.put("license",plateRecognition.getLicense());

        ReportDeliveryOrder deliveryOrder = deliveryOrderService.findByPlateNumber(plateRecognition.getLicense());
        IDIentification idIentification = null;

        if(deliveryOrder != null){
            count++;
            map.put("deliveryOrder_CompanyName",deliveryOrder.getCompanyName());

            map.put("product",deliveryOrder.getProductName());
            idIentification = idIdentificationRepository.findByIdNumber(deliveryOrder.getIdNumber());

        }else{
            map.put("deliveryOrder_CompanyName","--");
        }


        if(idIentification != null){
            count++;
            map.put("idNumber",idIentification.getId());
        }else{
            map.put("idNumber","--");
        }



        map.put("conditions",count);
        maps.add(map);

    }

    @Override
    public void addPlate_IDIdentification(Integer idIentificationId) {


        Map map = new HashMap<>();
        Integer count = 1;


        IDIentification idIentification = idIdentificationRepository.findById(idIentificationId);
        map.put("idNumber",idIentification.getId());


        ReportDeliveryOrder deliveryOrder = deliveryOrderService.findByIdNumber(idIentification.getIdNumber());


        PlateRecognition plateRecognition = null;

        if(deliveryOrder != null){
            count++;
            map.put("deliveryOrder_CompanyName",deliveryOrder.getCompanyName());
            map.put("product",deliveryOrder.getProductName());
            plateRecognition = plateRecognitionRepository.findByLicense(deliveryOrder.getPlateNumber());
        }else{
            map.put("deliveryOrder_CompanyName","--");
        }


        if(plateRecognition!= null){
            count++;
            map.put("license",plateRecognition.getLicense());
        }else{
            map.put("license","--");
        }

        map.put("conditions",count);
        maps.add(map);

    }




    @Override
    public void add_delivery_order(NotificationData notificationData) {


        Map map = new HashMap<>();
        Integer count = 1;

        ReportDeliveryOrder  deliveryOrder_from = notificationData.getObject();

        logger.debug("================={} ",deliveryOrder_from.toString());
        System.out.println("================={} "+deliveryOrder_from.toString());


        ReportDeliveryOrder deliveryOrder = deliveryOrderService.findById(deliveryOrder_from.getId());




        IDIentification idIentification = idIdentificationRepository.findByIdNumber(deliveryOrder.getIdNumber());





        PlateRecognition plateRecognition = plateRecognitionRepository.findByLicense(deliveryOrder.getPlateNumber());




        if(deliveryOrder != null){
            count++;
            map.put("deliveryOrder_CompanyName",deliveryOrder.getCompanyName());
            map.put("product",deliveryOrder.getProductName());
        }else{
            map.put("deliveryOrder_CompanyName","--");
        }

        if(idIentification!= null){
            count++;
            map.put("idNumber",idIentification.getIdNumber());
        }else{
            map.put("idNumber","--");
        }

        if(plateRecognition!= null){
            count++;
            map.put("license",plateRecognition.getLicense());
        }else{
            map.put("license","--");
        }

        map.put("conditions",count);
        maps.add(map);

    }

    @Override
    public void analyisQrcode(String text) {
        Map map = new HashMap<>();
        Integer count = 1;


        System.out.println("======text===text===text====== {}"+text );
        List<ReportDeliveryOrder> deliveryOrders = deliveryOrderService.findByValidQrcode(text);
        ReportDeliveryOrder deliveryOrder = deliveryOrders.get(deliveryOrders.size()-1);


        IDIentification idIentification = null;


        PlateRecognition plateRecognition =null;// plateRecognitionRepository.findByLicense(deliveryOrder.getPlateNumber());




        if(deliveryOrder != null){
            idIentification = idIdentificationRepository.findByIdNumber(deliveryOrder.getIdNumber());
            plateRecognition = plateRecognitionRepository.findByLicense(deliveryOrder.getPlateNumber());
            count++;
            map.put("deliveryOrder_CompanyName",deliveryOrder.getCompanyName());
            map.put("idNumber",deliveryOrder.getIdNumber());
            map.put("license",deliveryOrder.getPlateNumber());
            map.put("product",deliveryOrder.getProductName());
        }else{
            map.put("deliveryOrder_CompanyName","--");
        }

        if(idIentification!= null){
            count++;
            if(idIentification.getIdNumber().equals(deliveryOrder.getIdNumber())){
                map.put("idNumber_verified",true);
            }else{
                map.put("idNumber_verified",false);
            }

        }else{
            map.put("idNumber_verified",null);
        }

        if(plateRecognition!= null){
            count++;
            if(plateRecognition.getLicense().equals(deliveryOrder.getPlateNumber())){
                map.put("license_verified",true);
            }else{
                map.put("license_verified",false);
            }

        }else{
            map.put("license_verified",null);
        }

        map.put("conditions",count);
        maps.add(map);
    }

    @Override
    public void synchroniz(String type, Map map) {

        if(DataSynchronizationTypeEnum.Inventory.getText().equals(type)){


            List<Inventory> inventories =inventoryService.getInventory();

            Map<String, Inventory> map_inventory = new HashMap<>();
            for(Inventory inventory:inventories){
                map_inventory.put(inventory.getNo(),inventory);

            }
            List<Map> inventory_new =(List) map.get("inventories");
            for(Map inventory_ :inventory_new){

             //   Date date = new Date((Long)inventory_.get("date"));
                String no = (String)inventory_.get("no");
                Inventory inventory = map_inventory.get(no);
 /*               if(inventory.getModifyDate().before(date)){*/
                    if(inventory!= null){



                        List<Map> priceCategories = (List)map_inventory.get("priceCategories");

                        if(priceCategories!= null){
                            for(Map price : priceCategories){
                                inventory.setQuote((BigDecimal)price.get("value"));
                            }
                        }

                        inventory.setQuantityOnHand((BigDecimal) (inventory_.get("quantityOnHand")));



                    inventoryService.update(inventory);

                }else{

                    inventoryService.createInventoryFromMap(inventory_);

                }



            }

        }

            if(DataSynchronizationTypeEnum.Distributor.getText().equals(type)) {


                List<Distributor> inventories = distributorService.get();

                Map<String, Distributor> map_inventory = new HashMap<>();
                for (Distributor inventory : inventories) {

                    map_inventory.put(inventory.getCompanyNo(), inventory);

                }
                List<Map> inventory_new = (List) map.get("distributors");
                for (Map inventory_ : inventory_new) {

                   // Date date = new Date((Long) inventory_.get("date"));
                    String no = (String) inventory_.get("no");
                    Distributor inventory = map_inventory.get(no);
 /*               if(inventory.getModifyDate().before(date)){*/
                    if (inventory != null) {


                        List<Map> priceCategories = (List) map_inventory.get("priceCategories");
/*

                        for (Map price : priceCategories) {
                            inventory.setN((BigDecimal) price.get("value"));
                        }
                        inventory.setQuantityOnHand((BigDecimal) (inventory_.get("quantityOnHand")));
*/


                        distributorService.update(inventory);

                    } else {

                        distributorService.createDistributorFromMap(inventory_);

                    }


                }

            }





        if(DataSynchronizationTypeEnum.Qrcode.getText().equals(type)) {



            String inventory_new = (String) map.get("uniqueId");


            qrcodeService.upDateBindQrcodeForDistributor(inventory_new,map);

        }

    }

    @Override
    public OperationResult synchronize() {


/*        String url = "http://localhost:8085/synchronize";

        Map<String, Object> uriParams = new HashMap<String, Object>();
        // uriParams.put("id",storageSpace.getId());

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)

            .queryParam("appId", Constants.APP_ID);
           *//*      .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize());*//*

        ResponseEntity<Map> empResource = restTemplate.exchange(builder.buildAndExpand(uriParams).toUri(), HttpMethod.GET,null,  new ParameterizedTypeReference<Map>() {});
        if (empResource.getStatusCode() == HttpStatus.OK) {


            Map resources = empResource.getBody();
            List<Map> result = new ArrayList<Map>();


        }*/




        Map map = new HashMap<>();
      /*          map.put("type", DataSynchronizationTypeEnum.Distributor.getText());
                map.put("plateNumber", reportDeliveryOrder.getPlateNumbers());
                map.put("product", transportOperation.getProductId());

                map.put("product", transportOperation.getProductId());
                map.put("deliveryOrderId", reportDeliveryOrder.getId());


                map.put("deliveryOrderNo", reportDeliveryOrder.getNo());
                Company company = companyService.getCompanyById(transportOperation.getCompanyId());
*/

        List<Distributor> collaborators = distributorService.get();;


        List<Map> distributors = new ArrayList<>();

        for(Distributor collaborator: collaborators){
            Map distributor = new HashMap<>();

            distributor.put("name", collaborator.getName());
            distributor.put("no", collaborator.getCompanyNo());

           if(collaborator.getUniqueId() == null){
               collaborator.setUniqueId( RandomStringUtils.randomAlphanumeric(30).toUpperCase());

               collaborator = distributorService.update(collaborator);
           }
            distributor.put("uniqueId", collaborator.getUniqueId());

            distributors.add(distributor);
        }
        map.put("distributors",distributors);

        map.put("appId",configurationService.getAppId());
        map.put("type",DataSynchronizationTypeEnum.Distributor.getText());
        String msg = JSON.toJSONString(map);

        mqttService.publishToHost(msg);











        return null;





    }












    public static String setDatetime(String date, String time) {
        String osName = System.getProperty("os.name");
        String dateTimeMessage = date + " " + time;
        try {
            if (osName.matches("^(?i)Windows.*$")) { // Window 系统
                String cmd;

                cmd = " cmd /c date " + date; // 格式：yyyy-MM-dd
                Runtime.getRuntime().exec(cmd);

                cmd = " cmd /c time " + time; // 格式 HH:mm:ss
                Runtime.getRuntime().exec(cmd);
            } else if (osName.matches("^(?i)Linux.*$")) {// Linux 系统
                String command = "date -s " + "\"" + date + " " + time + "\"";// 格式：yyyy-MM-dd HH:mm:ss
                Runtime.getRuntime().exec(command);
            } else {

            }
        } catch (IOException e) {
            return e.getMessage();
        }
        return dateTimeMessage;
    }
}
