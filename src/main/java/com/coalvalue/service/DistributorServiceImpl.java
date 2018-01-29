package com.coalvalue.service;


import com.coalvalue.domain.Distributor;
import com.coalvalue.domain.entity.*;
import com.coalvalue.enumType.FinancialConstants;
import com.coalvalue.enumType.SynchronizeEnum;
import com.coalvalue.repository.*;
import com.coalvalue.web.MobileDistributorController;
import com.coalvalue.web.valid.DistributorCreateForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.BaseServiceImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by silence yuan on 2015/7/25.
 */

@Service("distributorService")
public class DistributorServiceImpl extends BaseServiceImpl implements DistributorService {

    @Autowired
    private DistributorRepository distributorRepository;

    @Autowired
    private InventoryTransferRepository inventoryTransferRepository;

    @Autowired
    private DistributorService distributorService;


    @Autowired
    private InstanceTransportRepository instanceTransportRepository;

    @Autowired
    private AdvancedPaymentTransferRepository advancedPaymentTransferRepository;

    @Override
    @Transactional
    public Distributor getDistributor(String companyNo, String traderName) {

        Distributor distributor = distributorRepository.getByCompanyNo(companyNo);
        if(distributor == null){
            distributor = new Distributor();
            distributor.setName(traderName);
            distributor.setCompanyNo(companyNo);
            distributor = distributorRepository.save(distributor);
        }
        return distributor;
    }

    @Override
    public Page<Map> query(Object o, Pageable pageable) {

        Page<Distributor> distributors = distributorRepository.findAll(pageable);

        Page<Map> page = distributors.map(new Converter<Distributor, Map>() {
            public Map convert(Distributor objectEntity) {

                ObjectMapper m = new ObjectMapper();
                Map<String,Object> mappedObject = m.convertValue(objectEntity,Map.class);


                String companiesUrl = linkTo(methodOn(MobileDistributorController.class).detail(objectEntity.getId(), null)).withSelfRel().getHref();

                mappedObject.put("url",companiesUrl);




                return mappedObject;
            }
        });
        return page;


    }

    @Override
    public Distributor getById(Integer index) {


        return distributorRepository.findById(index);
    }

    @Override
    public Map get(Distributor deliveryOrder) {
        ObjectMapper m = new ObjectMapper();
        Map<String,Object> mappedObject = m.convertValue(deliveryOrder,Map.class);


        String companiesUrl = linkTo(methodOn(MobileDistributorController.class).detail(deliveryOrder.getId(), null)).withSelfRel().getHref();

        mappedObject.put("url",companiesUrl);
        return mappedObject;
    }

    @Override
    public Distributor getByNo(String index) {
        return distributorRepository.findByCompanyNo(index);
    }

    @Override
    public Page<Map> queryInventoryTransfer(Distributor o, Pageable pageable) {


        Page<InventoryTransfer> pages = inventoryTransferRepository.findByDistributor(o.getId(),pageable);



        Page<Map> page = pages.map(new Converter<InventoryTransfer, Map>() {
            public Map convert(InventoryTransfer objectEntity) {

                ObjectMapper m = new ObjectMapper();
                Map<String,Object> mappedObject = m.convertValue(objectEntity,Map.class);


/*                String companiesUrl = linkTo(methodOn(MobileDistributorController.class).detail(objectEntity.getCollaboratorId(), null, null)).withSelfRel().getHref();

                mappedObject.put("distributorUrl",companiesUrl);*/


                InstanceTransport instanceTransport = instanceTransportRepository.findById(objectEntity.getInstanceId());


                mappedObject.put("plateNumber",instanceTransport.getPlateNumber());


                Distributor distributor = distributorService.getById(objectEntity.getDistributor());
                String distributorUrl = linkTo(methodOn(MobileDistributorController.class).detail(distributor.getId(), null)).withSelfRel().getHref();

                mappedObject.put("distributorUrl",distributorUrl);



                return mappedObject;
            }
        });
        return page;
    }

    @Override
    public List<InventoryTransfer> getTransfers(Distributor distributor) {


        return inventoryTransferRepository.findByDistributor(distributor.getId());
    }



    @Override
    @Transactional
    public AdvancedPaymentTransfer createAdvancedPayment(InstanceTransport instanceTransport, BigDecimal amount) {

        Distributor inventory = distributorRepository.findById(instanceTransport.getDistibutorId());

        if(inventory.getAdvancedPaymentAmount() == null){

        }else{
            inventory.setAdvancedPaymentAmount(new BigDecimal(0));
        }
        BigDecimal balance = inventory.getAdvancedPaymentAmount().subtract(amount);
        if(balance.floatValue()>0){


            inventory.setAdvancedPaymentAmount(balance);

            advancedPaymentTransfer(instanceTransport, amount);



            AdvancedPaymentTransfer inventoryTransfer = new AdvancedPaymentTransfer();
            inventoryTransfer.setAmount(amount);
            inventoryTransfer.setBalance(balance);

            inventoryTransfer.setDistributorId(instanceTransport.getDistibutorId());
            inventoryTransfer.setInstanceId(instanceTransport.getId());





            inventoryTransfer.setInventoryId(inventory.getId());



            return advancedPaymentTransferRepository.save(inventoryTransfer);
        }else{
            return null;
        }



    }


