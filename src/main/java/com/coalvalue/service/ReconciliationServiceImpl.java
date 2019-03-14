package com.coalvalue.service;


import com.coalvalue.domain.entity.Distributor;
import com.coalvalue.domain.entity.*;
import com.coalvalue.enumType.DataSynchronizationTypeEnum;
import com.coalvalue.enumType.ReconcileStatusEnum;
import com.coalvalue.notification.NotificationData;
import com.coalvalue.repository.*;
import com.coalvalue.web.MobileDistributorController;
import com.coalvalue.web.MobileReconciliationController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by silence yuan on 2015/7/25.
 */

@Service("reconciliationService")
public class ReconciliationServiceImpl extends BaseServiceImpl implements ReconciliationService {

    @Autowired
    private BehaviouralRepository behaviouralRepository;

    @Autowired
    private DeliveryOrderService deliveryOrderService;


    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private DistributorService distributorService;


    @Autowired
    private QrcodeService qrcodeService;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReconciliationRepository reconciliationRepository;
    @Autowired
    private ReconciliationItemRepository reconciliationItemRepository;

    @Autowired
    private InventoryTransferRepository inventoryTransferRepository;
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
    @Transactional
    public void syncImmediately(SyncProperties synchronized_, Map map) {

        String type = (String)map.get("type");
     //   synchronizedRepository.save(synchronized_);
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



    }

    @Override
    public Page<Map> findAll(Distributor distributor, Pageable pageable) {
        Page<Reconciliation> pages = null;
        if(distributor==null){
            pages = reconciliationRepository.findAll(pageable);
        }else{
            pages = reconciliationRepository.findByDistributorId(distributor.getId(),pageable);
        }

        Page<Map> page = pages.map(new Function<Reconciliation, Map>() {
            public Map apply(Reconciliation objectEntity) {

                ObjectMapper m = new ObjectMapper();
                Map<String,Object> mappedObject = m.convertValue(objectEntity,Map.class);

                Distributor distributor = distributorService.getById(objectEntity.getDistributorId());

                mappedObject.put("distributor",distributor.getName());
                mappedObject.put("periedEndDate",objectEntity.getPeriedEndDate());
                mappedObject.put("periodBeginDate",objectEntity.getPeriodBeginDate());




                String distributorUrl = linkTo(methodOn(MobileDistributorController.class).detail(distributor.getNo(), null)).withSelfRel().getHref();

                mappedObject.put("distributorUrl",distributorUrl);
                mappedObject.put("createDate",objectEntity.getCreateDate());

                String url = linkTo(methodOn(MobileReconciliationController.class).detail(objectEntity.getId(), null)).withSelfRel().getHref();

                mappedObject.put("url",url);
                return mappedObject;
            }
        });
        return page;


    }

    @Override
    public Reconciliation getById(Integer index) {
        return reconciliationRepository.findById(index).get();
    }

    @Override
    public Page<Map> queryreconciliationItems(Reconciliation reconciliation, Pageable pageable) {

        Page<ReconciliationItem> pages = reconciliationItemRepository.findByReconciliationId(reconciliation.getId(), pageable);
        Page<Map> page = pages.map(new Function<ReconciliationItem, Map>() {
            public Map apply(ReconciliationItem objectEntity) {

                ObjectMapper m = new ObjectMapper();
                Map<String,Object> mappedObject = m.convertValue(objectEntity,Map.class);

                mappedObject.put("createDate",objectEntity.getCreateDate());

                Distributor distributor = distributorService.getByNo(reconciliation.getDistributorNo());
                mappedObject.put("distributorNo",distributor.getNo());
                mappedObject.put("distributor",distributor.getName());

                InventoryTransfer inventoryTransfer = inventoryTransferRepository.findById(objectEntity.getTransferId()).get();
                mappedObject.put("deliveryOrderNo",inventoryTransfer.getDeliveryOrderNo());

                mappedObject.put("license",inventoryTransfer.getLicense());

                mappedObject.put("amount",inventoryTransfer.getAmount());
                mappedObject.put("unitPrice",inventoryTransfer.getUnitPrice());


                mappedObject.put("totalPrice",inventoryTransfer.getAmount());

                return mappedObject;
            }
        });
        return page;


    }


    @Override
    public Page<Map> queryStatistic(Reconciliation reconciliation, Pageable pageable) {

        List<ReconciliationItem> reconciliationItems = reconciliationItemRepository.findByReconciliationId(reconciliation.getId());

        Multimap<String, ReconciliationItem> inventoryTransfers_groups = ArrayListMultimap.create();
        for(ReconciliationItem instanceTransport: reconciliationItems){
            inventoryTransfers_groups.put(instanceTransport.getInventoryNo(),instanceTransport);
        }


        List<Map> maps = new ArrayList<Map>();
        for(String distributorId : inventoryTransfers_groups.keySet()) {


            Collection<ReconciliationItem> inventoryTransfers_for_distributor = inventoryTransfers_groups.get(distributorId);
            Inventory distributor_ = inventoryService.getInventoryByNo(distributorId);

            BigDecimal totalQuantity = inventoryTransfers_for_distributor.stream().map(e -> e.getNetWeight()).reduce((acc, item) -> {
                return acc.add(item);
            }).get();


            Map map = new HashMap<>();
            map.put("coalType",distributor_.getCoalType());
            map.put("granularity",distributor_.getGranularity());
            map.put("no",distributor_.getNo());
            map.put("count",inventoryTransfers_for_distributor.size());
            map.put("totalQuantity",totalQuantity);
            maps.add(map);


        }

        Page<Map> pages = new PageImpl<Map>(maps,pageable,maps.size());
        return pages;




    }

    @Override
    public Page<Map> findAllReconciliation_confirmed(Object o, Pageable pageable) {
        Page<Reconciliation> pages  = reconciliationRepository.findByStatus(ReconcileStatusEnum.Checked.getText(),pageable);

        Page<Map> page = pages.map(new Function<Reconciliation, Map>() {
            public Map apply(Reconciliation objectEntity) {

                ObjectMapper m = new ObjectMapper();
                Map<String,Object> mappedObject = m.convertValue(objectEntity,Map.class);

                Distributor distributor = distributorService.getById(objectEntity.getDistributorId());

                mappedObject.put("distributor",distributor.getName());
                mappedObject.put("periedEndDate",objectEntity.getPeriedEndDate());
                mappedObject.put("periodBeginDate",objectEntity.getPeriodBeginDate());




                String distributorUrl = linkTo(methodOn(MobileDistributorController.class).detail(distributor.getNo(), null)).withSelfRel().getHref();

                mappedObject.put("distributorUrl",distributorUrl);
                mappedObject.put("createDate",objectEntity.getCreateDate());

                String url = linkTo(methodOn(MobileReconciliationController.class).detail(objectEntity.getId(), null)).withSelfRel().getHref();

                mappedObject.put("url",url);
                return mappedObject;
            }
        });
        return page;



    }


}
