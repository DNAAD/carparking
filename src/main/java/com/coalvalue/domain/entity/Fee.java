package com.coalvalue.domain.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by yuan zhao  on 08/10/2015.
 */

@Entity

@Table(name = "fee")

public class Fee extends BaseDomain {


    @Column(name = "company_id")
    private Integer companyId;


    @Column(name = "item_id")
    private Integer itemId;


    @NotNull
    @Column(name = "type")
    private String type;

    @Column(name = "status")
    private String status;

    @Column(name = "amount")
    private BigDecimal amount;


    @Column(name = "discount")
    private BigDecimal discount;
    private Integer combinedId;
    private String pricingStrategy;


    @NotNull
    private String distributorNo;

    @NotNull
    private String no;

    public String getDistributorNo() {
        return distributorNo;
    }

    public void setDistributorNo(String distributorNo) {
        this.distributorNo = distributorNo;
    }

    private String tax;

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }



    public Fee() {
    }

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public Integer getCombinedId() {
        return combinedId;
    }

    public void setCombinedId(Integer combinedId) {
        this.combinedId = combinedId;
    }

    public void setPricingStrategy(String pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }

    public String getPricingStrategy() {
        return pricingStrategy;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    ;
}
