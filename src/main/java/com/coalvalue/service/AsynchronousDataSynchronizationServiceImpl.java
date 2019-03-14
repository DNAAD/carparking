package com.coalvalue.service;


import com.alibaba.fastjson.JSON;
import com.coalvalue.domain.entity.Distributor;
import com.coalvalue.domain.OperationResult;
import com.coalvalue.domain.entity.*;
import com.coalvalue.enumType.DataSynchronizationTypeEnum;
import com.coalvalue.notification.NotificationData;
import com.coalvalue.repository.*;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private InventoryRepository inventoryRepository;

    @Autowired
    private DistributorService distributorService;
    @Autowired
    private MqttService mqttService;




    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private QrcodeService qrcodeService;

    @Autowired
    private UserRepository userRepository;



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


        IDIentification idIentification = idIdentificationRepository.findById(idIentificationId).get();
        map.put("idNumber",idIentification.getId());


        ReportDeliveryOrder deliveryOrder = deliveryOrderService.findByIdNumber(idIentification.getIdNumber());


        PlateRecognition plateRecognition = null;

        if(deliveryOrder != null){
            count++;
            map.put("deliveryOrder_CompanyName",deliveryOrder.getCompanyName());
            map.put("product",deliveryOrder.getProductName());
            plateRecognition = plateRecognitionRepository.findByLicense(deliveryOrder.getLicense());
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

        ReportDeliveryOrder  deliveryOrder_from = (ReportDeliveryOrder)notificationData.getObject();

        logger.debug("================={} ",deliveryOrder_from.toString());
        System.out.println("================={} "+deliveryOrder_from.toString());


        ReportDeliveryOrder deliveryOrder = deliveryOrderService.findById(deliveryOrder_from.getId());




        IDIentification idIentification = idIdentificationRepository.findByIdNumber(deliveryOrder.getIdNumber());





        PlateRecognition plateRecognition = plateRecognitionRepository.findByLicense(deliveryOrder.getLicense());




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
            plateRecognition = plateRecognitionRepository.findByLicense(deliveryOrder.getLicense());
            count++;
            map.put("deliveryOrder_CompanyName",deliveryOrder.getCompanyName());
            map.put("idNumber",deliveryOrder.getIdNumber());
            map.put("license",deliveryOrder.getLicense());
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
            if(plateRecognition.getLicense().equals(deliveryOrder.getLicense())){
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

            List<Inventory> inventories = null;//inventoryService.getInventory(product.getNo());

            Map<String, Inventory> map_inventory_local = new HashMap<>();
            for(Inventory inventory:inventories){
                map_inventory_local.put(inventory.getNo(), inventory);
            }

            List<Map> inventory_news =(List) map.get("inventories");
            for(Map inventory_new :inventory_news){

                // Date date = new Date((Long)inventory_.get("date"));
                String no = (String)inventory_new.get("no");
                Inventory inventory_local = map_inventory_local.get(no);
 /*               if(inventory.getModifyDate().before(date)){*/

                if(inventory_local!= null){ // 找到了 库存

                    Date lastSyncTimestamp_remote = new Date((Long)inventory_new.get("lastSyncTimestamp"));
                    if(inventory_local.getLastSyncTimestamp().before(lastSyncTimestamp_remote)){ //旧有版本

                        List<Map> priceCategories = (List)map_inventory_local.get("priceCategories");

                        if(priceCategories!= null){
                            for(Map price : priceCategories){
                                inventory_local.setQuote((BigDecimal) price.get("value"));
                            }
                        }

                        inventory_local.setQuantityOnHand((BigDecimal) (inventory_new.get("quantityOnHand")));
                        inventory_local.setLastSyncTimestamp(lastSyncTimestamp_remote);

                    }




                }else{// 新的库存


                }



            }

        }

            if(DataSynchronizationTypeEnum.Distributor.getText().equals(type)) {


                List<Distributor> inventories = distributorService.get();

                Map<String, Distributor> map_inventory = new HashMap<>();
                for (Distributor inventory : inventories) {

                    map_inventory.put(inventory.getNo(), inventory);

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





        Map map = new HashMap<>();

        List<Distributor> collaborators = distributorService.get();;


        List distributors = new ArrayList<>();
        for(Distributor collaborator: collaborators){
            Map distributor = new HashMap<>();
            distributor.put("name", collaborator.getName());
            distributor.put("no", collaborator.getNo());
           if(collaborator.getUniqueId() == null){
               collaborator.setUniqueId( RandomStringUtils.randomAlphanumeric(30).toUpperCase());
               collaborator = distributorService.update(collaborator);
           }
            distributor.put("uniqueId", collaborator.getUniqueId());

            distributors.add(distributor);
        }
        map.put("distributors",distributors);


        map.put("type",DataSynchronizationTypeEnum.Distributor.getText());
        String msg = JSON.toJSONString(map);
        mqttService.publishToHost(msg);



        return null;





    }

    @Override
    @Transactional
    public void syncImmediately(SyncProperties synchronized_, Map map) {

        String type = (String)map.get("type");
      //  synchronizedRepository.save(synchronized_);
        if(DataSynchronizationTypeEnum.Employee.getText().equals(type)){

            logger.debug("=============== 建立 员工 employee");
            String employeeName = (String)map.get("employeeName");
            String employeePassword = (String)map.get("employeePassword");

            User user = userRepository.findByUserName(employeeName);
            if(user== null){
                user= new User();
                user.setUserName(employeeName);
                user.setPassword(employeePassword);
              //  user.set
                userRepository.save(user);
            }


        }

        if(DataSynchronizationTypeEnum.Inventory.getText().equals(type)){

            logger.debug("=============== 同步 Inventory ");
            Map inventoryMap = (Map)map.get("content");

            List<Map> inventoris = (List<Map>)inventoryMap.get("inventories");
            for(Map inventory_map : inventoris){
                BigDecimal quote = (BigDecimal)inventory_map.get("price");
                String no = (String)inventory_map.get("no");
                Inventory inventory = inventoryRepository.findByNo(no);
                inventory.setQuote(quote);
            }


        }

    }



    //   public void syncImmediately(Context context) {
/*        Bundle bundle = new Bundle();

        //将此同步放在同步请求队列前面，立即进行同步而不延迟
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        //忽略当前设置强制发起同步
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context),
                context.getString(R.string.content_authority), bundle);*/
  //  }

  /*  public static Account getSyncAccount(Context context) {
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(
                context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        // If the password doesn't exist, the account doesn't exist
        if ( null == accountManager.getPassword(newAccount) ) {

*/
    /*
     * Add the account and account type, no password or user data
     * If successful, return the Account object, otherwise report an error.

            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
        /*
         * If you don't set android:syncable="true" in
         * in your <provider> element in the manifest,
         * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
         * here.


        }
        return newAccount;
    }
*/
/*    作者：Artyhacker
    链接：https://www.jianshu.com/p/dc9a2693478e
    來源：简书
    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。*/



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
