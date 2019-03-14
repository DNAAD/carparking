package com.coalvalue.service;


import com.alibaba.fastjson.JSON;
import com.coalvalue.configuration.MqttPublishSample;
import com.coalvalue.configuration.ReactorEventConfig;
import com.coalvalue.domain.entity.*;
import com.coalvalue.enumType.DataSynchronizationTypeEnum;
import com.coalvalue.enumType.FinancialConstants;
import com.coalvalue.enumType.ResourceType;
import com.coalvalue.enumType.SynchronizeEnum;

import com.coalvalue.notification.NotificationData_quotation;
import com.coalvalue.repository.*;

import com.coalvalue.service.sync.DifferentialSyncService;
import com.coalvalue.task.SystemStatusBroadcast;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import reactor.bus.Event;
import reactor.bus.EventBus;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Created by silence yuan on 2015/7/25.
 */

@Service("syncService")
public class SyncServiceImpl implements SyncService {


    @Autowired
    private AdvancedPaymentTransferRepository advancedPaymentTransferRepository;

    @Autowired
    private MqttPublishSample mqttPublishSample;

    @Autowired
    private MqttClient mqttClient;
    @Autowired
    private InventoryTransferRepository inventoryTransferRepository;



    @Autowired
    private FeeService feeService;

    @Autowired
    private QuotationPlanRepository quotationPlanRepository;


    @Autowired
    private ConfigurationRepository configurationRepository;


    @Autowired
    private InventoryRepository inventoryRepository;



    @Autowired
    private SystemStatusBroadcast systemStatusBroadcast;

    @Autowired
    private DifferentialSyncService differentialSyncService;




    @Autowired
    private EventBus eventBus;


    public Map map = new HashMap();

    @Override
    public SyncProperties createPrepayments(AdvancedPaymentTransfer posting) {
        SyncProperties syncProperties = new SyncProperties();
        syncProperties.setTimestamp(posting.getCreateDate());
        syncProperties.setItemType(ResourceType.Advanced_Payment_Transfer.getText());
        syncProperties.setItemId(posting.getId());
        syncProperties.setSyncStatus(SynchronizeEnum.SyncPending.getText());
        return null;// syncPropertiesRepository.save(syncProperties);
    }





