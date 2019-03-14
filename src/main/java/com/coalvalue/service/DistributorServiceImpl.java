package com.coalvalue.service;


import com.coalvalue.domain.entity.Distributor;
import com.coalvalue.domain.entity.*;
import com.coalvalue.enumType.FinancialConstants;
import com.coalvalue.enumType.SynchronizeEnum;
import com.coalvalue.repository.*;
import com.coalvalue.web.MobileDistributorController;
import com.coalvalue.web.valid.DistributorCreateForm;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

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

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public Distributor getDistributor(String companyNo, String traderName) {

        Distributor distributor = distributorRepository.findByNo(companyNo);
        if(distributor == null){
            distributor = new Distributor();
            distributor.setName(traderName);
            distributor.setName(companyNo);
            distributor = distributorRepository.save(distributor);
        }
        return distributor;
    }

    @Override
    public Page<Map> query(Object o, Pageable pageable) {

        Page<Distributor> distributors = distributorRepository.findAll(pageable);

        Page<Map> page = distributors.map(new Function<Distributor, Map>() {
            public Map apply(Distributor objectEntity) {

                ObjectMapper m = new ObjectMapper();
                Map<String,Object> mappedObject = m.convertValue(objectEntity,Map.class);


                String companiesUrl = linkTo(methodOn(MobileDistributorController.class).detail(objectEntity.getNo(), null)).withSelfRel().getHref();

                mappedObject.put("url",companiesUrl);

                if(objectEntity.getAdvancedPaymentAmount() == null){

                }else{
                    objectEntity.setAdvancedPaymentAmount(new BigDecimal(0));
                    distributorRepository.save(objectEntity);
                }


                return mappedObject;
            }
        });
        return page;


    }

    @Override
    public Distributor getById(Integer index) {


        return distributorRepository.findById(index).get();
    }

    @Override
    public Map get(Distributor deliveryOrder) {
        ObjectMapper m = new ObjectMapper();
        Map<String,Object> mappedObject = m.convertValue(deliveryOrder,Map.class);


        String companiesUrl = linkTo(methodOn(MobileDistributorController.class).detail(deliveryOrder.getNo(), null)).withSelfRel().getHref();

        mappedObject.put("url",companiesUrl);
        return mappedObject;
    }

    @Override
    public Distributor getByNo(String index) {
        return distributorRepository.findByNo(index);
    }

    @Override
    public Page<Map> queryInventoryTransfer(Distributor o, Pageable pageable) {


        Page<InventoryTransfer> pages = inventoryTransferRepository.findByDistributorNo(o.getNo(),pageable);



        Page<Map> page = pages.map(new Function<InventoryTransfer, Map>() {
            public Map apply(InventoryTransfer objectEntity) {

                ObjectMapper m = new ObjectMapper();
                Map<String,Object> mappedObject = m.convertValue(objectEntity,Map.class);



                InstanceTransport instanceTransport = instanceTransportRepository.findByUuid(objectEntity.getInstanceUuid());


                mappedObject.put("plateNumber",instanceTransport.getLicense());


                Distributor distributor = distributorService.getByNo(objectEntity.getDistributorNo());
                String distributorUrl = linkTo(methodOn(MobileDistributorController.class).detail(distributor.getNo(), null)).withSelfRel().getHref();

                mappedObject.put("distributorUrl",distributorUrl);

                mappedObject.put("createDate",objectEntity.getCreateDate());

                return mappedObject;
            }
        });
        return page;
    }

    @Override
    public List<InventoryTransfer> getTransfers(Distributor distributor) {


        return inventoryTransferRepository.findByDistributorNo(distributor.getNo());
    }



    @Override
    @Transactional
    public AdvancedPaymentTransfer createAdvancedPayment(InstanceTransport instanceTransport, BigDecimal amount) {

        Distributor inventory = distributorRepository.findByNo(instanceTransport.getDistributorNo());


        BigDecimal balance = inventory.getAdvancedPaymentAmount().subtract(amount);
        if(balance.floatValue()>0){


            inventory.setAdvancedPaymentAmount(balance);

            advancedPaymentTransfer(instanceTransport, amount);



            AdvancedPaymentTransfer inventoryTransfer = new AdvancedPaymentTransfer();
            inventoryTransfer.setAmount(amount);
            inventoryTransfer.setBalance(balance);

            inventoryTransfer.setDistributorNo(instanceTransport.getDistributorNo());
            inventoryTransfer.setInstanceId(instanceTransport.getId());





            inventoryTransfer.setInventoryId(inventory.getId());



            return advancedPaymentTransferRepository.save(inventoryTransfer);
        }else{
            return null;
        }



    }


    @Override
    @Transactional
    public AdvancedPaymentTransfer addAdvancedPayment(Distributor distributor, BigDecimal amount) {


        if(distributor.getAdvancedPaymentAmount() == null){
            distributor.setAdvancedPaymentAmount(new BigDecimal(0));
        }



            distributor.setAdvancedPaymentAmount(distributor.getAdvancedPaymentAmount().add(amount));
        distributor = distributorRepository.save(distributor);



            AdvancedPaymentTransfer inventoryTransfer = new AdvancedPaymentTransfer();
            inventoryTransfer.setAmount(amount);
            inventoryTransfer.setBalance(distributor.getAdvancedPaymentAmount());

            inventoryTransfer.setDistributorNo(distributor.getNo());


            inventoryTransfer.setInventoryId(distributor.getId());



            return advancedPaymentTransferRepository.save(inventoryTransfer);




    }

    @Override
    public Page<Map> queryAdvancedPaymentTransferr(Distributor distributor, Pageable pageable) {

        Page<AdvancedPaymentTransfer> pages = advancedPaymentTransferRepository.findByDistributorNo(distributor.getNo(),pageable);



        Page<Map> page = pages.map(new Function<AdvancedPaymentTransfer, Map>() {
            public Map apply(AdvancedPaymentTransfer objectEntity) {

                ObjectMapper m = new ObjectMapper();
                Map<String,Object> mappedObject = m.convertValue(objectEntity,Map.class);


/*                String companiesUrl = linkTo(methodOn(MobileDistributorController.class).detail(objectEntity.getCollaboratorId(), null, null)).withSelfRel().getHref();

                mappedObject.put("distributorUrl",companiesUrl);*/

                if(objectEntity.getInstanceId()!= null){
                    InstanceTransport instanceTransport = instanceTransportRepository.findById(objectEntity.getInstanceId()).get();
                    mappedObject.put("plateNumber",instanceTransport.getLicense());

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
                mappedObject.put("createDate",objectEntity.getCreateDate());
                return mappedObject;
            }
        });
        return page;
    }


    private void advancedPaymentTransfer(InstanceTransport instanceTransport, BigDecimal grossWeight) {


    }


    @Override
    public List<AdvancedPaymentTransfer> getAdvancedPaymentTransfers(Distributor distributor) {


        return advancedPaymentTransferRepository.findByDistributorNo(distributor.getNo());
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

            distributor.setStatus(distributorCreateForm.getName());
            distributor.setSynchronizedStatus(SynchronizeEnum.SyncPending.getText());


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
        String no = (String)inventory_.get("no");
        Distributor distributor = distributorRepository.findByNo(no);
        if(distributor== null){
            distributor = new Distributor();
            distributor.setNo((String)inventory_.get("no"));
            distributor.setUniqueId( RandomStringUtils.randomAlphanumeric(30).toUpperCase());
            distributor.setAdvancedPaymentAmount(new BigDecimal(0));
            distributor.setSynchronizedStatus(SynchronizeEnum.SyncPending.getText());
            //  distributor.setName((String)inventory_.get("name"));
        }
        distributor.setName((String) inventory_.get("name"));
        return distributorRepository.save(distributor);

    }

    @Override
    public List<Distributor> getAll() {


        return distributorRepository.findAll();
    }

    @Override
    public List<Map> getEnumAll() {
        Page<Distributor> distributors = distributorRepository.findAll(new PageRequest(0,1000));

        Page<Map> page = distributors.map(new Function<Distributor, Map>() {
            public Map apply(Distributor objectEntity) {

                ObjectMapper m = new ObjectMapper();
                Map<String,Object> mappedObject = m.convertValue(objectEntity,Map.class);


                String companiesUrl = linkTo(methodOn(MobileDistributorController.class).detail(objectEntity.getNo(), null)).withSelfRel().getHref();

                mappedObject.put("url",companiesUrl);

                mappedObject.put("name",objectEntity.getName());


                return mappedObject;
            }
        });
        return page.getContent();


    }

    @Override
    public Page<Map> queryEmployee(Distributor distributor, Pageable pageable) {
        Page<Employee> distributors = employeeRepository.findByCompanyNo(distributor.getNo(),pageable);

        Page<Map> page = distributors.map(new Function<Employee, Map>() {
            public Map apply(Employee objectEntity) {
                ObjectMapper m = new ObjectMapper();
                Map<String,Object> mappedObject = m.convertValue(objectEntity,Map.class);
                return mappedObject;
            }
        });
        return page;
    }


}
