package com.coalvalue.domain.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by yuan zhao  on 08/10/2015.
 */

@Entity

@Table(name = "inventory",catalog="storage")

public class Inventory extends BaseDomain {



    @NotNull
    @Column(name = "no")
    private String no;


    @NotNull
    @Column(name = "granularity")
    private String granularity;

    @NotNull
    @Column(name = "coalType")
    private String coalType;


    private BigDecimal quote;
    @Column(name = "synchronized_status")
    private String synchronizedStatus;
    @Column(name = "synchronized_time")

    private Date lastSyncTimestamp;


    @NotNull
    private String productNo;
    private String storageNo;
    private BigDecimal indicator1;
    private BigDecimal indicator2;
    private BigDecimal indicator3;
    private BigDecimal indicator4;
    private BigDecimal indicator5;


    public BigDecimal getIndicator3() {
        return indicator3;
    }

    public void setIndicator3(BigDecimal indicator3) {
        this.indicator3 = indicator3;
    }

    public BigDecimal getIndicator4() {
        return indicator4;
    }

    public void setIndicator4(BigDecimal indicator4) {
        this.indicator4 = indicator4;
    }

    public BigDecimal getIndicator5() {
        return indicator5;
    }

    public void setIndicator5(BigDecimal indicator5) {
        this.indicator5 = indicator5;
    }

    public String getSynchronizedStatus() {
        return synchronizedStatus;
    }

    public void setSynchronizedStatus(String synchronizedStatus) {
        this.synchronizedStatus = synchronizedStatus;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getGranularity() {
        return granularity;
    }

    public void setGranularity(String granularity) {
        this.granularity = granularity;
    }

    public String getCoalType() {
        return coalType;
    }

    public void setCoalType(String coalType) {
        this.coalType = coalType;
    }




    @Column(name = "quantity_on_hand")
    private BigDecimal quantityOnHand;

    @Column(name = "status")
    private String status;

    public Inventory() {
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

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setStorageNo(String storageNo) {
        this.storageNo = storageNo;
    }

    public String getStorageNo() {
        return storageNo;
    }

    public void setIndicator1(BigDecimal indicator1) {
        this.indicator1 = indicator1;
    }

    public BigDecimal getIndicator1() {
        return indicator1;
    }

    public void setIndicator2(BigDecimal indicator2) {
        this.indicator2 = indicator2;
    }

    public BigDecimal getIndicator2() {
        return indicator2;
    }


    ;
}