    @Override
    public void sync(List<SyncProperties> syncProperties_remote, Map<String, Object> contentMap) {


/*        Distributor distributor = distributorService.getByNo(distributorNo);

        System.out.println("distributor---- " + ":"+distributor.toString() );
        distributorService.addAdvancedPayment(distributor,new BigDecimal(234));*/
        //String distributorNo  = (String)map.get("distributorNo");


        List<SyncProperties> syncProperties_sync = new ArrayList<>();
        List<SyncProperties> syncProperties_POSTING = syncProperties_remote.stream().filter(e->e.getItemType().equals(ResourceType.Posting.getText())).collect(Collectors.toList());



        if(syncProperties_POSTING.size()>0){
           List<SyncProperties> syncPropertieses_local =null;// syncPropertiesRepository.findBySyncStatus(SynchronizeEnum.SyncPending.getText());
           System.out.println("===================syncPropertiesRepository==== {} "+syncPropertieses_local.size());
           List<SyncProperties> syncProperties_Advanced_Payment_Transfer = syncPropertieses_local.stream().filter(e->e.getItemType().equals(ResourceType.Advanced_Payment_Transfer.getText())).collect(Collectors.toList());

           List<SyncProperties> syncProperties_all = new ArrayList<>();
           syncProperties_all.addAll(syncProperties_POSTING);
           syncProperties_all.addAll(syncProperties_Advanced_Payment_Transfer);

           Collections.sort(syncProperties_all, new Comparator<SyncProperties>(){

               /*
                * int compare(Person p1, Person p2) 返回一个基本类型的整型，
                * 返回负数表示：p1 小于p2，
                * 返回0 表示：p1和p2相等，
                * 返回正数表示：p1大于p2
                */
               public int compare(SyncProperties p1, SyncProperties p2) {
                   //按照Person的年龄进行升序排列
                   if(p1.getTimestamp().isAfter(p2.getTimestamp())){
                       //" > "
                       return 1;
                   }
/*                if(p1.getLastSyncTimestamp().equals(p2.getLastSyncTimestamp())){
                    return 0;
                }*/
                   return -1;
               }
           });







           for(SyncProperties syncProperties: syncProperties_all){
               if(syncProperties.getItemType().equals(ResourceType.Advanced_Payment_Transfer.getText())){

                   System.out.println("syncProperties Advanced_Payment_Transfer "+syncProperties.toString());
                   AdvancedPaymentTransfer advancedPaymentTransfer = advancedPaymentTransferRepository.findByUuid(syncProperties.getObjectUuid());
                   advancedPaymentTransfer.setSyncStatus(SynchronizeEnum.Been_synchronized.getText());
                   advancedPaymentTransferRepository.save(advancedPaymentTransfer);

               }


               if(syncProperties.getItemType().equals(ResourceType.Posting.getText())){

                   AdvancedPaymentTransfer advancedPaymentTransfer = new AdvancedPaymentTransfer();


                   AdvancedPaymentTransfer advancedPaymentTransfer_last = advancedPaymentTransferRepository.findTop1ByDistributorNoAndSyncStatusOrderByCreateDateDesc(syncProperties.getDistributorNo(),SynchronizeEnum.Been_synchronized.getText());
                   advancedPaymentTransfer.setDistributorNo(syncProperties.getDistributorNo());
                   advancedPaymentTransfer.setAmount(new BigDecimal(100));
                   advancedPaymentTransfer.setDebitCreditType(FinancialConstants.CREDIT.name());
                   advancedPaymentTransfer.setSyncStatus(SynchronizeEnum.Been_synchronized.getText());
                   if(advancedPaymentTransfer_last== null){
                       advancedPaymentTransfer.setBalance(advancedPaymentTransfer.getAmount());

                   }else{
                       advancedPaymentTransfer.setBalance(advancedPaymentTransfer_last.getBalance().add(advancedPaymentTransfer.getAmount()));


                   }
                   advancedPaymentTransferRepository.save(advancedPaymentTransfer);
                   syncProperties_sync.add(syncProperties);

               }
           }

       }


        List<SyncProperties> syncProperties_employee = syncProperties_remote.stream().filter(e->e.getItemType().equals(ResourceType.Employee.getText())).collect(Collectors.toList());

       if(syncProperties_employee.size()>0){

       }



        List<SyncProperties> syncProperties_QUOTATION_PLAN = syncProperties_remote.stream().filter(e->e.getItemType().equals(ResourceType.QUOTATION_PLAN.getText())).collect(Collectors.toList());

        if(syncProperties_QUOTATION_PLAN.size()>0){
            List<Inventory> inventories = inventoryRepository.findAll();
            if(syncProperties_QUOTATION_PLAN.size()> 1){
                System.out.println("------------------=======================================++++++++++++++++++++++++++++++");
                System.out.println("------------------=======================================++++++++++++++++++++++++++++++");
                System.out.println("------------------=======================================++++++++++++++++++++++++++++++");
                System.out.println("------------------=======================================++++++++++++++++++++++++++++++");
                System.out.println("------------------=======================================++++++++++++++++++++++++++++++");

            }
            for(SyncProperties syncProperties: syncProperties_QUOTATION_PLAN){

                Map quotationPlan_map_remote = JSON.parseObject(syncProperties.getContent(),Map.class);



                QuotationPlan quotationPlan_quotationPlan_remote = JSON.parseObject((String)quotationPlan_map_remote.get("quotationPlan"),QuotationPlan.class);

                if(quotationPlan_quotationPlan_remote.getStatus().equals(QuotationPlanStatusEnum.Executed.getText())){
                    String uuid = quotationPlan_quotationPlan_remote.getUuid();
                    QuotationPlan quotationPlan = quotationPlanRepository.findByUuid(uuid);
                    quotationPlan.setStatus(QuotationPlanStatusEnum.Executed.getText());
                    quotationPlanRepository.save(quotationPlan);

                    NotificationData_quotation notificationData = new NotificationData_quotation();

  /*                  for (Inventory inventory : inventories) {
                        if(inventory.getProbationaryQuote()!= null){
                            inventory.setQuote(inventory.getProbationaryQuote());
                            inventory.setProbationaryQuote(null);
                        }
                        inventoryRepository.save(inventory);
                    }
*/



                    eventBus.notify(ReactorEventConfig.notificationConsumer_change_price_group_quotation_pre_event, Event.wrap(notificationData));
                    syncProperties_sync.add(syncProperties);



                }else{

                    List<Map> quotationPlan_extra_remote = JSON.parseArray((String)quotationPlan_map_remote.get("extra"),Map.class);

                    QuotationPlan quotationPlan = quotationPlanRepository.findTop1ByOrderByCreateDateDesc();



                    if(quotationPlan!= null && quotationPlan_quotationPlan_remote.getStatus().equals(QuotationPlanStatusEnum.INACTIVE.getText())){
                        quotationPlan.setStatus(QuotationPlanStatusEnum.INACTIVE.getText());
                        quotationPlanRepository.save(quotationPlan);
                        NotificationData_quotation notificationData = new NotificationData_quotation();
                        eventBus.notify(ReactorEventConfig.notificationConsumer_change_price_group_quotation_pre_event, Event.wrap(notificationData));

                        syncProperties_sync.add(syncProperties);
                    }

                    if(quotationPlan_quotationPlan_remote.getStatus().equals(QuotationPlanStatusEnum.ACTIVE.getText())){
                        quotationPlan = new QuotationPlan();
                        quotationPlan.setStatus(QuotationPlanStatusEnum.ACTIVE.getText());
                        quotationPlan.setUuid(quotationPlan_quotationPlan_remote.getUuid());
                        quotationPlan.setTriggerDate(quotationPlan_quotationPlan_remote.getTriggerDate());
                        quotationPlanRepository.save(quotationPlan);

                        for (Inventory inventory : inventories) {
                            Boolean find = false;
                            for (Map map : quotationPlan_extra_remote) {

                                String productNo = (String) map.get("productNo");
                                if (inventory.getProductNo().equals(productNo)) {
                                    BigDecimal expectPrice = (BigDecimal) map.get("expectPrice");
                                    String priceCategory = (String) map.get("priceCategory");
                                 //   inventory.setProbationaryQuote(expectPrice);
                                    find = true;
                                }

                            }
                            if (find == false) {
                                //inventory.setProbationaryQuote(null);
                            }
                            inventoryRepository.save(inventory);

/*                        priceElement.put("expectPrice", priceCategory.getExpectedValue());
                        priceElement.put("priceCategory",priceCategory.getName());*/

                        }
                        NotificationData_quotation notificationData = new NotificationData_quotation();


                        eventBus.notify(ReactorEventConfig.notificationConsumer_change_price_group_quotation_pre_event, Event.wrap(notificationData));

                        syncProperties_sync.add(syncProperties);

                    }
                }




            }


/*            for(SyncProperties syncProperties: syncProperties_employee){
                syncProperties.setLocalRemote("remote");
                syncPropertiesRepository.save(syncProperties);
            }*/


        }


        List<SyncProperties> syncProperties_fee = syncProperties_remote.stream().filter(e->e.getItemType().equals(ResourceType.FEE.getText())).collect(Collectors.toList());

        if(syncProperties_fee.size()>0){
            for(SyncProperties syncProperties: syncProperties_fee){

                Fee fee_remote = JSON.parseObject(syncProperties.getContent(),Fee.class);
                feeService.syncAddFee(fee_remote);

                syncProperties_sync.add(syncProperties);
            }


        }





        if(syncProperties_sync.size()>0){
           Map result = new HashMap();

           result.put("type",DataSynchronizationTypeEnum.Sync.getText());
           //contentMap.put("uuid",uuid);
           result.put("content",JSON.toJSONString(syncProperties_sync));

           String client_request        = "request/"+ mqttPublishSample.getChannal_topic();
           try {
               mqttClient.publish(client_request, JSON.toJSONString(result).getBytes(),2,false);
           } catch (MqttException e) {
               e.printStackTrace();
           }
       }

       return;


    }




