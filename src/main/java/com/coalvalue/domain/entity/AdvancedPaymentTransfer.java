package com.coalvalue.domain.entity;

import com.coalvalue.domain.BaseDomain;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Created by yuan zhao  on 08/10/2015.
 */

@Entity

@Table(name = "advanced_payment_transfer")

public class AdvancedPaymentTransfer extends BaseDomain {




    private BigDecimal amount;


    private String type;

    private String debitCreditType;

    public String getDebitCreditType() {
        return debitCreditType;
    }

    public void setDebitCreditType(String debitCreditType) {
        this.debitCreditType = debitCreditType;
    }

    private Integer distributorId;
    private Integer instanceId;

    private Integer inventoryId;
    private BigDecimal balance;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }

    public AdvancedPaymentTransfer() {

    }


    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public void setDistributorId(Integer distributor) {
        this.distributorId = distributor;
    }

    public Integer getDistributorId() {
        return distributorId;
    }

    public void setInstanceId(Integer instanceId) {
        this.instanceId = instanceId;
    }

    public Integer getInstanceId() {
        return instanceId;
    }

    ;
}
