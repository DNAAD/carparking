package com.coalvalue.service;


import com.coalvalue.configuration.ReactorEventConfig;
import com.coalvalue.domain.entity.*;
import com.coalvalue.enumType.*;
import com.coalvalue.notification.NotificationData;
import com.coalvalue.repository.*;
import com.coalvalue.service.sync.DifferentialSyncService;
import com.coalvalue.service.sync.SynchronizationService;
import com.coalvalue.task.LiveBroadcast;
import com.coalvalue.web.MobileDistributorController;
import com.coalvalue.web.MobileInstanceTransprotController;
import com.coalvalue.web.MobileTransferController;
import com.coalvalue.web.valid.InstanceTransportCreateForm;
import com.coalvalue.web.valid.InventoryTranferFormCorrect;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.util.DateUtils;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.bus.Event;
import reactor.bus.EventBus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.Function;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by silence yuan on 2015/7/25.
 */

@Service("inventoryTransferService")
public class InventoryTransferServiceImpl extends BaseServiceImpl implements InventoryTransferService {

    @Autowired
    private BehaviouralRepository behaviouralRepository;
    @Autowired
    private InstanceTransportRepository instanceTransportRepository;

    @Autowired
    private EventBus eventBus;
    @Autowired
    private LiveBroadcast liveBroadcast;


    @Autowired
    private DistributorRepository distributorRepository;


    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ReportDeliveryOrderRepository reportDeliveryOrderRepository;
    @Autowired
    private InventoryTransferRepository inventoryTransferRepository;



    @Autowired
    private DifferentialSyncService differentialSyncService;

    @Override
    @Transactional
    public void create(NotificationData data) {

        Behavioural behavioural = new Behavioural();
        behavioural = behaviouralRepository.save(behavioural);

    }

    @Override
    public Page<Map> query(Object om, String searchText, Pageable pageable) {

        Page<InventoryTransfer> transports = inventoryTransferRepository.findByReconcileStatus(ReconcileStatusEnum.NoReconciliation.getText(),pageable);

        ObjectMapper m = new ObjectMapper();

        Page<Map> page = transports.map(new Function<InventoryTransfer, Map>() {
            public Map apply(InventoryTransfer objectEntity) {

                Map<String,Object> mappedObject = m.convertValue(objectEntity,Map.class);

                InstanceTransport instanceTransport = instanceTransportRepository.findByUuid(objectEntity.getInstanceUuid());
                Duration p = Duration.between(instanceTransport.getCreateDate(), objectEntity.getCreateDate());
                mappedObject.put("createDate",objectEntity.getCreateDate());
                mappedObject.put("duration",p.getSeconds()/60);
                mappedObject.put("netWeight",objectEntity.getWeight());
                String url = linkTo(methodOn(MobileTransferController.class).detail(objectEntity.getNo(), null, null)).withSelfRel().getHref();
                mappedObject.put("url",url);


                String companiesUrl = linkTo(methodOn(MobileDistributorController.class).detail(objectEntity.getDistributorNo(), null)).withSelfRel().getHref();
                Distributor distributor = distributorRepository.findByNo(objectEntity.getDistributorNo());
                mappedObject.put("distributorUrl",companiesUrl);
                mappedObject.put("distributor",distributor.getName()+distributor.getNo());


                if(instanceTransport.getOutboundTime() != null){
                    mappedObject.put("loadingTime",DateUtils.getDistanceTime(instanceTransport.getCreateDate(),instanceTransport.getOutboundTime()));
                }
/*                if(instanceTransport.getGrossWeight() != null){
                    mappedObject.put("netWeight",instanceTransport.getGrossWeight().subtract(instanceTransport.getTareWeight()));
                }*/
                mappedObject.put("outboundTime",instanceTransport.getOutboundTime());
                mappedObject.put("license",instanceTransport.getLicense());

                mappedObject.put("boundTime",instanceTransport.getBoundTime());
               // mappedObject.put("status",FinancialStatusEnum.fromString(objectEntity.getStatus()).getDisplayText());
                mappedObject.put("reconcileStatus",ReconcileStatusEnum.fromString(objectEntity.getReconcileStatus()).getDisplayText());

                //ReportDeliveryOrder reportDeliveryOrder = reportDeliveryOrderRepository.findByNo(objectEntity.getDeliveryOrderNo());
                return mappedObject;
            }
        });
        return page;

    }

