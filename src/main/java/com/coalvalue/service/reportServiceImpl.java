package com.coalvalue.service;

import com.coalvalue.configuration.ReactorEventConfig;
import com.coalvalue.domain.entity.Distributor;
import com.coalvalue.domain.OperationResult;

import com.coalvalue.domain.entity.*;

import com.coalvalue.enumType.ReconcileStatusEnum;
import com.coalvalue.notification.NotificationData_reconciliation;
import com.coalvalue.repository.*;
import com.coalvalue.web.MobileDistributorController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ArrayListMultimap;

import com.google.common.collect.Multimap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.bus.Event;
import reactor.bus.EventBus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

//import org.springframework.security.core.Authentication;

/**
 * Created by silence yuan on 2015/6/28.
 */
@Service("reportService")
public class reportServiceImpl extends BaseServiceImpl implements ReportService {






    @Autowired
    private InventoryRepository inventoryRepository;



    @Autowired
    private EventBus eventBus;
    @Autowired
    private ConfigurationService configurationService;


    private static final Logger logger = LoggerFactory.getLogger(reportServiceImpl.class);


    @Autowired
    private DistributorRepository distributorRepository;

    @Autowired
    private ReconciliationRepository reconciliationRepository;

    @Autowired
    private ReconciliationItemRepository reconciliationItemRepository;

    @Autowired
    private InventoryTransferRepository inventoryTransferRepository;


    @Autowired
    private MqttService mqttService;






    @Override
    @Transactional
    public OperationResult  createReconciliation(LocalDateTime startDate, LocalDateTime endDate) {

        OperationResult location = new OperationResult();
        location.setSuccess(true);

      //  List<ReportDeliveryOrder> reportDeliveryOrders = reportDeliveryOrderRepository.findByCompanyNo(distributor.getCompanyNo());




        List<InventoryTransfer> inventoryTransfers = inventoryTransferRepository.findByReconcileStatusAndCreateDateBetween(ReconcileStatusEnum.NoReconciliation.getText(),startDate, endDate);
        List<Inventory> inventories = inventoryRepository.findAll();

        Map<Integer, Inventory> inventoryMap = inventories.stream().collect(Collectors.toMap(Inventory::getId,Function.identity()));


        Multimap<String, InventoryTransfer> inventoryTransfers_groups = ArrayListMultimap.create();
        for(InventoryTransfer instanceTransport: inventoryTransfers){
            inventoryTransfers_groups.put(instanceTransport.getDistributorNo(),instanceTransport);
        }



        List<Reconciliation> reconciliations = new ArrayList<>();

        for(String distributorId : inventoryTransfers_groups.keySet()) {

            Map distributor_map = new HashMap<>();
            Collection<InventoryTransfer>  inventoryTransfers_for_distributor = inventoryTransfers_groups.get(distributorId);
            Distributor distributor_ = distributorRepository.findByNo(distributorId);

            BigDecimal totalQuantity = inventoryTransfers_for_distributor.stream().map(e->e.getWeight()).reduce((acc, item) -> {
                return acc.add(item);
            }).get();


            Reconciliation reconciliation = new Reconciliation();
            reconciliation.setPeriodBeginDate(startDate);
            reconciliation.setPeriedEndDate(endDate);
            reconciliation.setDistributorId(distributor_.getId());
            reconciliation.setStatus(ReconcileStatusEnum.NoReconciliation.getText());
            reconciliation.setTotalCount(inventoryTransfers_for_distributor.size());
            reconciliation.setDistributorNo(distributor_.getNo());
            reconciliation = reconciliationRepository.save(reconciliation);


            distributor_map.put("name", distributor_.getName());





            BigDecimal totalAmount = new BigDecimal(0);


            List<Map> reconciliation_item = new ArrayList<>();
            List<ReconciliationItem> reconciliationItems = new ArrayList<>();
            for(InventoryTransfer inventoryTransfer: inventoryTransfers_for_distributor){
                Map reconciliation_item_map = new HashMap<>();
                reconciliation_item.add(reconciliation_item_map);


                if(inventoryTransfer.getId() != null  && inventoryTransfer.getUnitPrice()!= null){
                    BigDecimal amount = inventoryTransfer.getWeight().multiply(inventoryTransfer.getUnitPrice());
                    //          totalAmount = totalAmount.add(amount);
                }


                ReconciliationItem reconciliationItem = new ReconciliationItem();

                reconciliationItem.setDistributorNo(distributor_.getNo());

                reconciliationItem.setPlateNumber(inventoryTransfer.getLicense());
                reconciliationItem.setNetWeight(inventoryTransfer.getWeight());
                reconciliationItem.setReconciliationId(reconciliation.getId());
                reconciliationItem.setInventoryNo(inventoryTransfer.getInventoryNo());
                reconciliationItem.setTransferId(inventoryTransfer.getId());
                reconciliationItem = reconciliationItemRepository.save(reconciliationItem);
                reconciliationItems.add(reconciliationItem);



                inventoryTransfer.setReconcileStatus(ReconcileStatusEnum.BeingReconciled.getText());
                inventoryTransfer = inventoryTransferRepository.save(inventoryTransfer);

            }



            distributor_map.put("detail_items", reconciliation_item);
            distributor_map.put("total_amount", totalQuantity);




            reconciliation.setTotalQuantity(totalQuantity);
            reconciliation.setTotalAmount(totalAmount);
            reconciliation.setReconciliationItems(reconciliationItems);

            reconciliation = reconciliationRepository.save(reconciliation);

            reconciliations.add(reconciliation);
        }

        Map map = new HashMap();
        map.put("totalInstanceTransportCount", inventoryTransfers.size());



        NotificationData_reconciliation notificationData = new NotificationData_reconciliation();
        notificationData.setObject(reconciliations);
        Configuration configuration = configurationService.getConfiguration(ConfigurationServiceImpl.PRODUCER_CONFIGURATION_KEY_companyNo);
        notificationData.setProducerNo(configuration.getStringValue());
        //notificationData.setDistributor(distributor);
        eventBus.notify(ReactorEventConfig.notificationConsumer_reconciliation_event, Event.wrap(notificationData));



        //mqttService.publishTo_Delivery_web_server(JSON.toJSONString(map));

        return location;
    }