   //@Scheduled(fixedDelay = 9000,   initialDelay=3000)
    @Transactional
    public void reportCloudDeliveryServer() {
        List<SyncProperties> syncPropertieses = null;//syncPropertiesRepository.findBySyncStatus(SynchronizeEnum.SyncPending.getText());


        List<SyncProperties> syncPropertiesMap = new ArrayList<>();
        for(SyncProperties syncProperties: syncPropertieses){
            syncProperties.setSyncSequence(UUID.randomUUID().toString());

            //syncProperties.setDistributorNo(account.getCustomerNo());
            syncPropertiesMap.add(syncProperties);
        }




        Map result = new HashMap();

        result.put("type",DataSynchronizationTypeEnum.Sync.getText());
        Map contentMap = new HashMap();
        //contentMap.put("uuid",uuid);
        result.put("content",syncPropertiesMap);

        String client_request        = "request/"+ mqttPublishSample.getChannal_topic();
        try {
            mqttClient.publish(client_request, JSON.toJSONString(result).getBytes(),2,false);
        } catch (MqttException e) {
            e.printStackTrace();
        }


    }












    @Override
    @Transactional
    // @Scheduled(fixedDelay = 60*1000,   initialDelay=3000)
    public void syncCompare() {


        System.out.println("     differentialSyncService.differentialSync();===================++++++++++++++++++++++++++++++");

        if (!systemStatusBroadcast.canSync()) {
            return;
        }
        differentialSyncService.differentialSync(null, "group");

    }


