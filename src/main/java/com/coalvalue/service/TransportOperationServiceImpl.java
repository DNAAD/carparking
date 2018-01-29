package com.coalvalue.service;


import com.coalvalue.configuration.CommonConstant;

import com.coalvalue.configuration.Constants;
import com.coalvalue.domain.Distributor;
import com.coalvalue.domain.OperationResult;

import com.coalvalue.domain.entity.*;

import com.coalvalue.enumType.*;

import com.coalvalue.repository.*;

import com.coalvalue.util.SequenceGenerator;

import com.domain.entity.User;

import com.service.BaseServiceImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by silence yuan on 2015/7/25.
 */
@Service("transportOperationService")
public class TransportOperationServiceImpl extends BaseServiceImpl implements TransportOperationService {



    @Autowired
    private TransportOperationRepository transportOperationRepository;


    @Autowired
    private CompanyService companyService;

    @Autowired
    private SequenceGenerator sequenceGenerator;



    @Autowired
    private ReportDeliveryOrderRepository reportDeliveryOrderRepository;

    @Autowired
    private WxService wxService;

    @Autowired
    private BehaviouralService behaviouralService;


    @Autowired
    private InstanceTransportService instantService;




    @Autowired
    private WxTemporaryQrcodeRepository wxTemporaryQrcodeRepository;

    @Autowired
    private EquipmentService equipmentService;







    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    @Autowired
    private InstanceTransportRepository instanceTransportRepository;






    @Override
    @Transactional

    public TransportOperation createOperation(TransportOperation transportOperation) {
        return transportOperationRepository.save(transportOperation);
    }


    @Override
    public TransportOperation getOperationById(Integer id) {
        return transportOperationRepository.findOne(id);
    }


    @Override
    public InstanceTransport getInstanceOperationById(Integer id) {
        return instanceTransportRepository.findOne(id);
    }




    @Override
    public List<TransportOperation> getOperationsByUserAndStorageSpaceAndStatus(User user, StorageSpace storageSpace, TransportOperationStatusEnum createPendingCanvassing) {
        return transportOperationRepository.findByDriverIdAndSpaceIdAndStatus(user.getId(), storageSpace.getId(),createPendingCanvassing.getText());
    }

    @Override
    public OperationResult rejectOperation(TransportOperation transportOperation, User user) {

        OperationResult operationResult = new OperationResult();
        operationResult.setSuccess(true);
        transportOperation.setStatus(CommonConstant.TRANSPORT_OPERATION_STATUS_REJECT);
        transportOperation = transportOperationRepository.save(transportOperation);

        operationResult.setResultObject(transportOperation);
        return operationResult;
    }

    @Override
    public OperationResult agreetOperation(TransportOperation transportOperation, BigDecimal tareWeight, String ladingBileNo, User user, Boolean sendToCollaborator) {

        OperationResult operationResult = new OperationResult();
        operationResult.setSuccess(true);


/*
        if(sendToCollaborator== null || !sendToCollaborator){
            logger.debug("==========  war in {}",sendToCollaborator);
            transportOperation.setStatus(TransportOperationStatusEnum.Waiting_Print_delivery_order.getText());
            transportOperation.setTareWeight(tareWeight);
            transportOperation = transportOperationRepository.save(transportOperation);
            operationResult.setResultObject(transportOperation);
            return operationResult;
        }
*/



        transportOperation.setStatus(TransportOperationStatusEnum.LOADING.getText());
        transportOperation.setTareWeight(tareWeight);


        transportOperation = transportOperationRepository.save(transportOperation);

        operationResult.setResultObject(transportOperation);
        return operationResult;
    }





    @Override
    @Transactional
    public TransportOperation update(TransportOperation transportOperation) {

        return transportOperationRepository.save(transportOperation);
    }

