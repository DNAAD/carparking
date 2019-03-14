package com.coalvalue.domain.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Peter Xu on 10/02/2017.
 */
@Entity
@Table(name = "Being_Weight_Object",catalog="storage")
public class BeingWeightObject extends BaseDomain {


    private String status;



    private String distributor;
    private String product;
    private String productName;

    private String inventoryNo;

    private String license;


    public BeingWeightObject() {
    }

    public String getDistributor() {
        return distributor;
    }


    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }


    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