    @Transactional
  // @Scheduled(fixedDelay = 60*1000,   initialDelay=3000)
    public void syncCompare___() {



        System.out.println("syncCompare------------------=======================================++++++++++++++++++++++++++++++");

        if(!systemStatusBroadcast.canSync()){
            return;
        }

/*
        List<String> inventories  = inventoryRepository.findUuidBy();
        List<String> storageSpaces = storageSpaceRepository.findUuidBy() ;

        List<String> products = productRepository.findUuidBy() ;

        List<String> employees = employeeRepository.findUuidBy() ;


        List<String> distributors  = distributorRepository.findUuidBy() ;

        List<String> configurations  = configurationRepository.findUuidBy() ;

        List<String> inventoryTransfers = inventoryTransferRepository.findUuidBy() ;*/



        List<QuotationPlan> quotationPlans = quotationPlanRepository.findAll();
        List<Configuration> configurations = configurationRepository.findAll();


        Map map = quotationPlans.stream().collect(
                Collectors.toMap(QuotationPlan::getUuid, QuotationPlan::getModifyDate));

        Map configurations_map = configurations.stream().collect(
                Collectors.toMap(Configuration::getUuid, Configuration::getModifyDate));

        Map content = new HashMap();
        content.put("QuotationPlan",map);
        content.put("Configuration",configurations_map);
        Map result = new HashMap();

        result.put("type",DataSynchronizationTypeEnum.Sync_compare.getText());
        result.put("content",JSON.toJSONString(content));

        String client_request        = "request/"+ mqttPublishSample.getChannal_topic();
        try {
            mqttClient.publish(client_request, JSON.toJSONString(result).getBytes(),2,false);
        } catch (MqttException e) {
            e.printStackTrace();
        }


    }







    @Override
    //@Transactional(isolation = Isolation.READ_UNCOMMITTED,propagation=Propagation.REQUIRED)
    @Transactional(isolation = Isolation.SERIALIZABLE)

      public void syncImmediately() {


        System.out.println("立即同步信息");
          List<SyncProperties> syncPropertieses_local = null;//syncPropertiesRepository.findBySyncStatus(SynchronizeEnum.SyncPending.getText());
        System.out.println("立即同步信息"+syncPropertieses_local.toString());
          List<SyncProperties> syncProperties_DELIVERY_ORDER = syncPropertieses_local.stream().filter(e->e.getItemType().equals(ResourceType.INVENTORY_TRANSFER.getText())).collect(Collectors.toList());

        System.out.println("立即同步信息"+syncProperties_DELIVERY_ORDER.size());


          for(SyncProperties syncProperties: syncProperties_DELIVERY_ORDER){
              System.out.println("立即同步信息"+syncProperties.toString());
              InventoryTransfer reportDeliveryOrder = inventoryTransferRepository.findByUuid(syncProperties.getObjectUuid());
              syncProperties.setContent(JSON.toJSONString(reportDeliveryOrder));
              syncProperties.setSyncStatus(SynchronizeEnum.Syncing.getText());
              syncProperties.setSyncTimes(1);
              syncProperties.setSyncSequence(UUID.randomUUID().toString());
             // syncPropertiesRepository.save(syncProperties);
          }


          if(syncProperties_DELIVERY_ORDER.size()>0){
              Map result = new HashMap();

              result.put("type",DataSynchronizationTypeEnum.Sync_local.getText());
              Map contentMap = new HashMap();
              //contentMap.put("uuid",uuid);
              result.put("content",JSON.toJSONString(syncProperties_DELIVERY_ORDER));

              String client_request        = "request/"+ mqttPublishSample.getChannal_topic();
              try {
                  mqttClient.publish(client_request, JSON.toJSONString(result).getBytes(),0,false);
                  System.out.println("发送同步信息");

              } catch (MqttException e) {
                  e.printStackTrace();
              }
          }




/*
          Bundle bundle = new Bundle();

        //将此同步放在同步请求队列前面，立即进行同步而不延迟
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        //忽略当前设置强制发起同步
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context),
                context.getString(R.string.content_authority), bundle);*/
      }
}
