package com.coalvalue.service.sync;


import com.alibaba.fastjson.JSON;
import com.coalvalue.configuration.MqttPublishSample;
import com.coalvalue.configuration.ReactorEventConfig;
import com.coalvalue.domain.entity.Distributor;
import com.coalvalue.domain.OperationResult;
import com.coalvalue.domain.entity.*;
import com.coalvalue.enumType.DataSynchronizationTypeEnum;
import com.coalvalue.enumType.ResourceType;
import com.coalvalue.enumType.SynchronizeEnum;
import com.coalvalue.notification.liveEvent.NotificationData_sync;
import com.coalvalue.repository.*;
import com.coalvalue.service.*;
import com.coalvalue.task.SystemStatusBroadcast;

import org.apache.commons.lang3.RandomStringUtils;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import reactor.bus.Event;
import reactor.bus.EventBus;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by silence yuan on 2015/7/25.
 */

@Service("synchronizationService")
public class SynchronizationService extends BaseServiceImpl {



    @Autowired
    private InventoryTransferRepository inventoryTransferRepository;

    @Autowired
    private MqttClient mqttClient;
    @Autowired
    private MqttPublishSample mqttPublishSample;




    public static List<SyncProperties> processing = new ArrayList<>();


    @Transactional
    public void syncImmediately() {

        List<SyncProperties> syncPropertieses =null;// synchronizedRepository.findBySyncStatus(SynchronizeEnum.SyncPending.getText());
        if(syncPropertieses.size()>0){
            List<SyncProperties> syncProperties_POSTING = syncPropertieses.stream().filter(e->e.getItemType().equals(ResourceType.INVENTORY_TRANSFER.getText())).collect(Collectors.toList());

            List<SyncProperties> ready = new ArrayList<>();
            for(SyncProperties syncProperties: syncProperties_POSTING){
                InventoryTransfer inventoryTransfer = inventoryTransferRepository.findById(syncProperties.getItemId()).get();
                syncProperties.setSyncSequence(UUID.randomUUID().toString());
                syncProperties.setContent(JSON.toJSONString(inventoryTransfer));
                ready.add(syncProperties);
             //   synchronizedRepository.save(syncProperties);
                processing.add(syncProperties);

            }

            if(ready.size()>0){
                Map map = new HashMap<>();
                map.put("type", DataSynchronizationTypeEnum.Sync_local.getText());
                map.put("content", JSON.toJSONString(ready));
                System.out.println("======发送 Reconciliation 信息");
                String client_request        = "request/"+ mqttPublishSample.getChannal_topic();
                try {
                    mqttClient.publish(client_request, JSON.toJSONString(map).getBytes(),2,false);
                } catch (MqttException e) {
                    e.printStackTrace();
                }

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


}