    @Override
    @Transactional
    public InstanceTransport createFromDeliveryOrderInputTareWeight(ReportDeliveryOrder reportDeliveryOrder, InstanceTransportCreateForm locationCreateForm) throws MqttException {


        if(!reportDeliveryOrder.getStatus().equals(DeliveryOrderStatusEnum.Valid.getText())){
            return null;
        }
        Inventory inventory = inventoryRepository.findByNo(reportDeliveryOrder.getInventoryNo());

        InstanceTransport instanceTransport = instanceTransportRepository.findByDeliveryOrderNo(reportDeliveryOrder.getNo());

        if(instanceTransport == null){
            Distributor distributor = distributorRepository.findByNo(reportDeliveryOrder.getProducerNo());


            instanceTransport = new InstanceTransport();
            instanceTransport.setTareWeight(locationCreateForm.getTareWeight());



            instanceTransport.setDeliveryOrderNo(reportDeliveryOrder.getNo());
            instanceTransport.setCoalType(inventory.getCoalType());
            instanceTransport.setGranularity(inventory.getGranularity());


            instanceTransport.setInventoryNo(inventory.getNo());


            instanceTransport.setLicense(reportDeliveryOrder.getLicense());

            instanceTransport.setDistributorNo(distributor.getNo());

            instanceTransport.setInventoryNo(inventory.getNo());
            instanceTransport.setUnitPrice(inventory.getQuote());

            instanceTransport.setBoundTime(LocalDateTime.now());



            instanceTransport.setStatus(InstanceTransportStatusEnum.LOADING.getText());



            reportDeliveryOrder.setStatus(DeliveryOrderStatusEnum.Taking_delivery.getText());
            reportDeliveryOrder = reportDeliveryOrderRepository.save(reportDeliveryOrder);


            instanceTransport =  instanceTransportRepository.save(instanceTransport);
        }else{
            instanceTransport.setTareWeight(locationCreateForm.getTareWeight());
            instanceTransport.setBoundTime(LocalDateTime.now());
            instanceTransport =  instanceTransportRepository.save(instanceTransport);
        }



        NotificationData notificationData = new NotificationData();
        notificationData.setObject(reportDeliveryOrder);
        notificationData.setInventory(inventory);
        notificationData.setInstanceTransport(instanceTransport);

        eventBus.notify(ReactorEventConfig.notificationConsumer_input_tareweight_event, Event.wrap(notificationData));

        return instanceTransport;


    }
/*

    @Override
    @Transactional
    public OperationResult createTransfer(InstanceTransport instanceTransport, InventoryTransferCreateForm locationCreateForm) {
        OperationResult operationResult = new OperationResult();

        if(!instanceTransport.getStatus().equals(InstanceTransportStatusEnum.LOADING.getText())){
            operationResult.setSuccess(false);
            operationResult.setErrorMessage("不是装载状态");
         return operationResult;
        }
        logger.debug("InstanceTransport  {}" ,instanceTransport,toString());

      //  BigDecimal netWeight = locationCreateForm.getGrossWeight().subtract(instanceTransport.getTareWeight());

        InventoryTransfer inventoryTransfer = inventoryTransferRepository.findByInstanceId(instanceTransport.getId());
        BigDecimal grossWeight = locationCreateForm.getGrossWeight();
        BigDecimal tareWeight = instanceTransport.getTareWeight();
      //  BigDecimal netWeight = locationCreateForm.getNetWeight();
        BigDecimal netWeight =grossWeight.subtract(tareWeight);
        if(netWeight.floatValue()<=0){
            operationResult.setSuccess(false);
            operationResult.setErrorMessage("净重 不能为负");
            return operationResult;
        }

        if(inventoryTransfer== null){
            Inventory inventory = inventoryRepository.findByNo(instanceTransport.getInventoryNo());
            ReportDeliveryOrder reportDeliveryOrder = reportDeliveryOrderRepository.findById(instanceTransport.getDeliveryOrderId()).get();
            Distributor distributor = distributorRepository.findByCompanyNo(instanceTransport.getDistibutorNo());

            instanceTransport.setGrossWeight(grossWeight);
            instanceTransport.setNetWeight(netWeight);
            instanceTransport.setOutboundTime(LocalDateTime.now());
            instanceTransport.setStatus(InstanceTransportStatusEnum.LEAVE.getText());
            instanceTransport = instanceTransportRepository.save(instanceTransport);




            inventoryTransfer = new InventoryTransfer();
            inventoryTransfer.setNo(sequenceGenerator.nextInventoryTransferNo());
            inventoryTransfer.setAmount(netWeight);
            inventoryTransfer.setDistributorNo(distributor.getCompanyNo());

            inventoryTransfer.setInstanceId(instanceTransport.getId());
            inventoryTransfer.setWeight(netWeight);
            inventoryTransfer.setUnitPrice(instanceTransport.getUnitPrice());
            inventoryTransfer.setStatus(FinancialStatusEnum.nsettled.getText());
            inventoryTransfer.setReconcileStatus(ReconcileStatusEnum.NoReconciliation.getText());
            inventoryTransfer.setDeliveryOrderNo(reportDeliveryOrder.getNo());
            inventoryTransfer.setInventoryNo(inventory.getNo());
            inventoryTransfer.setDistributorNo(distributor.getCompanyNo());




            if(inventory.getQuantityOnHand()!= null){
                BigDecimal balance = inventory.getQuantityOnHand().subtract(netWeight);
                if(balance.floatValue()>0){
                    inventoryTransfer.setBalance(balance);

                    inventory.setQuantityOnHand(balance);
                }else{
                    inventoryTransfer.setBalance(new BigDecimal(0));
                    inventory.setQuantityOnHand(new BigDecimal(0));
                }
                inventoryRepository.save(inventory);

            }

            inventoryTransfer = inventoryTransferRepository.save(inventoryTransfer);





*//*
            BigDecimal quote = inventory.getQuote();
            if(quote== null){
                inventory.setQuote(new BigDecimal(10));
                inventory = inventoryRepository.save(inventory);
            }
*//*

            reportDeliveryOrder.setStatus(DeliveryOrderStatusEnum.Completed.getText());
            reportDeliveryOrderRepository.save(reportDeliveryOrder);











*//*            BigDecimal amount = inventoryTransfer.getAmount().multiply(inventory.getQuote());


            Distributor distributor = distributorRepository.findById(instanceTransport.getDistibutorId()).get();
            distributor.setAdvancedPaymentAmount(distributor.getAdvancedPaymentAmount().subtract(amount));
            distributorRepository.save(distributor);*//*




*//*
            AdvancedPaymentTransfer advancedPaymentTransfer = new AdvancedPaymentTransfer();
            advancedPaymentTransfer.setAmount(amount);
            advancedPaymentTransfer.setDistributorId(distributor.getId());
            advancedPaymentTransfer.setDebitCreditType(FinancialConstants.CREDIT.getValue());
            advancedPaymentTransfer.setInstanceId(instanceTransport.getId());
            advancedPaymentTransfer.setInventoryId(inventory.getId());

            advancedPaymentTransfer.setBalance(distributor.getAdvancedPaymentAmount().subtract(advancedPaymentTransfer.getAmount()));
            advancedPaymentTransferRepository.save(advancedPaymentTransfer);
*//*

            operationResult.setSuccess(true);





            NotificationData notificationData = new NotificationData();

            notificationData.setInventoryTransfer(inventoryTransfer);
        //    notificationData.setAdvancedPaymentTransfer(advancedPaymentTransfer);
            notificationData.setInventory(inventory);
            notificationData.setInstanceTransport(instanceTransport);
            notificationData.setDeliveryOrder(reportDeliveryOrder);
            eventBus.notify(ReactorEventConfig.notificationConsumer_input_netweight_event, Event.wrap(notificationData));
        }else{
            operationResult.setSuccess(false);
            operationResult.setErrorMessage("找到 旧的 出库信息");
        }

        return operationResult;

    }*/


