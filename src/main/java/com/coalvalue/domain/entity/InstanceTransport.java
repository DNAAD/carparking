package com.coalvalue.domain.entity;

import com.coalvalue.domain.BaseDomain;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by yuan zhao  on 08/10/2015.
 */

@Entity

@Table(name = "instance_transport")

public class InstanceTransport extends BaseDomain {



    @Column(name = "transport_id")
    private Integer transportId;

    @Column(name = "manufacturer_id")
    private Integer manufacturerId;
    private BigDecimal grossWeight;
    private Integer inventoryId;

    private Integer deliveryOrderId;


    public Integer getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    @Column(name = "time_interval_id")
    private Integer timeIntervalId;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;
    private Integer queuingId;
    private String status;
    private BigDecimal tareWeight;




    @Column(name = "financial_status")
    private String financialStatus;

    public String getFinancialStatus() {
        return financialStatus;
    }

    public void setFinancialStatus(String financialStatus) {
        this.financialStatus = financialStatus;
    }

    public String getCoalType() {
        return coalType;
    }

    public void setCoalType(String coal_type) {
        this.coalType = coal_type;
    }

    public String getGranularity() {
        return granularity;
    }

    public void setGranularity(String granularity) {
        this.granularity = granularity;
    }

    @Column(name = "coal_type")
    private String coalType;

    @Column(name = "granularity")
    private String granularity;

    @Column(name = "plate_number")
    private String plateNumber;


    @Column(name = "bound_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date boundTime;

    @Column(name = "outbound_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date outboundTime;

    public Date getBoundTime() {
        return boundTime;
    }

    public void setBoundTime(Date boundTime) {
        this.boundTime = boundTime;
    }

    public Date getOutboundTime() {
        return outboundTime;
    }

    public void setOutboundTime(Date outboundTime) {
        this.outboundTime = outboundTime;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Column(name = "net_weight")
    private BigDecimal netWeight;

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

    private String note;
    private String type;
    private Integer companyId;
    private Integer collaboratorId;
    private Integer storageId;

    public Integer getStorageId() {
        return storageId;
    }

    public void setStorageId(Integer storageId) {
        this.storageId = storageId;
    }

    public InstanceTransport() {
    }

    public Integer getTransportId() {
        return transportId;
    }

    public void setTransportId(Integer transportId) {
        this.transportId = transportId;
    }

    public Integer getTimeIntervalId() {
        return timeIntervalId;
    }

    public void setTimeIntervalId(Integer timeIntervalId) {
        this.timeIntervalId = timeIntervalId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }


    public void setCollaboratorId(Integer collaboratorId) {
        this.collaboratorId = collaboratorId;
    }

    public Integer getDistibutorId() {
        return collaboratorId;
    }

    public void setQueuingId(Integer queuingId) {
        this.queuingId = queuingId;
    }

    public Integer getQueuingId() {
        return queuingId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setTareWeight(BigDecimal tareWeight) {
        this.tareWeight = tareWeight;
    }

    public BigDecimal getTareWeight() {
        return tareWeight;
    }

    public BigDecimal getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(BigDecimal grossWeight) {
        this.grossWeight = grossWeight;
    }

    public Integer getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }

    public void setDeliveryOrderId(Integer deliveryOrderId) {
        this.deliveryOrderId = deliveryOrderId;
    }

    public Integer getDeliveryOrderId() {
        return deliveryOrderId;
    }
}