    @Override
    @Transactional
    public Page<Map> getReport(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {

        OperationResult location = new OperationResult();
        location.setSuccess(true);


        List<InventoryTransfer> inventoryTransfers = inventoryTransferRepository.findByReconcileStatusAndCreateDateBetween(ReconcileStatusEnum.NoReconciliation.getText(),startDate, endDate);
        List<Inventory> inventories = inventoryRepository.findAll();

        Map<Integer, Inventory> inventoryMap = inventories.stream().collect(Collectors.toMap(Inventory::getId,Function.identity()));


        Multimap<String, InventoryTransfer> inventoryTransfers_groups = ArrayListMultimap.create();
        for(InventoryTransfer instanceTransport: inventoryTransfers){
            inventoryTransfers_groups.put(instanceTransport.getDistributorNo(),instanceTransport);
        }



        List<Map> reconciliations = new ArrayList<>();

        for(String distributorId : inventoryTransfers_groups.keySet()) {


            Collection<InventoryTransfer>  inventoryTransfers_for_distributor = inventoryTransfers_groups.get(distributorId);
            Distributor distributor_ = distributorRepository.findByNo(distributorId);

            BigDecimal totalQuantity = inventoryTransfers_for_distributor.stream().map(e->e.getWeight()).reduce((acc, item) -> {
                return acc.add(item);
            }).get();

            ObjectMapper m = new ObjectMapper();
            Map<String,Object> mappedObject = m.convertValue(distributor_,Map.class);


            String companiesUrl = linkTo(methodOn(MobileDistributorController.class).detail(distributor_.getNo(), null)).withSelfRel().getHref();

            mappedObject.put("url",companiesUrl);



            mappedObject.put("name", distributor_.getName());


            mappedObject.put("totalQuantity", totalQuantity);
            mappedObject.put("totalAmount", totalQuantity);
            mappedObject.put("totalTaxAmount", totalQuantity);








            reconciliations.add(mappedObject);


        }



        return new PageImpl<Map>(reconciliations, pageable, reconciliations.size());


    }

}
