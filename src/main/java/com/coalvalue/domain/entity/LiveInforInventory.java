package com.coalvalue.domain.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by yuan zhao  on 08/10/2015.
 */

@Entity

@Table(name = "live_infor_inventory",catalog="storage")

public class LiveInforInventory extends BaseDomain {




    @Column(name = "storage_no")
    private String storageNo;
    @Column(name = "companyNo")
    private String companyNo;


    @Column(name = "quantity_on_hand")
    private BigDecimal quantityOnHand;
    @Column(name = "quote")
    private BigDecimal quote;




    @Column(name = "loading_count")
    private Integer loadingCount;

    @Column(name = "waiting_count")
    private Integer waitingCount;


    @Column(name = "degree")
    private String degree;

    @Column(name = "average_waiting_time")
    private BigDecimal averageWaitingTime;

    @Column(name = "average_laytime")
    private BigDecimal averageLaytime;

    @Column(name = "inventory_no")
    private String inventoryNo;


    @Column(name = "synchronized_status")
    private String synchronizedStatus;
    @Column(name = "synchronized_time")

    private Date lastSyncTimestamp;



    public String getSynchronizedStatus() {
        return synchronizedStatus;
    }

    public void setSynchronizedStatus(String synchronizedStatus) {
        this.synchronizedStatus = synchronizedStatus;
    }





    @Column(name = "status")
    private String status;

    public LiveInforInventory() {
    }

    public String getInventoryNo() {
        return inventoryNo;
    }

    public void setInventoryNo(String inventoryNo) {
        this.inventoryNo = inventoryNo;
    }

    public String getStorageNo() {
        return storageNo;
    }

    public void setStorageNo(String storageNo) {
        this.storageNo = storageNo;
    }

    public String getCompanyNo() {
        return companyNo;
    }

    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    public Integer getLoadingCount() {
        return loadingCount;
    }

    public void setLoadingCount(Integer loadingCount) {
        this.loadingCount = loadingCount;
    }

    public Integer getWaitingCount() {
        return waitingCount;
    }

    public void setWaitingCount(Integer waitingCount) {
        this.waitingCount = waitingCount;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public BigDecimal getAverageWaitingTime() {
        return averageWaitingTime;
    }

    public void setAverageWaitingTime(BigDecimal averageWaitingTime) {
        this.averageWaitingTime = averageWaitingTime;
    }

    public BigDecimal getAverageLaytime() {
        return averageLaytime;
    }

    public void setAverageLaytime(BigDecimal averageLaytime) {
        this.averageLaytime = averageLaytime;
    }

    public BigDecimal getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(BigDecimal quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
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

    public BigDecimal getQuote() {
        return quote;
    }

    public void setQuote(BigDecimal quote) {
        this.quote = quote;
    }

    public Date getLastSyncTimestamp() {
        return lastSyncTimestamp;
    }

    public void setLastSyncTimestamp(Date lastSyncTimestamp) {
        this.lastSyncTimestamp = lastSyncTimestamp;
    }


    ;
}
