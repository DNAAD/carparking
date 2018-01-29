package com.coalvalue.domain.entity;

import com.coalvalue.domain.BaseDomain;
import com.coalvalue.enumType.EventEnum;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by yuan zhao  on 08/10/2015.
 */

@Entity

@Table(name = "transport_operation")

public class TransportOperation extends BaseDomain {




    @Column(name = "space_id")
    private Integer spaceId;

    @Column(name = "shipment_id")
    private Integer shipmentId;

    @Column(name = "type")
    private String type;

    @Column(name = "status")
    private String status;

    @Column(name = "payment_status")
    private String paymentStatus;
    private String no;
    private Integer companyId;
    private String consigneeIdNumber;

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Column(name = "canvassing_id")
    private Integer canvassingId;


    @Column(name = "gross_weight")
    private BigDecimal grossWeight;

    @Column(name = "net_weight")
    private BigDecimal netWeight;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;
    private BigDecimal marketPrice;
    private BigDecimal purchasePrice;
    private Integer productTypeId;
    private Integer contractId;

    @Column(name = "report_delivery_order_id")
    private Integer reportDeliveryOrderId;
    private Integer productNo;

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Column(name = "actual_weight")
    private BigDecimal actualWeight;

    private BigDecimal tareWeight;
    private String driverName;
    private String driverPhone;
    private Integer fromTransport;
    private Integer toTransport;

    public BigDecimal getTareWeight() {
        return tareWeight;
    }

    public void setTareWeight(BigDecimal tareWeight) {
        this.tareWeight = tareWeight;
    }

    @Column(name = "vehicle_type")
    private String vehicleType;

    @Column(name = "plate_number")
    private String plateNumber;

    @Column(name = "cargo_type")
    private String cargoType;

    @Column(name = "bound_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date boundTime;

    @Column(name = "outbound_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date outboundTime;

    private Integer orderId;


    private Integer driverId;
    private Integer truckId;
    private Integer partnerId;
    private String partnerName;


    private Integer productId;
    private String productType;

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

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

    public BigDecimal getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(BigDecimal grossWeight) {
        this.grossWeight = grossWeight;
    }

    public BigDecimal getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(BigDecimal netWeight) {
        this.netWeight = netWeight;
    }

    public BigDecimal getActualWeight() {
        return actualWeight;
    }

    public void setActualWeight(BigDecimal actualWeight) {
        this.actualWeight = actualWeight;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getCargoType() {
        return cargoType;
    }

    public void setCargoType(String cargoType) {
        this.cargoType = cargoType;
    }

    public Integer getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(Integer spaceId) {
        this.spaceId = spaceId;
    }

    public Integer getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Integer shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCanvassingId() {
        return canvassingId;
    }

    public void setCanvassingId(Integer canvassingId) {
        this.canvassingId = canvassingId;
    }

    public TransportOperation() {
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


    public boolean canbeChangeToShippingStatus() {
        return true;
    }




    public Integer getTruckId() {
        return truckId;
    }

    public void setTruckId(Integer truckId) {
        this.truckId = truckId;
    }

    public boolean canBeDeliverShipment() {

        return getOrderId() != null;

    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerName() {
        return partnerName;
    }


    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setFromTransport(Integer fromTransport) {
        this.fromTransport = fromTransport;
    }

    public Integer getFromTransport() {
        return fromTransport;
    }

    public void setToTransport(Integer toTransport) {
        this.toTransport = toTransport;
    }

    public Integer getToTransport() {
        return toTransport;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setProductTypeId(Integer productTypeID) {
        this.productTypeId = productTypeID;
    }

    public Integer getProductTypeId() {
        return productTypeId;
    }

    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    public void setReportDeliveryOrderId(Integer reportDeliveryOrderId) {
        this.reportDeliveryOrderId = reportDeliveryOrderId;
    }

    public Integer getReportDeliveryOrderId() {
        return reportDeliveryOrderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public void setProductNo(Integer productNo) {
        this.productNo = productNo;
    }

    public Integer getProductNo() {
        return productNo;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }


    public void setConsigneeIdNumber(String consigneeIdNumber) {
        this.consigneeIdNumber = consigneeIdNumber;
    }

    public String getConsigneeIdNumber() {
        return consigneeIdNumber;
    }
}
