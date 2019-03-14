package com.coalvalue.sync;

import com.alibaba.fastjson.JSON;
import com.coalvalue.configuration.ReactorEventConfig;
import com.coalvalue.domain.entity.*;
import com.coalvalue.domain.pojo.ReportDeliveryOrder_remote;
import com.coalvalue.enumType.DeliveryOrderStatusEnum;
import com.coalvalue.enumType.EchoSessionTypeEnum;
import com.coalvalue.enumType.ResourceType;
import com.coalvalue.notification.NotificationData;
import com.coalvalue.repository.ConfigurationRepository;
import com.coalvalue.repository.ReportDeliveryOrderRepository;
import com.coalvalue.service.DistributorService;
import com.coalvalue.service.EmployeeService;
import com.coalvalue.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import reactor.bus.Event;
import reactor.bus.EventBus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class SyncReportDeliveryOrder {
    public String supportType = ResourceType.DELIVERY_ORDER.getText();


    @Autowired
    private ReportDeliveryOrderRepository reportDeliveryOrderRepository;




    @Autowired
    private EventBus eventBus;
    @Transactional
    public List<SyncProperties> diffSync_from_server(List<SyncProperties> syncPropertiesesMap, String syncType, LocalDateTime lastSync) {


        List<SyncProperties> syncPropertieses_QuotationPlan = syncPropertiesesMap.stream().filter(e -> e.getItemType().equals(supportType)).collect(Collectors.toList());

        if(syncPropertieses_QuotationPlan.size() == 0){
            return new ArrayList<>();
        }


        List<String> uuids = syncPropertieses_QuotationPlan.stream().map(e -> e.getObjectUuid()).collect(Collectors.toList());
        Map<String, ReportDeliveryOrder> map_item = reportDeliveryOrderRepository.findByUuidIn(uuids).
                stream().collect(Collectors.toMap(vo -> vo.getUuid(), Function.identity()));


        List<ReportDeliveryOrder> syncProperties_sync_response_notifiy = new ArrayList<>();
        for (SyncProperties syncProperties : syncPropertieses_QuotationPlan) {
            System.out.println("开始 建立一张提煤单 ");
            ReportDeliveryOrder_remote quotationPlan_remote = JSON.parseObject(syncProperties.getContent(), ReportDeliveryOrder_remote.class);
            ReportDeliveryOrder reportDeliveryOrder = map_item.get(quotationPlan_remote.getUuid());


            String productCoalType = quotationPlan_remote.getProductNo();//(String)map.get("productCoalType");

            String inventoryNo = quotationPlan_remote.getInventoryNo();//(String)map.get("inventoryNo");

            String productGranularity = quotationPlan_remote.getProductNo();//(String)map.get("productGranularity");


            String distributorNo = quotationPlan_remote.getDistributorNo();//(String)map.get("distributorNo");


            String plateNumber = quotationPlan_remote.getLicense();//get("plateNumber");
            String traderName =  quotationPlan_remote.getDistributorNo();//.get("distributor");
            String idNumber =  quotationPlan_remote.getIdNumber();//.get("idNumber");
            String qrcode =  quotationPlan_remote.getQrcode();//.get("qrcode");

            String no = quotationPlan_remote.getNo();//get("no");

            String operatorPhone = quotationPlan_remote.getOperatorPhone();//get("operatorPhone");
            String operatorNo =  quotationPlan_remote.getOperatorNo();//get("operatorNo");
            String operatorName = quotationPlan_remote.getOperatorName();//get("operatorName");

            String consigneeName =  quotationPlan_remote.getConsigneeName();//get("consigneeName");
            String consigneePhone = quotationPlan_remote.getConsigneePhone();//get("consigneePhone");
            String consigneeNo =  quotationPlan_remote.getConsigneeNo();//get("consigneeId");


            String storageNo = quotationPlan_remote.getStorageNo();//get("storageNo");
            String productNo = quotationPlan_remote.getProductNo();//get("productNo");


            if (reportDeliveryOrder == null) {

                reportDeliveryOrder = new ReportDeliveryOrder();
                reportDeliveryOrder.setNo(no);
                reportDeliveryOrder.setLicense(plateNumber);
                reportDeliveryOrder.setProductNo(productNo);
                reportDeliveryOrder.setInventoryNo(inventoryNo);
                reportDeliveryOrder.setProducerNo(quotationPlan_remote.getProducerNo());
                reportDeliveryOrder.setDistributorNo(distributorNo);
                reportDeliveryOrder.setStorageNo(storageNo);
                reportDeliveryOrder.setOperatorNo(operatorNo);
                reportDeliveryOrder.setUuid(quotationPlan_remote.getUuid());


                reportDeliveryOrder.setIdNumber(idNumber);
                reportDeliveryOrder.setProductName(productCoalType + "/" + productGranularity);


                reportDeliveryOrder.setCompanyName(traderName);
                reportDeliveryOrder.setQrcode(qrcode);


                reportDeliveryOrder.setConsigneeName(consigneeName);
                reportDeliveryOrder.setConsigneePhone(consigneePhone);
                reportDeliveryOrder.setConsigneeNo(consigneeNo);


                reportDeliveryOrder.setStatus(DeliveryOrderStatusEnum.Valid.getText());


                reportDeliveryOrder.setOperatorName(operatorName);
                reportDeliveryOrder.setOperatorPhone(operatorPhone);

                reportDeliveryOrder = reportDeliveryOrderRepository.save(reportDeliveryOrder);


                syncProperties_sync_response_notifiy.add(reportDeliveryOrder);

            }






        }



        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            public void afterCommit() {


                if (syncType != null && syncType.equals(EchoSessionTypeEnum.Whole.getText())) {

                } else {

                    for (ReportDeliveryOrder deliveryOrder : syncProperties_sync_response_notifiy) {

                        NotificationData notificationData = new NotificationData();
                        notificationData.setObject(deliveryOrder);

                        eventBus.notify(ReactorEventConfig.notificationConsumer_create_delivery_order_event, reactor.bus.Event.wrap(notificationData));

                    }
                }


            }
        });

        return syncPropertieses_QuotationPlan;

    }
    @Transactional
    public List<SyncProperties> differentialSync(List<SyncProperties> syncPropertieses_quotationPlan, LocalDateTime _lastSync) {
        System.out.println("======同步的 数据类型"+ supportType);
        List<SyncProperties> syncProperties_fee = new ArrayList<>();

        List<ReportDeliveryOrder> reportDeliveryOrders = reportDeliveryOrderRepository.findByModifyDateAfter(_lastSync);

        for(ReportDeliveryOrder reportDeliveryOrder: reportDeliveryOrders){
            SyncProperties syncProperties = new SyncProperties();
            syncProperties.setContent(JSON.toJSONString(reportDeliveryOrder));
            syncProperties.setObjectUuid(reportDeliveryOrder.getUuid());
            syncProperties.setItemType(supportType);
            syncProperties_fee.add(syncProperties);

        }
        System.out.println("发现 要同步的数据"+ syncProperties_fee.toString());

        return syncProperties_fee;
    }


}
