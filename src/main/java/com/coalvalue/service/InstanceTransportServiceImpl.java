package com.coalvalue.service;


import com.coalvalue.configuration.ReactorEventConfig;
import com.coalvalue.domain.entity.Distributor;
import com.coalvalue.domain.OperationResult;
import com.coalvalue.domain.entity.*;
import com.coalvalue.dto.InstanceTransportDto;
import com.coalvalue.enumType.*;
import com.coalvalue.notification.NotificationData;
import com.coalvalue.notification.liveEvent.NotificationData_sync;
import com.coalvalue.repository.*;
import com.coalvalue.repository.specific.InstanceTransportSpec;
import com.coalvalue.service.strategy.StrategyService;
import com.coalvalue.service.sync.SynchronizationService;
import com.coalvalue.task.LiveBroadcast;
import com.coalvalue.util.SequenceGenerator;
import com.coalvalue.web.InventoryTransferCreateForm;
import com.coalvalue.web.MobileDistributorController;
import com.coalvalue.web.MobileInstanceTransprotController;
import com.coalvalue.web.valid.InstanceTransportCreateForm;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.util.DateUtils;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.core.support.Assert;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by silence yuan on 2015/7/25.
 */

@Service("instanceTransportService")
public class InstanceTransportServiceImpl extends BaseServiceImpl implements InstanceTransportService {

    @Autowired
    private BehaviouralRepository behaviouralRepository;
    @Autowired
    private InstanceTransportRepository instanceTransportRepository;

    @Autowired
    private EventBus eventBus;

    @Autowired
    private LiveBroadcast liveBroadcast;
    @Autowired
    private FeeService feeService;
    @Autowired
    private StrategyService strategyService;



    @Autowired
    private DistributorRepository distributorRepository;


    @Autowired
    private InventoryTransferRepository inventoryTransferRepository;

    @Autowired
    private AdvancedPaymentTransferRepository advancedPaymentTransferRepository;




    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ReportDeliveryOrderRepository reportDeliveryOrderRepository;

    @Autowired
    private EmployeeService employeeService;


    @Override
    @Transactional
    public void create(NotificationData data) {

        Behavioural behavioural = new Behavioural();
        behavioural = behaviouralRepository.save(behavioural);

    }

    @Override
    public Page<Map> query(Object om ,Pageable pageable) {

        Page<InstanceTransport> transports = instanceTransportRepository.findAll(pageable);


        Page<Map> page = transports.map(new Function<InstanceTransport, Map>() {
            public Map apply(InstanceTransport objectEntity) {

                ObjectMapper m = new ObjectMapper();
                Map<String,Object> mappedObject = m.convertValue(objectEntity,Map.class);



                String companiesUrl = linkTo(methodOn(MobileDistributorController.class).detail(objectEntity.getDistributorNo(), null)).withSelfRel().getHref();




                if(objectEntity.getOutboundTime() != null){
                    mappedObject.put("loadingTime",DateUtils.getDistanceTime(objectEntity.getCreateDate(),objectEntity.getOutboundTime()));
                }


                if(objectEntity.getGrossWeight() != null){
                    mappedObject.put("netWeight",objectEntity.getGrossWeight().subtract(objectEntity.getTareWeight()));
                }
                Distributor distributor = distributorRepository.findByNo(objectEntity.getDistributorNo());
                mappedObject.put("distributorUrl",companiesUrl);
                mappedObject.put("distributor",distributor.getName()+distributor.getNo());

                mappedObject.put("createDate",objectEntity.getCreateDate());
                mappedObject.put("outboundTime",objectEntity.getOutboundTime());

                String url = linkTo(methodOn(MobileInstanceTransprotController.class).detail(objectEntity.getId(), null, null)).withSelfRel().getHref();
                mappedObject.put("url",url);

                return mappedObject;
            }
        });
        return page;

    }

    @Override
    @Transactional(rollbackFor={Exception.class},propagation = Propagation.REQUIRED, readOnly = false)

