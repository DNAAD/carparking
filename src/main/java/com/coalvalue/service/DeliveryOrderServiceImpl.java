package com.coalvalue.service;


import com.coalvalue.domain.Distributor;
import com.coalvalue.domain.entity.*;
import com.coalvalue.dto.DeliveryOrderDto;
import com.coalvalue.enumType.DeliveryOrderStatusEnum;
import com.coalvalue.enumType.ResourceType;

import com.coalvalue.repository.ReportDeliveryOrderRepository;

import com.coalvalue.repository.specific.DeliveryOrderSpec;
import com.coalvalue.web.MobileDeliveryOrderController;
import com.coalvalue.web.MobileDistributorController;
import com.coalvalue.web.MobileInventoryController;
import com.domain.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.BaseServiceImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

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
    private InstanceTransportService instanceTransportService;






    @Override
    public Map get(ReportDeliveryOrder deliveryOrder) {


        ObjectMapper m = new ObjectMapper();
        Map<String,Object> map = m.convertValue(deliveryOrder,Map.class);


        Inventory inventory = inventoryService.getInventoryByNo(deliveryOrder.getInventoryNo());
        String productUrl =linkTo(methodOn(MobileInventoryController.class).detail(inventory.getId(),null)).withSelfRel().getHref();




        map.put("productUrl", productUrl);

        map.put("id", deliveryOrder.getId()+"");
      //  WxTemporaryQrcode wxeneral = null;//wxService.getByTransportation_Deliver_order(deliveryOrder, Constants.WX_QRCODE_TYPE_transportOperation_DELIVER_ORDER);
        map.put("weixin_qrcode_content", deliveryOrder.getQrcode());

        map.put("no", deliveryOrder.getId());
      //  map.put("driverName", deliveryOrder.getDriverName());
        map.put("accessCode", deliveryOrder.getAccessCode());

     //   map.put("driverPhone", deliveryOrder.getDriverPhone());
        map.put("creationDate", deliveryOrder.getCreateDate());
        map.put("status", deliveryOrder.getStatus());







       // deliveryOrder.setItemType(ResourceType.TRANSPORT_OPERATION.getText());


/*

        Inventory product = inventoryService.getInventoryById(deliveryOrder.getId());
        Company productRcompany = null;
        if(product != null){
            productRcompany = companyService.getCompanyById(product.getCompanyId());
            map.put("productSource", productRcompany.getCompanyName());
            map.put("productName", product.getCoalType() + product.getGranularity());
        }else {
            map.put("productSource", "无关联产品");

        }
*/

        StorageSpace storageSpace  = null;//storageSpaceService.getStorageSpace(operation.getSpaceId());

     //   Company distributorCompany = companyService.getCompanyById(storageSpace.getCompanyId());


      //  map.put("storage",storage_view.view(storageSpace.getId(),null, ViewDetail_SummaryEnum.detail) );

        map.put("createDate", deliveryOrder.getPlateNumber());


        map.put("companyName", deliveryOrder.getCompanyName());


        Distributor distributor = distributorService.getByNo(deliveryOrder.getCompanyNo());
        String distributorUrl =linkTo(methodOn(MobileDistributorController.class).detail(distributor.getId(),null)).withSelfRel().getHref();

        map.put("distributorUrl", distributorUrl);
        map.put("companyNo", deliveryOrder.getCompanyNo());



       // map.put("vehicleType", deliveryOrder.getPlateNumber());

        map.put("plateNumber", deliveryOrder.getPlateNumber());
     //   map.put("netWeight", deliveryOrder.getNetWeight());



        map.put("quotation", inventory.getQuote());
        map.put("inventory", inventory.getQuantityOnHand());
        map.put("advancedPaymentAmount", distributor.getAdvancedPaymentAmount());


        InstanceTransport instanceTransport = instanceTransportService.getBy_InstanceTransport_Id(deliveryOrder.getId());

        if(instanceTransport != null){
            map.put("netWeight", instanceTransport.getTareWeight());
            map.put("grossWeight", instanceTransport.getGrossWeight());
            map.put("tareWeight", instanceTransport.getTareWeight());
            map.put("boundTime", instanceTransport.getBoundTime());
            map.put("outboundTime", instanceTransport.getOutboundTime());
        }

        //map.put("driverName", deliveryOrder.getDriverName());
        //map.put("driverPhone", deliveryOrder.getDriverPhone());

        if(deliveryOrder.getStatus()!= null){
            map.put("status", DeliveryOrderStatusEnum.fromString(deliveryOrder.getStatus()).getDisplayText());

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
    @Transactional
    public ReportDeliveryOrder createDeliveryOrder_(TransportOperation transportOperation) {
        List<ReportDeliveryOrder> reportDeliveryOrders = reportDeliveryOrderRepository.findByTransportOperationIdAndStatus(transportOperation.getId(), DeliveryOrderStatusEnum.Valid.getText());

        if(reportDeliveryOrders.size() == 0){
            ReportDeliveryOrder reportDeliveryOrder = create(transportOperation);


/*        reportDeliveryOrder.setCompanyName(user.getCompany().getCompanyName());
        reportDeliveryOrder.setCompanyNo(user.getCompany().getCompanyNo());*/
/*        reportDeliveryOrder.setConsigneeName(transportOperationCreateForm.getConsigneeName());
        reportDeliveryOrder.setConsigneePhone(transportOperationCreateForm.getConsigneePhone());
        reportDeliveryOrder.setPlateNumbers(transportOperationCreateForm.getPlateNumber());
        reportDeliveryOrder.setQrcodeUrl(wxQrcode.getQrCode());*/


            return reportDeliveryOrder;

        }else{

            ReportDeliveryOrder reportDeliveryOrder = reportDeliveryOrders.get(0);
            reportDeliveryOrder.setStatus(DeliveryOrderStatusEnum.Invalid.getText());
            reportDeliveryOrderRepository.save(reportDeliveryOrder);



            reportDeliveryOrder = create(transportOperation);

            return reportDeliveryOrder;


        }



    }

    @Override
    public ReportDeliveryOrder createDeliveryOrder(ResourceType canvassing, Integer id) {

        List<ReportDeliveryOrder> reportDeliveryOrders = reportDeliveryOrderRepository.findByItemIdAndItemTypeAndStatus(id,canvassing.getText(), DeliveryOrderStatusEnum.Valid.getText());

        if(reportDeliveryOrders.size() == 0){
            ReportDeliveryOrder reportDeliveryOrder =create(ResourceType.CANVASSING,id);

/*        reportDeliveryOrder.setCompanyName(user.getCompany().getCompanyName());
        reportDeliveryOrder.setCompanyNo(user.getCompany().getCompanyNo());*/
/*        reportDeliveryOrder.setConsigneeName(transportOperationCreateForm.getConsigneeName());
        reportDeliveryOrder.setConsigneePhone(transportOperationCreateForm.getConsigneePhone());
        reportDeliveryOrder.setPlateNumbers(transportOperationCreateForm.getPlateNumber());
        reportDeliveryOrder.setQrcodeUrl(wxQrcode.getQrCode());*/


            return reportDeliveryOrder;

        }else{

            ReportDeliveryOrder reportDeliveryOrder = reportDeliveryOrders.get(0);
            reportDeliveryOrder.setStatus(DeliveryOrderStatusEnum.Invalid.getText());
            reportDeliveryOrderRepository.save(reportDeliveryOrder);



            reportDeliveryOrder =  create(ResourceType.CANVASSING,id);

            return reportDeliveryOrder;


        }



    }




    private ReportDeliveryOrder create(TransportOperation transportOperation) {
        ReportDeliveryOrder reportDeliveryOrder = new ReportDeliveryOrder();
        reportDeliveryOrder.setTransportOperationId(transportOperation.getId());

        reportDeliveryOrder.setTransportOperationId(transportOperation.getId());
        reportDeliveryOrder.setTicket(RandomStringUtils.randomAlphanumeric(40));
        reportDeliveryOrder.setStatus(DeliveryOrderStatusEnum.Valid.getText());

        reportDeliveryOrder.setItemId(transportOperation.getId());
        reportDeliveryOrder.setItemType(ResourceType.CANVASSING.getText());

        reportDeliveryOrder.setProductName(" ---product name ");
        transportOperation.setReportDeliveryOrderId(reportDeliveryOrder.getId());

        reportDeliveryOrder.setAccessCode(RandomStringUtils.randomNumeric(6));

        reportDeliveryOrder = reportDeliveryOrderRepository.save(reportDeliveryOrder);
        return reportDeliveryOrder;

    }

    private ReportDeliveryOrder create(ResourceType canvassing, Integer id) {
        ReportDeliveryOrder reportDeliveryOrder = new ReportDeliveryOrder();
        reportDeliveryOrder.setItemId(id);
        reportDeliveryOrder.setItemType(canvassing.getText());

        reportDeliveryOrder.setTicket(RandomStringUtils.randomAlphanumeric(40));
        reportDeliveryOrder.setStatus(DeliveryOrderStatusEnum.Valid.getText());


        reportDeliveryOrder.setProductName(" ---product name ");

        reportDeliveryOrder.setAccessCode(RandomStringUtils.randomNumeric(10));

        reportDeliveryOrder = reportDeliveryOrderRepository.save(reportDeliveryOrder);
        return reportDeliveryOrder;

    }


    @Override
    public ReportDeliveryOrder getValid(ResourceType canvassing, String content) {



        return reportDeliveryOrderRepository.findByItemTypeAndAccessCode(canvassing.getText(),content);
    }

    @Override
    public Page<ReportDeliveryOrder> getValidPage(ResourceType transportOperation, String verificationCode, User user, Pageable pageable) {
        return reportDeliveryOrderRepository.findByItemTypeAndAccessCode(transportOperation.getText(),verificationCode,pageable);

    }

    @Override
    public Page<ReportDeliveryOrder> getValidPageByQrcode(ResourceType transportOperation, String verificationCode, User user, Pageable pageable) {
        return reportDeliveryOrderRepository.findByItemTypeAndQrcodeUrl(transportOperation.getText(),verificationCode,pageable);

    }

    @Override
    public Page<Map> query(String o, Pageable pageable) {


        DeliveryOrderDto deliveryOrderDto = new DeliveryOrderDto();
        deliveryOrderDto.setSearchText(o);
        DeliveryOrderSpec bankAccountSpec =  new DeliveryOrderSpec(deliveryOrderDto);
        Specification<ReportDeliveryOrder> spec = bankAccountSpec.queryMessagesSpec();

        Page<ReportDeliveryOrder>  pages = reportDeliveryOrderRepository.findAll(spec,pageable);
        Page<Map> page = pages.map(new Converter<ReportDeliveryOrder, Map>() {
            public Map convert(ReportDeliveryOrder objectEntity) {

                ObjectMapper m = new ObjectMapper();
                Map<String,Object> mappedObject = m.convertValue(objectEntity,Map.class);


                String companiesUrl = linkTo(methodOn(MobileDeliveryOrderController.class).detail(objectEntity.getId(), null, null)).withSelfRel().getHref();

                mappedObject.put("url",companiesUrl);


                Distributor distributor = distributorService.getByNo(objectEntity.getCompanyNo());
                String distributorUrl = linkTo(methodOn(MobileDistributorController.class).detail(distributor.getId(), null)).withSelfRel().getHref();

                mappedObject.put("distributorUrl",distributorUrl);
                return mappedObject;
            }
        });
        return page;


    }


    @Override
    public Page<Map> query_synthesized(String o, Pageable pageable) {


        DeliveryOrderDto deliveryOrderDto = new DeliveryOrderDto();
        deliveryOrderDto.setSearchText(o);
        deliveryOrderDto.setStatus(DeliveryOrderStatusEnum.Valid.getText());

        DeliveryOrderSpec deliveryOrderSpec =  new DeliveryOrderSpec(deliveryOrderDto);
        Specification<ReportDeliveryOrder> spec = deliveryOrderSpec.querySynthesizedSpec();

        Page<ReportDeliveryOrder>  pages = reportDeliveryOrderRepository.findAll(spec,pageable);
        Page<Map> page = pages.map(new Converter<ReportDeliveryOrder, Map>() {
            public Map convert(ReportDeliveryOrder objectEntity) {

                ObjectMapper m = new ObjectMapper();
                Map<String,Object> mappedObject = m.convertValue(objectEntity,Map.class);


                String companiesUrl = linkTo(methodOn(MobileDeliveryOrderController.class).detail(objectEntity.getId(), null, null)).withSelfRel().getHref();

                mappedObject.put("url",companiesUrl);


                Distributor distributor = distributorService.getByNo(objectEntity.getCompanyNo());
                String distributorUrl = linkTo(methodOn(MobileDistributorController.class).detail(distributor.getId(), null)).withSelfRel().getHref();

                mappedObject.put("distributorUrl",distributorUrl);


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
        return reportDeliveryOrderRepository.findByPlateNumber(idNumber);
    }

    @Override
    public ReportDeliveryOrder findById(Integer deliveryOrderId) {

        return reportDeliveryOrderRepository.findById(deliveryOrderId);
    }

    @Override
    public List<ReportDeliveryOrder> findByValidQrcode(String text) {

        return reportDeliveryOrderRepository.findByQrcodeAndStatus(text,DeliveryOrderStatusEnum.Valid.getText());
    }

    @Override
    public ReportDeliveryOrder findByIdAndStatus(Integer id, String text) {
        return reportDeliveryOrderRepository.findById(id);
    }

    @Override
    public ReportDeliveryOrder getById(Integer index) {
        return reportDeliveryOrderRepository.findById(index);
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
        ReportDeliveryOrder reportDeliveryOrder = reportDeliveryOrderRepository.findById(index);
        ObjectMapper m = new ObjectMapper();
        Map<String,Object> mappedObject = m.convertValue(reportDeliveryOrder,Map.class);

        return mappedObject;


    }
}
