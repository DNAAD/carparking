package com.coalvalue.service;


import com.coalvalue.domain.Distributor;
import com.coalvalue.domain.entity.*;
import com.coalvalue.dto.InstanceTransportDto;
import com.coalvalue.enumType.DeliveryOrderStatusEnum;
import com.coalvalue.enumType.FinancialConstants;
import com.coalvalue.enumType.FinancialStatusEnum;
import com.coalvalue.enumType.InstanceTransportStatusEnum;
import com.coalvalue.notification.NotificationData;
import com.coalvalue.repository.*;
import com.coalvalue.repository.specific.InstanceTransportSpec;
import com.coalvalue.web.InventoryTransferCreateForm;
import com.coalvalue.web.MobileDistributorController;
import com.coalvalue.web.MobileInstanceTransprotController;
import com.coalvalue.web.valid.InstanceTransportCreateForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.BaseServiceImpl;
import com.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

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
    private DistributorRepository distributorRepository;


    @Autowired
    private InventoryTransferRepository inventoryTransferRepository;


    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ReportDeliveryOrderRepository reportDeliveryOrderRepository;
    @Autowired
    private AdvancedPaymentTransferRepository advancedPaymentTransferRepository;




    @Override
    @Transactional
    public void create(NotificationData data) {

        Behavioural behavioural = new Behavioural();
        behavioural = behaviouralRepository.save(behavioural);

    }

    @Override
    public Page<Map> query(Object om ,Pageable pageable) {

        Page<InstanceTransport> transports = instanceTransportRepository.findAll(pageable);


        Page<Map> page = transports.map(new Converter<InstanceTransport, Map>() {
            public Map convert(InstanceTransport objectEntity) {

                ObjectMapper m = new ObjectMapper();
                Map<String,Object> mappedObject = m.convertValue(objectEntity,Map.class);



                String companiesUrl = linkTo(methodOn(MobileDistributorController.class).detail(objectEntity.getDistibutorId(), null)).withSelfRel().getHref();




                if(objectEntity.getOutboundTime() != null){
                    mappedObject.put("loadingTime",DateUtils.getDistanceTime(objectEntity.getCreateDate(),objectEntity.getOutboundTime()));
                }



                if(objectEntity.getGrossWeight() != null){
                    mappedObject.put("netWeight",objectEntity.getGrossWeight().subtract(objectEntity.getTareWeight()));
                }
                Distributor distributor = distributorRepository.findById(objectEntity.getDistibutorId());
                mappedObject.put("distributorUrl",companiesUrl);
                mappedObject.put("distributor",distributor.getName()+distributor.getCompanyNo());



                String url = linkTo(methodOn(MobileInstanceTransprotController.class).detail(objectEntity.getDistibutorId(), null, null)).withSelfRel().getHref();
                mappedObject.put("url",url);

                return mappedObject;
            }
        });
        return page;

    }

    @Override
    @Transactional
    public InstanceTransport createFromDeliveryOrder(ReportDeliveryOrder reportDeliveryOrder, InstanceTransportCreateForm locationCreateForm) {


        InstanceTransport instanceTransport = instanceTransportRepository.findByDeliveryOrderId(reportDeliveryOrder.getId());

        if(instanceTransport == null){
            instanceTransport = new InstanceTransport();
            instanceTransport.setTareWeight(locationCreateForm.getTareWeight());


            //instanceTransport.setInventoryId();

            instanceTransport.setTransportId(reportDeliveryOrder.getTransportOperationId());

            instanceTransport.setDeliveryOrderId(reportDeliveryOrder.getId());



            instanceTransport.setPlateNumber(reportDeliveryOrder.getPlateNumber());
            Distributor distributor = distributorRepository.findByCompanyNo(reportDeliveryOrder.getCompanyNo());

            instanceTransport.setCollaboratorId(distributor.getId());
            Inventory inventory = inventoryRepository.findByNo(reportDeliveryOrder.getInventoryNo());
            instanceTransport.setInventoryId(inventory.getId());
            instanceTransport.setBoundTime(new Date());
            instanceTransport.setStatus(InstanceTransportStatusEnum.LOADING.getText());

            reportDeliveryOrder.setStatus(DeliveryOrderStatusEnum.Completed.getText());
            reportDeliveryOrderRepository.save(reportDeliveryOrder);

            return instanceTransportRepository.save(instanceTransport);
        }else{
            instanceTransport.setTareWeight(locationCreateForm.getTareWeight());
            instanceTransport.setBoundTime(new Date());
            return instanceTransportRepository.save(instanceTransport);
        }




    }

    @Override
    public InstanceTransport getById(Integer id) {
        return instanceTransportRepository.findById(id);
    }

    @Override
    @Transactional
    public InventoryTransfer createTransfer(InstanceTransport instanceTransport, InventoryTransferCreateForm locationCreateForm) {

        logger.debug("InstanceTransport  {}" ,instanceTransport,toString());

        outBound(instanceTransport,locationCreateForm.getGrossWeight(),locationCreateForm.getNetWeight());



        InventoryTransfer inventoryTransfer = new InventoryTransfer();
        inventoryTransfer.setAmount(locationCreateForm.getNetWeight());
        BigDecimal netWeight =locationCreateForm.getGrossWeight().subtract(instanceTransport.getTareWeight());

        if(netWeight.floatValue()<=0){

            return null;
        }
        inventoryTransfer.setAmount(netWeight);
        inventoryTransfer.setDistributor(instanceTransport.getDistibutorId());
        inventoryTransfer.setInstanceId(instanceTransport.getId());

        inventoryTransfer.setStatus(FinancialStatusEnum.nsettled.getText());


        Inventory inventory = inventoryRepository.findById(instanceTransport.getInventoryId());



        BigDecimal quote = inventory.getQuote();
        if(quote== null){
            inventory.setQuote(new BigDecimal(10));
            inventory = inventoryRepository.save(inventory);
        }



        BigDecimal balance = inventory.getQuantityOnHand().subtract(netWeight);
        if(balance.floatValue()>0){
            inventoryTransfer.setBalance(balance);

            inventory.setQuantityOnHand(balance);
        }else{
            inventoryTransfer.setBalance(new BigDecimal(0));
            inventory.setQuantityOnHand(new BigDecimal(0));
        }



        inventoryTransfer.setInventoryId(inventory.getId());


        AdvancedPaymentTransfer advancedPaymentTransfer = new AdvancedPaymentTransfer();
        advancedPaymentTransfer.setAmount(inventoryTransfer.getAmount().multiply(inventory.getQuote()));

        Distributor distributor = distributorRepository.findById(instanceTransport.getDistibutorId());
        distributor.setAdvancedPaymentAmount(distributor.getAdvancedPaymentAmount().subtract(advancedPaymentTransfer.getAmount()));
        distributorRepository.save(distributor);

        advancedPaymentTransfer.setDistributorId(distributor.getId());
        advancedPaymentTransfer.setDebitCreditType(FinancialConstants.CREDIT.getValue());
        advancedPaymentTransfer.setInstanceId(instanceTransport.getId());
        advancedPaymentTransfer.setInventoryId(inventory.getId());

        advancedPaymentTransfer.setBalance(distributor.getAdvancedPaymentAmount().subtract(advancedPaymentTransfer.getAmount()));


        advancedPaymentTransferRepository.save(advancedPaymentTransfer);



        return inventoryTransferRepository.save(inventoryTransfer);
    }

    @Override
    public InstanceTransport getBy_InstanceTransport_Id(Integer id) {
        return instanceTransportRepository.findByDeliveryOrderId(id);
    }

    @Override
    public Page<Map> query_synthesized(InstanceTransportDto instanceTransportDto, String searchText, Pageable pageable) {



        instanceTransportDto.setSearchText(searchText);
        System.out.print("========================= {}" + instanceTransportDto.toString());


        InstanceTransportSpec instanceTransportSpec =  new InstanceTransportSpec(instanceTransportDto);
        Specification<InstanceTransport> spec = instanceTransportSpec.querySynthesizedSpec();

        Page<InstanceTransport> transports = instanceTransportRepository.findAll(spec,pageable);


        Page<Map> page = transports.map(new Converter<InstanceTransport, Map>() {
            public Map convert(InstanceTransport objectEntity) {

                ObjectMapper m = new ObjectMapper();
                Map<String,Object> mappedObject = m.convertValue(objectEntity,Map.class);



                String companiesUrl = linkTo(methodOn(MobileDistributorController.class).detail(objectEntity.getDistibutorId(), null)).withSelfRel().getHref();




                if(objectEntity.getOutboundTime() != null){
                    mappedObject.put("loadingTime",DateUtils.getDistanceTime(objectEntity.getCreateDate(),objectEntity.getOutboundTime()));
                }



                if(objectEntity.getGrossWeight() != null){
                    mappedObject.put("netWeight",objectEntity.getGrossWeight().subtract(objectEntity.getTareWeight()));
                }
                Distributor distributor = distributorRepository.findById(objectEntity.getDistibutorId());
                mappedObject.put("distributorUrl",companiesUrl);
                mappedObject.put("distributor",distributor.getName()+distributor.getCompanyNo());


                String url = linkTo(methodOn(MobileInstanceTransprotController.class).detail(objectEntity.getId(), null, null)).withSelfRel().getHref();
                mappedObject.put("url",url);
                return mappedObject;
            }
        });
        return page;
    }

    @Override
    public Map get(InstanceTransport deliveryOrder) {
        ObjectMapper m = new ObjectMapper();
        Map<String,Object> mappedObject = m.convertValue(deliveryOrder,Map.class);

        return mappedObject;


    }


    @Transactional
    public InstanceTransport outBound(InstanceTransport instanceTransport, BigDecimal grossWeight, BigDecimal netWeight) {


        instanceTransport.setGrossWeight(netWeight);
        instanceTransport.setNetWeight(netWeight);
        instanceTransport.setOutboundTime(new Date());
        instanceTransport.setStatus(InstanceTransportStatusEnum.LEAVE.getText());
        return instanceTransportRepository.save(instanceTransport);



    }
}