    @Override
    public OperationResult exitOperation(TransportOperation transportOperation, BigDecimal netWeight, User user) {


        Date now = new Date();
        OperationResult operationResult = new OperationResult();
        operationResult.setSuccess(false);

        if(transportOperation.getNetWeight() == null && netWeight == null){
            operationResult.setErrorMessage("需要矿发 净重数据");
        }

        transportOperation.setStatus(CommonConstant.TRANSPORT_OPERATION_STATUS_LEAVE);
        transportOperation.setNetWeight(netWeight);


    /*    //TODO 这个 表示 是 中介商发往煤矿的。  煤矿做的 操作。
        if(transportOperation.getFromTransport()!= null && transportOperation.getToTransport() ==null){

            //是 链条的 最后一个最终的 一个 transoportOperation 离开

            logger.debug("是 链条的 最后一个最终的 一个 transoportOperation 离开");
            TransportOperation fromTransportOperation = transportOperationRepository.findById(transportOperation.getFromTransport());

            fromTransportOperation.setStatus(TransportOperationStatusEnum.PARTNER_LEAVE.getText());
            fromTransportOperation.setNetWeight(netWeight);

            fromTransportOperation = transportOperationRepository.save(fromTransportOperation);

            transportOperation.setStatus(TransportOperationStatusEnum.LEAVE.getText());

            operationResult.setSuccess(true);
            operationResult.setResultObject(transportOperationRepository.save(transportOperation));
            return operationResult;
        }
*/



        TransportOperation toTransportOperation = null;
        if(transportOperation.getFromTransport() == null && transportOperation.getToTransport() != null){

            logger.debug("///开始的 一个");  // 要，下一个 是 完成，（ leave 状态），这个才能设置为leave，
            toTransportOperation = transportOperationRepository.findById(transportOperation.getToTransport());

            if(toTransportOperation != null && toTransportOperation.getStatus().equals(TransportOperationStatusEnum.LEAVE.getText())){
                transportOperation.setStatus(TransportOperationStatusEnum.LEAVE.getText());
                transportOperation.setNetWeight(toTransportOperation.getNetWeight());

                transportOperation.setOutboundTime(new Date());
                operationResult.setSuccess(true);
                operationResult.setResultObject(transportOperationRepository.save(transportOperation));

                //financeService.makeFinance(EventEnum.TRANSPORT_OPERATION_SET_STATUS_LEAVE_PASSIVE,transportOperation,user,FinanceTransactionEnum.PAYMENT_to_broker);

            }

            if(toTransportOperation != null && !toTransportOperation.getStatus().equals(TransportOperationStatusEnum.LEAVE.getText())){
                transportOperation.setStatus(TransportOperationStatusEnum.LEAVE.getText());
                transportOperation.setNetWeight(netWeight);

                toTransportOperation.setStatus(TransportOperationStatusEnum.LEAVE_CREATE_SHIPMENT.getText());
                toTransportOperation.setNetWeight(netWeight);
                transportOperationRepository.save(transportOperation);



                transportOperation.setOutboundTime(new Date());
                operationResult.setSuccess(true);
                operationResult.setResultObject(transportOperationRepository.save(transportOperation));

           //     callingQueuingService.update(toTransportOperation.getSpaceId(),transportOperation.getProductId(),transportOperation.getId(), EventEnum.TRANSPORT_OPERATION_SET_STATUS_LEAVE_PASSIVE_BY_distributor.getText());
                //financeService.makeFinance(EventEnum.TRANSPORT_OPERATION_SET_STATUS_LEAVE_PASSIVE,transportOperation,user,FinanceTransactionEnum.PAYMENT_to_broker);

            }

        }

/*
        StorageSpace storageSpace = storageSpaceService.getStorageSpace(transportOperation.getSpaceId());
        if(ResourceType.COAL_PRODUCT.getText().equals(transportOperation.getProductType())){
            Product product = productService.getById(transportOperation.getProductId());
            Inventory inventory = inventoryService.getInventory(product,storageSpace);
            if(inventory != null){
                inventoryService.reduceInvetory(inventory,netWeight,user, null);
            }
        }
*/


        Company company = companyService.getCompanyById(transportOperation.getPartnerId());



        return operationResult;
    }









    @Override
    public OperationResult printDeliveryOrder(TransportOperation transportOperation, User user) {


        OperationResult operationResult = new OperationResult();
        operationResult.setSuccess(true);


   //     StorageSpace storageSpace = storageSpaceService.getOriginStorage(transportOperation.getSpaceId());
   //     equipmentService.printer(transportOperation,storageSpace,"textfd反反复复反反复复反反复复反反复复反反复复反反复复反反复复反反复复反反复复吩咐" +
      //          "f");

/*
        transportOperation.setStatus(TransportOperationStatusEnum.LOADING.getText());

        transportOperation = transportOperationRepository.save(transportOperation);
*/

        operationResult.setResultObject(transportOperation);
        return operationResult;

    }

    @Override
    public Page<TransportOperation> getOperationsByStorageSpaceAndStatusPage(StorageSpace storageSpace, Integer granularity, List<TransportOperationStatusEnum> createPending, Pageable pageable) {


        List<String> statues = new ArrayList<String>();
        for(TransportOperationStatusEnum transportOperationStatusEnum:createPending){
            statues.add(transportOperationStatusEnum.getText());
        }

        return transportOperationRepository.findBySpaceIdAndStatusInAndProductIdAndProductType(storageSpace.getId(),statues,granularity, ResourceType.COAL_PROMOTION.getText(),pageable);
    }



    @Override
    @Transactional
    public OperationResult createDeliveryOrder_coal_pit(StorageSpace storageSpace, Object o, User user) {


        TransportOperation transportOperation = new TransportOperation();
     //   transportOperation.setType(TransportOperationTypeEnum.SCATTERED_STORAGE_CREATE_PARTER_NOT_CONTROLE.getText());




        transportOperation.setNo(sequenceGenerator.nextTransportNO());





        transportOperation  = transportOperationRepository.save(transportOperation);



        transportOperation.setBoundTime(new Date());

        transportOperation = transportOperationRepository.save(transportOperation);


        transportOperation.setStatus(TransportOperationStatusEnum.LOADING.getText());
        transportOperation = transportOperationRepository.save(transportOperation);



        OperationResult operationResult = new OperationResult();
        operationResult.setSuccess(true);
        operationResult.setResultObject(transportOperation);
        return operationResult;



    }


