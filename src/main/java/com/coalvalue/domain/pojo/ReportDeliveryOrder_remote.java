package com.coalvalue.domain.pojo;

import com.coalvalue.domain.entity.BaseDomain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.math.BigDecimal;

/**
 * Created by yuan zhao  on 08/10/2015.
 */


public class ReportDeliveryOrder_remote extends BaseDomain {



    private String distributorNo;


    private String inventoryNo;

    private String producerNo;

    private String storageNo;

    private String orderInventoryNo;
    private String canvassingNo;
    private String productNo;


    public String getProducerNo() {
        return producerNo;
    }

    public void setProducerNo(String producerNo) {
        this.producerNo = producerNo;
    }


    private String itemType;



    private String idNumber;


    private String license;


    private String qrcode;
    private String no;
    private Integer synthesizedId;
    private String weightNoteNo;
    private BigDecimal weightNoteNetWeight;
    private BigDecimal weightNoteTareWeight;





    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }


    private String storageName;


    private String companyName;









    private Integer transportOperationId;
    private String accessCode;








    public String getInventoryNo() {
        return inventoryNo;
    }

    public void setInventoryNo(String inventoryNo) {
        this.inventoryNo = inventoryNo;
    }

    public String getDistributorNo() {
        return distributorNo;
    }

    public void setDistributorNo(String distributorNo) {
        this.distributorNo = distributorNo;
    }

    private String productName;
    private String consignor;
    private String consignorPrincipal;


    private String consigneeName;

    private String consigneePhone;



    private String consigneeNo;




    private String qrcodeUrl;


    private String status;


    private String ticket;

    private Integer count;



    private String operatorPhone;


    private String operatorNo;


    private String operatorName;


    public String getOperatorPhone() {
        return operatorPhone;
    }

    public void setOperatorPhone(String operatorPhone) {
        this.operatorPhone = operatorPhone;
    }

    public String getOperatorNo() {
        return operatorNo;
    }

    public void setOperatorNo(String operatorNo) {
        this.operatorNo = operatorNo;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }


    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getStorageName() {
        return storageName;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }

    public String getStorageNo() {
        return storageNo;
    }

    public void setStorageNo(String storageNo) {
        this.storageNo = storageNo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getConsignor() {
        return consignor;
    }

    public void setConsignor(String consignor) {
        this.consignor = consignor;
    }

    public String getConsignorPrincipal() {
        return consignorPrincipal;
    }

    public void setConsignorPrincipal(String consignorPrincipal) {
        this.consignorPrincipal = consignorPrincipal;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getConsigneePhone() {
        return consigneePhone;
    }

    public void setConsigneePhone(String consigneePhone) {
        this.consigneePhone = consigneePhone;
    }

    public String getConsigneeNo() {
        return consigneeNo;
    }

    public void setConsigneeNo(String consigneeNo) {
        this.consigneeNo = consigneeNo;
    }


    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public void setTransportOperationId(Integer transportOperationId) {
        this.transportOperationId = transportOperationId;
    }

    public Integer getTransportOperationId() {
        return transportOperationId;
    }
    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getNo() {
        return no;
    }


    public void setSynthesizedId(Integer synthesizedId) {
        this.synthesizedId = synthesizedId;
    }

    public Integer getSynthesizedId() {
        return synthesizedId;
    }

    public String getWeightNoteNo() {
        return weightNoteNo;
    }

    public void setWeightNoteNo(String weightNoteNo) {
        this.weightNoteNo = weightNoteNo;
    }

    public void setWeightNoteNetWeight(BigDecimal weightNoteNetWeight) {
        this.weightNoteNetWeight = weightNoteNetWeight;
    }

    public BigDecimal getWeightNoteNetWeight() {
        return weightNoteNetWeight;
    }

    public void setWeightNoteTareWeight(BigDecimal weightNoteTareWeight) {
        this.weightNoteTareWeight = weightNoteTareWeight;
    }

    public BigDecimal getWeightNoteTareWeight() {
        return weightNoteTareWeight;
    }

    public void setOrderInventoryNo(String orderInventoryNo) {
        this.orderInventoryNo = orderInventoryNo;
    }

    public String getOrderInventoryNo() {
        return orderInventoryNo;
    }

    public void setCanvassingNo(String canvassingNo) {
        this.canvassingNo = canvassingNo;
    }

    public String getCanvassingNo() {
        return canvassingNo;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }
}
