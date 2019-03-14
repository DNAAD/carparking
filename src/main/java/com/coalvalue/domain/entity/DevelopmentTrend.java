package com.coalvalue.domain.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by yuan zhao  on 11/13/2015.
 */
@Entity
@Table(name = "development_trend")
public class DevelopmentTrend extends BaseDomain {


    @NotNull
    @Column(name = "price")
    private BigDecimal price;
    private LocalDateTime changeDate;

    public void setPrice(BigDecimal price) {
        this.price = price;
    }



    @NotNull
    private String priceCategoryUuid;

    public DevelopmentTrend() {
    }

    public BigDecimal getPrice() {
        return price;
    }


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }



    public void setPriceCategoryUuid(String priceCategoryUuid) {
        this.priceCategoryUuid = priceCategoryUuid;
    }

    public String getPriceCategoryUuid() {
        return priceCategoryUuid;
    }

    public void setChangeDate(LocalDateTime changeDate) {
        this.changeDate = changeDate;
    }

    public LocalDateTime getChangeDate() {
        return changeDate;
    }
}
