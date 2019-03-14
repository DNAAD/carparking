package com.coalvalue.domain.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Created by yuan zhao  on 10/18/2015.
 */
@Entity
@Table(name = "reconciliation_item",catalog="storage")
public class ReconciliationItem extends BaseDomain {


    @Column(name = "no")
    private String no;


    @Column(name = "plate_number")
    private String plateNumber;
    @Column(name = "reconciliation_id")
    private Integer reconciliationId;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;
    @Column(name = "net_weight")
    private BigDecimal netWeight;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "operator")
    private String operator;
    @Column(name = "status")
    private String status;
    private String inventoryNo;
    private String transferNo;
    private Integer transferId;
    private String distributorNo;



    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public Integer getReconciliationId() {
        return reconciliationId;
    }

    public void setReconciliationId(Integer reconciliationId) {
        this.reconciliationId = reconciliationId;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(BigDecimal netWeight) {
        this.netWeight = netWeight;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public ReconciliationItem() {
    }


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public void setInventoryNo(String inventoryNo) {
        this.inventoryNo = inventoryNo;
    }

    public String getInventoryNo() {
        return inventoryNo;
    }

    public void setTransferNo(String transferNo) {
        this.transferNo = transferNo;
    }

    public String getTransferNo() {
        return transferNo;
    }

    public Integer getTransferId() {
        return transferId;
    }

    public void setTransferId(Integer transferId) {
        this.transferId = transferId;
    }

    public void setDistributorNo(String distributorNo) {
        this.distributorNo = distributorNo;
    }

    public String getDistributorNo() {
        return distributorNo;
    }
}