    public InstanceTransport createFromDeliveryOrderInputTareWeight(ReportDeliveryOrder reportDeliveryOrder, Fee fee, InstanceTransportCreateForm locationCreateForm) throws MqttException {


       // PriceCategory priceCategory = feeService.getPriceCategory(fee,reportDeliveryOrder.getProductNo());


        if(!reportDeliveryOrder.getStatus().equals(DeliveryOrderStatusEnum.Valid.getText())){
            return null;
        }

        InstanceTransport instanceTransport = instanceTransportRepository.findByDeliveryOrderNo(reportDeliveryOrder.getNo());

        if(instanceTransport == null){

            Inventory inventory = inventoryRepository.findByNo(reportDeliveryOrder.getInventoryNo());


            Distributor distributor = distributorRepository.findByNo(reportDeliveryOrder.getDistributorNo());


            instanceTransport = new InstanceTransport();
            instanceTransport.setTareWeight(locationCreateForm.getTareWeight());

            instanceTransport.setCoalType(inventory.getCoalType());
            instanceTransport.setGranularity(inventory.getGranularity());
            instanceTransport.setStorageNo(reportDeliveryOrder.getStorageNo());


            instanceTransport.setInventoryNo(inventory.getNo());


            instanceTransport.setLicense(reportDeliveryOrder.getLicense());

            instanceTransport.setDistributorNo(distributor.getNo());


        //   instanceTransport.setUnitPrice(priceCategory.getValue());


            instanceTransport.setBoundTime(LocalDateTime.now());

            instanceTransport.setDeliveryOrderNo(reportDeliveryOrder.getNo());

            instanceTransport.setStatus(InstanceTransportStatusEnum.LOADING.getText());

            instanceTransport.setUuid(UUID.randomUUID().toString());

            instanceTransport =  instanceTransportRepository.save(instanceTransport);
            reportDeliveryOrder.setStatus(DeliveryOrderStatusEnum.Taking_delivery.getText());
            reportDeliveryOrder.setTransportOperationUuid(instanceTransport.getUuid());
            reportDeliveryOrder = reportDeliveryOrderRepository.save(reportDeliveryOrder);
            instanceTransport.setDeliveryOrderNo(reportDeliveryOrder.getNo());

            liveBroadcast.reportQueueEvent_TEST(instanceTransport,"in");


        }else{
            instanceTransport.setTareWeight(locationCreateForm.getTareWeight());
            instanceTransport.setBoundTime(LocalDateTime.now());
            instanceTransport =  instanceTransportRepository.save(instanceTransport);

        }
















      ReportDeliveryOrder notify_reportDeliveryOrder1 = reportDeliveryOrder;
        InstanceTransport notify_instanceTransport = instanceTransport;
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter(){
            public void afterCommit(){
                System.out.println("commit!!!");
                NotificationData notificationData = new NotificationData();
                notificationData.setObject(notify_reportDeliveryOrder1);
                Inventory inventory = inventoryRepository.findByNo(notify_instanceTransport.getInventoryNo());
                notificationData.setInventory(inventory);
                notificationData.setInstanceTransport(notify_instanceTransport);
                eventBus.notify(ReactorEventConfig.notificationConsumer_input_tareweight_event, Event.wrap(notificationData));
            }
        });
        return instanceTransport;
    }






    @Override
    public InstanceTransport getById(Integer id) {
        return instanceTransportRepository.findById(id).get();
    }




    @Override
    @Transactional(rollbackFor={Exception.class},propagation = Propagation.REQUIRED, readOnly = false)

    public OperationResult createTransfer(InstanceTransport instanceTransport, InventoryTransferCreateForm locationCreateForm, User user) throws MqttException {

        Assert.notNull(instanceTransport," instanceTransport 不能为 null");
        Assert.notNull(instanceTransport," instanceTransport 不能为 null");
        Assert.notNull(instanceTransport," instanceTransport 不能为 null");


        OperationResult operationResult = new OperationResult();

        if(!instanceTransport.getStatus().equals(InstanceTransportStatusEnum.LOADING.getText())){
            operationResult.setSuccess(false);
            operationResult.setErrorMessage("不是装载状态");
            return operationResult;
        }


        BigDecimal netWeight = null;
        if(locationCreateForm.getNetWeight()!= null){
            netWeight =locationCreateForm.getNetWeight();

        }else{
            BigDecimal grossWeight = locationCreateForm.getGrossWeight();
            BigDecimal tareWeight = instanceTransport.getTareWeight();
            netWeight =grossWeight.subtract(tareWeight);
        }
        if(netWeight.floatValue()<=0){
            operationResult.setSuccess(false);
            operationResult.setErrorMessage("净重 不能为负");
            return operationResult;
        }


        InventoryTransfer inventoryTransfer = inventoryTransferRepository.findByInstanceUuid(instanceTransport.getUuid());






        if(inventoryTransfer!= null){

            operationResult.setSuccess(false);
            operationResult.setErrorMessage("找到 旧的 出库信息");
            return operationResult;
        }



        Inventory inventory = inventoryRepository.findByNo(instanceTransport.getInventoryNo());
        ReportDeliveryOrder reportDeliveryOrder = reportDeliveryOrderRepository.findByNo(instanceTransport.getDeliveryOrderNo());
        Distributor distributor = distributorRepository.findByNo(instanceTransport.getDistributorNo());
        if(locationCreateForm.getGrossWeight()!= null){
            instanceTransport.setGrossWeight(locationCreateForm.getGrossWeight());
        }else{
            instanceTransport.setGrossWeight(instanceTransport.getTareWeight().add(netWeight));
        }


        instanceTransport.setOutboundTime(LocalDateTime.now());
        instanceTransport.setStatus(InstanceTransportStatusEnum.LEAVE.getText());
        instanceTransport = instanceTransportRepository.save(instanceTransport);




        inventoryTransfer = new InventoryTransfer();
        inventoryTransfer.setLicense(instanceTransport.getLicense());

        inventoryTransfer.setDistributorNo(instanceTransport.getDistributorNo());
        inventoryTransfer.setDistributorNo(distributor.getNo());

        inventoryTransfer.setInstanceUuid(instanceTransport.getUuid());
        inventoryTransfer.setWeight(netWeight);






        inventoryTransfer.setDeliveryOrderNo(reportDeliveryOrder.getNo());
        inventoryTransfer.setInventoryNo(inventory.getNo());
        inventoryTransfer.setDistributorNo(distributor.getNo());



        Employee employee = employeeService.getEmployee(user);

        inventoryTransfer.setWeighmanNo(employee.getNo());
        inventoryTransfer.setWeighmanName(employee.getRealName());
        inventoryTransfer.setWeighmanPhone(employee.getMobile());


        inventoryTransfer.setOperatorNo(reportDeliveryOrder.getOperatorNo());
        inventoryTransfer.setOperatorPhone(reportDeliveryOrder.getOperatorPhone());
        inventoryTransfer.setOperatorName(reportDeliveryOrder.getOperatorName());


        inventoryTransfer.setConsigneeName(reportDeliveryOrder.getConsigneeName());
        inventoryTransfer.setConsigneePhone(reportDeliveryOrder.getConsigneePhone());



        inventoryTransfer.setUuid(UUID.randomUUID().toString());


        //   inventoryService.
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


        inventoryTransfer.setReconcileStatus(ReconcileStatusEnum.NoReconciliation.getText());
        inventoryTransfer.setStorageNo(inventory.getStorageNo());
        inventoryTransfer.setProductNo(inventory.getProductNo());
        inventoryTransfer.setProducerNo(user.getCompanyNo());





        feeService.addFee(inventoryTransfer,distributor,instanceTransport,inventory);
        inventoryTransfer = inventoryTransferRepository.save(inventoryTransfer);





/*
            BigDecimal quote = inventory.getQuote();
            if(quote== null){
                inventory.setQuote(new BigDecimal(10));
                inventory = inventoryRepository.save(inventory);
            }
*/

        reportDeliveryOrder.setStatus(DeliveryOrderStatusEnum.Completed.getText());
        reportDeliveryOrderRepository.save(reportDeliveryOrder);


        liveBroadcast.reportQueueEvent_TEST(instanceTransport,"out");













        operationResult.setSuccess(true);
        operationResult.setResultObject(inventoryTransfer);











        InventoryTransfer notify_inventoryTransfer = inventoryTransfer;
        InstanceTransport notify_instanceTransport = instanceTransport;
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            public void afterCommit() {

                NotificationData notificationData = new NotificationData();

                notificationData.setInventoryTransfer(notify_inventoryTransfer);
                //    notificationData.setAdvancedPaymentTransfer(advancedPaymentTransfer);
                notificationData.setInventory(inventory);
                notificationData.setInstanceTransport(notify_instanceTransport);
                notificationData.setDeliveryOrder(reportDeliveryOrder);
                eventBus.notify(ReactorEventConfig.notificationConsumer_input_netweight_event, Event.wrap(notificationData));
                strategyService.command(StrategyService.Strategy_COMMAND.Strategy_COMMAND_过磅,notify_instanceTransport.getUuid());



            }
        });

        return operationResult;

    }



    @Override
    public Page<Map> query_synthesized(InstanceTransportDto instanceTransportDto, String searchText, Pageable pageable) {



        instanceTransportDto.setSearchText(searchText);



        InstanceTransportSpec instanceTransportSpec =  new InstanceTransportSpec(instanceTransportDto);
        Specification<InstanceTransport> spec = instanceTransportSpec.querySynthesizedSpec();

        Page<InstanceTransport> transports = instanceTransportRepository.findAll(spec,pageable);
        ObjectMapper m = new ObjectMapper();

        Page<Map> page = transports.map(new Function<InstanceTransport, Map>() {
            public Map apply(InstanceTransport objectEntity) {


                Map<String,Object> mappedObject = m.convertValue(objectEntity,Map.class);



                String companiesUrl = linkTo(methodOn(MobileDistributorController.class).detail(objectEntity.getDistributorNo(), null)).withSelfRel().getHref();



                if(objectEntity.getOutboundTime() != null){
                    mappedObject.put("loadingTime",DateUtils.getDistanceTime(objectEntity.getCreateDate(),objectEntity.getOutboundTime()));
                }



                if(objectEntity.getGrossWeight() != null){
                    mappedObject.put("netWeight",objectEntity.getGrossWeight().subtract(objectEntity.getTareWeight()));
                }
                Distributor distributor = distributorRepository.findByNo(objectEntity.getDistributorNo());
                mappedObject.put("distributorUrl",companiesUrl);
                mappedObject.put("inventoryNo",objectEntity.getInventoryNo());
                mappedObject.put("distributor",distributor.getName()+distributor.getNo());
                mappedObject.put("outboundTime",objectEntity.getOutboundTime());
                mappedObject.put("boundTime",objectEntity.getBoundTime());
             //   mappedObject.put("boundTime",objectEntity.getDeliveryOrderNo());
                mappedObject.put("createDate",objectEntity.getCreateDate());
                mappedObject.put("unitPrice", objectEntity.getUnitPrice());
                String url = linkTo(methodOn(MobileInstanceTransprotController.class).detail(objectEntity.getId(), null, null)).withSelfRel().getHref();
                mappedObject.put("url",url);
                return mappedObject;
            }
        });
        return page;
    }

    @Override
    public Map get(InstanceTransport instanceTransport) {
        ObjectMapper m = new ObjectMapper();
        Map<String,Object> mappedObject = m.convertValue(instanceTransport,Map.class);

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
    public Page<Map> query_BeingLoaded(Pageable pageable) {


        Page<InstanceTransport> transports = instanceTransportRepository.findByStatus(InstanceTransportStatusEnum.LOADING.getText(),pageable);


        Page<Map> page = transports.map(new Function<InstanceTransport, Map>() {
            public Map apply(InstanceTransport objectEntity) {

                ObjectMapper m = new ObjectMapper();
                Map<String,Object> mappedObject = m.convertValue(objectEntity,Map.class);



                String companiesUrl = linkTo(methodOn(MobileDistributorController.class).detail(objectEntity.getDistributorNo(), null)).withSelfRel().getHref();




                if(objectEntity.getOutboundTime() != null){
                    mappedObject.put("loadingTime",DateUtils.getDistanceTime(objectEntity.getCreateDate(),objectEntity.getOutboundTime()));
                }



                if(objectEntity.getGrossWeight() != null){
                    mappedObject.put("netWeight",objectEntity.getGrossWeight().subtract(objectEntity.getTareWeight()));
                }
                Distributor distributor = distributorRepository.findByNo(objectEntity.getDistributorNo());
                mappedObject.put("distributorUrl",companiesUrl);
                mappedObject.put("distributor",distributor.getName());
                mappedObject.put("outboundTime",objectEntity.getOutboundTime());
                mappedObject.put("boundTime",objectEntity.getBoundTime());
                mappedObject.put("createDate",objectEntity.getCreateDate());
                mappedObject.put("product",objectEntity.getCoalType()+"/"+objectEntity.getGranularity());
                mappedObject.put("status",InstanceTransportStatusEnum.fromString(objectEntity.getStatus()).getDisplayText());
                String url = linkTo(methodOn(MobileInstanceTransprotController.class).detail(objectEntity.getId(), null, null)).withSelfRel().getHref();
                mappedObject.put("url",url);
                return mappedObject;
            }
        });
        return page;
    }

    @Override
    public InstanceTransport getLoadingByLicense(String license) {
        return instanceTransportRepository.findByLicenseAndStatus( license,InstanceTransportStatusEnum.LOADING.getText());
    }


    @Transactional
    public InstanceTransport outBound(InstanceTransport instanceTransport, BigDecimal grossWeight, BigDecimal netWeight) {


        instanceTransport.setGrossWeight(grossWeight);
        instanceTransport.setNetWeight(netWeight);
        instanceTransport.setOutboundTime(LocalDateTime.now());
        instanceTransport.setStatus(InstanceTransportStatusEnum.LEAVE.getText());
        return instanceTransportRepository.save(instanceTransport);



    }
}