    @Override
    @Transactional
    public AdvancedPaymentTransfer addAdvancedPayment(Distributor inventory, BigDecimal amount) {


        if(inventory.getAdvancedPaymentAmount() == null){
            inventory.setAdvancedPaymentAmount(new BigDecimal(0));
        }



            inventory.setAdvancedPaymentAmount(inventory.getAdvancedPaymentAmount().add(amount));
        inventory = distributorRepository.save(inventory);



            AdvancedPaymentTransfer inventoryTransfer = new AdvancedPaymentTransfer();
            inventoryTransfer.setAmount(amount);
            inventoryTransfer.setBalance(inventory.getAdvancedPaymentAmount());

            inventoryTransfer.setDistributorId(inventory.getId());


            inventoryTransfer.setInventoryId(inventory.getId());

            return advancedPaymentTransferRepository.save(inventoryTransfer);




    }

    @Override
    public Page<Map> queryAdvancedPaymentTransferr(Distributor distributor, Pageable pageable) {

        Page<AdvancedPaymentTransfer> pages = advancedPaymentTransferRepository.findByDistributorId(distributor.getId(),pageable);



        Page<Map> page = pages.map(new Converter<AdvancedPaymentTransfer, Map>() {
            public Map convert(AdvancedPaymentTransfer objectEntity) {

                ObjectMapper m = new ObjectMapper();
                Map<String,Object> mappedObject = m.convertValue(objectEntity,Map.class);


/*                String companiesUrl = linkTo(methodOn(MobileDistributorController.class).detail(objectEntity.getCollaboratorId(), null, null)).withSelfRel().getHref();

                mappedObject.put("distributorUrl",companiesUrl);*/

                if(objectEntity.getInstanceId()!= null){
                    InstanceTransport instanceTransport = instanceTransportRepository.findById(objectEntity.getInstanceId());
                    mappedObject.put("plateNumber",instanceTransport.getPlateNumber());

                }

                if(objectEntity.getDebitCreditType().equals(FinancialConstants.CREDIT.name())){
                    mappedObject.put("credit",objectEntity.getAmount());
                }
                if(objectEntity.getDebitCreditType().equals(FinancialConstants.DEBIT.name())){
                    mappedObject.put("debit",objectEntity.getAmount());
                }
     /*           if(objectEntity.getDistributor()!= null){
                    Distributor distributor = distributorService.getById(objectEntity.getDistributor());
                    String distributorUrl = linkTo(methodOn(MobileDistributorController.class).detail(distributor.getId(), null, null)).withSelfRel().getHref();

                    mappedObject.put("distributorUrl",distributorUrl);

                    mappedObject.put("distributor",distributor.getName()+distributor.getCompanyNo());
                }
*/
                return mappedObject;
            }
        });
        return page;
    }


    private void advancedPaymentTransfer(InstanceTransport instanceTransport, BigDecimal grossWeight) {


    }


    @Override
    public List<AdvancedPaymentTransfer> getAdvancedPaymentTransfers(Distributor distributor) {


        return advancedPaymentTransferRepository.findByDistributorId(distributor.getId());
    }

    @Override
    public long getDistributorNumber() {
        return distributorRepository.count();
    }

    @Override
    public Distributor create(DistributorCreateForm distributorCreateForm) {

        Distributor distributor = distributorRepository.getByName(distributorCreateForm.getName());
        if(distributor == null){
            distributor = new Distributor();
            distributor.setName(distributorCreateForm.getName());
            distributor.setName(distributorCreateForm.getName());
            distributor.setStatus(distributorCreateForm.getName());
            distributor.setSynchronizedStatus(SynchronizeEnum.Not_Been_synchronized.getText());


            distributor.setUniqueId( RandomStringUtils.randomAlphanumeric(30).toUpperCase());
            distributor.setAdvancedPaymentAmount(new BigDecimal(0));
            //distributor.setCompanyNo(companyNo);
            distributor = distributorRepository.save(distributor);
        }
        return distributor;


    }

    @Override
    public List<Distributor> get() {


        return distributorRepository.findAll();
    }

    @Override
    @Transactional
    public Distributor update(Distributor inventory) {
        return distributorRepository.save(inventory);
    }

    @Override
    @Transactional

    public Distributor createDistributorFromMap(Map inventory_) {

        Distributor distributor = new Distributor();
        distributor.setCompanyNo((String)inventory_.get("no"));
        distributor.setName((String) inventory_.get("name"));
      //  distributor.setName((String)inventory_.get("name"));
        return distributorRepository.save(distributor);
    }


}