    @Override
    public Map<String, Object> queryListByVerificationCodeCoalpit(String verificationCode, User user, Pageable pageable) {

        if(verificationCode == null){
            verificationCode = "11";
        }
        // ShipmentVerification verification = verificationRepository.findByVerifyCode(verificationCode);
        Page<WxTemporaryQrcode> wxTemporaryQrcodes= wxTemporaryQrcodeRepository.findByContentAndAppId(verificationCode, Constants.APP_ID,pageable);

        if(wxTemporaryQrcodes.getTotalElements() != 0){
            HashMap<String,Object> map = new HashMap<>();
            TransportOperation operation = transportOperationRepository.findById(wxTemporaryQrcodes.getContent().get(0).getItemId());
          //  ledService.transport(operation, user.getCompany());
        }

        List<TransportOperation> transportOperations = new ArrayList<>();
        for(WxTemporaryQrcode wxTemporaryQrcode:wxTemporaryQrcodes){


            TransportOperation operation = transportOperationRepository.findById(wxTemporaryQrcode.getItemId());
/*

            if(operation.getToTransport()!= null){
                operation = transportOperationRepository.findById(operation.getToTransport());
            }
*/

            transportOperations.add(operation);
        }
        Map<String,Object> objectMap = new HashMap<>();
        objectMap.put("totalElements",wxTemporaryQrcodes.getTotalElements());
        objectMap.put("totalPages",wxTemporaryQrcodes.getTotalPages());
        objectMap.put("totalElements",wxTemporaryQrcodes.getTotalElements());
        return objectMap;
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
  @Transactional
    public ReportDeliveryOrder createDeliveryOrder(Distributor distributor, Map map) {




      String plateNumber =(String)map.get("plateNumber");
      String traderName =(String)map.get("traderName");
      String idNumber =(String)map.get("idNumber");
      String productCoalType =(String)map.get("productCoalType");
      String productGranularity =(String)map.get("productGranularity");
      String qrcode =(String)map.get("qrcode");
      String companyNo =(String)map.get("companyNo");
      Integer deliveryOrderId =(Integer)map.get("deliveryOrderId");
      String product =(Integer)map.get("product")+"";
      String type =(String)map.get("type");
      String no =(String)map.get("no");
      String inventoryNo =((String)map.get("inventoryNo")).toString();

      System.out.print("兴建提煤单");


        System.out.print("兴建提煤单");
        ReportDeliveryOrder reportDeliveryOrder = new ReportDeliveryOrder();
        reportDeliveryOrder.setPlateNumber(plateNumber);
        reportDeliveryOrder.setIdNumber(idNumber);
        reportDeliveryOrder.setProductName(productCoalType+" "+ productGranularity);
        reportDeliveryOrder.setCompanyName(traderName);
        reportDeliveryOrder.setQrcode(qrcode);
        reportDeliveryOrder.setNo(no);
        reportDeliveryOrder.setInventoryNo(inventoryNo);
        reportDeliveryOrder.setCompanyNo(distributor.getCompanyNo());

      reportDeliveryOrder.setSynthesizedId(deliveryOrderId);


        reportDeliveryOrder.setStatus(DeliveryOrderStatusEnum.Valid.getText());

        reportDeliveryOrder = reportDeliveryOrderRepository.save(reportDeliveryOrder);

      return reportDeliveryOrder;

  }

    private ReportDeliveryOrder create(TransportOperation transportOperation) {
        ReportDeliveryOrder reportDeliveryOrder = new ReportDeliveryOrder();
        reportDeliveryOrder.setTransportOperationId(transportOperation.getId());

        reportDeliveryOrder.setTransportOperationId(transportOperation.getId());
        reportDeliveryOrder.setTicket(RandomStringUtils.randomAlphanumeric(40));
        reportDeliveryOrder.setStatus(DeliveryOrderStatusEnum.Valid.getText());


        reportDeliveryOrder.setProductName(" ---product name ");
        transportOperation.setReportDeliveryOrderId(reportDeliveryOrder.getId());

        reportDeliveryOrder.setAccessCode(RandomStringUtils.randomNumeric(6));
        reportDeliveryOrder.setItemId(transportOperation.getId());
        reportDeliveryOrder.setItemType(ResourceType.TRANSPORT_OPERATION.getText());
        reportDeliveryOrder = reportDeliveryOrder = reportDeliveryOrderRepository.save(reportDeliveryOrder);
        return reportDeliveryOrder;

    }

}

