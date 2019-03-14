package com.coalvalue.domain.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Created by yuan zhao  on 08/10/2015.
 */

@Entity

@Table(name = "Report_Delivery_Order",catalog="storage")

public class ReportDeliveryOrder extends BaseDomain {


    private String idNumber;

    @NotNull
    @Column(name = "license")
    private String license;
    @NotNull
    @Column(unique = true, name = "no",nullable = false)
    private String no;
    @NotNull
    private String distributorNo;
    @NotNull
    private String productNo;
    @NotNull
    @Column(name = "storage_no")
    private String storageNo;
    @Column(name = "producer_no")
    private String producerNo;

    @NotNull
    @Column(name = "inventory_no")
    private String inventoryNo;

    @NotNull
    @Column(name = "operator_no")
    private String operatorNo;

    @Column(name = "consignee_no")
    private String consigneeNo;




    private String qrcode;
    private String transportOperationUuid;


    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }


    @Column(name = "storage_name")
    private String storageName;


    @Column(name = "company_name")
    private String companyName;



    private String accessCode;



    @Column(name = "operator_name")
    private String operatorName;

    @Column(name = "operator_phone")
    private String operatorPhone;

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

    public String getOperatorPhone() {
        return operatorPhone;
    }

    public void setOperatorPhone(String operatorPhone) {
        this.operatorPhone = operatorPhone;
    }

    public String getInventoryNo() {
        return inventoryNo;
    }

    public void setInventoryNo(String inventoryNo) {
        this.inventoryNo = inventoryNo;
    }

    public String getProducerNo() {
        return producerNo;
    }

    public void setProducerNo(String producerNo) {
        this.producerNo = producerNo;
    }

    @Column(name = "product_name")
    private String productName;
    @Column(name = "consignor")
    private String consignor;
    @Column(name = "consignor_principal")
    private String consignorPrincipal;



    @Column(name = "consignee_name")
    private String consigneeName;
    @Column(name = "consignee_phone")
    private String consigneePhone;







    @Column(name = "qrcode_url")
    private String qrcodeUrl;


    @Column(name = "status")
    private String status;


    private String ticket;


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


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
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

    public void setLicense(String plateNumber) {
        this.license = plateNumber;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getNo() {
        return no;
    }


    public void setDistributorNo(String distributorNo) {
        this.distributorNo = distributorNo;
    }

    public String getDistributorNo() {
        return distributorNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setTransportOperationUuid(String transportOperationUuid) {

        this.transportOperationUuid = transportOperationUuid;
    }

    public String getTransportOperationUuid() {
        return transportOperationUuid;
    }
}
