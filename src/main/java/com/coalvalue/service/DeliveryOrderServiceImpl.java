package com.coalvalue.service;


import com.coalvalue.domain.entity.Distributor;
import com.coalvalue.domain.entity.*;
import com.coalvalue.dto.DeliveryOrderDto;
import com.coalvalue.enumType.DeliveryOrderStatusEnum;
import com.coalvalue.enumType.ResourceType;

import com.coalvalue.repository.InstanceTransportRepository;
import com.coalvalue.repository.ReportDeliveryOrderRepository;

import com.coalvalue.repository.specific.DeliveryOrderSpec;
import com.coalvalue.web.MobileDeliveryOrderController;
import com.coalvalue.web.MobileDistributorController;
import com.coalvalue.web.MobileInventoryController;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by silence yuan on 2015/7/25.
 */

@Service("deliveryOrderService")
public class DeliveryOrderServiceImpl extends BaseServiceImpl implements DeliveryOrderService {


    @Autowired
    private ReportDeliveryOrderRepository reportDeliveryOrderRepository;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private DistributorService distributorService;


    @Autowired
    private InstanceTransportRepository instanceTransportRepository;






    @Override
    public Map get(ReportDeliveryOrder deliveryOrder) {


        ObjectMapper m = new ObjectMapper();
        Map<String,Object> map = m.convertValue(deliveryOrder,Map.class);


        Inventory inventory = inventoryService.getInventoryByNo(deliveryOrder.getInventoryNo());
        String productUrl =linkTo(methodOn(MobileInventoryController.class).detail(inventory.getNo(),null)).withSelfRel().getHref();




        map.put("productUrl", productUrl);

        map.put("id", deliveryOrder.getId()+"");
      //  WxTemporaryQrcode wxeneral = null;//wxService.getByTransportation_Deliver_order(deliveryOrder, Constants.WX_QRCODE_TYPE_transportOperation_DELIVER_ORDER);
        map.put("weixin_qrcode_content", deliveryOrder.getQrcode());

        map.put("no", deliveryOrder.getNo());
      //  map.put("driverName", deliveryOrder.getDriverName());
        map.put("accessCode", deliveryOrder.getAccessCode());

        map.put("createDate", deliveryOrder.getCreateDate());
        map.put("modifyDate", deliveryOrder.getModifyDate());
        map.put("status", deliveryOrder.getStatus());


        map.put("companyName", deliveryOrder.getCompanyName());

        Distributor distributor = distributorService.getByNo(deliveryOrder.getDistributorNo());
        String distributorUrl =linkTo(methodOn(MobileDistributorController.class).detail(distributor.getNo(),null)).withSelfRel().getHref();

        map.put("distributorUrl", distributorUrl);
        map.put("companyNo", deliveryOrder.getProducerNo());



       // map.put("vehicleType", deliveryOrder.getPlateNumber());

        map.put("plateNumber", deliveryOrder.getLicense());
     //   map.put("netWeight", deliveryOrder.getNetWeight());

        map.put("quotation", inventory.getQuote());
        map.put("inventory", inventory.getQuantityOnHand());
        map.put("advancedPaymentAmount", distributor.getAdvancedPaymentAmount());



        InstanceTransport instanceTransport = instanceTransportRepository.findByDeliveryOrderNo(deliveryOrder.getNo());

        if(instanceTransport != null){
            map.put("netWeight", instanceTransport.getTareWeight());
            map.put("grossWeight", instanceTransport.getGrossWeight());
            map.put("tareWeight", instanceTransport.getTareWeight());
            map.put("boundTime", instanceTransport.getBoundTime());
            map.put("outboundTime", instanceTransport.getOutboundTime());
        }

        map.put("consigneeName", deliveryOrder.getConsigneeName());
        map.put("consigneePhone", deliveryOrder.getConsigneePhone());

        map.put("inventoryNo", inventory.getNo());
        map.put("coalType", inventory.getCoalType());
        map.put("granularity", inventory.getGranularity());


        if(deliveryOrder.getStatus()!= null){ map.put("status", DeliveryOrderStatusEnum.fromString(deliveryOrder.getStatus()).getDisplayText());

    }


        //Distributor company = distributorService.getDistributor(operation.getPartnerId());



                map.put("productSourceUrl", "无关联产品");


        //    if(company != null){

              //  map.put("companyLogoUrl", company.getSmallImage());
           //     map.put("companyName", company.getCompanyName());
                String companyUrl ="";  // linkTo(methodOn(MobileCompanyController.class).company(company.getId(),null)).withSelfRel().getHref();
                map.put("companyUrl", companyUrl);
        //    }


            String url = "";  //  linkTo(methodOn(MobileUserTransportOperationController.class).operationDetails(operation.getId(),null,null,null)).withSelfRel().getHref();
            map.put("url",url);

   //     }



        if(deliveryOrder.getStatus()!= null){
            map.put("operationStatus", DeliveryOrderStatusEnum.fromString(deliveryOrder.getStatus()).getDisplayText());
        }
/*
        map.put("boundTime",operation.getBoundTime());
        map.put("netWeight",operation.getNetWeight());
        map.put("tareWeight",operation.getTareWeight());
        map.put("outboundTime",operation.getOutboundTime());*/

        return map;

    }

