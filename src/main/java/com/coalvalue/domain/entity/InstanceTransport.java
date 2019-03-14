package com.coalvalue.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by yuan zhao  on 08/10/2015.
 */

@Entity

@Table(name = "instance_transport",catalog="storage")

public class InstanceTransport extends BaseDomain {

    @NotNull
    private String distributorNo;

    public String getDistributorNo() {
        return distributorNo;
    }

    public void setDistributorNo(String distributorNo) {
        this.distributorNo = distributorNo;
    }

    @Column(name = "transport_id")
    private Integer transportId;

    @Column(name = "manufacturer_id")
    private Integer manufacturerId;
    private BigDecimal grossWeight;




    @NotNull
    private String inventoryNo;

    @NotNull
    private String deliveryOrderNo;
    @NotNull
    @Column(name = "plate_number")
    private String license;








    public String getInventoryNo() {
        return inventoryNo;
    }

    public void setInventoryNo(String inventoryNo) {
        this.inventoryNo = inventoryNo;
    }

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





    @Column(name = "bound_time")
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime boundTime;

    @Column(name = "outbound_time")
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime outboundTime;

    public LocalDateTime getBoundTime() {
        return boundTime;
    }

    public void setBoundTime(LocalDateTime boundTime) {
        this.boundTime = boundTime;
    }

    public LocalDateTime getOutboundTime() {
        return outboundTime;
    }

    public void setOutboundTime(LocalDateTime outboundTime) {
        this.outboundTime = outboundTime;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
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


    private String storageNo;

    public String getStorageNo() {
        return storageNo;
    }

    public void setStorageNo(String storageNo) {
        this.storageNo = storageNo;
    }

    public InstanceTransport() {
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


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
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



    public String getDeliveryOrderNo() {
        return deliveryOrderNo;
    }

    public void setDeliveryOrderNo(String deliveryOrderNo) {
        this.deliveryOrderNo = deliveryOrderNo;
    }
}
