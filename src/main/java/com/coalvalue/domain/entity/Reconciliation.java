package com.coalvalue.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by yuan zhao  on 10/18/2015.
 */
@Entity
@Table(name = "reconciliation",catalog="storage")
public class Reconciliation extends BaseDomain {

    @NotNull
    private String distributorNo;


    @Column(name = "no")
    private String no;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;
    @Column(name = "total_quantity")
    private BigDecimal totalQuantity;

    @Column(name = "total_count")
    private Integer totalCount;
    @Transient
    private List<ReconciliationItem> reconciliationItems;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    @Column(name = "status")
    private String status;
    private Integer distributorId;



    public String getDistributorNo() {
        return distributorNo;
    }

    public void setDistributorNo(String distributorNo) {
        this.distributorNo = distributorNo;
    }

    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime periodBeginDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime periedEndDate;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(BigDecimal totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public Reconciliation() {
    }


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public void setDistributorId(Integer distributorId) {
        this.distributorId = distributorId;
    }

    public Integer getDistributorId() {
        return distributorId;
    }

    public void setPeriodBeginDate(LocalDateTime periodBeginDate) {
        this.periodBeginDate = periodBeginDate;
    }

    public LocalDateTime getPeriodBeginDate() {
        return periodBeginDate;
    }

    public void setPeriedEndDate(LocalDateTime periedEndDate) {
        this.periedEndDate = periedEndDate;
    }

    public LocalDateTime getPeriedEndDate() {
        return periedEndDate;
    }

    public void setReconciliationItems(List<ReconciliationItem> reconciliationItems) {
        this.reconciliationItems = reconciliationItems;
    }

    public List<ReconciliationItem> getReconciliationItems() {
        return reconciliationItems;
    }
}