    @Override
    public ReportDeliveryOrder getDeliveryOrderByTicket(String index) {
        return reportDeliveryOrderRepository.findByTicket(index);
    }


    @Override
    public Page<ReportDeliveryOrder> getValidPage(ResourceType transportOperation, String verificationCode, User user, Pageable pageable) {
        return null;//reportDeliveryOrderRepository.findByItemTypeAndAccessCode(transportOperation.getText(),verificationCode,pageable);

    }

    @Override
    public Page<ReportDeliveryOrder> getValidPageByQrcode(ResourceType transportOperation, String verificationCode, User user, Pageable pageable) {
        return null;//reportDeliveryOrderRepository.findByItemTypeAndQrcodeUrl(transportOperation.getText(),verificationCode,pageable);

    }

    @Override
    public Page<Map> query(String o, Pageable pageable) {
        DeliveryOrderDto deliveryOrderDto = new DeliveryOrderDto();
        deliveryOrderDto.setSearchText(o);
        DeliveryOrderSpec bankAccountSpec =  new DeliveryOrderSpec(deliveryOrderDto);
        Specification<ReportDeliveryOrder> spec = bankAccountSpec.queryMessagesSpec();

        Page<ReportDeliveryOrder>  pages = reportDeliveryOrderRepository.findAll(spec,pageable);
        Page<Map> page = pages.map(new Function<ReportDeliveryOrder, Map>() {
            public Map apply(ReportDeliveryOrder objectEntity) {
                ObjectMapper m = new ObjectMapper();
                Map<String,Object> mappedObject = m.convertValue(objectEntity,Map.class);
                String companiesUrl = linkTo(methodOn(MobileDeliveryOrderController.class).detail(objectEntity.getNo(), null, null)).withSelfRel().getHref();
                mappedObject.put("url",companiesUrl);
                Distributor distributor = distributorService.getByNo(objectEntity.getProducerNo());
                String distributorUrl = linkTo(methodOn(MobileDistributorController.class).detail(distributor.getNo(), null)).withSelfRel().getHref();
                mappedObject.put("distributorUrl",distributorUrl);
                mappedObject.put("createDate",objectEntity.getCreateDate());
                mappedObject.put("status",DeliveryOrderStatusEnum.fromString(objectEntity.getStatus()).getDisplayText());
                return mappedObject;
            }
        });
        return page;
    }


    @Override
    public Page<Map> query_report_delivery(DeliveryOrderDto searchText, Pageable pageable) {



        DeliveryOrderSpec deliveryOrderSpec =  new DeliveryOrderSpec(searchText);
        Specification<ReportDeliveryOrder> spec = deliveryOrderSpec.querySynthesizedSpec();

        Page<ReportDeliveryOrder>  pages = reportDeliveryOrderRepository.findAll(spec,pageable);

        ObjectMapper m = new ObjectMapper();
        m.registerModule(new JavaTimeModule());
/*        m.registerModule(
                new SimpleModule("dateConverter", Version.unknownVersion())
                        .addDeserializer(Date.class, new JsonDeserializer<Date>() { *//* doesn't invoked on jsonpatch request *//* })
                        .addSerializer(Date.class, new JsonSerializer<Date>() { *//* invoked on get request *//* }));
        */
        Page<Map> page = pages.map(new Function<ReportDeliveryOrder, Map>() {
            public Map apply(ReportDeliveryOrder objectEntity) {


                Map<String,Object> mappedObject = m.convertValue(objectEntity,Map.class);

                Inventory inventory = inventoryService.getInventoryByNo(objectEntity.getInventoryNo());

                if(inventory!= null){
                    mappedObject.put("productName",inventory.getCoalType()+"/"+inventory.getGranularity());
                    mappedObject.put("coalType",inventory.getCoalType());
                    mappedObject.put("granularity",inventory.getGranularity());

                }
                String companiesUrl = linkTo(methodOn(MobileDeliveryOrderController.class).detail(objectEntity.getUuid(), null, null)).withSelfRel().getHref();

                mappedObject.put("url",companiesUrl);


                Distributor distributor = distributorService.getByNo(objectEntity.getDistributorNo());
                String distributorUrl = linkTo(methodOn(MobileDistributorController.class).detail(objectEntity.getDistributorNo(), null)).withSelfRel().getHref();

                mappedObject.put("distributorUrl",distributorUrl);
                mappedObject.put("distributorName",distributor.getName());

                mappedObject.put("status",DeliveryOrderStatusEnum.fromString(objectEntity.getStatus()).getDisplayText());


                return mappedObject;
            }
        });
        return page;


    }