    @Override
    public Map get(InventoryTransfer transfer) {
        ObjectMapper m = new ObjectMapper();
        Map<String,Object> mappedObject = m.convertValue(transfer,Map.class);
        InstanceTransport instanceTransport = instanceTransportRepository.findByUuid(transfer.getInstanceUuid());

        String companiesUrl = linkTo(methodOn(MobileDistributorController.class).detail(instanceTransport.getDistributorNo(), null)).withSelfRel().getHref();


        if(instanceTransport.getOutboundTime() != null){
            mappedObject.put("loadingTime",DateUtils.getDistanceTime(instanceTransport.getCreateDate(),instanceTransport.getOutboundTime()));
        }

        if(instanceTransport.getGrossWeight() != null){
            mappedObject.put("netWeight",instanceTransport.getGrossWeight().subtract(instanceTransport.getTareWeight()));
        }
        Distributor distributor = distributorRepository.findByNo(instanceTransport.getDistributorNo());
        mappedObject.put("distributorUrl",companiesUrl);
        mappedObject.put("distributor",distributor.getName()+distributor.getNo());

        mappedObject.put("createDate",instanceTransport.getCreateDate());
        mappedObject.put("outboundTime",instanceTransport.getOutboundTime());
        mappedObject.put("boundTime",instanceTransport.getBoundTime());
        String url = linkTo(methodOn(MobileInstanceTransprotController.class).detail(instanceTransport.getId(), null, null)).withSelfRel().getHref();
        mappedObject.put("url",url);




        Inventory inventory = inventoryRepository.findByNo(instanceTransport.getInventoryNo());

        mappedObject.put("inventoryNo",inventory.getNo());
        mappedObject.put("granularity",inventory.getGranularity());
        mappedObject.put("coalType",inventory.getCoalType());
        mappedObject.put("productNo",inventory.getProductNo());


        ReportDeliveryOrder reportDeliveryOrder = reportDeliveryOrderRepository.findByNo(instanceTransport.getDeliveryOrderNo());

        mappedObject.put("deliveryOrderNo",reportDeliveryOrder.getNo());


        return mappedObject;


    }

