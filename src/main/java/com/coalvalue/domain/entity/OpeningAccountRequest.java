package com.coalvalue.domain.entity;


import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by yuan zhao  on 08/10/2015.
 */

@Entity

@Table(name = "opening_account_request")

public class OpeningAccountRequest extends BaseDomain {

    private String status;


    private String producerNo;

    private String distributorNo;


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProducerNo() {
        return producerNo;
    }

    public void setProducerNo(String producerNo) {
        this.producerNo = producerNo;
    }

    public String getDistributorNo() {
        return distributorNo;
    }

    public void setDistributorNo(String distributorNo) {
        this.distributorNo = distributorNo;
    }

}

