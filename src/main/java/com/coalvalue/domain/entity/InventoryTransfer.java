package com.coalvalue.domain.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by yuan zhao  on 08/10/2015.
 */

@Entity

@Table(name = "inventory_transfer",catalog="storage")

public class InventoryTransfer extends BaseDomain {





    private BigDecimal amount;


    private String type;
    private String instanceUuid;


    private BigDecimal balance;
    private String status;



    //@NotNull
    private String reconcileStatus;
    @NotNull
    private BigDecimal weight;
   // @NotNull
    private BigDecimal unitPrice;



    private String tax;


    @NotNull
    private String storageNo;


    @NotNull
    private String producerNo;



    @NotNull
    private String productNo;



    @NotNull
    private String deliveryOrderNo;


    private String no;
    @NotNull
    private String inventoryNo;
    @NotNull
    private String distributorNo;
    private String license;



    @NotNull
    private String weighmanNo;
    @NotNull
    private String weighmanName;
    private String syncStatus;
    private BigDecimal taxAmount;
    private String weighmanPhone;
    private String pricingStrategy;
    private BigDecimal discount;
    private String operatorPhone;
    @NotNull
    private String operatorNo;
    private String operatorName;
    private String consigneeName;
    private String consigneePhone;


    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getOperatorNo() {
        return operatorNo;
    }

    public void setOperatorNo(String operatorNo) {
        this.operatorNo = operatorNo;
    }

    public String getWeighmanNo() {
        return weighmanNo;
    }

    public void setWeighmanNo(String weighmanNo) {
        this.weighmanNo = weighmanNo;
    }

    public String getWeighmanName() {
        return weighmanName;
    }

    public void setWeighmanName(String weighmanName) {
        this.weighmanName = weighmanName;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getReconcileStatus() {
        return reconcileStatus;
    }

    public void setReconcileStatus(String reconcileStatus) {
        this.reconcileStatus = reconcileStatus;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }


    public InventoryTransfer() {
    }


    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }


    public String getInstanceUuid() {
        return instanceUuid;
    }

    public void setInstanceUuid(String instanceUuid) {
        this.instanceUuid = instanceUuid;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setInventoryNo(String inventoryNo) {
        this.inventoryNo = inventoryNo;
    }

    public String getInventoryNo() {
        return inventoryNo;
    }

    public void setDeliveryOrderNo(String deliveryOrderNo) {
        this.deliveryOrderNo = deliveryOrderNo;
    }

    public String getDeliveryOrderNo() {
        return deliveryOrderNo;
    }

    public void setDistributorNo(String distributorNo) {
        this.distributorNo = distributorNo;
    }

    public String getDistributorNo() {
        return distributorNo;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }


    public void setSyncStatus(String syncStatus) {

        this.syncStatus = syncStatus;
    }

    public String getSyncStatus() {
        return syncStatus;
    }


    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setWeighmanPhone(String weighmanPhone) {
        this.weighmanPhone = weighmanPhone;
    }

    public String getWeighmanPhone() {
        return weighmanPhone;
    }

    public void setPricingStrategy(String pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }

    public String getPricingStrategy() {
        return pricingStrategy;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public String getStorageNo() {
        return storageNo;
    }

    public void setStorageNo(String storageNo) {
        this.storageNo = storageNo;
    }

    public void setProducerNo(String producerNo) {
        this.producerNo = producerNo;
    }

    public String getProducerNo() {
        return producerNo;
    }

    public void setOperatorPhone(String operatorPhone) {
        this.operatorPhone = operatorPhone;
    }

    public String getOperatorPhone() {
        return operatorPhone;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneePhone(String consigneePhone) {
        this.consigneePhone = consigneePhone;
    }

    public String getConsigneePhone() {
        return consigneePhone;
    }
}
