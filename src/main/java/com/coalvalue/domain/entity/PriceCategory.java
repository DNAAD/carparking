package com.coalvalue.domain.entity;

import com.coalvalue.enumType.PriceCategoryStatusEnum;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by yuan zhao  on 08/10/2015.
 */

@Entity

@Table(name = "price_category",catalog="storage")

public class PriceCategory extends BaseDomain {

    @NotNull
    private String name;
    @NotNull
    private BigDecimal value;

    private String itemType;


    private Boolean major;

    @NotNull
    private String status;

    @NotNull
    private Integer seq;

    private BigDecimal expectedValue;
    @NotNull
    private String productNo;
    @NotNull
    private String objectUuid;

    public Boolean getMajor() {
        return major;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public void setMajor(Boolean major) {
        this.major = major;
    }

    private Date lastChangeTime;

    public PriceCategory() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }


    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }



    public void setLastChangeTime(Date lastChangeTime) {
        this.lastChangeTime = lastChangeTime;
    }

    public Date getLastChangeTime() {
        return lastChangeTime;
    }

    public String getStatus() {
        return status == null? PriceCategoryStatusEnum.CLOSE.getText() : status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getExpectedValue() {
        return expectedValue;
    }

    public void setExpectedValue(BigDecimal expectedValue) {
        this.expectedValue = expectedValue;
    }

    public String getObjectUuid() {
        return objectUuid;
    }

    public void setObjectUuid(String objectUuid) {
        this.objectUuid = objectUuid;
    }
}