    @Override
    public ReportDeliveryOrder findByPlateNumber(String license) {


        return reportDeliveryOrderRepository.findByProductName(license);
    }

    @Override
    public ReportDeliveryOrder findByIdNumber(String idNumber) {
        return reportDeliveryOrderRepository.findByLicense(idNumber);
    }

    @Override
    public ReportDeliveryOrder findById(Integer deliveryOrderId) {

        return reportDeliveryOrderRepository.findById(deliveryOrderId).get();
    }

    @Override
    public List<ReportDeliveryOrder> findByValidQrcode(String text) {

        return reportDeliveryOrderRepository.findByQrcodeAndStatus(text,DeliveryOrderStatusEnum.Valid.getText());
    }

    @Override
    public ReportDeliveryOrder findByIdAndStatus(Integer id, String text) {
        return reportDeliveryOrderRepository.findById(id).get();
    }

    @Override
    public ReportDeliveryOrder getById(Integer index) {
        return reportDeliveryOrderRepository.findById(index).get();
    }

    @Override
    public void generateQrcodeAccessCode(WxTemporaryQrcode wxeneral, ReportDeliveryOrder deliveryOrder) {
        if(deliveryOrder.getQrcodeUrl()==null){
            deliveryOrder.setQrcodeUrl(wxeneral.getContent());
            reportDeliveryOrderRepository.save(deliveryOrder);
        }

    }
    @Transactional
    public void update(ReportDeliveryOrder deliveryOrder) {

            reportDeliveryOrderRepository.save(deliveryOrder);

    }


    @Override
    public Map getMapById(Integer index) {
        ReportDeliveryOrder reportDeliveryOrder = reportDeliveryOrderRepository.findById(index).get();
        ObjectMapper m = new ObjectMapper();
        Map<String,Object> mappedObject = m.convertValue(reportDeliveryOrder,Map.class);

        return mappedObject;


    }

    @Override
    public Page<Map> getDeliveryOrderForDistributor(Distributor distributor, Pageable amount) {

        Page<ReportDeliveryOrder> reportDeliveryOrder = reportDeliveryOrderRepository.findByDistributorNo(distributor.getNo(),amount);

        Page<Map> page = reportDeliveryOrder.map(new Function<ReportDeliveryOrder, Map>() {
            public Map apply(ReportDeliveryOrder objectEntity) {

                ObjectMapper m = new ObjectMapper();
                Map<String,Object> mappedObject = m.convertValue(objectEntity,Map.class);

                Inventory inventory = inventoryService.getInventoryByNo(objectEntity.getInventoryNo());

                if(inventory!= null){
                    mappedObject.put("productName",inventory.getCoalType()+"/"+inventory.getGranularity());

                }else{
                    mappedObject.put("productName","--------------");


                }
                String companiesUrl = linkTo(methodOn(MobileDeliveryOrderController.class).detail(objectEntity.getNo(), null, null)).withSelfRel().getHref();

                mappedObject.put("url",companiesUrl);


                Distributor distributor = distributorService.getByNo(objectEntity.getProducerNo());
                String distributorUrl = linkTo(methodOn(MobileDistributorController.class).detail(distributor.getNo(), null)).withSelfRel().getHref();

                mappedObject.put("distributorUrl",distributorUrl);
                mappedObject.put("createDate",objectEntity.getCreateDate());
                mappedObject.put("status",DeliveryOrderStatusEnum.fromString(objectEntity.getStatus()).getDisplayText());


                return mappedObject;
            }
        });
        return page;
    }

    @Override
    public ReportDeliveryOrder getValidByLicense(String license) {
        List<ReportDeliveryOrder> reportDeliveryOrders =  reportDeliveryOrderRepository.findByLicenseAndStatus(license,DeliveryOrderStatusEnum.Valid.getText());
        if(reportDeliveryOrders.size()!= 0){
            return reportDeliveryOrders.get(0);
        }else{
            return null;
        }

    }

    @Override
    public ReportDeliveryOrder getByNo(String no) {

        return reportDeliveryOrderRepository.findByNo(no);

    }
}
