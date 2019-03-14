package com.coalvalue.domain.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by yuan zhao  on 08/10/2015.
 */

@Entity

@Table(name = "quotation_plan",catalog="storage")

public class QuotationPlan extends BaseDomain {



    @Column(name = "company_id")
    private Integer companyId;


    @Column(name = "status")
    private String status;


    // @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "trigger_date")
    private Date triggerDate;
    private Date beingAdjustedTime;

    public QuotationPlan() {
    }

    @Column(name = "type")
    private String type;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTriggerDate() {
        return triggerDate;
    }

    public void setTriggerDate(Date triggerDate) {
        this.triggerDate = triggerDate;
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

    public void setBeingAdjustedTime(Date beingAdjustedTime) {
        this.beingAdjustedTime = beingAdjustedTime;
    }

    public Date getBeingAdjustedTime() {
        return beingAdjustedTime;
    }

    ;
}