    @Override
    public InventoryTransfer getById(Integer index) {
        return inventoryTransferRepository.findById(index).get();
    }

    @Override
    public InventoryTransfer correct(InventoryTransfer inventoryTransfer, InventoryTranferFormCorrect locationCreateForm) {

        inventoryTransfer = correct_Transactional(inventoryTransfer,locationCreateForm);

        differentialSyncService.syncImmediately();

         return inventoryTransfer;
    }

    @Transactional
    public InventoryTransfer correct_Transactional(InventoryTransfer inventoryTransfer, InventoryTranferFormCorrect locationCreateForm) {
        if(locationCreateForm.getWeight()!= null){
            inventoryTransfer.setWeight(locationCreateForm.getWeight());
        }
        if(locationCreateForm.getUnitPrice()!= null){
            inventoryTransfer.setUnitPrice(locationCreateForm.getUnitPrice());
        }

        if(locationCreateForm.getAmount()!= null){
            inventoryTransfer.setAmount(locationCreateForm.getAmount());
        }

        if(locationCreateForm.getTaxAmount()!= null){
            inventoryTransfer.setTaxAmount(locationCreateForm.getTaxAmount());
        }
        inventoryTransfer.setStatus(FinancialStatusEnum.correct.getText());

        InventoryTransfer inventoryTransfer1 =  inventoryTransferRepository.save(inventoryTransfer);


        differentialSyncService.syncImmediately();

        return inventoryTransfer;
    }



    @Override
    public InventoryTransfer findByNo(String index) {
        return inventoryTransferRepository.findByNo(index);
    }


}
