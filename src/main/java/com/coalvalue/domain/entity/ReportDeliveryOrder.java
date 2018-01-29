package com.coalvalue.domain.entity;

import com.coalvalue.domain.BaseDomain;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created by yuan zhao  on 08/10/2015.
 */

@Entity

@Table(name = "Report_Delivery_Order")

public class ReportDeliveryOrder extends BaseDomain {
    @Column(name = "item_type")
    private String itemType;

    @Column(name = "item_id")
    private Integer itemId;

    private String idNumber;


    private String plateNumber;


    private String qrcode;
    private String no;
    private Integer synthesizedId;

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

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    @Column(name = "storage_name")
    private String storageName;
    @Column(name = "storage_no")
    private String storageNo;
    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_no")
    private String companyNo;
    private Integer transportOperationId;
    private String accessCode;

    @Column(name = "inventory_no")
    private String inventoryNo;

    public String getInventoryNo() {
        return inventoryNo;
    }

    public void setInventoryNo(String inventoryNo) {
        this.inventoryNo = inventoryNo;
    }

    public String getCompanyNo() {
        return companyNo;
    }

    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
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


    @Column(name = "consignee_no")
    private String consigneeNo;
    @Column(name = "consignee_id")
    private String consigneeId;



    @Column(name = "qrcode_url")
    private String qrcodeUrl;


    @Column(name = "status")
    private String status;


    private String ticket;

    @Column(name = "count")
    private Integer count;

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

    public String getConsigneeId() {
        return consigneeId;
    }

    public void setConsigneeId(String consigneeId) {
        this.consigneeId = consigneeId;
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

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
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
}
